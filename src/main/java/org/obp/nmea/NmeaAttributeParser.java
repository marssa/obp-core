package org.obp.nmea;

import org.obp.Attributes;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public interface NmeaAttributeParser {
    public boolean recognizes(NmeaLine line);
    public Attributes parse(NmeaLineScanner scanner);
}
