package org.marssa.services.serial;

import gnu.io.SerialPort;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class SerialPortsTest {
    private SerialPorts serialPorts = new SerialPorts();

    @Before
    public void setUp() {
        serialPorts.init();
    }

    @After
    public void tearDown() {
        serialPorts.releaseAll();
    }

    @Test
    @Ignore
    public void cliTestIfReceivesFromPort() throws Exception {
        SerialPort port = serialPorts.open("bu353-port", "test-suite");
        Assert.assertNotNull(port);

        InputStream in = port.getInputStream();

        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = in.read(buffer)) > -1) {
                System.out.print(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
