package org.marssa.nmea;

import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */

@Service
public class ParserGPGSA implements NmeaLineParser<GPGSA> {

    @Override
    public boolean matchesLine(NmeaLine line) {
        return line.getName().equals(GPGSA.SIGNATURE) && line.getDataSize() >= 17;
    }

    private byte[] parseSatelliteChannels(NmeaLineScanner sc) {
        byte[] channels = new byte[12];
        for(int i=0; i<channels.length; i++) {
            channels[i] = sc.nextByte();
        }
        return channels;
    }

    @Override
    public GPGSA parseLine(NmeaLine line) {
        NmeaLineScanner sc = line.scanner();
        return new GPGSA(
                GPGSA.FixMode.fromString(sc.next()),
                GPGSA.FixType.fromString(sc.next()),
                parseSatelliteChannels(sc),
                sc.nextDouble(),
                sc.nextDouble(),
                sc.nextDouble());
    }
}
