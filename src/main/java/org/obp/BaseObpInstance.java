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
import org.obp.data.Vessel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */
public abstract class BaseObpInstance implements ObpInstance {

    private static Logger logger = Logger.getLogger(BaseObpInstance.class);

    protected ConcurrentMap<Object,Device> instruments = new ConcurrentHashMap<>();
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
    public void attachInstrument(Device device) {
        instruments.put(device.getId(), device);
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
    public void detachInstrument(Device device) {
        instruments.remove(device.getId());
    }

    @Override
    public void attachExplorer(Explorer explorer) {
        explorers.put(explorer.getId(), explorer);
    }

    @Override
    public void detachExplorer(Explorer explorer) {
        explorers.remove(explorer.getId());
    }

    private List<Device> instrumentsByReliabilityReversed() {
        List<Device> list = new ArrayList<>(instruments.values());
        Collections.sort(list, new Comparator<Device>() {
            @Override
            public int compare(Device i1, Device i2) {
                return i2.getReliability().compareTo(i1.getReliability());
            }
        });
        return list;
    }

    @Override
    public Readouts resolveReadouts(String... keys) {
        Readouts readouts = new Readouts();
        for(String key : keys) {
            for(Device device : instrumentsByReliabilityReversed()) {
                if(device.getStatus() == Device.Status.OPERATIONAL && device.getReadouts().containsKey(key)) {
                    readouts.putIfAbsent(key, device.getReadouts().get(key));
                }
            }
        }
        return readouts;
    }

    @Override
    public Readouts resolveReadouts() {
        Readouts readouts = new Readouts();
        for(Device device : instrumentsByReliabilityReversed()) {
            if(device.getStatus() == Device.Status.OPERATIONAL) {
                for(Map.Entry<String,Readout> entry : device.getReadouts().entrySet()) {
                    readouts.putIfAbsent(entry.getKey(), entry.getValue());
                }
            }
        }
        return readouts;
    }

    @Override
    public Readout resolveReadout(String name) {
        for(Device device : instrumentsByReliabilityReversed()) {
            if(device.getStatus() == Device.Status.OPERATIONAL) {
                Readout readout = device.getReadouts().get(name);
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
        for(Device device : instruments.values()) {
            if(device.getStatus() == Device.Status.OPERATIONAL) {
                for(Map.Entry<String,Readout> entry : device.getReadouts().entrySet()) {
                    infos.add(entry.getValue());
                }
            }
        }
        return infos;
    }

    @Override
    public List<Vessel> getBodies() {
        List<Vessel> bodies = new ArrayList<>();
        for(Explorer explorer : explorers.values()) {
            bodies.addAll(explorer.scan());
        }

        // TODO: remove duplicates and perform general verification

        return bodies;
    }


    @Override
    public Collection<Device> getInstruments() {
        return Collections.unmodifiableCollection(instruments.values());
    }

    @Override
    public Device getInstrument(String id) {
        return instruments.get(id);
    }
}
