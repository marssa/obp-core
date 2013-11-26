package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.AttributeMap;
import org.obp.AttributeNames;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.message.WIXDR;

import java.io.IOException;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */

public class ParserWIXDRTest extends ParserTest {

    private ParserWIXDR parser = new ParserWIXDR();

    @Test
    public void shouldRejectInvalidLine() throws IOException {
        NmeaLine line = lineFrom("$WIXDR,F,024.0,C,,");
        Assert.assertNotNull(line);
        Assert.assertFalse(parser.recognizes(line));
    }

    @Test
    public void shouldParseValidLine() throws IOException {
        NmeaLine line = lineFrom("$WIXDR,C,024.0,C,,");
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        AttributeMap am = parser.parse(line.scanner());
        Assert.assertEquals(24.0, am.getDouble(AttributeNames.WIND_TEMPERATURE),0.01);
    }

}
