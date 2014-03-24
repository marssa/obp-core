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

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPRMC implements NmeaAttributeParser {

    private static final DateTimeFormatter FIX_DATETIME_FORMATTER = DateTimeFormat.forPattern("ddMMyy HHmmss.SSS").withZoneUTC();

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("GPRMC") && line.getDataSize() >= 9;
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        String fixTime = scanner.next();

        am.put(LATITUDE, scanner.skip().nextLatitudeDDMM());
        am.put(LONGITUDE, scanner.nextLongitudeDDMM());
        am.put(SPEED_OVER_GROUND, scanner.nextVelocityKnots());
        am.put(TRUE_NORTH_COURSE, scanner.nextDouble());

        String fixDate = scanner.next();

        am.put(GPS_MAGNETIC_VARIATION, scanner.nextAzimuthDegrees());
        am.put(TIME, FIX_DATETIME_FORMATTER.parseMillis(fixDate+" "+fixTime));

        return am;
    }
}
