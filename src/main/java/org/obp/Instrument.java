package org.obp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface Instrument extends Identified {
    public static final String NO_VALUE = "0";

    public static enum Reliability {
        NONE, POOR, MEDIUM, GOOD, HIGH;
    }

    public static enum Status {
        OFF, MALFUNCTION, OPERATIONAL;
    }

    UUID getObpInstanceUuid();
    boolean providesAll(String... names);
    boolean provides(String name);
    boolean isWorking();
    BasicInstrument.Status getStatus();
    List<String> getAttributeNames();
    List<Map.Entry<String, Object>> getAttributeEntries();
    String getString(String name);
    Double getDouble(String name);
    Object getValue(String name);
    Attribute getAttribute(String name);
    Reliability getReliability();
    long getUpdateTime();
}
