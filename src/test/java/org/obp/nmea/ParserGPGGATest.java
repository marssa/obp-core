package org.obp.nmea;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */
public class ParserGPGGATest {
    private ParserGPGGA parser = new ParserGPGGA();

    @Test
    public void shouldParseValidLines() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPGGA,084421.963,5109.8201,N,01653.4313,E,0,03,,-42.7,M,42.7,M,,0000*68".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaLine line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.matchesLine(line));
        GPGGA gpgga = parser.parseLine(line.scanner());
        Assert.assertEquals(new DateTime(DateTimeZone.UTC).withTime(8, 44, 21, 963).getMillis(), gpgga.getFixTime());
        Assert.assertEquals(GPGGA.FixQuality.INVALID, gpgga.getFixQuality());
        Assert.assertEquals(3, gpgga.getNumberOfSatellitesInView());
    }

}
