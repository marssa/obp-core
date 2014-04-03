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
public class Attributes implements Map<String, Object> {
    public static final String NA = "n/a";

    private Map<String, Object> map;
    private UUID intrumentUuid;
    private Reliability reliability = Reliability.UNDEFINED;
    private boolean allRequested = false;

    public Attributes() {
        this.map = new HashMap<String, Object>();
    }

    protected Attributes(Map<String, Object> map) {
        this.map = map;
    }

    public UUID getIntrumentUuid() {
        return intrumentUuid;
    }

    public void setIntrumentUuid(UUID intrumentUuid) {
        this.intrumentUuid = intrumentUuid;
    }

    public Reliability getReliability() {
        return reliability;
    }

    public void setReliability(Reliability reliability) {
        this.reliability = reliability;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public boolean containsAllKeys(String... keys) {
        for(String key : keys) {
            if(!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public double getDouble(String key) {
        Object o = map.get(key);
        return o!=null ? (double)o : 0;
    }

    public long getLong(String key) {
        Object o = map.get(key);
        return o!=null ? (long)o : 0;
    }

    public byte getByte(String key) {
        Object o = map.get(key);
        return o!=null ? (byte)o : 0;
    }

    public String getString(String key) {
        return (String)map.get(key);
    }

    public String formatKnots(String key) {
        Object o = get(key);
        return o!=null ? SpeedUtil.formatKnots((double)o) : NA;
    }

    public String formatAngle(String key) {
        Object o = get(key);
        return o!=null ? AngleUtil.format((double)o) : NA;
    }

    public String formatTime(String key) {
        Object o = get(key);
        return o!=null ? TimeUtil.formatTime((long) o) : NA;
    }

    public String formatDateTime(String key) {
        Object o = get(key);
        return o!=null ? TimeUtil.formatDateTime((long) o) : NA;
    }

    public Object formatTemperature(String key) {
        Object o = map.get(key);
        return o!=null ? TemperatureUtil.format((double) o) : NA;
    }

    public Attributes filter(String... keys) {
        Attributes attributes = new Attributes();
        for(String key : keys) {
            Object value = get(key);
            if(value!=null) {
                attributes.put(key,value);
            }
        }
        return attributes;
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public Object putIfAbsent(String key, Object value) {
        Object o = map.get(key);
        return o==null ? map.put(key, value) : o;
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void removeWithPrefix(String prefix) {
        for(Entry<String,Object> entry : map.entrySet()) {
            if(entry.getKey().startsWith(prefix)) {
                map.remove(entry.getKey());
            }
        }
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    public Attributes immutableClone() {
        return new Attributes(Collections.unmodifiableMap(map));
    }

    public Attributes clone() {
        return new Attributes(new HashMap<>(map));
    }

    public static Attributes newConcurrent() {
        return new Attributes(new ConcurrentHashMap<String,Object>());
    }

}
