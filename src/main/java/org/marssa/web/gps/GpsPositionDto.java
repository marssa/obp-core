package org.marssa.web.gps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class GpsPositionDto {
    String latitude;
    String longitude;
    String utc;
}
