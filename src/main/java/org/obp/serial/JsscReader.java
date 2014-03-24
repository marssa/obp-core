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
