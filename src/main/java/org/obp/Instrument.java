package org.obp;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface Instrument extends Identified {
    public static final String NO_VALUE = "0";

    public static enum Status {
        OFF, MALFUNCTION, OPERATIONAL;
    }

    boolean isWorking();
    Reliability getReliability();
    BaseInstrument.Status getStatus();
    Attributes getAttributes();
    Attributes getAttributes(String... keys);
    AttributeInfo getAttribute(String key);
    Object get(String key);
    long getUpdateTime();
}
