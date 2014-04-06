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
import org.obp.Readouts;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.obp.Readout.*;

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
        Map<String,Object> am = parser.parse(line.scanner());
        Assert.assertEquals(336.45, (double)am.get(TRUE_NORTH_COURSE), 0.00001);
        Assert.assertEquals(1.0, (double)am.get(SPEED_OVER_GROUND), 0.00001);
    }
}
