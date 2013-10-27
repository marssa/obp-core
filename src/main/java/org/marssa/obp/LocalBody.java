package org.marssa.obp;

import org.marssa.gps.NmeaGpsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.marssa.obp.RandomDummyInstrument.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Component
public class LocalBody extends Body {

    @Autowired
    public LocalBody(
            @Value("${marssa.local.uuid}") String uuid,
            @Value("${marssa.local.name}") String name,
            @Value("${marssa.local.description}") String description) {
        super(UUID.fromString(uuid), name, description, true);
    }

    @Autowired
    private NmeaGpsReceiver nmeaGpsReceiver;

    private Instrument dummyOutdoorWeatherStation() {
        Map<String, RandomDummyInstrument.DoubleRange> attributes = new HashMap<>();
        attributes.put(AttributeNames.TEMPERATURE, DoubleRange.create(-10.0,30.4));
        attributes.put(AttributeNames.ATMOSPHERIC_PRESSURE, DoubleRange.create(99800.0, 10200.0));
        attributes.put(AttributeNames.WIND_SPEED, DoubleRange.create(0.0, 30.0));
        return new RandomDummyInstrument("Hurricane H102","dummy outdoor weather station",attributes);
    }

    private Instrument dummyIndoorWeatherStation() {
        Map<String, RandomDummyInstrument.DoubleRange> attributes = new HashMap<>();
        attributes.put(AttributeNames.TEMPERATURE, DoubleRange.create(18.0,25.0));
        attributes.put(AttributeNames.RELATIVE_HUMIDITY, DoubleRange.create(50.0, 100.0));
        return new RandomDummyInstrument("Typhoon T1000","dummy indoor weather station",attributes);
    }

    @PostConstruct
    protected void init() {
        attachInstrument(nmeaGpsReceiver);
        attachInstrument(dummyOutdoorWeatherStation());
        attachInstrument(dummyIndoorWeatherStation());
    }
}
