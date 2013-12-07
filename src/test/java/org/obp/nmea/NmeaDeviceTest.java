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
