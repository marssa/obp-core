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

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.NmeaParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.obp.Readout.WIND_TEMPERATURE;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */

@Service
public class ParserWIXDR implements NmeaParser {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("WIXDR") && line.getDataSize() >= 3 &&
                line.getData(0).equals("C") &&
                line.getData(2).equals("C");
    }

    @Override
    public Map<String,Object> parse(NmeaLineScanner scanner) {
        Map<String,Object> map = new HashMap<>();
        map.put(WIND_TEMPERATURE, scanner.skip().nextDouble());
        return map;
    }
}
