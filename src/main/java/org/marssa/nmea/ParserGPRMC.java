package org.marssa.nmea;

import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPRMC implements NmeaLineParser {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals(GPRMC.SIGNATURE) && line.getDataSize() >= 9;
    }

    private boolean detectWarningFlag(String str) {
        return str!=null && str.equals("V");
    }

    @Override
    public GPRMC parseLine(NmeaLine line) {
        NmeaLineScanner sc = line.scanner();
        return new GPRMC(
                sc.next(),
                detectWarningFlag(sc.next()),
                sc.nextLatitudeDDMM(),
                sc.nextLongitudeDDMM(),
                sc.nextVelocityKnots(),
                sc.nextDouble(),
                sc.next(),
                sc.nextAzimuthDegrees());
    }
}
