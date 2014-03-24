/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
