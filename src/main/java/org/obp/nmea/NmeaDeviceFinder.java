package org.obp.nmea;

import jssc.SerialPortList;
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

    private boolean listenAndValidate(String portName, Set<String> requiredMessages) throws Exception {
        try {
            Set<String> remaining = new HashSet<>(requiredMessages);
            try(NmeaDevice device = NmeaDevice.createAndOpen(portName)) {
                if(device.isOpened()) {
                    NmeaBufferedReader reader = device.getReader();
                    for(int i=0; i<MESSAGES_TO_READ; i++) {
                        NmeaLine line = reader.fetchLine();
                        remaining.remove(line.getName());
                        if(remaining.isEmpty()) {
                            logger.debug("all required messages found");
                            return true;
                        }
                    }
                }
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
    public synchronized NmeaDevice findAndOpen(String deviceUri) throws Exception {
        String[] tokens = deviceUri.split(" ");
        if(tokens.length==1) {
            return NmeaDevice.createAndOpen(tokens[0]);
        }

        String devicePrefix = tokens[0];
        Set<String> requiredMessages = new HashSet<>(Arrays.asList(tokens).subList(1,tokens.length));
        logger.info("finding NMEA device starting with "+ devicePrefix +" supporting "+requiredMessages+" ...");

        List<String> portNames = findMatchingIdentifiers(devicePrefix);
        if(!portNames.isEmpty()) {
            for(String portName : portNames) {
                logger.debug("checking device "+portName);
                if(listenAndValidate(portName, requiredMessages)) {
                    logger.info("found "+portName);
                    return NmeaDevice.createAndOpen(portName);
                }
            }
        }

        logger.error("matching device not found");
        return null;
    }

    private List<String> findMatchingIdentifiers(String namePrefix) {
        List<String> matches = new ArrayList<>();
        for(String portName : SerialPortList.getPortNames()) {
            if(portName.startsWith(namePrefix)) {
                matches.add(portName);
            }
        }
        return matches;
    }

}
