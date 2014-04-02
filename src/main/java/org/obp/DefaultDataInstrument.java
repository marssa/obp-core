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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-10
 */
public class DefaultDataInstrument extends BaseInstrument {

    private static Logger logger = Logger.getLogger(DefaultDataInstrument.class);

    private Attributes defaults;

    public DefaultDataInstrument(String resourceName) {
        super(UUID.randomUUID(), "defaults", "predefined default data");
        logger.info("init default attributes");
        defaults = loadDefaultsFromResource(resourceName);
        initKeys(defaults.keySet());
        reliability = Reliability.DEFAULT;
    }

    private Attributes loadDefaultsFromResource(String name) {
        Properties properties = new Properties();
        try(InputStream is = getClass().getResourceAsStream(name)) {
            properties.load(is);
        } catch (IOException e) {
            logger.error("unable to load properties from "+name,e);
        }

        Attributes attributes = new Attributes();
        for(Map.Entry entry : properties.entrySet()) {
            logger.info(entry.getKey() + " = " + entry.getValue());
            attributes.put((String) entry.getKey(), parseValue(entry.getValue()));
        }

        return attributes;
    }

    private Object parseValue(Object value) {
        String str = (String)value;
        try{
            return Double.parseDouble(str);
        } catch (Exception e) {
            return str;
        }
    }

    @Override
    public Attributes getAttributes() {
        updateInstrumentAttributes(defaults);
        return defaults;
    }
}