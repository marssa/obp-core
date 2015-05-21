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

package org.obp.log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    public Date getTimestampAsDate() {
        return new Date(timestamp);
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
