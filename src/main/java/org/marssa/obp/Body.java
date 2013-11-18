package org.marssa.obp;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public class Body {
    private String name;
    private double latitude;
    private double longitude;

    public Body(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Body(String name) {
        this.name = name;
        this.latitude = Double.NaN;
        this.longitude = Double.NaN;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
