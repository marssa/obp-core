package org.marssa.services.gps;

import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */

public interface GpsReceiver {
    public double getLatitude();
    public double getLongitude();
    public long getFixTime();
}
