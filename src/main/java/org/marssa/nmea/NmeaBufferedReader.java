package org.marssa.nmea;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaBufferedReader implements AutoCloseable {

    public static final Pattern NMEA_LINE = Pattern.compile("^[$|!](\\w{2})(\\w{3})[,]([\\w|,|\\.]{0,72})(?:[*]([A-F0-9]{2})){0,1}$");
    public static final int NMEA_TALKER_ID_GROUP = 1;
    public static final int NMEA_TYPE_GROUP = 2;
    public static final int NMEA_DATA_GROUP = 3;
    public static final int NMEA_CHECKSUM_GROUP = 4;
    public static final String NMEA_DATA_SEPARATOR = ",";

    private static Logger logger = Logger.getLogger(NmeaBufferedReader.class);

    private BufferedReader reader;

    public NmeaBufferedReader(InputStream is) {
        reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII));
    }

    private byte calculateChecksum(String str) {
        int checksum = 0;
        if(!str.isEmpty()) {
            checksum = str.charAt(0);
            if(str.length()>1) {
                for(int i=1; i<str.length(); i++) {
                    checksum ^= str.charAt(i);
                }
            }
        }
        return (byte)checksum;
    }

    private boolean lineIsValid(String line, Matcher matcher) {
        if(matcher.groupCount() >= NMEA_CHECKSUM_GROUP) {
            String str = matcher.group(NMEA_CHECKSUM_GROUP);
            if(str!=null) {
                byte checksum = Byte.parseByte(str,16);
                int start = matcher.start(NMEA_TALKER_ID_GROUP);
                int end = matcher.start(NMEA_CHECKSUM_GROUP)-1;
                return checksum == calculateChecksum(line.substring(start,end));
            }
        }
        return true;
    }

    public NmeaMessage readMessage() throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            Matcher matcher = NMEA_LINE.matcher(line);
            if(matcher.matches()) {
                if(lineIsValid(line, matcher)) {
                    return new NmeaMessage(
                            matcher.group(NMEA_TALKER_ID_GROUP),
                            matcher.group(NMEA_TYPE_GROUP),
                            matcher.group(NMEA_DATA_GROUP).split(NMEA_DATA_SEPARATOR));
                } else {
                    logger.warn("checksum error in line: "+line);
                }
            } else {
                logger.warn("malformed line: "+line);
            }
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if(reader!=null) {
            reader.close();
        }
    }
}
