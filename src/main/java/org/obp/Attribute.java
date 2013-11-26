package org.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public class Attribute {

    private UUID instrument;
    private Reliability reliability;
    private String name;
    private Object value;

    public Attribute(UUID instrument, Reliability reliability, String name, Object value) {
        this.instrument = instrument;
        this.reliability = reliability;
        this.name = name;
        this.value = value;
    }

    public UUID getInstrument() {
        return instrument;
    }

    public Reliability getReliability() {
        return reliability;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public double getDouble() {
        return (double)value;
    }

    public String getString() {
        return (String)value;
    }
}
