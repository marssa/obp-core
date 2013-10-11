package org.marssa.services.gps;

import org.apache.log4j.Logger;
import org.marssa.nmea.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
@Service
public class NmeaGpsReceiver implements GpsReceiver {
    private static Logger logger = Logger.getLogger(NmeaGpsReceiver.class);

    private NmeaDevice device;
    private Listener listener;

    private AtomicReference<GPGLL> lastGPGLL = new AtomicReference<>();
    private AtomicReference<GPVTG> lastGPVGT = new AtomicReference<>();

    @Value("${marssa.gps.nmea.portName}")
    private String portName;

    @Autowired
    private ParserGPGLL parserGPGLL;

    @Autowired
    private ParserGPVTG parserGPGVT;

    class Listener implements Runnable {

        private volatile boolean stop;
        private CountDownLatch done;

        public void stop() throws InterruptedException {
            stop = true;
            done.await();
        }

        @Override
        public void run() {
            logger.info("starting GPS NMEA listener ...");
            try(NmeaBufferedReader reader = device.getReader()) {
                done = new CountDownLatch(1);
                stop = false;
                try {
                    while(!stop && reader.lineReady()) {
                        NmeaLine line = reader.getLine();
                        if(parserGPGLL.matchesLine(line)) {
                            lastGPGLL.set(parserGPGLL.parseLine(line));
                            logger.debug("parsed: "+lastGPGLL.get());
                        } else if(parserGPGVT.matchesLine(line)) {
                            lastGPVGT.set(parserGPGVT.parseLine(line));
                            logger.debug("parsed: " + lastGPVGT.get());
                        }
                    }
                    done.countDown();
                } catch (IOException e) {
                    logger.fatal("listener error",e);
                }
            } catch (Exception e) {
                logger.fatal("listener error",e);
            }
            logger.info("listener stopped.");
        }
    }

    @PostConstruct
    public void init() throws Exception {
        logger.info("init NMEA GPS receiver at port "+portName);
        try {
            device = new NmeaDevice(portName);
            listener = new Listener();
            new Thread(listener).start();
            logger.info("listener started");
        } catch(Exception e) {
            logger.error("error binding to NMEA device, no live data will be provided.");
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if(listener!=null) {
            listener.stop();
        }
    }

    @Override
    public double getLatitude() {
        return lastGPGLL.get().getLatitude();
    }

    @Override
    public double getLongitude() {
        return lastGPGLL.get().getLongitude();
    }

    @Override
    public double getTrueNorthHeading() {
        return lastGPVGT.get().getTrueNorthHeading();
    }

    @Override
    public double getVelocityOverGround() {
        return lastGPVGT.get().getVelocityOverGround();
    }

    @Override
    public long getFixTime() {
        return lastGPGLL.get().getFixTime();
    }
}
