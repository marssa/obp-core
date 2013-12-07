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
    @Ignore
    public void shouldFindSomething() throws Exception {
        Set<String> requiredMessages = new HashSet<>(Arrays.asList("GPGLL"));
        String portName = finder.find("/dev/tty.usbserial", requiredMessages);
        Assert.assertNotNull(portName);
    }
}
