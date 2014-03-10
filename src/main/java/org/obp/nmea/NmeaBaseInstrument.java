package org.obp.nmea;

import org.apache.log4j.Logger;
import org.obp.BaseInstrument;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-16
 */
public abstract class NmeaBaseInstrument extends BaseInstrument {

    private static Logger logger = Logger.getLogger(NmeaBaseInstrument.class);

    private LineListener lineListener;
    private String deviceUri;

    public NmeaBaseInstrument(UUID uuid, String name, String description, String deviceUri, String... keys) {
        super(uuid, name, description);
        initKeys(keys);
        this.deviceUri = deviceUri;
    }

    protected void initLineListener(NmeaDeviceFinder deviceFinder, String deviceUri) {
        logger.info("initKeys "+getName());
        try {
            NmeaDevice device = deviceFinder.findAndOpen(deviceUri);
            if(device==null) {
                logger.error("port not found for pattern: "+deviceUri);
                setStatus(Status.OFF);
                return;
            }

            lineListener = new LineListener(device);
            new Thread(lineListener,getName()+"-listener").start();
            setStatus(Status.OPERATIONAL);

        } catch (Exception e) {
            logger.error("error binding to NMEA device: "+e.getMessage());
            setStatus(Status.MALFUNCTION);
        }
    }

    protected void destroyLineListener() {
        if(lineListener!=null) {
            lineListener.stop();
        }
        setStatus(Status.OFF);
    }

    protected abstract void parseLine(NmeaLine line);

    class LineListener implements Runnable {

        private volatile boolean stop = false;
        private CountDownLatch done = new CountDownLatch(1);
        private NmeaDevice device;

        LineListener(NmeaDevice device) throws Exception {
            this.device = device;
        }

        public void stop() {
            stop = true;
            try {
                done.await();
            } catch (InterruptedException e) {
                logger.error("error stopping listener",e);
            }
        }

        @Override
        public void run() {
            try {
                NmeaBufferedReader reader = device.getReader();
                done = new CountDownLatch(1);
                stop = false;
                try {
                    while(!stop && reader.lineReady()) {
                        parseLine(reader.getLine());
                    }
                } catch (Exception e) {
                    logger.fatal("listener error in line "+reader.getLine(),e);
                } finally {
                    done.countDown();
                }
            } catch (Exception e) {
                logger.fatal("listener error",e);
            } finally {
                device.close();
            }
            logger.info("stopped.");
        }
    }
}
