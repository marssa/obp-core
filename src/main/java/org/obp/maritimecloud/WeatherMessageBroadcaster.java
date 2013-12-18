package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastOptions;
import org.apache.log4j.Logger;
import org.obp.ObpInstance;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */
class WeatherMessageBroadcaster implements Runnable {

    private static Logger logger = Logger.getLogger(WeatherMessageBroadcaster.class);

    private ObpInstance obpInstance;
    private MaritimeCloudConnector connector;
    private BroadcastOptions options = new BroadcastOptions();

    WeatherMessageBroadcaster(ObpInstance obpInstance, MaritimeCloudConnector connector) {
        this.obpInstance = obpInstance;
        this.connector = connector;
    }

    @Override
    public void run() {
        logger.debug("broadcasting weather data ...");
        connector.broadcast(new WeatherMessage(obpInstance));
    }
}
