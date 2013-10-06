package org.marssa.nmea;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaBufferedReader implements AutoCloseable {

    public static final Pattern LINE_PATTERN = Pattern.compile("^[$|!](\\w{5})[,]([\\w|,|.|-]{0,72})(?:[*]([A-F0-9]{2})){0,1}$");
    public static final int LINE_NAME_GROUP = 1;
    public static final int LINE_DATA_GROUP = 2;
    public static final int LINE_CHECKSUM_GROUP = 3;
    public static final String DATA_SEPARATOR = ",";

    private static Logger logger = Logger.getLogger(NmeaBufferedReader.class);

    private BufferedReader reader;
    private NmeaLine lastLine;

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
        if(matcher.groupCount() >= LINE_CHECKSUM_GROUP) {
            String str = matcher.group(LINE_CHECKSUM_GROUP);
            if(str!=null) {
                byte checksum = Byte.parseByte(str,16);
                int start = matcher.start(LINE_NAME_GROUP);
                int end = matcher.start(LINE_CHECKSUM_GROUP)-1;
                return checksum == calculateChecksum(line.substring(start,end));
            }
        }
        return true;
    }

    public boolean lineReady() throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            Matcher matcher = LINE_PATTERN.matcher(line);
            if(matcher.matches()) {
                if(lineIsValid(line, matcher)) {
                    lastLine = new NmeaLine(
                            matcher.group(LINE_NAME_GROUP),
                            matcher.group(LINE_DATA_GROUP).split(DATA_SEPARATOR));
                    return true;
                } else {
                    logger.warn("checksum error in line: "+line);
                }
            } else {
                logger.warn("malformed line: "+line);
            }
        }
        return false;
    }

    public NmeaLine getLine() {
        return lastLine;
    }

    public NmeaLine fetchLine() throws IOException {
        if(lineReady()) {
            return getLine();
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
