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

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-19
 */
public class DistanceUtil {

    public static final String UNIT = "m";
    public static final String SHORT_FORMAT = "0.00";

    private DistanceUtil() {
    }

    public static final String format(double distance) {
        return new DecimalFormat(SHORT_FORMAT).format(distance)+" "+UNIT;
    }
}
