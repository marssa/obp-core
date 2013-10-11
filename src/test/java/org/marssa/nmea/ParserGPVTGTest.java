package org.marssa.nmea;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public class ParserGPVTGTest {
    private ParserGPVTG parser = new ParserGPVTG();

    @Test
    public void shouldParseValidLine() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPVTG,336.45,T,,M,0.52,N,1.0,K,A*0C".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaLine line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.matchesLine(line));
        GPVTG msg = parser.parseLine(line);
        Assert.assertEquals(336.45, msg.getTrueNorthHeading(), 0.00001);
        Assert.assertEquals(1000.00/3600.00, msg.getVelocityOverGround(), 0.00001);
    }
}
