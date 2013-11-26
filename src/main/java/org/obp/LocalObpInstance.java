package org.obp;

import org.apache.log4j.Logger;
import org.obp.dummy.DummyRandomInstrument;
import org.obp.gps.NmeaGpsReceiver;
import org.obp.dummy.DummyRadar;
import org.obp.weather.LcjCv3f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Service
public class LocalObpInstance extends BasicIdentified implements ObpInstance {
    private static Logger logger = Logger.getLogger(LocalObpInstance.class);

    private ConcurrentMap<UUID,Instrument> instruments = new ConcurrentHashMap<>();
    private ConcurrentMap<UUID, Explorer> explorers = new ConcurrentHashMap<>();

    @Autowired
    private NmeaGpsReceiver nmeaGpsReceiver;

    @Autowired
    private LcjCv3f nmeaWindVane;

    @Autowired
    private DummyRadar dummyRadar;

    @Autowired
    public LocalObpInstance(
            @Value("${obp.local.uuid}") UUID uuid,
            @Value("${obp.local.name}") String name,
            @Value("${obp.local.description}") String description) {
        super(uuid, name, description);
    }

    @PostConstruct
    public void init() {
        attachInstrument(nmeaGpsReceiver);
        attachInstrument(nmeaWindVane);
        attachInstrument(dummyOutdoorWeatherStation());
        attachInstrument(dummyIndoorWeatherStation());

        attachExplorer(dummyRadar);
    }

    private Instrument dummyOutdoorWeatherStation() {
        Map<String, DummyRandomInstrument.DoubleRange> attributes = new HashMap<>();
        attributes.put(AttributeNames.AIR_TEMPERATURE, DummyRandomInstrument.DoubleRange.create(-10.0, 30.4));
        attributes.put(AttributeNames.AIR_PRESSURE, DummyRandomInstrument.DoubleRange.create(99800.0, 10200.0));
        attributes.put(AttributeNames.WIND_SPEED, DummyRandomInstrument.DoubleRange.create(0.0, 30.0));
        return new DummyRandomInstrument(getUuid(), "Hurricane H102","dummy outdoor weather station",attributes);
    }

    private Instrument dummyIndoorWeatherStation() {
        Map<String, DummyRandomInstrument.DoubleRange> attributes = new HashMap<>();
        attributes.put(AttributeNames.AIR_TEMPERATURE, DummyRandomInstrument.DoubleRange.create(18.0, 25.0));
        attributes.put(AttributeNames.RELATIVE_HUMIDITY, DummyRandomInstrument.DoubleRange.create(50.0, 100.0));
        return new DummyRandomInstrument(getUuid(), "Typhoon T1000","dummy indoor weather station",attributes);
    }

    @Override
    public List<Body> getBodies() {
        List<Body> bodies = new ArrayList<>();
        for(Explorer explorer : explorers.values()) {
            bodies.addAll(explorer.scan());
        }

        // TODO: remove duplicates and perform general verification

        return bodies;
    }

    @Override
    public Map<String, Object> getAttributeValues(String... names) {
        Map<String,Object> attributes = new HashMap<>();
        for(String name : names) {
            for(Instrument instrument : instruments.values()) {
                if(instrument.provides(name)) {
                    attributes.put(name, instrument.getValue(name));
                }
            }
        }
        return attributes;
    }

    @Override
    public Map<String, Object> getAllAttributeValues() {
        Map<String,Object> attributes = new HashMap<>();
        for(Instrument instrument : instruments.values()) {
            for(Map.Entry<String,Object> entry : instrument.getAttributeEntries()) {
                if(!attributes.containsKey(entry.getKey())) {
                    attributes.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return attributes;
    }

    @Override
    public Attribute getAttribute(String name) {
        for(Instrument instrument : instruments.values()) {
            if(instrument.provides(name)) {
                return instrument.getAttribute(name);
            }
        }
        return null;
    }

    @Override
    public void attachInstrument(Instrument instrument) {
        instruments.put(instrument.getUuid(), instrument);
    }

    @Override
    public void detachInstrument(Instrument instrument) {
        instruments.remove(instrument.getUuid());
    }

    @Override
    public void attachExplorer(Explorer explorer) {
        explorers.put(explorer.getUuid(), explorer);
    }

    @Override
    public void detachExplorer(Explorer explorer) {
        explorers.remove(explorer.getUuid());
    }

}
