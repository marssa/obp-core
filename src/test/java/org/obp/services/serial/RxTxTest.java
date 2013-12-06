package org.obp.services.serial;

import gnu.io.*;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 * Time: 11:16
 */
public class RxTxTest {

    private String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    @Test
    public void shouldEnumerateSerialPorts() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        Assert.assertNotNull(portEnum);

        while ( portEnum.hasMoreElements() )
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }
    }

    @Test
    @Ignore
    public void shouldOpenAndClosePort() throws NoSuchPortException, PortInUseException, IOException, TooManyListenersException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/tty.usbserial");
        Assert.assertNotNull(portIdentifier);
        Assert.assertFalse(portIdentifier.isCurrentlyOwned());

        SerialPort serialPort = (SerialPort) portIdentifier.open(portIdentifier.getName(),2000);
        Assert.assertNotNull(serialPort);
        Assert.assertTrue(portIdentifier.isCurrentlyOwned());
        serialPort.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                System.out.println(serialPortEvent);
            }
        });

        serialPort.getInputStream().close();
        serialPort.getOutputStream().close();
        serialPort.removeEventListener();
        serialPort.close();
        Assert.assertFalse(portIdentifier.isCurrentlyOwned());
    }

}
