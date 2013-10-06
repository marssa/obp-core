package org.marssa.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system time is number of milliseconds since the UNIX epoch
 *
 */

public final class TimeUtil {
    public static final long parseUtcHHMMSS(int hhmmss) {
        MutableDateTime mdt = MutableDateTime.now(DateTimeZone.UTC);
        mdt.setSecondOfMinute(hhmmss % 100);
        mdt.setMinuteOfHour((hhmmss / 100) % 100);
        mdt.setHourOfDay(hhmmss / 10000);
        return mdt.toDateTime().getMillis();
    }
}
