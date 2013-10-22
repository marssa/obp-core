package org.marssa.web.gps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-22
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
public class GpsCoordinatesDto {
    double latitude;
    double longitude;
}
