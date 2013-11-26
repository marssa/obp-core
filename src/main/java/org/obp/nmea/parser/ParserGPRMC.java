package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPRMC;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */

@Service
public class ParserGPRMC implements NmeaLineParser {
    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals(GPRMC.SIGNATURE) && line.getDataSize() >= 9;
    }

    private boolean detectWarningFlag(String str) {
        return str!=null && str.equals("V");
    }

    @Override
    public GPRMC parse(NmeaLineScanner scanner) {
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
