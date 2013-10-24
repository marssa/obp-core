package org.marssa.data;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Service
public class Vicinity {
    private static Logger logger = Logger.getLogger(Vicinity.class);

    private ConcurrentMap<UUID, Body> bodies = new ConcurrentHashMap<>();

    public Collection<Body> getBodies() {
        return Collections.unmodifiableCollection(bodies.values());
    }

    public Body getBody(UUID uuid) {
        return bodies.get(uuid);
    }

    public void addBody(Body body) {
        bodies.put(body.getUuid(), body);
        logger.info("added body "+body);
    }
}
