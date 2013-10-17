package org.marssa.nmea;

import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPGGA implements NmeaLineParser<GPGGA> {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals(GPGGA.SIGNATURE) && line.getDataSize() >= 13 &&
                line.getData(9).equals("M") &&
                line.getData(11).equals("M");
    }

    @Override
    public GPGGA parseLine(NmeaLine line) {
        NmeaLineScanner sc = line.scanner();
        return new GPGGA(
                sc.nextUtcHHMMSS(),
                sc.nextLatitudeDDMM(),
                sc.nextLongitudeDDMM(),
                sc.next(),
                sc.nextByte(),
                sc.nextDouble(),
                sc.nextAltitude(),
                sc.nextLong(),
                sc.next());
    }
}
