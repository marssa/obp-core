package org.marssa.services.serial;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
class RegistryEntry {
    private String portName;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private boolean parity;

    public RegistryEntry(String portName, int baudRate, int dataBits, boolean parity, int stopBits) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public String getPortName() {
        return portName;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public boolean isParity() {
        return parity;
    }

    public int getParityAsInt() {
        return parity ? 1 : 0;
    }
}
