package org.marssa.obp;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
public class Readout<T> {
    private long utc;
    private T value;

    public Readout(long utc, T value) {
        this.utc = utc;
        this.value = value;
    }

    public long getUtc() {
        return utc;
    }

    public T getValue() {
        return value;
    }
}
