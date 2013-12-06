package org.obp.nmea;

import gnu.io.RXTXVersion;
import junit.framework.Assert;
import org.junit.Before;
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

    @Before
    public void setUp() {
        System.out.println("RXTX: "+ RXTXVersion.getVersion());
    }

    @Test
    @Ignore
    public void shouldFindSomething() throws Exception {
        Set<String> requiredMessages = new HashSet<>(Arrays.asList("GPGLL"));
        NmeaDevice device = finder.find("/dev/cu.usbserial", requiredMessages);
        Assert.assertNotNull(device);
    }
}
