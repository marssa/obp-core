/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.nmea.parser;

import org.obp.nmea.NmeaSentence;
import org.obp.nmea.NmeaSentenceParser;
import org.obp.nmea.NmeaSentenceScanner;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */

@Service
public class ParserGPGSV implements NmeaSentenceParser<GPGSV> {

    @Override
    public boolean recognizes(NmeaSentence line) {
        return line.getName().equals(GPGSV.SIGNATURE) && line.getDataSize() >= 7;
    }

    private GPGSV.SV[] parseSvs(NmeaSentenceScanner scanner) {
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
    public GPGSV parse(NmeaSentenceScanner scanner) {
        return new GPGSV(
                scanner.nextByte(),
                scanner.nextByte(),
                scanner.nextByte(),
                parseSvs(scanner));
    }
}
