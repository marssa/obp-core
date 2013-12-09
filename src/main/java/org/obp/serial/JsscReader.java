package org.obp.serial;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-9
 */
public class JsscReader extends Reader {

    private SerialPort serialPort;

    public JsscReader(SerialPort serialPort) {
        super();
        this.serialPort = serialPort;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        try {
            int bytesToRead = Math.min(len, serialPort.getInputBufferBytesCount());
            byte[] buf = serialPort.readBytes(bytesToRead);
            for(byte b : buf) {
                cbuf[off++] = (char)b;
            }
            return buf.length;
        } catch (SerialPortException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        // intentionally do nothing
    }
}
