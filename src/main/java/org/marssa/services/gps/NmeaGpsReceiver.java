package org.marssa.services.gps;

import org.apache.log4j.Logger;
import org.marssa.nmea.GPGLL;
import org.marssa.nmea.GPGLLParser;
import org.marssa.nmea.NmeaBufferedReader;
import org.marssa.nmea.NmeaDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
@Service
public class NmeaGpsReceiver implements GpsReceiver {
    private static Logger logger = Logger.getLogger(NmeaGpsReceiver.class);

    private NmeaDevice device;
    private Listener listener;

    private double latitude;
    private double longitude;
    private long fixTime;

    @Autowired
    private GPGLLParser gpgllParser;

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
                        if(gpgllParser.matchesLine(reader.getLine())) {
                            GPGLL gpgll = gpgllParser.parseLine(reader.getLine());
                            latitude = gpgll.getLatitude();
                            longitude = gpgll.getLongitude();
                            fixTime = gpgll.getFixTime();
                            logger.debug("GPGLL: "+gpgll.getLatitude()+" "+gpgll.getLongitude()+" "+ DateFormat.getTimeInstance().format(new Date(fixTime)));
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
        device = new NmeaDevice("/dev/cu.usbserial");
        listener = new Listener();
        new Thread(listener).start();
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        listener.stop();
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public long getFixTime() {
        return fixTime;
    }
}
