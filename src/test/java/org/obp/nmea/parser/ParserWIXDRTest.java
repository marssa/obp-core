/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.nmea.parser;

import org.junit.Assert;
import org.junit.Test;
import org.obp.Readout;
import org.obp.nmea.NmeaLine;

import java.io.IOException;
import java.util.Map;

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
        Map<String,Object> am = parser.parse(line.scanner());
        Assert.assertEquals(24.0, (double)am.get(Readout.WIND_TEMPERATURE),0.01);
    }

}
