package org.marssa.web.gps;

import org.marssa.gps.GpsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-22
 */
@Controller
public class MapController {

    @Autowired
    private GpsReceiver gpsReceiver;

    @RequestMapping("/simpleMapView")
    public String simpleMapView(ModelMap model) {
        model.addAttribute("latitude", gpsReceiver.getLatitude());
        model.addAttribute("longitude", gpsReceiver.getLongitude());
        return "simpleMapView";
    }
}
