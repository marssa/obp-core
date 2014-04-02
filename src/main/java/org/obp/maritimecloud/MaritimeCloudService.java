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
import net.maritimecloud.net.broadcast.*;
import net.maritimecloud.net.service.ServiceEndpoint;
import net.maritimecloud.net.service.registration.ServiceRegistration;
import net.maritimecloud.util.function.Consumer;
import org.apache.log4j.Logger;
import org.obp.local.LocalObpInstance;
import org.obp.remote.RemoteObpLocator;
import org.obp.web.config.ObpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URISyntaxException;
import java.util.concurrent.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

@Service
public class MaritimeCloudService {

    private static Logger logger = Logger.getLogger(MaritimeCloudService.class);

    public static final int BROADCAST_RADIUS = 50000;
    public static final int OPERATIONS_TIMEOUT = 30;
    public static final int BEACON_PERIOD = 20;

    private ScheduledExecutorService broadcaster = Executors.newScheduledThreadPool(1);
    private MaritimeCloudClient client;

    @Autowired
    private LocalObpInstance localObpInstance;

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

    @Autowired
    private ObpConfig config;

    private void initMaritimeCloudClient() throws URISyntaxException {
        logger.info("init client");
        MaritimeCloudClientConfiguration conf = MaritimeCloudClientConfiguration.create(clientUri);
        conf.setPositionReader(new ObpPositionReader(localObpInstance));
        conf.setHost(serverUri);
        conf.properties().setName(localObpInstance.getName());
        conf.properties().setDescription(localObpInstance.getDescription());
        conf.properties().setOrganization(localObpInstance.getOrganization());
        client = conf.build();

        try {
            client.connection().awaitConnected(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error connecting client", e);
        } finally {
            logger.debug("client connected: " + client.connection().isConnected());
        }
    }

    @PostConstruct
    public void init() throws URISyntaxException {
        if(serviceEnabled) {
            logger.info("init MC services...");
            initMaritimeCloudClient();
            startBroadcastBeacon();
            startBroadcastListener();
            registerServices();
            logger.info("done.");
        }
    }

    private void startBroadcastBeacon() {
        if(broadcastBeaconEnabled) {
            logger.debug("start OBP beacon");
            broadcaster.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    broadcast(new ObpBeaconMessage(
                            localObpInstance.getName(),
                            localObpInstance.getUri(),
                            localObpInstance.getUuid()), BROADCAST_RADIUS);
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

    private void waitForRegistration(ServiceRegistration sr) {
        try {
            sr.awaitRegistered(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error registering service",e);
        }
    }

    private void registerServices() {
        logger.debug("register weather service");
        waitForRegistration(client.serviceRegister(WeatherService.SIP, WeatherService.callback(localObpInstance)));
    }

    public WeatherService.Response callWeatherService(int radius) throws InterruptedException, ExecutionException, TimeoutException {
        ConnectionFuture<ServiceEndpoint<WeatherService.Request, WeatherService.Response>> locator =
                client.serviceLocate(WeatherService.SIP).withinDistanceOf(radius).nearest();

        ServiceEndpoint<WeatherService.Request, WeatherService.Response> se = locator.get(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
        ConnectionFuture<WeatherService.Response> invoke = se.invoke(new WeatherService.Request());
        return invoke.get(OPERATIONS_TIMEOUT, TimeUnit.SECONDS);
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
    public void shutdown() {
        logger.info("shutting down connector ...");

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
