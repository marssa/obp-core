package org.obp.nmea.parser;

import org.obp.AttributeNames;
import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */

@Service
public class ParserIIMWV implements NmeaAttributeParser {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("IIMWV") && line.getDataSize() >= 5 &&
                line.getData(1).equals("R") &&
                line.getData(3).equals("N") &&
                line.getData(4).equals("A");
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        am.put(WIND_ANGLE, scanner.nextDouble());
        am.put(WIND_SPEED, scanner.skip().nextVelocityKnots());
        return am;
    }
}
