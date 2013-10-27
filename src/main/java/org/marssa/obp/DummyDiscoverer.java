package org.marssa.obp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Component
public class DummyDiscoverer extends Explorer {
    private static Logger logger = Logger.getLogger(DummyDiscoverer.class);

    @Autowired
    public DummyDiscoverer(Realm realm) {
        super(UUID.randomUUID(), "Brave Radar", "dummy radar as external bodies explorer", realm);
    }

    private Body createDummyBody(String name, String description) {
        return new Body(UUID.randomUUID(), name, description,false);
    }

    @PostConstruct
    protected void init() {
        logger.info("introspection started ...");
        realm.addBody(createDummyBody("Pinta","dummy caravel-type vessel"));
        realm.addBody(createDummyBody("Nina","dummy caravel-type vessel"));
        realm.addBody(createDummyBody("Santa Maria","dummy caravel-type vessel"));
        logger.info("introspection finished.");
    }
}
