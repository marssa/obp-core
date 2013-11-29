package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.AttributeNames;
import org.obp.Attributes;
import org.obp.nmea.NmeaLine;

import java.io.IOException;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */
public class ParserIIMWVTest extends ParserTest {

    private ParserIIMWV parser = new ParserIIMWV();

    @Test
    public void shouldRejectInvalidLine() throws IOException {
        NmeaLine line = lineFrom("$IIMWV,B4D3,8D5D,4200,2EFF,F7,");
        Assert.assertNotNull(line);
        Assert.assertFalse(parser.recognizes(line));
    }

    @Test
    public void shouldParseValidLine() throws IOException {
        NmeaLine line = lineFrom("$IIMWV,270.0,R,1.12,N,A");
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        Attributes am = parser.parse(line.scanner());
        Assert.assertEquals(270.0, am.getDouble(WIND_ANGLE),0.001);
        Assert.assertEquals(0.576, am.getDouble(WIND_SPEED),0.001);
    }
}
