package org.obp.maritimecloud;

import net.maritimecloud.net.MaritimeCloudClient;
import net.maritimecloud.net.MaritimeCloudClientConfiguration;
import net.maritimecloud.net.broadcast.*;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

@Service
public class MaritimeCloudService {

    private static Logger logger = Logger.getLogger(MaritimeCloudService.class);
    private static final int BROADCAST_RADIUS = 50000;

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
        logger.info("init MaritimeCloud client");

        MaritimeCloudClientConfiguration conf = MaritimeCloudClientConfiguration.create(clientUri);
        conf.setPositionReader(new ObpPositionReader(localObpInstance));
        conf.setHost(serverUri);
        conf.properties().setName(localObpInstance.getName());
        conf.properties().setDescription(localObpInstance.getDescription());
        conf.properties().setOrganization(localObpInstance.getOrganization());
        client = conf.build();
    }

    @PostConstruct
    public void init() throws URISyntaxException {
        if(!serviceEnabled) {
            return;
        }

        initMaritimeCloudClient();

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
            }, 20, broadcastBeaconPeriod, TimeUnit.SECONDS);
        }

        if(broadcastBeaconListenerEnabled) {
            logger.debug("add OBP announcement listener");
            client.broadcastListen(ObpBeaconMessage.class, obpLocator);
        }

        logger.info("done.");
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
        client.close();
        try {
            client.awaitTermination(10, TimeUnit.SECONDS);
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
