package org.obp.nmea.parser;

import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineParser;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.message.GPGSV;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */

@Service
public class ParserGPGSV implements NmeaLineParser<GPGSV> {

    @Override
    public boolean recognizes(NmeaLine line) {
        return line.getName().equals(GPGSV.SIGNATURE) && line.getDataSize() >= 7;
    }

    private GPGSV.SV[] parseSvs(NmeaLineScanner scanner) {
        int num = scanner.getTokentLeft() / 4;
        GPGSV.SV sv[] = new GPGSV.SV[num];
        for(int i=0; i<num; i++) {
            sv[i] = new GPGSV.SV(
                    scanner.nextByte(),
                    scanner.nextDoubleOrNaN(),
                    scanner.nextDoubleOrNaN(),
                    scanner.nextDoubleOrNaN());
        }
        return sv;
    }

    @Override
    public GPGSV parse(NmeaLineScanner scanner) {
        return new GPGSV(
                scanner.nextByte(),
                scanner.nextByte(),
                scanner.nextByte(),
                parseSvs(scanner));
    }
}
