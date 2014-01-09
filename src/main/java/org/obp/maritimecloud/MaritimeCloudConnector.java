package org.obp.maritimecloud;

import net.maritimecloud.net.MaritimeCloudClient;
import net.maritimecloud.net.MaritimeCloudClientConfiguration;
import net.maritimecloud.net.broadcast.BroadcastFuture;
import net.maritimecloud.net.broadcast.BroadcastListener;
import net.maritimecloud.net.broadcast.BroadcastMessage;
import net.maritimecloud.net.broadcast.BroadcastOptions;
import net.maritimecloud.util.function.Consumer;
import net.maritimecloud.util.function.Supplier;
import net.maritimecloud.util.geometry.PositionTime;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */

public class MaritimeCloudConnector {

    private static Logger logger = Logger.getLogger(MaritimeCloudConnector.class);

    private static final int BROADCAST_RADIUS_KM = 5000;

    private MaritimeCloudClientConfiguration clientConfiguration;
    private MaritimeCloudClient client;
    private BroadcastOptions broadcastOptions;

    public MaritimeCloudConnector(String serverUri, String clientId, Supplier<PositionTime> positionSupplier) {
        clientConfiguration = MaritimeCloudClientConfiguration.create(clientId);
        clientConfiguration.setPositionSupplier(positionSupplier);
        clientConfiguration.setHost(serverUri);

        broadcastOptions = new BroadcastOptions();
        broadcastOptions.setBroadcastRadius(BROADCAST_RADIUS_KM);
        broadcastOptions.setReceiverAckEnabled(true);
    }

    public void init() {
        client = clientConfiguration.build();
    }

    public void shutdown() {
        try {
            if(client != null) {
                client.close();
                client.awaitTermination(10, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("exception shutting down MaritimeCloud connector",e);
        }
    }

    public <T extends BroadcastMessage> void addBroadcastListener(Class<T> msgType, BroadcastListener<T> listener) {
        if (client != null && listener!=null && msgType!=null) {
                client.broadcastListen(msgType,listener);
        }
    }

    public void broadcast(BroadcastMessage message) {
        try {
            if (client != null) {
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
}
