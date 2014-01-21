package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastListener;
import net.maritimecloud.net.broadcast.BroadcastMessageHeader;
import net.maritimecloud.util.geometry.PositionReader;
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

    private PositionReader positionReader;
    private MaritimeCloudConnector cloudConnector;
    private ScheduledExecutorService broadcaster = Executors.newScheduledThreadPool(1);

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

    private MaritimeCloudConnector createCloudConnector() throws URISyntaxException {
        return new MaritimeCloudConnector(serverUri,clientUri,positionReader);
    }

    @PostConstruct
    public void init() throws URISyntaxException {
        if(!serviceEnabled) {
            logger.info("service disabled");
            return;
        }

        logger.info("init connector (" + serverUri + ") ...");

        positionReader = new ObpPositionReader(localObpInstance);
        cloudConnector = createCloudConnector();

        if(broadcastBeaconEnabled) {
            logger.debug("start OBP beacon");
            broadcaster.scheduleAtFixedRate(
                    new ObpBeacon(localObpInstance, cloudConnector),
                    20, broadcastBeaconPeriod, TimeUnit.SECONDS);
        }

        cloudConnector.init();
        if(broadcastBeaconListenerEnabled) {
            logger.debug("add OBP announcement listener");
            cloudConnector.addBroadcastListener(ObpBeaconMessage.class, obpLocator);
        }

        logger.info("done.");
    }

    @PreDestroy
    public void shutdown() {
        logger.info("shutting down connector ...");
        broadcaster.shutdown();
        cloudConnector.shutdown();
        logger.info("done.");
    }
}
