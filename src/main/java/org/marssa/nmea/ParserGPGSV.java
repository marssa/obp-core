package org.marssa.nmea;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */

@Service
public class ParserGPGSV implements NmeaLineParser<GPGSV> {
    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals(GPGSV.SIGNATURE) && line.getDataSize() >= 7;
    }

    private GPGSV.SV[] parseSvs(NmeaLine line) {
        byte num = (byte)((line.getDataSize() - 3) / 4);
        GPGSV.SV sv[] = new GPGSV.SV[num];
        int item = 3;
        for(int i=0; i<num; i++) {
            sv[i] = new GPGSV.SV(
                    Byte.parseByte(line.getData(item++)),
                    NumberUtils.toDouble(line.getData(item++), Double.NaN),
                    NumberUtils.toDouble(line.getData(item++), Double.NaN),
                    NumberUtils.toDouble(line.getData(item++), Double.NaN));
        }
        return sv;
    }

    @Override
    public GPGSV parseLine(NmeaLine line) {
        return new GPGSV(
                Byte.parseByte(line.getData(0)),
                Byte.parseByte(line.getData(1)),
                Byte.parseByte(line.getData(2)),
                parseSvs(line));
    }
}
