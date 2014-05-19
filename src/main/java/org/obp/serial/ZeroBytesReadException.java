package org.obp.serial;

import java.io.IOException;

/**
 * Created by Robert Jaremczak
 * Date: 2014-5-19
 */
public class ZeroBytesReadException extends IOException {
    public ZeroBytesReadException() {
        super("can't read zero bytes");
    }
}
