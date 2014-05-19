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
import jssc.SerialPortTimeoutException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-9
 */
public class JsscReader extends Reader {

    public static final int READ_TIMEOUT_MS = 1000;
    private static Logger logger = Logger.getLogger(JsscReader.class);

    private SerialPort serialPort;
    private long lastDataReceived = System.currentTimeMillis();

    public JsscReader(SerialPort serialPort) {
        super();
        this.serialPort = serialPort;
    }

    private long timeSinceLastData() {
        return System.currentTimeMillis() - lastDataReceived;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        try {
            if(serialPort.getInputBufferBytesCount()<=0 & timeSinceLastData()>=READ_TIMEOUT_MS) {
                logger.warn("data not received within "+READ_TIMEOUT_MS+" ms");
                return -1;
            }

            int bytesToRead = Math.min(len, serialPort.getInputBufferBytesCount());
            byte[] buf = serialPort.readBytes(bytesToRead, READ_TIMEOUT_MS);
            for(byte b : buf) {
                cbuf[off++] = (char)b;
            }
            if(buf.length>0) {
                //logger.debug("data received after "+(System.currentTimeMillis()-lastDataReceived)+" ms");
                lastDataReceived = System.currentTimeMillis();
            }
            return buf.length;
        } catch(SerialPortTimeoutException e) {
            logger.debug("port read timeout");
            return -1;
        } catch (SerialPortException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        // intentionally do nothing
    }
}
