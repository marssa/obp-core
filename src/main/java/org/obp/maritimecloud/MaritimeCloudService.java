package org.obp.maritimecloud;

import org.apache.log4j.Logger;
import org.obp.local.LocalObpInstance;
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

    private PositionSupplier positionSupplier;
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

    @Value("${obp.maritimecloud.broadcast.weather.enabled}")
    private boolean broadcastWeatherEnabled;

    @Value("${obp.maritimecloud.broadcast.weather.period}")
    private long broadcastWeatherPeriod;

    @Value("${obp.maritimecloud.broadcast.weather.listener.enabled}")
    private boolean broadcastWeatherListenerEnabled;

    private MaritimeCloudConnector createCloudConnector() throws URISyntaxException {
        return new MaritimeCloudConnector(serverUri,clientUri,positionSupplier);
    }

    @PostConstruct
    public void init() throws URISyntaxException {
        if(!serviceEnabled) {
            logger.info("service disabled");
            return;
        }

        logger.info("init connector ("+serverUri+") ...");

        positionSupplier = new PositionSupplier(localObpInstance);
        cloudConnector = createCloudConnector();

        if(broadcastWeatherEnabled) {
            logger.debug("enable weather broadcaster");
            broadcaster.scheduleAtFixedRate(new WeatherMessageBroadcaster(localObpInstance, cloudConnector),
                    broadcastWeatherPeriod, broadcastWeatherPeriod, TimeUnit.SECONDS);
        }

        cloudConnector.init();
        if(broadcastWeatherListenerEnabled) {
            logger.debug("add weather broadcast listener");
            cloudConnector.addBroadcastListener(WeatherMessage.class, new WeatherMessageListener());
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
