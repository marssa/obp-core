package org.obp.dummy;

import org.apache.log4j.Logger;
import org.obp.BaseExplorer;
import org.obp.Body;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

public class DummyRadar extends BaseExplorer {
    private static Logger logger = Logger.getLogger(DummyRadar.class);

    private List<Body> bodies;

    public DummyRadar() {
        super(UUID.randomUUID(), "dummyRadar", "dummy radar as external bodies explorer");
    }

    @PostConstruct
    protected void init() {
        bodies = Collections.unmodifiableList(Arrays.asList(
                new Body("Pinta",1,1),
                new Body("Nina",2,2),
                new Body("Santa Maria",3,3)
        ));
    }

    @Override
    public List<Body> scan() {
        return bodies;
    }
}
