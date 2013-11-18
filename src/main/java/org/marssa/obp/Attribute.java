package org.marssa.obp;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public class Attribute {

    public static final String
            LATITUDE = "latitude",
            LONGITUDE = "longitude",
            TRUE_NORTH_COURSE = "trueNorthCourse",
            VELOCITY_OVER_GROUND = "velocityOverGround",
            ALTITUDE = "altitude",
            TEMPERATURE = "temperature",
            WIND_SPEED = "windSpeed",
            RELATIVE_HUMIDITY = "relativeHumidity",
            ATMOSPHERIC_PRESSURE = "atmosphericPressure",
            RELIABILITY = "reliability",
            UPDATE_TIME = "lastUpdate",
            DATA_STALE = "dataStale";

    private UUID instrument;
    private Instrument.Reliability reliability;
    private String name;
    private Object value;

    public Attribute(UUID instrument, Instrument.Reliability reliability, String name, Object value) {
        this.instrument = instrument;
        this.reliability = reliability;
        this.name = name;
        this.value = value;
    }

    public UUID getInstrument() {
        return instrument;
    }

    public Instrument.Reliability getReliability() {
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
