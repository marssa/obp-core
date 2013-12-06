package org.obp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */
public final class MapUtil {

    private MapUtil() {

    }

    public static final Map<Object,Object> filterByKeyPrefix(Map<Object, Object> input, String keyPrefix) {
        Map<Object,Object> output = new HashMap<>();
        for(Map.Entry<Object, Object> entry : input.entrySet()) {
            if(((String)entry.getKey()).startsWith(keyPrefix)) {
                output.put(entry.getKey(),entry.getValue());
            }
        }
        return output;
    }
}
