/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import java.util.concurrent.TimeUnit;

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
            @Value("${obp.local.description}") String description,
            @Value("${obp.local.organization}") String organization) {
        super(uuid, name, description, organization);
    }

    @PostConstruct
    public void init() {
        waitForReliablePosition();

        attachInstrument(nmeaGpsReceiver);
        attachInstrument(nmeaWindVane);
        attachInstrument(new DefaultDataInstrument("/defaults.properties"));
        attachInstrument(new SystemTimeInstrument());
        attachExplorer(new DummyRadar());

        logger.info("init local OBP instance:\n\n"+toString()+"\n");
    }

    private void waitForReliablePosition() {
        logger.info("wait until GPS reads position ...");
        long tmax = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20);
        while(!nmeaGpsReceiver.isPositionReceived() && System.currentTimeMillis() < tmax) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.error(e,e);
            }
        }
        logger.info(nmeaGpsReceiver.isPositionReceived() ? "position received" : "unable to receive position");
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
