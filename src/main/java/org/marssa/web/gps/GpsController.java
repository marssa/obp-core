package org.marssa.web.gps;

import org.marssa.services.gps.GpsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
public class GpsController {

    @Autowired
    private GpsReceiver gpsReceiver;

    @ResponseBody
    @RequestMapping("/api/gps/position")
    public GpsPositionDto position() {
        GpsPositionDto dto = new GpsPositionDto();
        dto.latitude = Double.toString(gpsReceiver.getLatitude());
        dto.longitude = Double.toString(gpsReceiver.getLongitude());
        dto.fixTime = DateFormat.getTimeInstance().format(gpsReceiver.getFixTime());
        return dto;
    }

    @RequestMapping("/liveGpsData")
    public ModelAndView liveData() {
        ModelAndView mav = new ModelAndView("liveGpsData");
        return mav;
    }
}
