package org.marssa.services.gps;

import org.apache.log4j.Logger;
import org.marssa.nmea.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
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

    private AtomicReference<GPGLL> lastGPGLL = new AtomicReference<>(GPGLL.DUMMY);
    private AggregateGPGSV aggregateGPGSV = new AggregateGPGSV();

    private volatile double trueNorthCourse;
    private volatile double velocityOverGround;

    @Value("${marssa.gps.nmea.portName}")
    private String portName;

    @Autowired
    private ParserGPGLL parserGPGLL;

    @Autowired
    private ParserGPVTG parserGPVTG;

    @Autowired
    private ParserGPGSV parserGPGSV;

    @Autowired
    private ParserGPRMC parserGPRMC;

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
                        logger.debug(line);
                        if(parserGPGLL.matchesLine(line)) {
                            lastGPGLL.set(parserGPGLL.parseLine(line));
                        } else if(parserGPVTG.matchesLine(line)) {
                            GPVTG gpvtg = parserGPVTG.parseLine(line);
                            trueNorthCourse = gpvtg.getTrueNorthCourse();
                            velocityOverGround = gpvtg.getVelocityOverGround();
                        } else if(parserGPRMC.matchesLine(line)) {
                            GPRMC gprmc = parserGPRMC.parseLine(line);
                            trueNorthCourse = gprmc.getTrueNorthCourse();
                            velocityOverGround = gprmc.getVelocityOverGround();
                        } else if(parserGPGSV.matchesLine(line)) {
                            aggregateGPGSV.update(parserGPGSV.parseLine(line));
                        }
                    }
                } catch (Exception e) {
                    logger.fatal("listener error in line "+reader.getLine(),e);
                } finally {
                    done.countDown();
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
            new Thread(listener,"listener").start();
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
    public double getTrueNorthCourse() {
        return trueNorthCourse;
    }

    @Override
    public double getVelocityOverGround() {
        return velocityOverGround;
    }

    @Override
    public List<GpsSatellite> getSatellitesInView() {
        return aggregateGPGSV.getSatellitesInView();
    }

    @Override
    public long getFixTime() {
        return lastGPGLL.get().getFixTime();
    }
}
