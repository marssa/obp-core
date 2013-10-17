package org.marssa.web.gps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class GpsPositionDto {
    double latitude;
    double longitude;
    String fixTime;
    double trueNorthCourse;
    double velocityOverGround;
    List<GpsSatelliteDto> satellitesInView = new ArrayList<>();
}
