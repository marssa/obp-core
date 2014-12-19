/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.maritimecloud;

import dk.dma.epd.common.prototype.enavcloud.intendedroute.IntendedRouteBroadcast;
import dk.dma.epd.common.prototype.enavcloud.intendedroute.IntendedRouteMessage;
import dk.dma.epd.common.prototype.enavcloud.intendedroute.Waypoint;
import net.maritimecloud.core.id.MaritimeId;
import net.maritimecloud.net.ConnectionFuture;
import net.maritimecloud.net.MaritimeCloudClient;
import net.maritimecloud.net.MaritimeCloudClientConfiguration;
import net.maritimecloud.net.broadcast.*;
import net.maritimecloud.net.service.ServiceEndpoint;
import net.maritimecloud.net.service.ServiceInvocationFuture;
import net.maritimecloud.net.service.invocation.InvocationCallback;
import net.maritimecloud.net.service.registration.ServiceRegistration;
import net.maritimecloud.net.service.spi.ServiceInitiationPoint;
import net.maritimecloud.net.service.spi.ServiceMessage;
import net.maritimecloud.util.function.Consumer;
import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;
import org.apache.log4j.Logger;
import org.obp.Configuration;
import org.obp.data.Body;
import org.obp.data.Route;
import org.obp.remote.RemoteBodiesService;
import org.obp.remote.RemoteObpLocator;
import org.obp.utils.DmaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

@Service
public class MaritimeCloudAgent implements BroadcastListener<IntendedRouteBroadcast> {

    private static Logger logger = Logger.getLogger(MaritimeCloudAgent.class);

    public static final int BROADCAST_RADIUS = 50000;
    public static final int OPERATIONS_TIMEOUT = 120;
    public static final int BEACON_PERIOD = 60;

    private ScheduledExecutorService broadcaster = Executors.newScheduledThreadPool(1);
    private MaritimeCloudClient client;

    private boolean intendedRouteListenerEnabled = true;

    private boolean intendedRouteBroadcastEnabled = true;


    @Autowired
    private Configuration config;

    @Autowired
    private RemoteObpLocator obpLocator;

    @Autowired
    private RemoteBodiesService remoteBodiesService;

