package org.obp.maritimecloud;

import org.apache.log4j.Logger;
import org.obp.ObpInstance;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-8
 */

public class ObpBeacon implements Runnable {

    private static final Logger logger = Logger.getLogger(ObpBeacon.class);

    private final MaritimeCloudConnector connector;
    private final ObpBeaconMessage announcement;

    ObpBeacon(ObpInstance obpInstance, MaritimeCloudConnector connector) {
        this.announcement = new ObpBeaconMessage(obpInstance.getName(), obpInstance.getUri(), obpInstance.getUuid());
        this.connector = connector;
    }

    @Override
    public void run() {
        logger.debug("broadcasting obp presence ...");
        connector.broadcast(announcement);
    }
}
