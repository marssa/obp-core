package org.obp.maritimecloud;

import org.apache.log4j.Logger;

import java.net.URI;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-8
 */

public class ObpBeacon implements Runnable {

    private static final Logger logger = Logger.getLogger(ObpBeacon.class);

    private final MaritimeCloudConnector connector;
    private final ObpAnnouncement announcement;

    ObpBeacon(URI uri, MaritimeCloudConnector connector) {
        this.announcement = new ObpAnnouncement(uri);
        this.connector = connector;
    }

    @Override
    public void run() {
        logger.debug("broadcasting obp presence ...");
        connector.broadcast(announcement);
    }
}
