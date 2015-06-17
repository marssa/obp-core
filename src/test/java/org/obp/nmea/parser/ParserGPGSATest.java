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
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaSentence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */
public class ParserGPGSATest {
    private ParserGPGSA parser = new ParserGPGSA();

    @Test
    public void shouldParseValidLines() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPGSA,A,3,11,20,01,17,14,32,19,31,,,,,1.7,1.0,1.3*3C".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaSentence line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        Map<String,Object> am = parser.parse(line.scanner());
        Assert.assertEquals(GpsFixMode.AUTO, am.get(GPS_FIX_MODE));
        Assert.assertEquals(GpsFixType.FIX3D, am.get(GPS_FIX_TYPE));
        Assert.assertEquals((byte)8, am.get(GPS_EFFECTIVE_SATELLITES));
        Assert.assertEquals(1.7, (double)am.get(PDOP),0.0001);
        Assert.assertEquals(1.0, (double)am.get(HDOP),0.0001);
        Assert.assertEquals(1.3, (double)am.get(VDOP),0.0001);
    }
}
