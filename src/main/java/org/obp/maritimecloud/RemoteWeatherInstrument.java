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
import org.obp.AttributeNames;
import org.obp.Attributes;
import org.obp.BaseInstrument;
import org.obp.Reliability;
import org.obp.utils.DistanceUtil;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-19
 */
public class RemoteWeatherInstrument extends BaseInstrument {

    public static Logger logger = Logger.getLogger(RemoteWeatherInstrument.class);
    public static final int POLLING_INTERVAL = 60;
    public static final int MAX_RADIUS = 20000;

    private ScheduledExecutorService poller = Executors.newScheduledThreadPool(1);
    private MaritimeCloudService maritimeCloudService;
    private int radius = MAX_RADIUS;
    private int pollingInterval = POLLING_INTERVAL;

    public RemoteWeatherInstrument(final MaritimeCloudService maritimeCloudService) {
        super(UUID.randomUUID(), "remoteWeatherService", "weather data from nearest OBP within defined range");
        this.maritimeCloudService = maritimeCloudService;
        status = Status.OPERATIONAL;
        reliability = Reliability.GOOD;

        logger.info("init remote weather instrument, radius: "+ DistanceUtil.format(radius)+", polling interval: "+pollingInterval+" s");
        poller.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    WeatherService.Response response = maritimeCloudService.callWeatherService(radius);
                    Attributes attr = new Attributes();
                    attr.put(AttributeNames.WIND_SPEED, response.windSpeed);
                    attr.put(AttributeNames.WIND_ANGLE, response.windAngle);
                    attr.put(AttributeNames.WIND_TEMPERATURE, response.windTemperature);
                    updateInstrumentAttributes(attr);
                    status = Status.OPERATIONAL;
                } catch (Exception e) {
                    logger.error("error polling remote weather service",e);
                    status = Status.MALFUNCTION;
                }
            }
        }, pollingInterval, pollingInterval, TimeUnit.SECONDS);
    }

    public void shutdown() {
        logger.debug("shutting down instrument...");

        poller.shutdown();
        try {
            poller.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error shutting down poller",e);
        }

        logger.debug("done.");
    }

}