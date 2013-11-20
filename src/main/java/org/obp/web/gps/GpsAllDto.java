package org.obp.web.gps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.obp.nmea.GPGGA;
import org.obp.nmea.GPGSA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class GpsAllDto extends GpsCoordinatesDto {
    String fixTime;
    double trueNorthCourse;
    double velocityOverGround;
    List<GpsSatelliteDto> satellitesInView = new ArrayList<>();
    GPGGA.FixQuality fixQuality;
    GPGSA.FixType fixType;
    GPGSA.FixMode fixMode;
    byte numSatellitesInView;
    double altitude;
    double pdop;
    double hdop;
    double vdop;
}
