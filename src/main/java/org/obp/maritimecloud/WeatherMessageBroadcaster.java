package org.obp.maritimecloud;

import dk.dma.enav.maritimecloud.MaritimeCloudClient;
import dk.dma.enav.maritimecloud.broadcast.BroadcastFuture;
import dk.dma.enav.maritimecloud.broadcast.BroadcastMessage;
import dk.dma.enav.maritimecloud.broadcast.BroadcastOptions;
import dk.dma.enav.util.function.Consumer;
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

        MaritimeCloudClient client = connector.getClient();
        if (client != null) {
            options.setBroadcastRadius(5000);
            options.setReceiverAckEnabled(true);
            WeatherMessage wm = new WeatherMessage(obpInstance);
            BroadcastFuture f = client.broadcast(wm, options);
            f.onAck(new Consumer<BroadcastMessage.Ack>() {
                public void accept(BroadcastMessage.Ack t) {
                    logger.debug("received by " + t.getId());
                }
            });
        }

        //connector.broadcast(new WeatherMessage(obpInstance));
    }
}
