package org.obp.nmea;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;
import org.obp.serial.JsscReader;

import java.io.IOException;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class NmeaDevice implements AutoCloseable {
    private static Logger logger = Logger.getLogger(NmeaDevice.class);

    public static final int BAUD_RATE = 4800;
    public static final int DATA_BITS = SerialPort.DATABITS_8;
    public static final int STOP_BITS = SerialPort.STOPBITS_1;
    public static final int PARITY = SerialPort.PARITY_NONE;
    public static final int TIMEOUT_MS = 5000;

    private SerialPort serialPort;

    public NmeaDevice(String portName) throws Exception {
        logger.debug("open NMEA device on port "+portName);
        serialPort = new SerialPort(portName);
        if(serialPort.isOpened()) {
            throw new Exception("port "+portName+"already in use");
        }
    }

    public NmeaBufferedReader getReader() throws IOException {
        return new NmeaBufferedReader(new JsscReader(serialPort));
    }

    public void open() {
        try {
            serialPort.openPort();
            serialPort.setParams(BAUD_RATE,DATA_BITS,STOP_BITS,PARITY);
        } catch (SerialPortException e) {
            logger.error("error opening serial port "+serialPort.getPortName()+":"+e.getMessage());
            close();
        }
    }

    @Override
    public void close() {
        try {
            if(serialPort!=null && serialPort.isOpened()) {
                logger.debug("close port "+serialPort.getPortName());
                serialPort.closePort();
            }
        } catch (SerialPortException e) {
            logger.error("error closing serial port "+serialPort.getPortName()+":"+e.getMessage());
        }
    }

    public static final NmeaDevice createAndOpen(String portName) throws Exception {
        NmeaDevice device = new NmeaDevice(portName);
        if(!device.isOpened()) {
            device.open();
            return device;
        }
        return null;
    }

    public boolean isOpened() {
        return serialPort!=null && serialPort.isOpened();
    }
}
