package org.marssa.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class Explorer extends Identifiable {

    protected Realm realm;

    public Explorer(UUID uuid, String name, String description, Realm realm) {
        super(uuid, name, description);
        this.realm = realm;
    }
}
