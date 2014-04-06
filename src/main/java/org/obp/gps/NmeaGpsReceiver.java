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

package org.obp.gps;

import org.apache.log4j.Logger;
import org.obp.Reliability;
import org.obp.nmea.NmeaBaseInstrument;
import org.obp.nmea.NmeaDeviceFinder;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.NmeaLineScanner;
import org.obp.nmea.parser.*;
import org.obp.utils.GpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
@Component
public class NmeaGpsReceiver extends NmeaBaseInstrument {
    private static Logger logger = Logger.getLogger(NmeaGpsReceiver.class);

    private AggregateGPGSV aggregateGPGSV = new AggregateGPGSV();

    @Value("${obp.local.nmea.gpsReceiver.device}")
    private String deviceUri;

    @Autowired
    private NmeaDeviceFinder deviceFinder;

    @Autowired
    private ParserGPGLL parserGPGLL;

    @Autowired
    private ParserGPVTG parserGPVTG;

    @Autowired
    private ParserGPGSV parserGPGSV;

    @Autowired
    private ParserGPRMC parserGPRMC;

    @Autowired
    private ParserGPGGA parserGPGGA;

    @Autowired
    private ParserGPGSA parserGPGSA;

    @Autowired
    public NmeaGpsReceiver(
            @Value("${obp.local.nmea.gpsReceiver.uuid}") UUID uuid,
            @Value("${obp.local.nmea.gpsReceiver.name}") String name,
            @Value("${obp.local.nmea.gpsReceiver.description}") String description,
            @Value("${obp.local.nmea.gpsReceiver.device}}") String deviceUri) {
        super(uuid, name, description, deviceUri,LATITUDE,LONGITUDE,ALTITUDE, SPEED_OVER_GROUND,TRUE_NORTH_COURSE);
    }

    @Override
    protected void parseLine(NmeaLine line) {
        NmeaLineScanner scanner = line.scanner();

        if(parserGPGLL.recognizes(line)) {
            updateReadouts(parserGPGLL.parse(scanner));
        } else if(parserGPVTG.recognizes(line)) {
            updateReadouts(parserGPVTG.parse(scanner));
        } else if(parserGPGGA.recognizes(line)) {
            updateReadouts(parserGPGGA.parse(scanner));
        } else if(parserGPRMC.recognizes(line)) {
            updateReadouts(parserGPRMC.parse(scanner));
        } else if(parserGPGSA.recognizes(line)) {
            updateReadouts(parserGPGSA.parse(scanner));
        } else if(parserGPGSV.recognizes(line)) {
            if(aggregateGPGSV.update(parserGPGSV.parse(scanner))) {
                updateReadouts(aggregateGPGSV.toAttributes());
            }
        }
    }

    public boolean isPositionReceived() {
        return getReadouts().filter(LATITUDE, LONGITUDE).size()==2;
    }

    @PostConstruct
    public void init() {
        initLineListener(deviceFinder, deviceUri);
    }

    @PreDestroy
    public void destroy() {
        destroyLineListener();
    }

    @Override
    public Reliability getReliability() {
        Reliability reliability = GpsUtil.estimateReliability(getReadouts());
        return reliability==Reliability.UNDEFINED ? Reliability.GOOD : reliability;
    }
}
