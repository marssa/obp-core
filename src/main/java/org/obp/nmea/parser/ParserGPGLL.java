package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPGLL;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */

@Service
public class ParserGPGLL implements NmeaLineParser<GPGLL> {
    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals(GPGLL.SIGNATURE) && line.getDataSize() >= 4;
    }

    @Override
    public GPGLL parse(NmeaLineScanner scanner) {
        return new GPGLL(
                scanner.nextLatitudeDDMM(),
                scanner.nextLongitudeDDMM(),
                scanner.hasNext() ? scanner.nextUtcHHMMSS() : 0);
    }
}
