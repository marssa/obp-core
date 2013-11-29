package org.obp.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system time is number of milliseconds since the UNIX epoch
 *
 */

public final class TimeUtil {
    public static final DateTimeFormatter UTC_TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm:ss").withZoneUTC();
    public static final DateTimeFormatter UTC_DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC();

    private TimeUtil() {
    }

    public static final long fromUtcHHMMSS(double time) {
        int hhmmss = (int)time;
        MutableDateTime mdt = MutableDateTime.now(DateTimeZone.UTC);
        mdt.setSecondOfMinute(hhmmss % 100);
        mdt.setMinuteOfHour((hhmmss / 100) % 100);
        mdt.setHourOfDay(hhmmss / 10000);
        mdt.setMillisOfSecond((int)((time - hhmmss) * 1000));
        return mdt.toDateTime().getMillis();
    }

    public static final long currentUtc() {
        return DateTime.now(DateTimeZone.UTC).getMillis();
    }

    public static final String formatTime(long time) {
        return UTC_TIME_FORMATTER.print(time);
    }

    public static final String formatDateTime(long time) {
        return UTC_DATETIME_FORMATTER.print(time);
    }
}
