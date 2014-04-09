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

import net.maritimecloud.net.ConnectionFuture;
import net.maritimecloud.net.MaritimeCloudClient;
import net.maritimecloud.net.MaritimeCloudClientConfiguration;
import net.maritimecloud.net.broadcast.BroadcastFuture;
import net.maritimecloud.net.broadcast.BroadcastMessage;
import net.maritimecloud.net.broadcast.BroadcastOptions;
import net.maritimecloud.net.service.ServiceEndpoint;
import net.maritimecloud.net.service.ServiceInvocationFuture;
import net.maritimecloud.net.service.invocation.InvocationCallback;
import net.maritimecloud.net.service.registration.ServiceRegistration;
import net.maritimecloud.net.service.spi.ServiceInitiationPoint;
import net.maritimecloud.net.service.spi.ServiceMessage;
import net.maritimecloud.util.function.Consumer;
import net.maritimecloud.util.geometry.PositionReader;
import org.apache.log4j.Logger;
import org.obp.remote.RemoteObpLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

@Service
public class MaritimeCloudAgent {

    private static Logger logger = Logger.getLogger(MaritimeCloudAgent.class);

    public static final int BROADCAST_RADIUS = 50000;
    public static final int OPERATIONS_TIMEOUT = 60;
    public static final int BEACON_PERIOD = 60;

    private ScheduledExecutorService broadcaster = Executors.newScheduledThreadPool(1);
    private MaritimeCloudClient client;

    @Value("${obp.local.name}")
    private String name;

    @Value("${obp.local.description}")
    private String description;

    @Value("${obp.local.organization}")
    private String organization;

    @Value("${obp.maritimecloud.client.uri}")
    private String clientUri;

    @Value("${obp.maritimecloud.server.uri}")
    private String serverUri;

    @Value("${obp.maritimecloud.service.enabled}")
    private boolean serviceEnabled;

    @Value("${obp.maritimecloud.broadcast.beacon.enabled}")
    private boolean broadcastBeaconEnabled;

    @Value("${obp.maritimecloud.broadcast.beacon.period}")
    private int broadcastBeaconPeriod;

    @Value("${obp.maritimecloud.broadcast.beacon.listener.enabled}")
    private boolean broadcastBeaconListenerEnabled;

    @Autowired
    private RemoteObpLocator obpLocator;

    private void buildAndConnectClient(PositionReader positionReader) {
        logger.info("init client");
        MaritimeCloudClientConfiguration conf = MaritimeCloudClientConfiguration.create(clientUri);
        conf.setPositionReader(positionReader);
        conf.setHost(serverUri);
        conf.properties().setName(name);
        conf.properties().setDescription(description);
        conf.properties().setOrganization(organization);
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

    public void connect(PositionReader positionReader) {
        if(serviceEnabled) {
            logger.info("connecting ...");
            buildAndConnectClient(positionReader);
            if(isConnected()) {
                startObpBeacon();
                startBroadcastListener();
                logger.info("done.");
            } else {
                logger.warn("unable to connect to MaritimeCloud server");
            }
        }
    }

    public boolean isConnected() {
        return client!=null && client.connection()!=null ? client.connection().isConnected() : false;
    }

    public void startObpBeacon() {
        if(broadcastBeaconEnabled) {
            logger.debug("start OBP beacon");
            broadcaster.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    broadcast(new ObpBeaconMessage(name), BROADCAST_RADIUS);
                }
            }, BEACON_PERIOD, broadcastBeaconPeriod, TimeUnit.SECONDS);
        }
    }

    private void startBroadcastListener() {
        if(broadcastBeaconListenerEnabled) {
            logger.debug("add OBP announcement listener");
            client.broadcastListen(ObpBeaconMessage.class, obpLocator);
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
}
