package org.marssa.obp;

import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger(Body.class);

    private ConcurrentMap<UUID,Instrument> instruments = new ConcurrentHashMap<>();
    private boolean local;

    public Body(UUID uuid, String name, String description, boolean local) {
        super(uuid, name, description);
        this.local = local;
    }

    public List<Instrument> getInstruments() {
        return new ArrayList<>(instruments.values());
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
        logger.info("body "+this+": attach instrument "+instrument);
        instruments.put(instrument.getUuid(), instrument);
    }

    public void detachInstrument(UUID uuid) {
        logger.info("body "+this+": deattach instrument "+instruments.get(uuid));
        instruments.remove(uuid);
    }

    public boolean isLocal() {
        return local;
    }
}
