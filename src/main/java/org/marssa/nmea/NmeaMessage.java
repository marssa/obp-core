package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
public abstract class NmeaMessage {
    private long creationTime = System.currentTimeMillis();

    public long getCreationTime() {
        return creationTime;
    }

    abstract public String getSignature();
}
