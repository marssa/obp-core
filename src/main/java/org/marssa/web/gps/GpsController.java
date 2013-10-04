package org.marssa.web.gps;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
@RequestMapping("/api/gps")
public class GpsController {

    @ResponseBody
    @RequestMapping(value = "/position")
    public GpsPositionDto position() {
        GpsPositionDto dto = new GpsPositionDto();
        dto.latitude = "12.123123 N";
        dto.longitude = "56.12 E";
        dto.utc = "23:02:22";
        return dto;
    }
}
