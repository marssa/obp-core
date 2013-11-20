package org.obp.nmea;

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
    public GPRMC parseLine(NmeaLineScanner scanner) {
        return new GPRMC(
                scanner.next(),
                detectWarningFlag(scanner.next()),
                scanner.nextLatitudeDDMM(),
                scanner.nextLongitudeDDMM(),
                scanner.nextVelocityKnots(),
                scanner.nextDouble(),
                scanner.next(),
                scanner.nextAzimuthDegrees());
    }
}
