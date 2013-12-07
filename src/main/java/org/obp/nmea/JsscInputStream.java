package org.obp.nmea;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-7
 */
public class JsscInputStream extends InputStream {
    private SerialPort serialPort;

    public JsscInputStream(SerialPort serialPort) {
        super();
        this.serialPort = serialPort;
    }

    @Override
    public int read() throws IOException {
        try {
            return serialPort.readBytes(1)[0];
        } catch (SerialPortException e) {
            throw new IOException("exception reading from port "+serialPort.getPortName(),e);
        }
    }
}
