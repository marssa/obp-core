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

import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;
import org.apache.log4j.Logger;
import org.obp.*;
import org.obp.data.Body;
import org.obp.data.Coordinates;
import org.obp.data.Route;
import org.obp.gps.NmeaGpsReceiver;
import org.obp.dummy.DummyRadar;
import org.obp.maritimecloud.MaritimeCloudAgent;
import org.obp.maritimecloud.RemoteWeatherInstrument;
import org.obp.maritimecloud.WeatherService;
import org.obp.remote.RemoteBodiesService;
import org.obp.remote.RemoteObpLocator;
import org.obp.weather.LcjCv3f;
import org.obp.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.obp.Readout.LATITUDE;
import static org.obp.Readout.LONGITUDE;
import static org.obp.Readout.TIME;

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
    private Configuration config;

    @Autowired
    private RemoteObpLocator remoteObpLocator;

    @Autowired
    private MaritimeCloudAgent maritimeCloudAgent;

    @Autowired
    private RemoteBodiesService remoteBodiesService;

    @Autowired
    private DefaultDataInstrument defaultDataInstrument;

    private ScheduledExecutorService scanner;

    private AtomicReference<Route> intendedRouteRef = new AtomicReference<>();


    @Autowired
    public LocalObpInstance(
            @Value("${obp.local.id}") String id,
            @Value("${obp.local.name}") String name,
            @Value("${obp.local.description}") String description,
            @Value("${obp.local.organization}") String organization) {
        super(id, name, description, organization);
    }

    @PostConstruct
    public void init() {
        attachInstrument(nmeaGpsReceiver);
        attachInstrument(nmeaWindVane);
        attachInstrument(defaultDataInstrument);//new DefaultDataInstrument("/defaults.properties"));
        attachInstrument(new SystemTimeInstrument());
        attachExplorer(new DummyRadar());

        scanner = Executors.newScheduledThreadPool(1);

        randomizeIntendedRoute();
        maritimeCloudAgent.connect(createPositionReader(),intendedRouteRef);
        if(maritimeCloudAgent.isConnected()) {
            if(config.isRemoteWeatherScanner()) {
                attachInstrument(new RemoteWeatherInstrument(scanner, maritimeCloudAgent));
            } else {
                maritimeCloudAgent.registerService(WeatherService.SIP, WeatherService.callback(this));
            }
        }

        //initRemoteBodies();

        logger.info("init local OBP instance:\n\n" + toString() + "\n");
    }

    private void initRemoteBodies() {
        Body shadow = new Body("shadow",getPosition().getLatitude()-0.0005,getPosition().getLongitude());
        shadow.setRoute(Route.randomStartingAt(shadow.getCoordinates()));
        remoteBodiesService.put(shadow);
        logger.debug("remote bodies: "+remoteBodiesService.getAll().size());
    }

    @PreDestroy
    public void shutdown() {
        logger.debug("shutting down local instance...");

        logger.debug("shutting down scanner");
        scanner.shutdown();
        try {
            scanner.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error shutting down executorService",e);
        }

        maritimeCloudAgent.disconnect();

        logger.debug("done.");
    }

    private PositionReader createPositionReader() {
        logger.info("waiting for position data ...");
        long tmax = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15);
        while(!nmeaGpsReceiver.isPositionReceived() && System.currentTimeMillis() < tmax) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.error(e,e);
            }
        }

        if(!nmeaGpsReceiver.isPositionReceived()) {
            logger.warn("position can't be determined");
        }

        return new PositionReader() {
            @Override
            public PositionTime getCurrentPosition() {
                Readouts readouts = resolveReadouts(LATITUDE, LONGITUDE, TIME);
                return PositionTime.create(
                        readouts.getDouble(LATITUDE),
                        readouts.getDouble(LONGITUDE),
                        readouts.getLong(TIME));
            }
        };
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

    public Coordinates getPosition() {
        Readouts readouts = resolveReadouts(LATITUDE,LONGITUDE);
        return new Coordinates(readouts.get(LATITUDE).getDouble(),readouts.get(LONGITUDE).getDouble());
    }

    public void randomizeIntendedRoute() {
        intendedRouteRef.set(Route.randomStartingAt(getPosition()));
    }

    public Route getIntendedRoute() {
        return intendedRouteRef.get();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name: ").append(getName()).append(" (").append(getId()).append(")\n");
        sb.append("type: ").append(isHub() ? "hub" : "standard node").append("\n");
        sb.append("external URI: ").append(getUri());
        return sb.toString();
    }
}
