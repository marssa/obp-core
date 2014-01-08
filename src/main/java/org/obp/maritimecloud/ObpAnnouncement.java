package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastMessage;

import java.net.URI;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-8
 */
public class ObpAnnouncement extends BroadcastMessage {

    private URI uri;

    public ObpAnnouncement(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
