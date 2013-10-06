package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public interface NmeaLineParser<T> {
    public boolean matchesLine(NmeaLine line);
    public T parseLine(NmeaLine line);
}
