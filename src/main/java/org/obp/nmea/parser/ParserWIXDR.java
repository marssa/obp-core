package org.obp.nmea.parser;

import org.obp.Attributes;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.springframework.stereotype.Service;

import static org.obp.AttributeNames.WIND_TEMPERATURE;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */

@Service
public class ParserWIXDR implements NmeaAttributeParser {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals("WIXDR") && line.getDataSize() >= 3 &&
                line.getData(0).equals("C") &&
                line.getData(2).equals("C");
    }

    @Override
    public Attributes parse(NmeaLineScanner scanner) {
        Attributes am = new Attributes();
        am.put(WIND_TEMPERATURE, scanner.skip().nextDouble());
        return am;
    }
}
