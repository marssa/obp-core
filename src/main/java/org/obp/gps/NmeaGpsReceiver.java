package org.obp.gps;

import org.apache.log4j.Logger;
import org.obp.BaseInstrument;
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
import java.util.concurrent.CountDownLatch;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
@Component
public class NmeaGpsReceiver extends BaseInstrument {
    private static Logger logger = Logger.getLogger(NmeaGpsReceiver.class);

    private Listener listener;
    private AggregateGPGSV aggregateGPGSV = new AggregateGPGSV();

    @Value("${obp.local.nmea.gpsReceiver.device}")
    private String uri;

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
            @Value("${obp.local.nmea.gpsReceiver.description}") String description) {
        super(uuid, name, description, Arrays.asList(LATITUDE,LONGITUDE,ALTITUDE, SPEED_OVER_GROUND,TRUE_NORTH_COURSE));
    }

    class Listener implements Runnable {

        private volatile boolean stop;
        private CountDownLatch done;
        private String portName;

        Listener(String deviceDescription) throws Exception {
            this.portName = deviceFinder.find(deviceDescription);
        }

        public void stop() throws InterruptedException {
            stop = true;
            done.await();
        }

        public boolean isPortFound() {
            return portName != null;
        }

        @Override
        public void run() {
            logger.info("starting "+getName()+" ...");
            try(NmeaDevice device = NmeaDevice.createAndOpen(portName)) {
                if(device.isOpened()) {
                    NmeaBufferedReader reader = device.getReader();
                    done = new CountDownLatch(1);
                    stop = false;
                    try {
                        while(!stop && reader.lineReady()) {
                            NmeaLine line = reader.getLine();
                            NmeaLineScanner scanner = line.scanner();
                            logger.debug(line);
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

    @PostConstruct
    public void init() throws Exception {
        logger.info("init NMEA GPS receiver");
        try {
            listener = new Listener(uri);
            if(listener.isPortFound()) {
                new Thread(listener,getName()+"-listener").start();
                setStatus(Status.OPERATIONAL);
            } else {
                setStatus(Status.OFF);
            }
        } catch(Exception e) {
            logger.error("error binding to NMEA device: "+e.getMessage());
            setStatus(Status.MALFUNCTION);
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
