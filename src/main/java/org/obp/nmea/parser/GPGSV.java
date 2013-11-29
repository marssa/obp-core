package org.obp.nmea.parser;

import org.obp.nmea.NmeaMessage;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
public class GPGSV extends NmeaMessage {
    public static final String SIGNATURE = "GPGSV";

    public static class SV {
        private byte id;
        private double elevation;
        private double azimuth;
        private double snr;

        public SV(byte id, double elevation, double azimuth, double snr) {
            this.id = id;
            this.elevation = elevation;
            this.azimuth = azimuth;
            this.snr = snr;
        }

        public byte getId() {
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

    private byte totalSentences;
    private byte sentenceNumber;
    private byte totalSatellitesInView;
    private SV[] sv;

    public GPGSV(byte totalSentences, byte sentenceNumber, byte totalSatellitesInView, SV... sv) {
        this.totalSentences = totalSentences;
        this.sentenceNumber = sentenceNumber;
        this.totalSatellitesInView = totalSatellitesInView;
        this.sv = sv;
    }

    public byte getTotalSentences() {
        return totalSentences;
    }

    public byte getSentenceNumber() {
        return sentenceNumber;
    }

    public byte getTotalSatellitesInView() {
        return totalSatellitesInView;
    }

    public SV getSv(int index) {
        return sv[index];
    }

    public int getSvSize() {
        return sv.length;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public String toString() {
        return getTotalSatellitesInView()+" visible satellites";
    }
}
