package org.obp.nmea.parser;

import org.obp.AttributeMap;
import org.obp.nmea.NmeaAttributeParser;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.WIXDR;
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
        return line.getName().equals(WIXDR.SIGNATURE) && line.getDataSize() >= 3 &&
                line.getData(0).equals("C") &&
                line.getData(2).equals("C");
    }

    @Override
    public AttributeMap parse(NmeaLineScanner scanner) {
        AttributeMap am = new AttributeMap();
        am.put(WIND_TEMPERATURE, scanner.skip().nextDouble());
        return am;
    }
}
