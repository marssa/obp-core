package org.marssa.services.gps;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
public class GpsSatellite {
    private int id;
    private double elevation;
    private double azimuth;
    private double snr;

    public GpsSatellite(int id, double elevation, double azimuth, double snr) {
        this.id = id;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.snr = snr;
    }

    public int getId() {
        return id;
    }

    public double getElevation() {
        return elevation;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getSnr() {
        return snr;
    }
}
