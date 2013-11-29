package org.obp.gps;

import org.obp.Attributes;
import org.obp.nmea.parser.GPGSV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-17
 */
public class AggregateGPGSV {
    private Map<Byte,GPGSV.SV> uniqueSvs = new HashMap<>();
    private AtomicReference<List<GPGSV.SV>> visibleSatellites = new AtomicReference<List<GPGSV.SV>>(new ArrayList<GPGSV.SV>());

    public boolean update(GPGSV msg) {
        for(int i=0; i<msg.getSvSize(); i++) {
            GPGSV.SV sv = msg.getSv(i);
            uniqueSvs.put(sv.getId(), sv);
        }

        if(msg.getSentenceNumber()==msg.getTotalSentences()) {
            visibleSatellites.set(new ArrayList<>(uniqueSvs.values()));
            uniqueSvs.clear();
            return true;
        }

        return false;
    }

    public List<GpsSatellite> getVisibleSatellites() {
        List<GpsSatellite> list = new ArrayList<>();
        for(GPGSV.SV sv : visibleSatellites.get()) {
            list.add(new GpsSatellite(sv.getId(),sv.getElevation(),sv.getAzimuth(),sv.getSnr()));
        }
        return list;
    }

    public Attributes toAttributes() {
        Attributes am = new Attributes();
        am.put(GPS_VISIBLE_SATELLITES, (byte)visibleSatellites.get().size());
        List<Map<String,Object>> satellites = new ArrayList<>();
        for(GPGSV.SV sv : visibleSatellites.get()) {
            Map<String,Object> satellite = new HashMap<>();
            satellite.put(GPS_SATELLITE_ID, sv.getId());
            satellite.put(GPS_SATELLITE_ELEVATION, sv.getElevation());
            satellite.put(GPS_SATELLITE_AZIMUTH, sv.getAzimuth());
            satellite.put(GPS_SATELLITE_SNR, sv.getSnr());
            satellites.add(satellite);
        }
        am.put(GPS_SATELLITES, satellites);
        return am;
    }
}
