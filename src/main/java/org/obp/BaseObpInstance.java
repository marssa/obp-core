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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */
public abstract class BaseObpInstance extends BaseIdentified implements ObpInstance {

    private static Logger logger = Logger.getLogger(BaseObpInstance.class);

    protected ConcurrentMap<UUID,Instrument> instruments = new ConcurrentHashMap<>();
    protected ConcurrentMap<UUID, Explorer> explorers = new ConcurrentHashMap<>();

    private String organization;

    public BaseObpInstance(UUID uuid, String name, String description, String organization) {
        super(uuid, name, description);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
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

    @Override
    public Attributes resolveAttributes(String... keys) {
        Attributes attributes = new Attributes();
        for(String key : keys) {
            for(Instrument instrument : instruments.values()) {
                if(instrument.getAttributes().containsKey(key)) {
                    attributes.putIfAbsent(key, instrument.getAttributes().get(key));
                }
            }
        }
        return attributes;
    }

    @Override
    public Attributes getAttributes() {
        Attributes attributes = new Attributes();
        for(Instrument instrument : instruments.values()) {
            for(Map.Entry<String,Object> entry : instrument.getAttributes().entrySet()) {
                attributes.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
        return attributes;
    }

    @Override
    public AttributeInfo getAttributeInfo(String key) {
        for(Instrument instrument : instruments.values()) {
            if(instrument.getAttributes().containsKey(key)) {
                return instrument.getAttribute(key);
            }
        }
        return null;
    }

    @Override
    public List<AttributeInfo> getAttributeInfos() {
        List<AttributeInfo> infos = new ArrayList<>();
        for(Instrument instrument : instruments.values()) {
            for(Map.Entry<String,Object> entry : instrument.getAttributes().entrySet()) {
                infos.add(new AttributeInfo(instrument, instrument.getReliability(), entry.getKey(), entry.getValue()));
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
}
