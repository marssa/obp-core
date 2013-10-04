package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaMessage {
    private String talkerId;
    private String type;
    private String[] data;

    public NmeaMessage(String talkerId, String type, String[] data) {
        this.talkerId = talkerId;
        this.type = type;
        this.data = data;
    }

    public String getTalkerId() {
        return talkerId;
    }

    public String getType() {
        return type;
    }

    public String getData(int index) {
        return data[index];
    }

    public int getDataSize() {
        return data==null ? 0 : data.length;
    }
}
