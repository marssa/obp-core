package org.marssa.obp;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Service
public class Realm {
    private static Logger logger = Logger.getLogger(Realm.class);

    private ConcurrentMap<UUID, Body> bodies = new ConcurrentHashMap<>();

    public List<Body> getBodies() {
        List<Body> sorted = new ArrayList<>(bodies.values());
        Collections.sort(sorted, new Comparator<Body>() {
            @Override
            public int compare(Body o1, Body o2) {
                Boolean b1 = o1.isLocal();
                Boolean b2 = o2.isLocal();
                return b2.compareTo(b1);
            }
        });
        return sorted;
    }

    public Body getBody(UUID uuid) {
        return bodies.get(uuid);
    }

    public void addBody(Body body) {
        bodies.put(body.getUuid(), body);
        logger.info("add body "+body);
    }

    public void removeBody(Body body) {
        bodies.remove(body.getUuid());
        logger.info("remove body " + body);
    }

}
