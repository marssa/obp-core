package org.marssa.nmea;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
public class ParserGPGSVTest {
    private ParserGPGSV parser = new ParserGPGSV();

    @Test
    public void shouldParseValidLine() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPGSV,3,1,12,13,79,049,,10,63,289,,23,48,073,,07,45,183,27*7E".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaLine line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.matchesLine(line));
        GPGSV msg = parser.parseLine(line.scanner());
        Assert.assertEquals(1, msg.getSentenceNumber());
        Assert.assertEquals(3, msg.getTotalSentences());
        Assert.assertEquals(12, msg.getTotalSatellitesInView());
        Assert.assertEquals(4, msg.getSvSize());
        Assert.assertEquals(13, msg.getSv(0).getId());
        Assert.assertEquals(63, msg.getSv(1).getElevation(), 0.00001);
        Assert.assertEquals(73, msg.getSv(2).getAzimuth(), 0.00001);
        Assert.assertEquals(27, msg.getSv(3).getSnr(), 0.00001);
    }
}
