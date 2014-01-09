package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastMessage;

import java.net.URI;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-8
 */
public class ObpBeaconMessage extends BroadcastMessage {

    private URI uri;
    private UUID uuid;
    private String name;

    ObpBeaconMessage() {
    }

    public ObpBeaconMessage(String name, URI uri, UUID uuid) {
        this.name = name;
        this.uri = uri;
        this.uuid = uuid;
    }

    public URI getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return name+" "+uri+" UUID: "+uuid;
    }
}
