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

import static org.obp.AttributeNames.*;

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
            @Value("${obp.local.nmea.lcjCv3fm6.uuid}") UUID uuid,
            @Value("${obp.local.nmea.lcjCv3fm6.name}") String name,
            @Value("${obp.local.nmea.lcjCv3fm6.description}") String description,
            @Value("${obp.local.nmea.lcjCv3fm6.device}") String deviceUri) {

        super(uuid, name, description, deviceUri, WIND_TEMPERATURE, WIND_ANGLE, WIND_SPEED);
    }

    @PostConstruct
    public void init() {
        initLineListener(deviceFinder, deviceUri);
    }

    @Override
    protected void parseLine(NmeaLine line) {
        if(parserWIXDR.recognizes(line)) {
            updateInstrumentAttributes(parserWIXDR.parse(line.scanner()));
        } else if(parserIIMWV.recognizes(line)) {
            updateInstrumentAttributes(parserIIMWV.parse(line.scanner()));
        }
        reliability = Reliability.HIGH;
    }

    @PreDestroy
    public void destroy() {
        destroyLineListener();
    }

}
