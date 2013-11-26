package org.obp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public class MutableAttributeMap extends AttributeMap {

    public MutableAttributeMap() {
        super(new HashMap<String, Object>());
    }

    public MutableAttributeMap(Map<String, Object> map) {
        super(map);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Map<String, Object> asMap() {
        return map;
    }
}
