package org.obp.nmea.parser;

import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */

@Service
public class ParserGPVTG implements NmeaAttributeParser {
    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("GPVTG") &&
                line.getDataSize() >= 8 &&
                line.getData(1).equals("T") &&
                line.getData(7).equals("K");
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        am.put(TRUE_NORTH_COURSE, scanner.nextDoubleOrNaN());
        am.put(SPEED_OVER_GROUND, scanner.skip(5).nextDouble());
        return am;
    }
}
