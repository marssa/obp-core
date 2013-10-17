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

    private long parseFixUtc(NmeaLine line) {
        if(line.getDataSize()>4) {
            return TimeUtils.fromUtcHHMMSS((int) Double.parseDouble(line.getData(4)));
        }
        return 0;
    }

    @Override
    public GPGLL parseLine(NmeaLine line) {
        return new GPGLL(
                LatitudeUtils.fromDDMM(Double.parseDouble(line.getData(0)), line.getData(1)),
                LongitudeUtil.fromDDMM(Double.parseDouble(line.getData(2)), line.getData(3)),
                parseFixUtc(line));
    }
}
