package org.marssa.services.gps;

import org.apache.log4j.Logger;
import org.marssa.nmea.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
    private ConcurrentMap<Byte,GPGSV.SV> receivedSVs = new ConcurrentHashMap<>();

    @Value("${marssa.gps.nmea.portName}")
    private String portName;

    @Autowired
    private ParserGPGLL parserGPGLL;

    @Autowired
    private ParserGPVTG parserGPVTG;

    @Autowired
    private ParserGPGSV parserGPGSV;

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
                            logger.info(line);
                            lastGPVGT.set(parserGPVTG.parseLine(line));
                        } else if(parserGPGSV.matchesLine(line)) {
                            updateReceivedSvs(parserGPGSV.parseLine(line));
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

    private void updateReceivedSvs(GPGSV msg) {
        for(int i=0; i<msg.getSvSize(); i++) {
            GPGSV.SV sv = msg.getSv(i);
            receivedSVs.putIfAbsent(sv.getId(),sv);
        }
    }

    @PostConstruct
    public void init() throws Exception {
        lastGPGLL.set(GPGLL.DUMMY);
        lastGPVGT.set(GPVTG.DUMMY);
        logger.info("init NMEA GPS receiver at port "+portName);
        try {
            device = new NmeaDevice(portName);
            listener = new Listener();
            new Thread(listener,"NMEA GPS listener").start();
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
    public List<GpsSatellite> getSatellitesInView() {
        List<GpsSatellite> list = new ArrayList<>();
        for(Map.Entry<Byte,GPGSV.SV> entry : receivedSVs.entrySet()) {
            GPGSV.SV sv = entry.getValue();
            list.add(new GpsSatellite(sv.getId(),sv.getElevation(),sv.getAzimuth(),sv.getSnr()));
        }
        return list;
    }

    @Override
    public long getFixTime() {
        return lastGPGLL.get().getFixTime();
    }
}
