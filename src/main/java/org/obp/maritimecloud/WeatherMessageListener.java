package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastListener;
import net.maritimecloud.net.broadcast.BroadcastMessageHeader;
import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */
class WeatherMessageListener implements BroadcastListener<WeatherMessage> {

    private static Logger logger = Logger.getLogger(WeatherMessageListener.class);

    @Override
    public void onMessage(BroadcastMessageHeader broadcastMessageHeader, WeatherMessage weatherMessage) {
        logger.debug("received message: "+weatherMessage);
    }
}
