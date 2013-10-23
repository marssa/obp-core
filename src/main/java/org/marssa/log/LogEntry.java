package org.marssa.log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-23
 */

@Entity
public class LogEntry {
    public static enum Level {
        INFO, WARN, ERROR, FATAL;
    }

    @Id
    @GeneratedValue
    private long id;

    private long timestamp;
    private Level level;
    private String origin;
    private String message;

    LogEntry() {
    }

    public LogEntry(Level level, String origin, String message) {
        this.level = level;
        this.origin = origin;
        this.message = message;
        this.timestamp = DateTime.now(DateTimeZone.UTC).getMillis();
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Level getLevel() {
        return level;
    }

    public String getOrigin() {
        return origin;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "["+level+":"+origin+"] "+message;
    }
}
