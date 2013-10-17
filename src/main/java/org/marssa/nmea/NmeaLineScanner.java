package org.marssa.nmea;

import org.apache.commons.lang3.math.NumberUtils;
import org.marssa.utils.*;

import java.util.Iterator;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */
public class NmeaLineScanner implements Iterator<String> {
    private String[] tokens;
    private int pointer = 0;

    public NmeaLineScanner(String[] tokens) {
        this.tokens = tokens;
        pointer = 0;
    }

    @Override
    public boolean hasNext() {
        return pointer < tokens.length;
    }

    @Override
    public String next() {
        return tokens[pointer++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public double nextLatitudeDDMM() {
        return LatitudeUtils.fromDDMM(NumberUtils.toDouble(next()), next());
    }

    public double nextLongitudeDDMM() {
        return LongitudeUtil.fromDDMM(NumberUtils.toDouble(next()), next());
    }

    public double nextAzimuthDegrees() {
        return AzimuthUtil.fromDegrees(NumberUtils.toDouble(next()), next());
    }

    public long nextUtcHHMMSS() {
        return TimeUtils.fromUtcHHMMSS((int) NumberUtils.toDouble(next()));
    }

    public double nextVelocityKnots() {
        return VelocityUtils.fromKnots(NumberUtils.toDouble(next()));
    }

    public double nextDouble() {
        return NumberUtils.toDouble(next());
    }
}