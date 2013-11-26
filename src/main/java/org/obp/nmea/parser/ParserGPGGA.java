package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPGGA;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPGGA implements NmeaLineParser<GPGGA> {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals(GPGGA.SIGNATURE) && line.getDataSize() >= 13 &&
                line.getData(9).equals("M") &&
                line.getData(11).equals("M");
    }

    @Override
    public GPGGA parse(NmeaLineScanner scanner) {
        return new GPGGA(
                scanner.nextUtcHHMMSS(),
                scanner.nextLatitudeDDMM(),
                scanner.nextLongitudeDDMM(),
                scanner.next(),
                scanner.nextByte(),
                scanner.nextDouble(),
                scanner.nextAltitude(),
                scanner.nextLong(),
                scanner.next());
    }
}
