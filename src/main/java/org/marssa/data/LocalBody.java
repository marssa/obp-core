package org.marssa.data;

import org.marssa.gps.NmeaGpsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Component
public class LocalBody extends Body {

    @Autowired
    public LocalBody(
            @Value("${marssa.local.uuid}") String uuid,
            @Value("${marssa.local.name}") String name,
            @Value("${marssa.local.description}") String description) {
        super(UUID.fromString(uuid), name, description);
    }

    @Autowired
    private NmeaGpsReceiver nmeaGpsReceiver;

    @PostConstruct
    protected void init() {
        attachInstrument(nmeaGpsReceiver);
    }
}
