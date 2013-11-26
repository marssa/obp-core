package org.obp.web;

import org.obp.LocalObpInstance;
import org.obp.gps.NmeaGpsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Controller
public class ObpController {

    @Autowired
    private LocalObpInstance obpInstance;

    @Autowired
    private NmeaGpsReceiver gpsReceiver;

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
        model.addAllAttributes(gpsReceiver.getAttributes(LATITUDE, LONGITUDE));
        return "simple/map";
    }

    @ResponseBody
    @RequestMapping("/simple/viewDataFeed")
    public Map<String, Object> all() {
        return gpsReceiver.getAttributes(LATITUDE,LONGITUDE,VELOCITY_OVER_GROUND,TRUE_NORTH_COURSE);
    }
}
