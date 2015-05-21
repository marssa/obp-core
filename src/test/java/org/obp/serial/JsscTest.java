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

package org.obp.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

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
