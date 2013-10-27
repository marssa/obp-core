package org.marssa.obp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Component
public class Introspector extends Explorer {
    private static Logger logger = Logger.getLogger(Introspector.class);

    @Autowired
    private Body localBody;

    @Autowired
    public Introspector(Realm realm) {
        super(UUID.randomUUID(), "introspector", "discovers local bodies", realm);
    }

    @PostConstruct
    protected void init() {
        logger.info("introspection started ...");
        realm.addBody(localBody);
        logger.info("introspection finished.");
    }

}
