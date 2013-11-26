package org.obp.nmea;

import org.obp.AttributeMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public interface NmeaAttributeParser {
    public boolean recognizes(NmeaLine line);
    public AttributeMap parse(NmeaLineScanner scanner);
}
