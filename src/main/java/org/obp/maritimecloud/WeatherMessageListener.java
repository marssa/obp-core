package org.obp.maritimecloud;

import dk.dma.enav.maritimecloud.broadcast.BroadcastListener;
import dk.dma.enav.maritimecloud.broadcast.BroadcastMessageHeader;
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
