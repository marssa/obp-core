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

package org.obp.nmea;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class NmeaDeviceTest {

    @Test
    @Ignore
    public void testReadFromBU353() throws Exception {
        try(NmeaDevice device = NmeaDevice.createAndOpen("/dev/tty.usbserial")) {
            NmeaBufferedReader reader = device.getReader();
            NmeaLine line;
            int counter = 20;
            while(counter > 0) {
                System.out.println(reader.fetchLine());
                counter--;
            }
        }
    }

}
