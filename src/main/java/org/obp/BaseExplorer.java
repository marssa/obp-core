package org.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class BaseExplorer extends BaseIdentified implements Explorer {

    public BaseExplorer(UUID uuid, String name, String description) {
        super(uuid, name, description);
    }
}
