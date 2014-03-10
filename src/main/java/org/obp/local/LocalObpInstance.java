package org.obp.local;

import org.apache.log4j.Logger;
import org.obp.*;
import org.obp.dummy.DummyRandomInstrument;
import org.obp.gps.NmeaGpsReceiver;
import org.obp.dummy.DummyRadar;
import org.obp.remote.RemoteObpLocator;
import org.obp.weather.LcjCv3f;
import org.obp.web.config.ObpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Service
public class LocalObpInstance extends BaseObpInstance {
    private static Logger logger = Logger.getLogger(LocalObpInstance.class);

    @Autowired
    private NmeaGpsReceiver nmeaGpsReceiver;

    @Autowired
    private LcjCv3f nmeaWindVane;

    @Autowired
    private ObpConfig config;

    @Autowired
    private RemoteObpLocator remoteObpLocator;

    @Autowired
    public LocalObpInstance(
            @Value("${obp.local.uuid}") UUID uuid,
            @Value("${obp.local.name}") String name,
            @Value("${obp.local.description}") String description) {
        super(uuid, name, description);
    }

    @PostConstruct
    public void init() {
        attachInstrument(nmeaGpsReceiver);
        attachInstrument(nmeaWindVane);
        attachInstrument(new DefaultDataInstrument("/defaults.properties"));
        attachInstrument(new SystemTimeInstrument());
        attachExplorer(new DummyRadar());

        logger.info("init local OBP instance:\n\n"+toString()+"\n");
    }

    @Override
    public URI getUri() {
        return config.getUri();
    }

    @Override
    public boolean isHub() {
        return config.isHub();
    }

    @Override
    public int knownRemotes() {
        return remoteObpLocator.knownRemotes();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name: ").append(getName()).append(" (").append(getUuid()).append(")\n");
        sb.append("type: ").append(isHub() ? "hub" : "standard node").append("\n");
        sb.append("external URI: ").append(getUri());
        return sb.toString();
    }
}
