package org.obp.nmea;

import gnu.io.CommPortIdentifier;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-5
 */

@Service
public class NmeaDeviceFinder {
    public static final int MESSAGES_TO_READ = 10;

    private static Logger logger = Logger.getLogger(NmeaDeviceFinder.class);

    private List<CommPortIdentifier> findMatchingIdentifiers(String devicePrefix) {
        List<CommPortIdentifier> matches = new ArrayList<>();
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while(portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if(portIdentifier.getName().startsWith(devicePrefix) && !portIdentifier.isCurrentlyOwned()) {
                matches.add(portIdentifier);
            }
        }
        return matches;
    }

    private boolean listenAndValidate(String portName, Set<String> requiredMessages) throws Exception {
        try {
            NmeaDevice device = new NmeaDevice(portName);
            if(device==null) return false;

            Set<String> remaining = new HashSet<>(requiredMessages);
            try(NmeaBufferedReader reader = device.getReader()) {
                for(int i=0; i<MESSAGES_TO_READ; i++) {
                    NmeaLine line = reader.fetchLine();
                    remaining.remove(line.getName());
                    if(remaining.isEmpty()) {
                        logger.debug("all required messages found");
                        return true;
                    }
                }
            } finally {
                device.close();
            }

            logger.debug("following messages not found: "+remaining);
            return false;

        } catch(Exception e) {
            logger.error("error auto-sensing port "+portName,e);
            return false;
        }
    }

    /**
     * synchronized on purpose, to avoid possible race conditions
     */
    public synchronized NmeaDevice find(String devicePrefix, Set<String> requiredMessages) throws Exception {
        logger.info("finding NMEA device on port "+devicePrefix+"* supporting "+requiredMessages+" ...");
        List<CommPortIdentifier> matches = findMatchingIdentifiers(devicePrefix);
        if(!matches.isEmpty()) {
            for(CommPortIdentifier commPortIdentifier : matches) {
                logger.debug("checking device "+commPortIdentifier.getName());
                if(listenAndValidate(commPortIdentifier.getName(), requiredMessages)) {
                    logger.info("found "+commPortIdentifier.getName());
                    return new NmeaDevice(commPortIdentifier.getName());
                }
            }
        }

        logger.error("matching device not found");
        return null;
    }

}
