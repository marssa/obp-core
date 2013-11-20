package org.obp.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
public interface NmeaMessageListener<T extends NmeaMessage> {
    public void incommingMessage(T message);
}
