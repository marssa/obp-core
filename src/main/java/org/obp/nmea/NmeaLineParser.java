package org.obp.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public interface NmeaLineParser<T> {
    public boolean recognizes(NmeaLine line);
    public T parse(NmeaLineScanner scanner);
}
