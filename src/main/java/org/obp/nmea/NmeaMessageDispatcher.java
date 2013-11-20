package org.obp.nmea;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */

@Service
public class NmeaMessageDispatcher {
    private ConcurrentMap<String,NmeaMessageListener> subscriptions;

    public void subscribe(NmeaMessageListener listener, String signature) {

    }

    public void unsubscribe(NmeaMessageListener listener, String signature) {

    }

    public void unsubscribe(NmeaMessageListener listener) {

    }
}
