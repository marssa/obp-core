package org.marssa.nmea;

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
        NmeaDevice device = new NmeaDevice("/dev/cu.usbserial");
        try(NmeaBufferedReader reader = device.getReader()) {
            NmeaLine line;
            int counter = 20;
            while((line = reader.fetchLine()) != null && counter > 0) {
                System.out.println(reader.fetchLine());
                counter--;
            }
        }
    }

}
