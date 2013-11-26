package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPGSA;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-18
 */

@Service
public class ParserGPGSA implements NmeaLineParser<GPGSA> {

    @Override
    public boolean recognizes(NmeaLine line) {
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
    public GPGSA parse(NmeaLineScanner scanner) {
        return new GPGSA(
                GPGSA.FixMode.fromString(scanner.next()),
                GPGSA.FixType.fromString(scanner.next()),
                parseSatelliteChannels(scanner),
                scanner.nextDouble(),
                scanner.nextDouble(),
                scanner.nextDouble());
    }
}
