package org.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class BasicExplorer extends BasicIdentified implements Explorer {

    public BasicExplorer(UUID uuid, String name, String description) {
        super(uuid, name, description);
    }
}
