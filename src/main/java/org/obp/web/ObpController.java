package org.obp.web;

import org.obp.Attributes;
import org.obp.LocalObpInstance;
import org.obp.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Controller
public class ObpController {

    @Autowired
    private LocalObpInstance obp;

    @RequestMapping("/simple/manifest")
    public String realmDetails(ModelMap model) {
        model.addAttribute("realm", obp);
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

    @RequestMapping("/simple/position")
    public String simplePositionDetails() {
        return "simple/position";
    }

    @RequestMapping("/simple/navigation")
    public String simpleNavigationDetails(ModelMap model) {
        Attributes attributes = obp.getAttributes().filter(SPEED_OVER_GROUND,TRUE_NORTH_COURSE,LONGITUDE,LATITUDE);
        model.addAttribute("sog", attributes.formatKnots(SPEED_OVER_GROUND));
        model.addAttribute("cog", attributes.formatAngle(TRUE_NORTH_COURSE));
        model.addAttribute("position", formatPosition(attributes));
        return "simple/navigation";
    }

    @RequestMapping("/simple/wind")
    public String simpleWindDetails(ModelMap model) {
        Attributes attributes = obp.getAttributes().filter(WIND_TEMPERATURE,WIND_SPEED,WIND_ANGLE);
        model.addAttribute("speed", attributes.formatKnots(WIND_SPEED));
        model.addAttribute("angle", attributes.formatAngle(WIND_ANGLE));
        model.addAttribute("temperature", attributes.formatTemperature(WIND_TEMPERATURE));
        return "simple/wind";
    }

    @RequestMapping("/simple/map")
    public String simpleMap(ModelMap model) {
        model.addAllAttributes(obp.getAttributes().filter(LATITUDE, LONGITUDE));
        return "simple/map";
    }

    private String formatPosition(Attributes attributes) {
        if(attributes.containsAllKeys(LATITUDE,LONGITUDE)) {
            return LatitudeUtil.toStringShort(attributes.getDouble(LATITUDE))+" "+
                    LongitudeUtil.toStringShort(attributes.getDouble(LONGITUDE));
        } else {
            return "n/a";
        }
    }

    @ResponseBody
    @RequestMapping("/simple/viewDataFeed")
    public Map<String,Object> all() {
        Attributes attributes = obp.resolveAttributes(
                LATITUDE, LONGITUDE, SPEED_OVER_GROUND, TRUE_NORTH_COURSE, WIND_SPEED, WIND_TEMPERATURE, TIME);
        Map<String,Object> map = new HashMap<>();
        map.put("sog", attributes.formatKnots(SPEED_OVER_GROUND));
        map.put("cog", attributes.formatAngle(TRUE_NORTH_COURSE));
        map.put("wind", attributes.formatKnots(WIND_SPEED));
        map.put("position", formatPosition(attributes));
        map.put("latitude", attributes.getDouble(LATITUDE));
        map.put("longitude", attributes.getDouble(LONGITUDE));
        map.put("water","n/a");
        map.put("depth","n/a");
        map.put("dateTime", attributes.formatDateTime(TIME));
        return map;
    }
}
