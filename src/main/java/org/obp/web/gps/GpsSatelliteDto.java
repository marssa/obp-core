package org.obp.web.gps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class GpsSatelliteDto {
    int id;
    double elevation;
    double azimuth;
    double snr;
}
