package org.obp.web.gps;

import org.obp.gps.GpsSatellite;
import org.obp.gps.NmeaGpsReceiver;
import org.obp.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
public class GpsController {

    @Autowired
    private NmeaGpsReceiver gpsReceiver;

    @ResponseBody
    @RequestMapping("/api/gps/position")
    public Map<String,Object> position() {
        return gpsReceiver.getAttributes(LATITUDE, LONGITUDE);
    }

    @ResponseBody
    @RequestMapping(value = {"/api/gps/all","/simple/gps/all"})
    public Map<String,Object> all() {
        return gpsReceiver.getAllAttributes();
    }

    @RequestMapping("/liveGpsData")
    public String liveData() {
        return "liveGpsData";
    }
}
