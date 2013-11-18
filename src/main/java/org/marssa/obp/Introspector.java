package org.marssa.obp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Component
public class Introspector extends BasicExplorer {
    private static Logger logger = Logger.getLogger(Introspector.class);

    private List<Body> bodies;

    public Introspector() {
        super(UUID.randomUUID(), "introspector", "discovers local bodies");
    }

    @PostConstruct
    protected void init() {
        bodies = Collections.unmodifiableList(Arrays.asList(new Body("own")));
    }

    @Override
    public List<Body> scan() {
        return bodies;
    }
}
