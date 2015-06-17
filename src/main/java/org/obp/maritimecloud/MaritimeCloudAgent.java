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

import net.maritimecloud.core.id.MaritimeId;
import net.maritimecloud.net.BroadcastMessage;
import net.maritimecloud.net.EndpointImplementation;
import net.maritimecloud.net.LocalEndpoint;
import net.maritimecloud.net.mms.MmsBroadcastOptions;
import net.maritimecloud.net.mms.MmsClient;
import net.maritimecloud.net.mms.MmsClientConfiguration;
import net.maritimecloud.util.geometry.Position;
import net.maritimecloud.util.geometry.PositionReader;
import org.apache.log4j.Logger;
import org.obp.Configuration;
import org.obp.ObpInstance;
import org.obp.Readouts;
import org.obp.data.Vessel;
import org.obp.data.Route;
import org.obp.remote.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.obp.Readout.SPEED_OVER_GROUND;
import static org.obp.Readout.TRUE_NORTH_COURSE;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

@Service
public class MaritimeCloudAgent {

    private static Logger logger = Logger.getLogger(MaritimeCloudAgent.class);

    public static final int BROADCAST_RADIUS = 50000;
    public static final int OPERATIONS_TIMEOUT = 120;
    public static final int BEACON_PERIOD = 60;

    private ScheduledExecutorService broadcaster = Executors.newSingleThreadScheduledExecutor();
    private MmsClient client;

    private boolean intendedRouteListenerEnabled = true;
    private boolean intendedRouteBroadcastEnabled = true;

    @Autowired
    private Configuration config;

    @Autowired
    private VesselService vesselService;

    private void buildAndConnectClient(PositionReader positionReader) {
        logger.info("init client");
        MmsClientConfiguration conf = MmsClientConfiguration.create(config.getClientUri());
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

    private String dumpConfiguration(MmsClientConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("server: ").append(conf.getHost());
        sb.append("id: ").append(conf.getId()).append("\n");
        sb.append("name: ").append(conf.properties().getName()).append("\n");
        sb.append("description: ").append(conf.properties().getDescription()).append("\n");
        sb.append("organization: ").append(conf.properties().getOrganization()).append("\n");
        sb.append("position: ").append(conf.getPositionReader().getCurrentPosition()).append("\n");
        return sb.toString();
    }

    public void connect(PositionReader positionReader, AtomicReference<Route> intendedRouteRef, ObpInstance obpInstance) {
        if(config.isServiceEnabled()) {
            logger.info("connecting ...");
            buildAndConnectClient(positionReader);
            if(isConnected()) {
                startObpBroadcast(obpInstance);
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

    public <T extends LocalEndpoint> T getNearestEndpoint(Class<T> clazz, int radius) {
        return client.endpointLocate(clazz).withinDistanceOf(radius).findNearest().join();
    }

    public void startObpBroadcast(final ObpInstance obpInstance) {
        if(config.isBroadcastBeaconEnabled()) {
            logger.debug("start OBP beacon");
            broadcaster.scheduleAtFixedRate(() -> {
                Readouts readouts = obpInstance.resolveReadouts(SPEED_OVER_GROUND, TRUE_NORTH_COURSE);
                MovementMsg msg = new MovementMsg();
                msg.setCog(readouts.getDouble(SPEED_OVER_GROUND));
                msg.setSog(readouts.getDouble(TRUE_NORTH_COURSE));

                // TODO: add actual heading here
                msg.setHeading(msg.getCog());
                broadcast(msg, BROADCAST_RADIUS);
            }, BEACON_PERIOD, config.getBroadcastBeaconPeriod(), TimeUnit.SECONDS);
        }
    }

    public void startIntendedRouteBroadcast(final AtomicReference<Route> intendedRouteRef) {
        if(intendedRouteBroadcastEnabled) {
            logger.debug("start intended route broadcast");
            broadcaster.scheduleAtFixedRate(() -> {
                IntendedRouteMsg msg = new IntendedRouteMsg();
                msg.addAllWaypoints(MessageConverters.waypointsToMessages(intendedRouteRef.get().getWaypoints()));
                broadcast(msg, BROADCAST_RADIUS);
            }, BEACON_PERIOD, config.getBroadcastBeaconPeriod(), TimeUnit.SECONDS);
        }
    }

    private void startBroadcastListener() {
        if(config.isBroadcastBeaconListenerEnabled()) {
            logger.debug("add OBP announcement listener");
            client.broadcastSubscribe(MovementMsg.class,
                    (context, broadcast) -> logger.info("OBP beacon message received: " + broadcast));
        }
    }

    private void startIntendedRouteListener() {
        if(intendedRouteListenerEnabled) {
            logger.debug("add intended route broadcast listener");
            client.broadcastSubscribe(IntendedRouteMsg.class, (header, broadcast) -> {
                MaritimeId id = header.getSender();
                Position pos = header.getSenderPosition();
                logger.debug("intended route announcement received from " + id);
                vesselService.put(new Vessel(id.toString(), id.toString(),
                        pos.getLatitude(), pos.getLongitude(),
                        MessageConverters.messagesToWaypoints(broadcast.getWaypoints())));
            });
        }
    }

    public <T extends EndpointImplementation> void registerService(T impl) {
        logger.debug("register service: " + impl.getEndpointName());
        client.endpointRegister(impl);
    }

    public void broadcast(BroadcastMessage message, int radius) {
        try {
            if (client != null) {
                client.broadcast(message, new MmsBroadcastOptions().toArea(radius));
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

            client.shutdown();
            try {
                client.awaitTermination(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("timeout waiting for client to close");
            }

            if(!client.isShutdown()) {
                logger.warn("client is not closed");
            }

            if(!client.isTerminated()) {
                logger.warn("client is not terminated");
            }
        }
    }
}
