package org.marssa.services.serial;

import gnu.io.SerialPort;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
class AllocationEntry {
    private SerialPort port;
    private String requestorId;

    public AllocationEntry(SerialPort port, String requestorId) {
        this.port = port;
        this.requestorId = requestorId;
    }

    public SerialPort getPort() {
        return port;
    }

    public String getRequestorId() {
        return requestorId;
    }
}
