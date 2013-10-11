package org.marssa.web.gps;

import org.marssa.services.gps.GpsReceiver;
import org.marssa.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
public class GpsController {

    @Value("${build.id}")
    private String buildId;

    @Autowired
    private GpsReceiver gpsReceiver;

    @ResponseBody
    @RequestMapping("/api/gps/position")
    public GpsPositionDto position() {
        GpsPositionDto dto = new GpsPositionDto();
        dto.fixTime = TimeUtil.toUtcString(gpsReceiver.getFixTime());
        dto.latitude = Double.toString(gpsReceiver.getLatitude());
        dto.longitude = Double.toString(gpsReceiver.getLongitude());
        dto.trueNorthHeading = Double.toString(gpsReceiver.getTrueNorthHeading());
        dto.velocityOverGround = Double.toString(gpsReceiver.getVelocityOverGround());
        return dto;
    }

    @RequestMapping("/liveGpsData")
    public ModelAndView liveData() {
        ModelAndView mav = new ModelAndView("liveGpsData");
        mav.addObject("buildId",buildId);
        return mav;
    }
}
