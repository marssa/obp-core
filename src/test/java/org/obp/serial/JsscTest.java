package org.obp.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static jssc.SerialPort.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */
public class JsscTest {

    @Ignore
    @Test
    public void shouldEnumerateAndRead() throws SerialPortException {
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            System.out.println(portNames[i]);
        }

        SerialPort serialPort = new SerialPort(portNames[0]);
        try {
            serialPort.openPort();
            serialPort.setParams(BAUDRATE_4800, DATABITS_8, STOPBITS_1, PARITY_NONE);

            System.out.println(new String(serialPort.readBytes(2000), StandardCharsets.US_ASCII));
        } finally {
            serialPort.closePort();
        }


    }

    @Ignore
    @Test
    public void shouldOpenAndClose() throws SerialPortException {
        SerialPort serialPort = new SerialPort("/dev/tty.usbserial");
        Assert.assertNotNull(serialPort);
        Assert.assertFalse(serialPort.isOpened());

        serialPort.openPort();
        serialPort.setParams(BAUDRATE_4800, DATABITS_8, STOPBITS_1, PARITY_NONE);
        Assert.assertTrue(serialPort.isOpened());

        serialPort.closePort();
        Assert.assertFalse(serialPort.isOpened());

        serialPort.openPort();
        serialPort.setParams(BAUDRATE_4800, DATABITS_8, STOPBITS_1, PARITY_NONE);
        Assert.assertTrue(serialPort.isOpened());

        serialPort.closePort();
        Assert.assertFalse(serialPort.isOpened());
    }
}
