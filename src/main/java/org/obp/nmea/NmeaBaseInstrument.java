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

package org.obp.nmea;

import org.apache.log4j.Logger;
import org.obp.BaseInstrument;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-16
 */
public abstract class NmeaBaseInstrument extends BaseInstrument {

    private static Logger logger = Logger.getLogger(NmeaBaseInstrument.class);

    private LineListener lineListener;
    private String deviceUri;

    public NmeaBaseInstrument(String id, String name, String description, String deviceUri, String... keys) {
        super(id, name, description);
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
