package org.obp.nmea.parser;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.obp.AttributeMap;
import org.obp.AttributeNames;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.message.GPRMC;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */
public class ParserGPRMCTest {
    private ParserGPRMC parser = new ParserGPRMC();
    @Test
    public void shouldParseValidLines() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPRMC,084426.963,V,5109.8210,N,01653.4292,E,,,161013,,,N*78".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaLine line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        AttributeMap am = parser.parse(line.scanner());
        Assert.assertEquals(new DateTime(2013, 10, 16, 8, 44, 26, 963, DateTimeZone.UTC).getMillis(), am.getLong(TIME));
    }
}
