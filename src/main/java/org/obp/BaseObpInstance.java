/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp;

import org.apache.log4j.Logger;
import org.obp.data.Body;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */
public abstract class BaseObpInstance implements ObpInstance {

    private static Logger logger = Logger.getLogger(BaseObpInstance.class);

    protected ConcurrentMap<Object,Instrument> instruments = new ConcurrentHashMap<>();
    protected ConcurrentMap<Object, Explorer> explorers = new ConcurrentHashMap<>();

    private String id;
    private String name;
    private String description;
    private String organization;

    public BaseObpInstance(String id, String name, String description, String organization) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public void attachInstrument(Instrument instrument) {
        instruments.put(instrument.getId(), instrument);
    }

    @Override
    public String getId() {
        return id;
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
    public void detachInstrument(Instrument instrument) {
        instruments.remove(instrument.getId());
    }

    @Override
    public void attachExplorer(Explorer explorer) {
        explorers.put(explorer.getId(), explorer);
    }

    @Override
    public void detachExplorer(Explorer explorer) {
        explorers.remove(explorer.getId());
    }

    private List<Instrument> instrumentsByReliabilityReversed() {
        List<Instrument> list = new ArrayList<>(instruments.values());
        Collections.sort(list, new Comparator<Instrument>() {
            @Override
            public int compare(Instrument i1, Instrument i2) {
                return i2.getReliability().compareTo(i1.getReliability());
            }
        });
        return list;
    }

    @Override
    public Readouts resolveReadouts(String... keys) {
        Readouts readouts = new Readouts();
        for(String key : keys) {
            for(Instrument instrument : instrumentsByReliabilityReversed()) {
                if(instrument.getStatus() == Instrument.Status.OPERATIONAL && instrument.getReadouts().containsKey(key)) {
                    readouts.putIfAbsent(key, instrument.getReadouts().get(key));
                }
            }
        }
        return readouts;
    }

    @Override
    public Readouts resolveReadouts() {
        Readouts readouts = new Readouts();
        for(Instrument instrument : instrumentsByReliabilityReversed()) {
            if(instrument.getStatus() == Instrument.Status.OPERATIONAL) {
                for(Map.Entry<String,Readout> entry : instrument.getReadouts().entrySet()) {
                    readouts.putIfAbsent(entry.getKey(), entry.getValue());
                }
            }
        }
        return readouts;
    }

    @Override
    public Readout resolveReadout(String name) {
        for(Instrument instrument : instrumentsByReliabilityReversed()) {
            if(instrument.getStatus() == Instrument.Status.OPERATIONAL) {
                Readout readout = instrument.getReadouts().get(name);
                if(readout!=null) {
                    return readout;
                }
            }
        }
        return null;
    }

    @Override
    public List<Readout> getAllReadouts() {
        List<Readout> infos = new ArrayList<>();
        for(Instrument instrument : instruments.values()) {
            if(instrument.getStatus() == Instrument.Status.OPERATIONAL) {
                for(Map.Entry<String,Readout> entry : instrument.getReadouts().entrySet()) {
                    infos.add(entry.getValue());
                }
            }
        }
        return infos;
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
    public Collection<Instrument> getInstruments() {
        return Collections.unmodifiableCollection(instruments.values());
    }

    @Override
    public Instrument getInstrument(String id) {
        return instruments.get(id);
    }
}
