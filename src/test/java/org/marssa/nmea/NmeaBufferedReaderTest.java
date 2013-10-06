package org.marssa.nmea;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaBufferedReaderTest {
    static InputStream LINES = new ByteArrayInputStream(
            ("$GPGSV,3,1,10,32,86,270,19,01,84,237,27,11,59,168,35,20,49,259,16*75\n" +
            "$GPVTG,24.26,T,,M,1.49,N,2.8,K,A*31\n"+
            "$GPVTG,307.86,T,,M,1.76,N,3.3,K,A\n"+
            "$GPVTGA,307.86,T,,M,1.76,N,3.3,K,A\n").getBytes(StandardCharsets.US_ASCII));

    @Test
    public void shouldReadAndMatchProperLines() throws IOException {
        NmeaBufferedReader reader = new NmeaBufferedReader(LINES);
        NmeaLine message = reader.fetchLine();
        Assert.assertNotNull(message);

        Assert.assertEquals("GPGSV", message.getName());

        String[] data = "3,1,10,32,86,270,19,01,84,237,27,11,59,168,35,20,49,259,16".split(",");
        for(int i=0; i<message.getDataSize(); i++) {
            Assert.assertEquals(data[i], message.getData(i));
        }

        message = reader.fetchLine();
        Assert.assertNotNull(message);

        message = reader.fetchLine();
        Assert.assertNull(reader.fetchLine());
    }
}
