package org.obp.nmea.message;

import org.apache.log4j.Logger;
import org.obp.nmea.NmeaMessage;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */
public class GPGSA extends NmeaMessage {
    public static final String SIGNATURE = "GPGSA";

    private static Logger logger = Logger.getLogger(GPGSA.class);

    public static enum FixMode {
        NA(""), MANUAL("M"), AUTO("A");

        private String code;

        FixMode(String code) {
            this.code = code;
        }

        public static FixMode fromString(String str) {
            for(FixMode m : FixMode.values()) {
                if(m.code.equals(str)) {
                    return m;
                }
            }
            return NA;
        }
    }

    public static enum FixType {
        NA(""), NOFIX("1"), FIX2D("2"), FIX3D("3");

        private String code;

        FixType(String code) {
            this.code = code;
        }

        public static FixType fromString(String str) {
            for(FixType m : FixType.values()) {
                if(m.code.equals(str)) {
                    return m;
                }
            }
            return NA;
        }
    }

    private FixMode fixMode;
    private FixType fixType;
    private byte[] satellitesUsed = new byte[12];
    private double hdop;
    private double pdop;
    private double vdop;

    public GPGSA(FixMode fixMode, FixType fixType, byte[] satellitesUsed, double pdop, double hdop, double vdop) {
        this.fixMode = fixMode;
        this.fixType = fixType;
        this.satellitesUsed = satellitesUsed;
        this.hdop = hdop;
        this.pdop = pdop;
        this.vdop = vdop;
    }

    public FixMode getFixMode() {
        return fixMode;
    }

    public FixType getFixType() {
        return fixType;
    }

    public byte[] getSatellitesUsed() {
        return satellitesUsed;
    }

    public byte getNumberOfSatellitesInView() {
        byte num = 0;
        for(byte sn : satellitesUsed) {
            if(sn > 0) {
                num++;
            }
        }
        return num;
    }

    public double getHdop() {
        return hdop;
    }

    public double getPdop() {
        return pdop;
    }

    public double getVdop() {
        return vdop;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
