package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaLine {
    private String name;
    private String[] data;

    public NmeaLine(String name, String[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getData(int index) {
        return data[index];
    }

    public int getDataSize() {
        return data==null ? 0 : data.length;
    }

    public String getDataAsString() {
        StringBuilder sb = new StringBuilder();
        for(String str : data) {
            sb.append(str.trim());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public String toString() {
        return name+" "+getDataAsString();
    }
}
