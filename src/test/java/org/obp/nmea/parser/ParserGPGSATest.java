package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.Attributes;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */
public class ParserGPGSATest {
    private ParserGPGSA parser = new ParserGPGSA();

    @Test
    public void shouldParseValidLines() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPGSA,A,3,11,20,01,17,14,32,19,31,,,,,1.7,1.0,1.3*3C".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaLine line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        Attributes am = parser.parse(line.scanner());
        Assert.assertEquals(GpsFixMode.AUTO, am.get(GPS_FIX_MODE));
        Assert.assertEquals(GpsFixType.FIX3D, am.get(GPS_FIX_TYPE));
        Assert.assertEquals(8, am.getByte(GPS_EFFECTIVE_SATELLITES));
        Assert.assertEquals(1.7, am.getDouble(PDOP),0.0001);
        Assert.assertEquals(1.0, am.getDouble(HDOP),0.0001);
        Assert.assertEquals(1.3, am.getDouble(VDOP),0.0001);
    }
}
