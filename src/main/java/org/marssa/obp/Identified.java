package org.marssa.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface Identified {
    UUID getUuid();
    String getName();
    String getDescription();
}
