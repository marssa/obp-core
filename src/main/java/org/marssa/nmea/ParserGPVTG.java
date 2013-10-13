package org.marssa.nmea;

import org.apache.commons.lang3.math.NumberUtils;
import org.marssa.utils.VelocityUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */

@Service
public class ParserGPVTG implements NmeaLineParser<GPVTG> {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals("GPVTG") &&
                line.getDataSize() >= 8 &&
                line.getData(1).equals("T") &&
                line.getData(7).equals("K");
    }

    @Override
    public GPVTG parseLine(NmeaLine line) {
        return new GPVTG(
                NumberUtils.toDouble(line.getData(0), Double.NaN),
                VelocityUtil.fromKmPerHour(NumberUtils.toDouble(line.getData(6)))
        );
    }
}
