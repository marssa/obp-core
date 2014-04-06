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

import org.obp.utils.AngleUtil;
import org.obp.utils.SpeedUtil;
import org.obp.utils.TemperatureUtil;
import org.obp.utils.TimeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public class Readouts implements Map<String, Readout> {
    public static final String NA = "n/a";

    private Map<String, Readout> readoutMap;
    private boolean allRequested = false;

    public Readouts() {
        this.readoutMap = new HashMap<String, Readout>();
    }

    protected Readouts(Map<String, Readout> readoutMap) {
        this.readoutMap = readoutMap;
    }

    @Override
    public int size() {
        return readoutMap.size();
    }

    @Override
    public boolean isEmpty() {
        return readoutMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return readoutMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return readoutMap.containsValue(value);
    }

    public boolean containsAllKeys(String... keys) {
        for(String key : keys) {
            if(!readoutMap.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Readout get(Object key) {
        return readoutMap.get(key);
    }

    @Override
    public Set<String> keySet() {
        return readoutMap.keySet();
    }

    @Override
    public Collection<Readout> values() {
        return readoutMap.values();
    }

    @Override
    public Set<Map.Entry<String, Readout>> entrySet() {
        return readoutMap.entrySet();
    }

    public double getDouble(String key) {
        Readout o = readoutMap.get(key);
        return o!=null ? (double)o.getValue() : 0;
    }

    public long getLong(String key) {
        Readout o = readoutMap.get(key);
        return o!=null ? (long)o.getValue() : 0;
    }

    public byte getByte(String key) {
        Readout o = readoutMap.get(key);
        return o!=null ? (byte)o.getValue() : 0;
    }

    public String formatKnots(String key) {
        Readout o = get(key);
        return o!=null ? SpeedUtil.formatKnots((double)o.getValue()) : NA;
    }

    public String formatAngle(String key) {
        Readout o = get(key);
        return o!=null ? AngleUtil.format((double)o.getValue()) : NA;
    }

    public String formatTime(String key) {
        Readout o = get(key);
        return o!=null ? TimeUtil.formatTime((long)o.getValue()) : NA;
    }

    public String formatDateTime(String key) {
        Readout o = get(key);
        return o!=null ? TimeUtil.formatDateTime((long)o.getValue()) : NA;
    }

    public Object formatTemperature(String key) {
        Readout o = readoutMap.get(key);
        return o!=null ? TemperatureUtil.format((double)o.getValue()) : NA;
    }

    public Readouts filter(String... keys) {
        Readouts readouts = new Readouts();
        for(String key : keys) {
            Readout value = get(key);
            if(value!=null) {
                readouts.put(key,value);
            }
        }
        return readouts;
    }

    public Map<String,Object> toValueMap() {
        Map<String,Object> valueMap = new HashMap<>(readoutMap.size());
        for(Map.Entry<String,Readout> entry : readoutMap.entrySet()) {
            valueMap.put(entry.getKey(), entry.getValue().getValue());
        }
        return valueMap;
    }

    @Override
    public Readout put(String key, Readout value) {
        return readoutMap.put(key, value);
    }

    public Object putIfAbsent(String key, Readout value) {
        Object o = readoutMap.get(key);
        return o==null ? readoutMap.put(key, value) : o;
    }

    @Override
    public Readout remove(Object key) {
        return readoutMap.remove(key);
    }

    public void removeWithPrefix(String prefix) {
        for(Entry<String,Readout> entry : readoutMap.entrySet()) {
            if(entry.getKey().startsWith(prefix)) {
                readoutMap.remove(entry.getKey());
            }
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Readout> m) {
        readoutMap.putAll(m);
    }

    @Override
    public void clear() {
        readoutMap.clear();
    }

    public Readouts immutableClone() {
        return new Readouts(Collections.unmodifiableMap(readoutMap));
    }

    public Readouts clone() {
        return new Readouts(new HashMap<>(readoutMap));
    }

    public static Readouts newConcurrent() {
        return new Readouts(new ConcurrentHashMap<String,Readout>());
    }

}
