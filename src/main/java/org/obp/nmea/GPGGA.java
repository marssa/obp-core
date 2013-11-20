package org.obp.nmea;

import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */
public class GPGGA extends NmeaMessage {
    public static final String SIGNATURE = "GPGGA";

    private static Logger logger = Logger.getLogger(GPGGA.class);

    public static enum FixQuality {
        NA(""),
        INVALID("0"),
        GPSFIX("1"),
        DGPSFIX("2"),
        PPSFIX("3"),
        RTK_INT("4"),
        RTK_FLOAT("5"),
        ESTIMATED("6");

        private String code;

        FixQuality(String code) {
            this.code=code;
        }

        public static FixQuality fromString(String str) {
            for(FixQuality fq : FixQuality.values()) {
                if(fq.code.equals(str)) {
                    return fq;
                }
            }
            logger.error("undefined code "+str);
            return NA;
        }

    }

    private long fixTime;
    private double latitude;
    private double longitude;
    private FixQuality fixQuality;
    private byte numberOfSatellitesInView;
    private double hdop;
    private double altitude;
    private long timeSinceLastDgpsUpdate;
    private String refDgpsStationId;

    public GPGGA(long fixTime, double latitude, double longitude, String fixQuality, byte numberOfSatellitesInView,
                 double hdop, double altitude, long timeSinceLastDgpsUpdate, String refDgpsStationId) {
        this.fixTime = fixTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fixQuality = FixQuality.fromString(fixQuality);
        this.numberOfSatellitesInView = numberOfSatellitesInView;
        this.hdop = hdop;
        this.altitude = altitude;
        this.timeSinceLastDgpsUpdate = timeSinceLastDgpsUpdate;
        this.refDgpsStationId = refDgpsStationId;
    }

    public long getFixTime() {
        return fixTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public FixQuality getFixQuality() {
        return fixQuality;
    }

    public byte getNumberOfSatellitesInView() {
        return numberOfSatellitesInView;
    }

    public double getHdop() {
        return hdop;
    }

    public double getAltitude() {
        return altitude;
    }

    public long getTimeSinceLastDgpsUpdate() {
        return timeSinceLastDgpsUpdate;
    }

    public String getRefDgpsStationId() {
        return refDgpsStationId;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
