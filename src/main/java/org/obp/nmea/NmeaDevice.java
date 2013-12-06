package org.obp.nmea;

import jssc.SerialPort;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class NmeaDevice {
    private static Logger logger = Logger.getLogger(NmeaDevice.class);

    public static final int BAUD_RATE = 4800;
    public static final int DATA_BITS = SerialPort.DATABITS_8;
    public static final int STOP_BITS = SerialPort.STOPBITS_1;
    public static final int PARITY = SerialPort.PARITY_NONE;
    public static final int TIMEOUT_MS = 5000;

    private SerialPort port;

    public NmeaDevice(String portName) throws Exception {
        logger.info("open NMEA device on port "+portName);
        /*
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()) {
                throw new IllegalAccessError("port "+portName+" is currently in use");
            }

            this.port = (SerialPort)portIdentifier.open(portName,TIMEOUT_MS);
            this.port.setSerialPortParams(BAUD_RATE,DATA_BITS,STOP_BITS,PARITY);
        } catch (NoSuchPortException e) {
            RxTxUtils.dumpAvailablePorts();
            throw e;
        }
        */
    }

    public NmeaBufferedReader getReader() throws IOException {
        return null;//new NmeaBufferedReader(port.getInputStream());
    }

    public void close() {
        // TODO: implement actual closing
    }
}
