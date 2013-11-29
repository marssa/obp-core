package org.obp.nmea;

import org.apache.commons.lang3.math.NumberUtils;
import org.obp.utils.*;

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

    public NmeaLineScanner skip() {
        this.pointer++;
        return this;
    }

    public NmeaLineScanner skip(int tokens) {
        this.pointer += tokens;
        return this;
    }

    public int getTokentLeft() {
        return tokens.length - pointer;
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
        return LatitudeUtil.fromDDMM(NumberUtils.toDouble(next()), next());
    }

    public double nextLongitudeDDMM() {
        return LongitudeUtil.fromDDMM(NumberUtils.toDouble(next()), next());
    }

    public double nextAzimuthDegrees() {
        return AzimuthUtil.fromDegrees(NumberUtils.toDouble(next()), next());
    }

    public long nextUtcHHMMSS() {
        double d = NumberUtils.toDouble(next());
        return TimeUtil.fromUtcHHMMSS((int) d) + (int)((d - (int)d) * 1000);
    }

    public double nextVelocityKnots() {
        return SpeedUtil.fromKnots(NumberUtils.toDouble(next()));
    }

    public double nextDouble() {
        return NumberUtils.toDouble(next());
    }

    public double nextDoubleOrNaN() {
        return NumberUtils.toDouble(next(), Double.NaN);
    }

    public byte nextByte() {
        return NumberUtils.toByte(next());
    }

    public double nextAltitude() {
        double alt = NumberUtils.toDouble(next(), Double.NaN);
        String unit = next();
        return unit.equals("M") ? alt : Double.NaN;
    }

    public long nextLong() {
        return NumberUtils.toLong(next());
    }
}
