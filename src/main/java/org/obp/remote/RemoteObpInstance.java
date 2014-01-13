package org.obp.remote;

import org.obp.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-17
 */
public class RemoteObpInstance implements ObpInstance {

    private UUID uuid;
    private URI uri;
    private String name;

    public RemoteObpInstance(URI uri) {
        this.uuid = UUID.randomUUID();
        this.name = "manually defined at "+uri;
        this.uri = uri;
    }

    public RemoteObpInstance(UUID uuid, URI uri, String name) {
        this.uuid = uuid;
        this.uri = uri;
        this.name = name;
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Body> getBodies() {
        return null;
    }

    @Override
    public Attributes resolveAttributes(String... keys) {
        return null;
    }

    @Override
    public Attributes getAttributes() {
        return null;
    }

    @Override
    public AttributeInfo getAttributeInfo(String key) {
        return null;
    }

    @Override
    public List<AttributeInfo> getAttributeInfos() {
        return null;
    }

    @Override
    public void attachInstrument(Instrument instrument) {
    }

    @Override
    public void detachInstrument(Instrument instrument) {
    }

    @Override
    public void attachExplorer(Explorer explorer) {
    }

    @Override
    public void detachExplorer(Explorer explorer) {
    }

    @Override
    public boolean isHub() {
        return false;
    }

    @Override
    public int knownRemotes() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
