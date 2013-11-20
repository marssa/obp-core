package org.obp.web.gps;

import org.obp.gps.GpsReceiver;
import org.obp.gps.GpsSatellite;
import org.obp.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public GpsCoordinatesDto position() {
        GpsCoordinatesDto dto = new GpsCoordinatesDto();
        dto.latitude = gpsReceiver.getLatitude();
        dto.longitude = gpsReceiver.getLongitude();
        return dto;
    }

    @ResponseBody
    @RequestMapping(value = {"/api/gps/all","/simple/gps/all"})
    public GpsAllDto all() {
        GpsAllDto dto = new GpsAllDto();
        dto.fixTime = TimeUtils.toUtcString(gpsReceiver.getFixTime());
        dto.latitude = gpsReceiver.getLatitude();
        dto.longitude = gpsReceiver.getLongitude();
        dto.trueNorthCourse = gpsReceiver.getTrueNorthCourse();
        dto.velocityOverGround = gpsReceiver.getVelocityOverGround();
        dto.satellitesInView = repack(gpsReceiver.getSatellitesInView());
        dto.numSatellitesInView = gpsReceiver.getNumberOfSatellitesInView();
        dto.fixQuality = gpsReceiver.getFixQuality();
        dto.fixMode = gpsReceiver.getFixMode();
        dto.fixType = gpsReceiver.getFixType();
        dto.altitude = gpsReceiver.getAltitude();
        dto.pdop = gpsReceiver.getPDop();
        dto.hdop = gpsReceiver.getHDop();
        dto.vdop = gpsReceiver.getVDop();
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
