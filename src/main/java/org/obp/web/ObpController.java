package org.obp.web;

import org.obp.gps.GpsReceiver;
import org.obp.LocalObpInstance;
import org.obp.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Controller
public class ObpController {

    @Autowired
    private LocalObpInstance obpInstance;

    @Autowired
    private GpsReceiver gpsReceiver;

    @RequestMapping("/simple/manifest")
    public String realmDetails(ModelMap model) {
        model.addAttribute("realm", obpInstance);
        return "simple/manifest";
    }

    @RequestMapping("/simple/start")
    public String simpleStart() {
        return "simple/start";
    }

    @RequestMapping("/simple/view")
    public String simpleSelection() {
        return "simple/main";
    }

    @RequestMapping("/simple/positionDetails")
    public String simplePositionDetails() {
        return "simple/positionDetails";
    }

    @RequestMapping("/simple/map")
    public String simpleMap(ModelMap model) {
        model.addAttribute("latitude", gpsReceiver.getLatitude());
        model.addAttribute("longitude", gpsReceiver.getLongitude());
        return "simple/map";
    }

    private String currentGpsPosition() {
        return LatitudeUtils.toStringShort(gpsReceiver.getLatitude())+" "+
                LongitudeUtil.toStringShort(gpsReceiver.getLongitude());
    }

    @ResponseBody
    @RequestMapping("/simple/viewDataFeed")
    public ViewDataFeed all() {
        ViewDataFeed dto = new ViewDataFeed();
        dto.latitude = gpsReceiver.getLatitude();
        dto.longitude = gpsReceiver.getLongitude();
        dto.position = currentGpsPosition();
        dto.speed = VelocityUtils.toStringKnots(gpsReceiver.getVelocityOverGround());
        dto.heading = Integer.toString((int)gpsReceiver.getTrueNorthCourse());
        return dto;
    }
}
