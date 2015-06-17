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

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-5
 */
public class NmeaConnectionFinderTest {

    private NmeaConnectionFinder finder = new NmeaConnectionFinder();

    @Test
    @Ignore
    public void shouldPassExactDeviceName() throws Exception {
        final String portName = "/dev/tty.usbserial";
        NmeaConnection device = finder.findAndOpen(portName);
        Assert.assertNotNull(device);
        Assert.assertTrue(device.isOpened());
        device.close();
    }

    @Test
    @Ignore
    public void shouldFindExpectedMatch() throws Exception {
        NmeaConnection device = finder.findAndOpen("/dev/tty.usbser WIXDR");
        Assert.assertNotNull(device);
        Assert.assertTrue(device.isOpened());
        device.close();
    }
}
