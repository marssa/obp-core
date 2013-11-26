package org.obp.nmea.parser;

import org.obp.AttributeMap;
import org.obp.AttributeNames;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPVTG;
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
        return line.getName().equals(GPVTG.SIGNATURE) &&
                line.getDataSize() >= 8 &&
                line.getData(1).equals("T") &&
                line.getData(7).equals("K");
    }

    @Override
    public AttributeMap parse(NmeaLineScanner scanner) {
        AttributeMap am = new AttributeMap();
        am.put(TRUE_NORTH_COURSE, scanner.nextDoubleOrNaN());
        am.put(VELOCITY_OVER_GROUND, scanner.skip(5).nextDouble());
        return am;
    }
}
