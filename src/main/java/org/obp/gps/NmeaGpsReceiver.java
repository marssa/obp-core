package org.obp.gps;

import org.apache.log4j.Logger;
import org.obp.nmea.*;
import org.obp.nmea.parser.*;
import org.obp.utils.GpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.UUID;

import static org.obp.AttributeNames.*;

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
            updateStandardInstrumentData(parserGPGLL.parse(scanner));
        } else if(parserGPVTG.recognizes(line)) {
            updateStandardInstrumentData(parserGPVTG.parse(scanner));
        } else if(parserGPGGA.recognizes(line)) {
            updateStandardInstrumentData(parserGPGGA.parse(scanner));
        } else if(parserGPRMC.recognizes(line)) {
            updateStandardInstrumentData(parserGPRMC.parse(scanner));
        } else if(parserGPGSA.recognizes(line)) {
            updateStandardInstrumentData(parserGPGSA.parse(scanner));
        } else if(parserGPGSV.recognizes(line)) {
            if(aggregateGPGSV.update(parserGPGSV.parse(scanner))) {
                updateStandardInstrumentData(aggregateGPGSV.toAttributes());
            }
        }

        reliability = GpsUtil.estimateReliability(attributes);
    }

    @PostConstruct
    public void init() {
        initLineListener(deviceFinder, deviceUri);
    }

    @PreDestroy
    public void destroy() {
        destroyLineListener();
    }
}
