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

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
public class ParserGPGSVTest {
    private ParserGPGSV parser = new ParserGPGSV();

    @Test
    public void shouldParseValidLine() throws IOException {
        InputStream is = new ByteArrayInputStream("$GPGSV,3,1,12,13,79,049,,10,63,289,,23,48,073,,07,45,183,27*7E".getBytes());
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        NmeaSentence line = reader.fetchLine();
        Assert.assertNotNull(line);
        Assert.assertTrue(parser.recognizes(line));
        GPGSV msg = parser.parse(line.scanner());
        Assert.assertEquals(1, msg.getSentenceNumber());
        Assert.assertEquals(3, msg.getTotalSentences());
        Assert.assertEquals(12, msg.getTotalSatellitesInView());
        Assert.assertEquals(4, msg.getSvSize());
        Assert.assertEquals(13, msg.getSv(0).getId());
        Assert.assertEquals(63, msg.getSv(1).getElevation(), 0.00001);
        Assert.assertEquals(73, msg.getSv(2).getAzimuth(), 0.00001);
        Assert.assertEquals(27, msg.getSv(3).getSnr(), 0.00001);
    }
}
