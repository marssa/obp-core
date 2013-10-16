package org.marssa.web.gps;

import org.marssa.ObpConfig;
import org.marssa.services.gps.GpsReceiver;
import org.marssa.services.gps.GpsSatellite;
import org.marssa.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
        dto.fixTime = TimeUtil.toUtcString(gpsReceiver.getFixTime());
        dto.latitude = gpsReceiver.getLatitude();
        dto.longitude = gpsReceiver.getLongitude();
        dto.trueNorthHeading = gpsReceiver.getTrueNorthHeading();
        dto.velocityOverGround = gpsReceiver.getVelocityOverGround();
        dto.satellitesInView = repack(gpsReceiver.getSatellitesInView());
        return dto;
    }

    private List<GpsSatelliteDto> repack(List<GpsSatellite> satellitesInView) {
        List<GpsSatelliteDto> list = new ArrayList<>();
        for(GpsSatellite sat : satellitesInView) {
            GpsSatelliteDto dto = new GpsSatelliteDto();
            dto.id = sat.getId();
            dto.elevation = sat.getElevation();
            dto.azimuth = sat.getAzimuth();
            dto.snr = sat.getSnr();
            list.add(dto);
        }
        return list;
    }

    @RequestMapping("/liveGpsData")
    public String liveData() {
        return "liveGpsData";
    }
}
