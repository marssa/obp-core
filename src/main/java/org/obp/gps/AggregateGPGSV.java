package org.obp.gps;

import org.obp.AttributeMap;
import org.obp.nmea.message.GPGSV;

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
    private AtomicReference<List<GPGSV.SV>> satellitesInView = new AtomicReference<List<GPGSV.SV>>(new ArrayList<GPGSV.SV>());

    public void update(GPGSV msg) {
        for(int i=0; i<msg.getSvSize(); i++) {
            GPGSV.SV sv = msg.getSv(i);
            uniqueSvs.put(sv.getId(), sv);
        }

        if(msg.getSentenceNumber()==msg.getTotalSentences()) {
            satellitesInView.set(new ArrayList<>(uniqueSvs.values()));
            uniqueSvs.clear();
        }
    }

    public List<GpsSatellite> getSatellitesInView() {
        List<GpsSatellite> list = new ArrayList<>();
        for(GPGSV.SV sv : satellitesInView.get()) {
            list.add(new GpsSatellite(sv.getId(),sv.getElevation(),sv.getAzimuth(),sv.getSnr()));
        }
        return list;
    }

    public AttributeMap toAttributeMap() {
        AttributeMap am = new AttributeMap();
        am.put(GPS_EFFECTIVE_SATELLITES, satellitesInView.get().size());
        for(GPGSV.SV sv : satellitesInView.get()) {
            am.put(GPS_SATELLITE_ELEVATION + sv.getId(), sv.getElevation());
            am.put(GPS_SATELLITE_AZIMUTH + sv.getId(), sv.getAzimuth());
            am.put(GPS_SATELLITE_SNR + sv.getId(), sv.getSnr());
        }
        return am;
    }
}
