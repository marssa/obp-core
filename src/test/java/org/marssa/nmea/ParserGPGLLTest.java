package org.marssa.nmea;

import org.junit.Assert;
import org.junit.Test;

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
            if(parser.matchesLine(reader.getLine())) {
                count++;
                GPGLL msg = parser.parseLine(reader.getLine());
            } else {
                Assert.assertFalse(reader.getLine().equals("GPGLL"));
            }
        }
        Assert.assertEquals(22,count);
    }
}
