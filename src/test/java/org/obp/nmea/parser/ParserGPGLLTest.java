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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class ParserGPGLLTest {
    private ParserGPGLL parser = new ParserGPGLL();

    @Test
    public void testAgainstSampleFile() throws IOException {
        InputStream is = getClass().getResourceAsStream("bu353-sample.nmea");
        NmeaBufferedReader reader = new NmeaBufferedReader(is);
        int count = 0;
        while(reader.lineReady()) {
            if(parser.recognizes(reader.getLine())) {
                count++;
                Map<String,Object> am = parser.parse(reader.getLine().scanner());
            } else {
                Assert.assertFalse(reader.getLine().equals("GPGLL"));
            }
        }
        Assert.assertEquals(22,count);
    }
}
