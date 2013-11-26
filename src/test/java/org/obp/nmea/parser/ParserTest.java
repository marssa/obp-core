package org.obp.nmea.parser;

import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public class ParserTest {

    protected NmeaLine lineFrom(String msg) throws IOException {
        InputStream is = new ByteArrayInputStream(msg.getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        return reader.fetchLine();
    }
}
