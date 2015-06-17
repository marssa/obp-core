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

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */

@Service
public class ParserGPGSA implements NmeaParser {

    @Override
    public boolean recognizes(NmeaSentence line) {
        return line.getName().equals("GPGSA") && line.getDataSize() >= 17;
    }

    private byte parseSatellitesUsed(NmeaSentenceScanner sc) {
        byte num = 0;
        for(int i=0; i<12; i++) {
            if(sc.nextByte()>0) {
                num++;
            }
        }
        return num;
    }

    @Override
    public Map<String,Object> parse(NmeaSentenceScanner scanner) {
        Map<String,Object> map = new HashMap<>();
        map.put(GPS_FIX_MODE, GpsFixMode.fromString(scanner.next()));
        map.put(GPS_FIX_TYPE, GpsFixType.fromString(scanner.next()));
        map.put(GPS_EFFECTIVE_SATELLITES, parseSatellitesUsed(scanner));
        map.put(PDOP, scanner.nextDouble());
        map.put(HDOP, scanner.nextDouble());
        map.put(VDOP, scanner.nextDouble());
        return map;
    }
}
