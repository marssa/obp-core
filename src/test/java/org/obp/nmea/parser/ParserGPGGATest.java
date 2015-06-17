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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaSentence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.obp.Readout.GPS_EFFECTIVE_SATELLITES;
import static org.obp.Readout.TIME;

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
        NmeaSentence line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        Map<String,Object> am = parser.parse(line.scanner());
        Assert.assertEquals(new DateTime(DateTimeZone.UTC).withTime(8, 44, 21, 963).getMillis(), am.get(TIME));
        Assert.assertEquals((byte)3, am.get(GPS_EFFECTIVE_SATELLITES));
    }

}
