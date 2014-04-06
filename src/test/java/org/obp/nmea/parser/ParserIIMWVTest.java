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
import org.obp.nmea.NmeaLine;
import org.obp.utils.SpeedUtil;

import java.io.IOException;
import java.util.Map;

import static org.obp.Readout.WIND_ANGLE;
import static org.obp.Readout.WIND_SPEED;

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
        Map<String,Object> am = parser.parse(line.scanner());
        Assert.assertEquals(270.0, (double)am.get(WIND_ANGLE),0.001);
        Assert.assertEquals(1.12, SpeedUtil.toKnots((double)am.get(WIND_SPEED)),0.001);
    }
}
