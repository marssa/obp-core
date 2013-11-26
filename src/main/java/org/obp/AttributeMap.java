package org.obp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public class AttributeMap {
    private Map<String, Object> map;

    public AttributeMap() {
        this.map = new HashMap<String, Object>();
    }

    public AttributeMap(Map<String, Object> map) {
        this.map = map;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void putAll(AttributeMap attributeMap) {
        for(Map.Entry<String,Object> entry : attributeMap.map.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    public Object get(String key) {
        return map.get(key);
    }

    public double getDouble(String key) {
        return (double)map.get(key);
    }

    public long getLong(String key) {
        return (long)map.get(key);
    }

    public byte getByte(String key) {
        return (byte)map.get(key);
    }

    public String getString(String key) {
        return (String)map.get(key);
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(map);
    }

    public Map<String, Object> toMapWithKeys(String... keys) {
        Map<String, Object> map = new HashMap<>();
        for(String key : keys) {
            Object value = this.map.get(key);
            if(value!=null) {
                map.put(key,value);
            }
        }
        return map;
    }
}
