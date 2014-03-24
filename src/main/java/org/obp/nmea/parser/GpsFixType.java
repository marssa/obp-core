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

package org.obp.nmea.parser;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public enum GpsFixType {
    NA(""), NOFIX("1"), FIX2D("2"), FIX3D("3");

    private String code;

    GpsFixType(String code) {
        this.code = code;
    }

    public static GpsFixType fromString(String str) {
        for(GpsFixType m : GpsFixType.values()) {
            if(m.code.equals(str)) {
                return m;
            }
        }
        return NA;
    }
}
