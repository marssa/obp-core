package org.obp.nmea.parser;

import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */

@Service
public class ParserGPGLL implements NmeaAttributeParser {
    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("GPGLL") && line.getDataSize() >= 4;
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        am.put(LATITUDE, scanner.nextLatitudeDDMM());
        am.put(LONGITUDE, scanner.nextLongitudeDDMM());
        if(scanner.hasNext()) {
            am.put(TIME, scanner.nextUtcHHMMSS());
        }
        return am;
    }
}
