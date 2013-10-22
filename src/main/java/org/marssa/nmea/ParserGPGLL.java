package org.marssa.nmea;

import org.marssa.utils.LatitudeUtils;
import org.marssa.utils.LongitudeUtil;
import org.marssa.utils.TimeUtils;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */

@Service
public class ParserGPGLL implements NmeaLineParser<GPGLL> {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals(GPGLL.SIGNATURE) && line.getDataSize() >= 4;
    }

    @Override
    public GPGLL parseLine(NmeaLineScanner scanner) {
        return new GPGLL(
                scanner.nextLatitudeDDMM(),
                scanner.nextLongitudeDDMM(),
                scanner.hasNext() ? scanner.nextUtcHHMMSS() : 0);
    }
}