    private void buildAndConnectClient(PositionReader positionReader) {
        logger.info("init client");
        MaritimeCloudClientConfiguration conf = MaritimeCloudClientConfiguration.create(config.getClientUri());
        conf.setPositionReader(positionReader);
        conf.setHost(config.getServerUri());
        conf.properties().setName(config.getName());
        conf.properties().setDescription(config.getDescription());
        conf.properties().setOrganization(config.getOrganization());
        logger.info("configuration:\n\n"+dumpConfiguration(conf));
        client = conf.build();

        try {
            client.connection().awaitConnected(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error connecting client", e);
        } finally {
            logger.debug("client connected: " + client.connection().isConnected());
        }
    }

    private String dumpConfiguration(MaritimeCloudClientConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(conf.getId()).append("\n");
        sb.append("name: ").append(conf.properties().getName()).append("\n");
        sb.append("description: ").append(conf.properties().getDescription()).append("\n");
        sb.append("organization: ").append(conf.properties().getOrganization()).append("\n");
        sb.append("position: ").append(conf.getPositionReader().getCurrentPosition()).append("\n");
        return sb.toString();
    }

    public void connect(PositionReader positionReader, AtomicReference<Route> intendedRouteRef) {
        if(config.isServiceEnabled()) {
            logger.info("connecting ...");
            buildAndConnectClient(positionReader);
            if(isConnected()) {
                startObpBroadcast();
                startIntendedRouteBroadcast(intendedRouteRef);
                startBroadcastListener();
                startIntendedRouteListener();

                logger.info("done.");
            } else {
                logger.warn("unable to connect to MaritimeCloud server");
            }
        }
    }

    public boolean isConnected() {
        return client!=null && client.connection()!=null ? client.connection().isConnected() : false;
    }

    public void startObpBroadcast() {
        if(config.isBroadcastBeaconEnabled()) {
            logger.debug("start OBP beacon");
            broadcaster.scheduleAtFixedRate(
                    () -> broadcast(new ObpBroadcast(config.getName()), BROADCAST_RADIUS),
                    BEACON_PERIOD, config.getBroadcastBeaconPeriod(), TimeUnit.SECONDS);
        }
    }

    public void startIntendedRouteBroadcast(final AtomicReference<Route> intendedRouteRef) {
        if(intendedRouteBroadcastEnabled) {
            logger.debug("start intended route broadcast");
            broadcaster.scheduleAtFixedRate(() -> {
                IntendedRouteBroadcast bcast = new IntendedRouteBroadcast();
                IntendedRouteMessage msg = new IntendedRouteMessage();
                msg.setWaypoints(new ArrayList<>(DmaUtil.convertWaypointsToDmaFormat(intendedRouteRef.get().getWaypoints())));
                bcast.setRoute(msg);
                broadcast(bcast, BROADCAST_RADIUS);
            }, BEACON_PERIOD, config.getBroadcastBeaconPeriod(), TimeUnit.SECONDS);
        }
    }

    private void startBroadcastListener() {
        if(config.isBroadcastBeaconListenerEnabled()) {
            logger.debug("add OBP announcement listener");
            client.broadcastListen(ObpBroadcast.class, obpLocator);
        }
    }

    private void startIntendedRouteListener() {
        if(intendedRouteListenerEnabled) {
            logger.debug("add intended route broadcast listener");
            client.broadcastListen(IntendedRouteBroadcast.class, this);
        }
    }

    public <T, S extends ServiceMessage<T>> void registerService(ServiceInitiationPoint<S> sip, InvocationCallback<S,T> callback) {
        logger.debug("register service: "+sip.getName());
        ServiceRegistration sr =client.serviceRegister(sip, callback);
        try {
            sr.awaitRegistered(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            logger.debug("registered.");
        } catch (InterruptedException e) {
            logger.error("error registering service",e);
        }
    }

    public <T, S extends ServiceMessage<T>> T callNearestServiceProvider(ServiceInitiationPoint<S>  sip, S request, int radius) {
        try {
            ConnectionFuture<ServiceEndpoint<S, T>> locator = client.serviceLocate(sip).withinDistanceOf(radius).nearest();
            ServiceEndpoint<S, T> se = locator.get(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            if(se!=null) {
                ServiceInvocationFuture<T> invoke = se.invoke(request);
                return invoke.get(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            }
        } catch (TimeoutException e) {
            // intentionally swallowed
        }
        catch (Exception e) {
            logger.warn("exception calling remote service "+sip.getName(),e);
        }

        return null;
    }

    public void broadcast(BroadcastMessage message, int radius) {
        try {
            if (client != null) {
                BroadcastOptions broadcastOptions = new BroadcastOptions();
                broadcastOptions.setBroadcastRadius(radius);
                broadcastOptions.setReceiverAckEnabled(true);

                BroadcastFuture bf = client.broadcast(message, broadcastOptions);
                bf.onAck(new Consumer<BroadcastMessage.Ack>() {
                    public void accept(BroadcastMessage.Ack t) {
                        logger.debug("received by " + t.getId());
                    }
                });
            }
        } catch (Exception e) {
            logger.warn("MaritimeCloud broadcasting error",e);
        }
    }

    @PreDestroy
    public void disconnect() {
        if(isConnected()) {
            logger.info("disconnecting from MaritimeCloud ...");

            broadcaster.shutdown();
            try {
                broadcaster.awaitTermination(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("broadcaster termination error",e);
            }

            client.close();
            try {
                client.awaitTermination(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("timeout waiting for client to close");
            }

            if(!client.isClosed()) {
                logger.warn("client is not closed");
            }

            if(!client.isTerminated()) {
                logger.warn("client is not terminated");
            }
        }
    }

    @Override
    public void onMessage(BroadcastMessageHeader broadcastMessageHeader, IntendedRouteBroadcast intendedRouteBroadcast) {
        MaritimeId id = broadcastMessageHeader.getId();
        PositionTime pt = broadcastMessageHeader.getPosition();
        logger.debug("intended route announcement received from "+id+":\n"+intendedRouteBroadcast.getRoute());
        IntendedRouteMessage msg = intendedRouteBroadcast.getRoute();
        remoteBodiesService.put(new Body(id.toString(), id.toString(), pt.getLatitude(), pt.getLongitude(),msg.getWaypoints()));
    }
}
