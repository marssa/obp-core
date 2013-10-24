package org.marssa.data;

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

    @Autowired
    private Body localBody;

    @Autowired
    public Introspector(Vicinity vicinity) {
        super(UUID.randomUUID(), "introspector", "discovers local bodies", vicinity);
    }

    @PostConstruct
    protected void init() {
        vicinity.addBody(localBody);
    }

    public Body getLocalBody() {
        return localBody;
    }
}
