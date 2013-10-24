package org.marssa.data;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class Explorer extends Identifiable {

    protected Vicinity vicinity;

    public Explorer(UUID uuid, String name, String description, Vicinity vicinity) {
        super(uuid, name, description);
        this.vicinity = vicinity;
    }
}
