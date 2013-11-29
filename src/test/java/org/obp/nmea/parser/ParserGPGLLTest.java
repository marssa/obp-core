package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.Attributes;
import org.obp.nmea.NmeaBufferedReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class ParserGPGLLTest {
    private ParserGPGLL parser = new ParserGPGLL();

    @Test
    public void testAgainstSampleFile() throws IOException {
        InputStream is = getClass().getResourceAsStream("bu353-sample.nmea");
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        int count = 0;
        while(reader.lineReady()) {
            if(parser.recognizes(reader.getLine())) {
                count++;
                Attributes am = parser.parse(reader.getLine().scanner());
            } else {
                Assert.assertFalse(reader.getLine().equals("GPGLL"));
            }
        }
        Assert.assertEquals(22,count);
    }
}
