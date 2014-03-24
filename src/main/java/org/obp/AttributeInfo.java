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

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public class AttributeInfo {

    private Identified instrument;
    private Reliability reliability;
    private String name;
    private Object value;

    public AttributeInfo(Identified instrument, Reliability reliability, String name, Object value) {
        this.instrument = instrument;
        this.reliability = reliability;
        this.name = name;
        this.value = value;
    }

    public Identified getInstrument() {
        return instrument;
    }

    public Reliability getReliability() {
        return reliability;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public double getDouble() {
        return (double)value;
    }

    public String getString() {
        return (String)value;
    }
}
