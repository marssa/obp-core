package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.message.GPGSA;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        GPGSA gpgsa = parser.parse(line.scanner());
        Assert.assertEquals(GPGSA.FixMode.AUTO, gpgsa.getFixMode());
        Assert.assertEquals(GPGSA.FixType.FIX3D, gpgsa.getFixType());

        byte[] channels = {11,20,1,17,14,32,19,31,0,0,0,0};
        Assert.assertArrayEquals(channels,gpgsa.getSatellitesUsed());

        Assert.assertEquals(1.7, gpgsa.getPdop(),0.0001);
        Assert.assertEquals(1.0, gpgsa.getHdop(),0.0001);
        Assert.assertEquals(1.3, gpgsa.getVdop(),0.0001);
    }
}
