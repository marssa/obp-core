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

package org.obp.maritimecloud;

import org.apache.log4j.Logger;
import org.obp.BaseDevice;
import org.obp.Reliability;
import org.obp.utils.DistanceUtil;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-19
 */
public class RemoteWeatherDevice extends BaseDevice {

    public static Logger logger = Logger.getLogger(RemoteWeatherDevice.class);
    public static final int POLLING_INTERVAL = 60;
    public static final int MAX_RADIUS = 20000;

    private ScheduledExecutorService executorService;
    private int radius = MAX_RADIUS;
    private int pollingInterval = POLLING_INTERVAL;

    public RemoteWeatherDevice(ScheduledExecutorService executorService, final MaritimeCloudAgent maritimeCloudAgent) {
        super(UUID.randomUUID().toString(), "remoteWeatherService", "weather data from nearest OBP within defined range");
        this.executorService = executorService;
        setStatus(Status.OPERATIONAL);

        logger.info("init remote weather instrument, radius: "+ DistanceUtil.format(radius)+", polling interval: "+pollingInterval+" s");
        this.executorService.scheduleWithFixedDelay(() -> {
            try {
                WeatherEndpoint endpoint = maritimeCloudAgent.getNearestEndpoint(WeatherEndpoint.class, radius);
                if (endpoint != null) {
                    WindMsg msg = endpoint.checkWind().join();
                    if(msg!=null) {
                        updateReadout(WIND_SPEED, msg.getWindSpeed());
                        updateReadout(WIND_ANGLE, msg.getWindAngle());
                        updateReadout(WIND_TEMPERATURE, msg.getWindTemperature());
                        setStatus(Status.OPERATIONAL);
                        logger.debug("remote weather data received");
                    } else {
                        logger.debug("endpoint unable to respond with wind data");
                    }
                } else {
                    logger.debug("unable to connect to weather endpoint");
                    setStatus(Status.OFF);
                }
            } catch (Exception e) {
                logger.error("error polling remote weather service", e);
                setStatus(Status.MALFUNCTION);
            }
        }, pollingInterval, pollingInterval, TimeUnit.SECONDS);
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    @Override
    public Reliability getReliability() {

        // TODO: consider using distance combined from the remote's reliability to provide calculated value

        return Reliability.GOOD;
    }
}
