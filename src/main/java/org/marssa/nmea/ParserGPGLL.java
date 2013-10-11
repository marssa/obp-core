package org.marssa.nmea;

import org.marssa.utils.LatitudeUtil;
import org.marssa.utils.LongitudeUtil;
import org.marssa.utils.TimeUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */

@Service
public class ParserGPGLL implements NmeaLineParser<GPGLL> {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals("GPGLL") && line.getDataSize() >= 4;
    }

    private long parseFixUtc(NmeaLine line) {
        if(line.getDataSize()>4) {
            return TimeUtil.fromUtcHHMMSS((int) Double.parseDouble(line.getData(4)));
        }
        return 0;
    }

    @Override
    public GPGLL parseLine(NmeaLine line) {
        return new GPGLL(
                LatitudeUtil.fromDDMM(Double.parseDouble(line.getData(0)), line.getData(1)),
                LongitudeUtil.parseDDMM(Double.parseDouble(line.getData(2)), line.getData(3)),
                parseFixUtc(line));
    }
}
