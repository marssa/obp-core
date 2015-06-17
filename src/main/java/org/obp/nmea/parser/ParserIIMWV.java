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

import org.obp.nmea.NmeaSentence;
import org.obp.nmea.NmeaSentenceScanner;
import org.obp.nmea.NmeaParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.obp.Readout.WIND_ANGLE;
import static org.obp.Readout.WIND_SPEED;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */

@Service
public class ParserIIMWV implements NmeaParser {

    @Override
    public boolean recognizes(NmeaSentence line) {
        return line.getName().equals("IIMWV") && line.getDataSize() >= 5 &&
                line.getData(1).equals("R") &&
                line.getData(3).equals("N") &&
                line.getData(4).equals("A");
    }

    @Override
    public Map<String,Object> parse(NmeaSentenceScanner scanner) {
        Map<String,Object> map = new HashMap<>();
        map.put(WIND_ANGLE, scanner.nextDouble());
        map.put(WIND_SPEED, scanner.skip().nextVelocityKnots());
        return map;
    }
}
