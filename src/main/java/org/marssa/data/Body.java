package org.marssa.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public class Body extends Identifiable {

    private ConcurrentMap<UUID,Instrument> instruments = new ConcurrentHashMap<>();

    public Body(UUID uuid, String name, String description) {
        super(uuid, name, description);
    }

    public List<Instrument> findInstrumentsProviding(String... attributes) {
        List<Instrument> matches = new ArrayList<>();
        for(Instrument instrument : instruments.values()) {
            if(instrument.providesAll(attributes)) {
                matches.add(instrument);
            }
        }
        return matches;
    }

    public void attachInstrument(Instrument instrument) {
        instruments.put(instrument.getUuid(), instrument);
    }

    public void detachInstrument(UUID uuid) {
        instruments.remove(uuid);
    }
}
