package org.obp;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public class AttributeMap {
    protected Map<String, Object> map;

    public AttributeMap(Map<String, Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public double getDouble(String key) {
        return (double)map.get(key);
    }

    public String getString(String key) {
        return (String)map.get(key);
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(map);
    }
}
