package org.marssa.web.obp;

import org.marssa.gps.GpsReceiver;
import org.marssa.obp.LocalObpInstance;
import org.marssa.obp.ObpInstance;
import org.marssa.utils.*;
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

    @RequestMapping("/realm/details")
    public String realmDetails(ModelMap model) {
        model.addAttribute("realm", obpInstance);
        return "realmDetails";
    }

    @RequestMapping("/simple/start")
    public String simpleStart() {
        return "simpleStart";
    }

    @RequestMapping("/simple/view")
    public String simpleSelection() {
        return "simpleView";
    }

    @RequestMapping("/simple/positionDetails")
    public String simplePositionDetails() {
        return "simplePositionDetails";
    }

    @RequestMapping("/simple/map")
    public String simpleMap(ModelMap model) {
        model.addAttribute("latitude", gpsReceiver.getLatitude());
        model.addAttribute("longitude", gpsReceiver.getLongitude());
        return "simpleMap";
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
