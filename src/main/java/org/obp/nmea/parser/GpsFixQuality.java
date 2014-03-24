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

import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public enum GpsFixQuality {

    NA(""),
    INVALID("0"),
    GPSFIX("1"),
    DGPSFIX("2"),
    PPSFIX("3"),
    RTK_INT("4"),
    RTK_FLOAT("5"),
    ESTIMATED("6");

    private static Logger logger = Logger.getLogger(GpsFixQuality.class);

    private String code;

    GpsFixQuality(String code) {
        this.code=code;
    }

    public static GpsFixQuality fromString(String str) {
        for(GpsFixQuality fq : GpsFixQuality.values()) {
            if(fq.code.equals(str)) {
                return fq;
            }
        }
        logger.error("undefined code "+str);
        return NA;
    }
}