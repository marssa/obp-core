package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.WIXDR;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */

@Service
public class ParserWIXDR implements NmeaLineParser<WIXDR> {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals(WIXDR.SIGNATURE) && line.getDataSize() >= 3 &&
                line.getData(0).equals("C") &&
                line.getData(2).equals("C");
    }

    @Override
    public WIXDR parse(NmeaLineScanner scanner) {
        return new WIXDR(scanner.skip().nextDouble());
    }
}
