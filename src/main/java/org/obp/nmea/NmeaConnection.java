/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class NmeaConnection implements AutoCloseable {
    private static Logger logger = Logger.getLogger(NmeaConnection.class);

    public static final int BAUD_RATE = 4800;
    public static final int DATA_BITS = SerialPort.DATABITS_8;
    public static final int STOP_BITS = SerialPort.STOPBITS_1;
    public static final int PARITY = SerialPort.PARITY_NONE;

    private SerialPort serialPort;

    public NmeaConnection(String portName) throws Exception {
        serialPort = new SerialPort(portName);
        logger.debug("open NMEA device on port "+portName);
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

    public static final NmeaConnection createAndOpen(String portName) throws Exception {
        NmeaConnection device = new NmeaConnection(portName);
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
