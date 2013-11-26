package org.obp.nmea.parser;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.obp.AttributeMap;
import org.obp.Instrument;
import org.obp.Reliability;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.obp.AttributeNames.*;

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
        Assert.assertTrue(parser.recognizes(line));
        AttributeMap am = parser.parse(line.scanner());
        Assert.assertEquals(new DateTime(DateTimeZone.UTC).withTime(8, 44, 21, 963).getMillis(), am.getLong(TIME));
        Assert.assertEquals(Reliability.MEDIUM, (Reliability)am.get(RELIABLILITY));
        Assert.assertEquals(3, am.getByte(GPS_EFFECTIVE_SATELLITES));
    }

}
