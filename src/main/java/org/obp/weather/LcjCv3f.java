package org.obp.weather;

import org.apache.log4j.Logger;
import org.obp.BaseInstrument;
import org.obp.LocalObpInstance;
import org.obp.Reliability;
import org.obp.nmea.NmeaBufferedReader;
import org.obp.nmea.NmeaDevice;
import org.obp.nmea.NmeaLine;
import org.obp.nmea.parser.ParserIIMWV;
import org.obp.nmea.parser.ParserWIXDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 *
 * LCJ Capteurs CV3Fm6 - wind vane
 */

@Component
public class LcjCv3f extends BaseInstrument {

    private static Logger logger = Logger.getLogger(LcjCv3f.class);

    private Listener listener;

    @Value("${obp.local.nmea.lcj_cv3fm6.uri}")
    private String uri;

    @Autowired
    private ParserWIXDR parserWIXDR;

    @Autowired
    private ParserIIMWV parserIIMWV;

    @Autowired
    public LcjCv3f(
            @Value("${obp.local.nmea.lcj_cv3fm6.uuid}") UUID uuid,
            @Value("${obp.local.nmea.lcj_cv3fm6.name}") String name,
            @Value("${obp.local.nmea.lcj_cv3fm6.description}") String description) {

        super(uuid, name, description, Arrays.asList(WIND_TEMPERATURE));
    }

    @PostConstruct
    public void init() throws Exception {
        logger.info("init "+getName()+" at port "+ uri);
        try {
            listener = new Listener(uri);
            new Thread(listener,getName()+"-listener").start();
            logger.info("started");
            setStatus(Status.OPERATIONAL);
        } catch(Exception e) {
            logger.error("port binding error "+e.getMessage());
            setStatus(Status.MALFUNCTION);
        }
    }

    class Listener implements Runnable {

        private volatile boolean stop;
        private CountDownLatch done;
        private String portName;

        Listener(String portName) {
            this.portName = portName;
        }

        public void stop() throws InterruptedException {
            stop = true;
            done.await();
        }

        @Override
        public void run() {
            logger.info("starting "+getName()+" listener ...");
            try(NmeaDevice device = NmeaDevice.createAndOpen(portName)) {
                if(device.isOpened()) {
                    NmeaBufferedReader reader = device.getReader();
                    done = new CountDownLatch(1);
                    stop = false;
                    try {
                        while(!stop && reader.lineReady()) {
                            NmeaLine line = reader.getLine();
                            if(parserWIXDR.recognizes(line)) {
                                updateStandardInstrumentData(parserWIXDR.parse(line.scanner()));
                            } else if(parserIIMWV.recognizes(line)) {
                                updateStandardInstrumentData(parserIIMWV.parse(line.scanner()));
                            }
                            reliability = Reliability.HIGH;
                        }
                    } catch (Exception e) {
                        logger.fatal("listener error in line "+reader.getLine(),e);
                    } finally {
                        done.countDown();
                    }
                }
            } catch (Exception e) {
                logger.fatal("listener error",e);
            }
            logger.info("stopped.");
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if(listener!=null) {
            listener.stop();
        }
        setStatus(Status.OFF);
    }

}
