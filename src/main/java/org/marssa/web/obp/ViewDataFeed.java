package org.marssa.web.obp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.marssa.web.gps.GpsCoordinatesDto;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class ViewDataFeed extends GpsCoordinatesDto {
    double latitude;
    double longitude;
    String position;
    String heading;
    String speed;
}
