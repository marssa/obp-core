package org.obp.nmea.parser;

import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.obp.utils.GpsUtil;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPGGA implements NmeaAttributeParser {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("GPGGA") && line.getDataSize() >= 13 &&
                line.getData(9).equals("M") &&
                line.getData(11).equals("M");
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        am.put(TIME, scanner.nextUtcHHMMSS());
        am.put(LATITUDE, scanner.nextLatitudeDDMM());
        am.put(LONGITUDE, scanner.nextLongitudeDDMM());
        am.put(GPS_FIX_QUALITY, scanner.next());
        am.put(GPS_EFFECTIVE_SATELLITES, scanner.nextByte());
        am.put(HDOP, scanner.nextDouble());
        am.put(ALTITUDE, scanner.nextAltitude());
        am.setReliability(GpsUtil.estimateReliability(am));
        return am;
    }
}
