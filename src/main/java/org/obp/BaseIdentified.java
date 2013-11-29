package org.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public class BaseIdentified implements Identified {
    private UUID uuid;
    private String name;
    private String description;

    public BaseIdentified(UUID uuid, String name, String description) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
