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
        Assert.assertTrue(parser.recognizes(line));
        Attributes am = parser.parse(line.scanner());
        Assert.assertEquals(336.45, am.getDouble(TRUE_NORTH_COURSE), 0.00001);
        Assert.assertEquals(1.0, am.getDouble(SPEED_OVER_GROUND), 0.00001);
    }
}
