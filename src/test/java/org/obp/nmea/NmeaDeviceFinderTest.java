package org.obp.nmea;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-5
 */
public class NmeaDeviceFinderTest {

    private NmeaDeviceFinder finder = new NmeaDeviceFinder();

    @Test
    public void shouldPassExactDeviceName() throws Exception {
        final String portName = "/dev/tty.usbserial";
        String port = finder.find(portName);
        Assert.assertEquals(portName, port);
    }

    @Test
    @Ignore
    public void shouldFindExpectedMatch() throws Exception {
        String port = finder.find("/dev/tty.usbser GPGLL");
        Assert.assertEquals("/dev/tty.usbserial",port);
    }
}
