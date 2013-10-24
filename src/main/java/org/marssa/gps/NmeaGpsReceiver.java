package org.marssa.gps;

import org.apache.log4j.Logger;
import org.marssa.data.Vicinity;
import org.marssa.data.Instrument;
import org.marssa.nmea.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.marssa.data.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
@Service
public class NmeaGpsReceiver extends Instrument implements GpsReceiver {
    private static Logger logger = Logger.getLogger(NmeaGpsReceiver.class);

    private NmeaDevice device;
    private Listener listener;

    private AggregateGPGSV aggregateGPGSV = new AggregateGPGSV();
    private volatile long fixTime;
    private volatile double latitude;
    private volatile double longitude;
    private volatile double trueNorthCourse;
    private volatile double velocityOverGround;
    private GPGGA.FixQuality fixQuality;
    private GPGSA.FixMode fixMode;
    private GPGSA.FixType fixType;
    private byte numberOfSatellitesInView;
    private double pdop;
    private double hdop;
    private double vdop;
    private double altitude;

    @Value("${marssa.local.nmeaGpsReceiver.uri}")
    private String uri;

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
    private Vicinity vicinity;

    @Autowired
    public NmeaGpsReceiver(
            @Value("${marssa.local.nmeaGpsReceiver.uuid}") UUID uuid,
            @Value("${marssa.local.nmeaGpsReceiver.name}") String name,
            @Value("${marssa.local.nmeaGpsReceiver.description}") String description) {
        super(uuid, name, description, Arrays.asList(LATITUDE,LONGITUDE,ALTITUDE, VELOCITY_OVER_GROUND,TRUE_NORTH_COURSE));
    }

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
                        NmeaLineScanner scanner = line.scanner();
                        logger.debug(line);
                        if(parserGPGLL.matchesLine(line)) {
                            GPGLL gpgll = parserGPGLL.parseLine(scanner);
                            fixTime = gpgll.getFixTime();
                            latitude = gpgll.getLatitude();
                            longitude = gpgll.getLongitude();
                            updateInstrumentData();
                        } else if(parserGPVTG.matchesLine(line)) {
                            GPVTG gpvtg = parserGPVTG.parseLine(scanner);
                            trueNorthCourse = gpvtg.getTrueNorthCourse();
                            velocityOverGround = gpvtg.getVelocityOverGround();
                            updateInstrumentData();
                        } else if(parserGPGGA.matchesLine(line)) {
                            GPGGA gpgga = parserGPGGA.parseLine(scanner);
                            fixTime = gpgga.getFixTime();
                            latitude = gpgga.getLatitude();
                            longitude = gpgga.getLongitude();
                            numberOfSatellitesInView = gpgga.getNumberOfSatellitesInView();
                            hdop = gpgga.getHdop();
                            altitude = gpgga.getAltitude();
                            fixQuality = gpgga.getFixQuality();
                            updateInstrumentData();
                        } else if(parserGPRMC.matchesLine(line)) {
                            GPRMC gprmc = parserGPRMC.parseLine(scanner);
                            fixTime = gprmc.getFixTime();
                            latitude = gprmc.getLatitude();
                            longitude = gprmc.getLongitude();
                            trueNorthCourse = gprmc.getTrueNorthCourse();
                            velocityOverGround = gprmc.getVelocityOverGround();
                            updateInstrumentData();
                        } else if(parserGPGSA.matchesLine(line)) {
                            GPGSA gpgsa = parserGPGSA.parseLine(scanner);
                            fixMode = gpgsa.getFixMode();
                            fixType = gpgsa.getFixType();
                            pdop = gpgsa.getPdop();
                            hdop = gpgsa.getHdop();
                            vdop = gpgsa.getVdop();
                            numberOfSatellitesInView = gpgsa.getNumberOfSatellitesInView();
                            updateInstrumentData();
                        } else if(parserGPGSV.matchesLine(line)) {
                            aggregateGPGSV.update(parserGPGSV.parseLine(scanner));
                            updateInstrumentData();
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

    protected void updateInstrumentData() {
        setAttribute(LATITUDE, latitude);
        setAttribute(LONGITUDE, longitude);
        setAttribute(ALTITUDE, altitude);
        setAttribute(TRUE_NORTH_COURSE, trueNorthCourse);
        setAttribute(VELOCITY_OVER_GROUND, velocityOverGround);
        touch();
    }

    @PostConstruct
    public void init() throws Exception {
        logger.info("init NMEA GPS receiver at port "+ uri);
        try {
            device = new NmeaDevice(uri);
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
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
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
    public GPGGA.FixQuality getFixQuality() {
        return fixQuality;
    }

    @Override
    public GPGSA.FixMode getFixMode() {
        return fixMode;
    }

    @Override
    public GPGSA.FixType getFixType() {
        return fixType;
    }

    @Override
    public byte getNumberOfSatellitesInView() {
        return numberOfSatellitesInView;
    }

    @Override
    public double getPDop() {
        return pdop;
    }

    @Override
    public double getVDop() {
        return vdop;
    }

    @Override
    public double getHDop() {
        return hdop;
    }

    @Override
    public double getAltitude() {
        return altitude;
    }

    @Override
    public long getFixTime() {
        return fixTime;
    }


}