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
