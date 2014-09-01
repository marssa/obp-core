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

package org.obp.weather;

import org.apache.log4j.Logger;
import org.obp.Reliability;
import org.obp.nmea.*;
import org.obp.nmea.parser.ParserIIMWV;
import org.obp.nmea.parser.ParserWIXDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 *
 * LCJ Capteurs CV3Fm6 - wind vane
 */

@Component
public class LcjCv3f extends NmeaBaseInstrument {

    private static Logger logger = Logger.getLogger(LcjCv3f.class);

    @Value("${obp.local.nmea.lcjCv3fm6.device}")
    private String deviceUri;

    @Autowired
    private NmeaDeviceFinder deviceFinder;

    @Autowired
    private ParserWIXDR parserWIXDR;

    @Autowired
    private ParserIIMWV parserIIMWV;

    @Autowired
    public LcjCv3f(
            @Value("${obp.local.nmea.lcjCv3fm6.id}") String id,
            @Value("${obp.local.nmea.lcjCv3fm6.name}") String name,
            @Value("${obp.local.nmea.lcjCv3fm6.description}") String description,
            @Value("${obp.local.nmea.lcjCv3fm6.device}") String deviceUri) {

        super(id, name, description, deviceUri, WIND_TEMPERATURE, WIND_ANGLE, WIND_SPEED);
    }

    @PostConstruct
    public void init() {
        initLineListener(deviceFinder, deviceUri);
    }

    @Override
    protected void parseLine(NmeaLine line) {
        if(parserWIXDR.recognizes(line)) {
            updateReadouts(parserWIXDR.parse(line.scanner()));
        } else if(parserIIMWV.recognizes(line)) {
            updateReadouts(parserIIMWV.parse(line.scanner()));
        }
    }

    @PreDestroy
    public void destroy() {
        destroyLineListener();
    }

    @Override
    public Reliability getReliability() {
        return Reliability.HIGH;
    }
}
