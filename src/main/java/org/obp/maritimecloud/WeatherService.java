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

import net.maritimecloud.net.service.invocation.InvocationCallback;
import net.maritimecloud.net.service.spi.Service;
import net.maritimecloud.net.service.spi.ServiceInitiationPoint;
import net.maritimecloud.net.service.spi.ServiceMessage;
import org.apache.log4j.Logger;
import org.obp.Attributes;
import org.obp.ObpInstance;
import org.obp.utils.AngleUtil;
import org.obp.utils.SpeedUtil;
import org.obp.utils.TemperatureUtil;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-11
 */
public class WeatherService extends Service {

    private static Logger logger = Logger.getLogger(WeatherService.class);

    public static InvocationCallback<Request, Response> callback(final ObpInstance obpInstance) {
        return new InvocationCallback<Request, Response>() {
            @Override
            public void process(Request message, Context<Response> context) {
                logger.debug("weather service invoked by "+context.getCaller());
                Attributes attributes = obpInstance.resolveAttributes(WIND_SPEED, WIND_ANGLE, WIND_TEMPERATURE, LATITUDE, LONGITUDE);
                Response response = new Response();
                response.windSpeed = attributes.getDouble(WIND_SPEED);
                response.windAngle = attributes.getDouble(WIND_ANGLE);
                response.windTemperature = attributes.getDouble(WIND_TEMPERATURE);
                response.latitude = attributes.getDouble(LATITUDE);
                response.longitude = attributes.getDouble(LONGITUDE);
                context.complete(response);
            }
        };
    }

    public static final ServiceInitiationPoint<Request> SIP = new ServiceInitiationPoint<>(Request.class);

    public static class Request extends ServiceMessage<Response> {};

    public static class Response extends ServiceMessage<Void> {

        public double latitude;
        public double longitude;
        public double windSpeed;
        public double windAngle;
        public double windTemperature;

        @Override
        public String toString() {
            return SpeedUtil.format(windSpeed)+" "+ AngleUtil.format(windAngle)+" "+ TemperatureUtil.format(windTemperature);
        }
    }
}
