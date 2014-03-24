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

import net.maritimecloud.net.broadcast.BroadcastMessage;
import org.obp.Attributes;
import org.obp.ObpInstance;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */
class WeatherMessage extends BroadcastMessage {

    private double windAngle;
    private double windSpeed;
    private double windTemperature;

    public WeatherMessage(ObpInstance obpInstance) {
        Attributes attributes = obpInstance.resolveAttributes(WIND_ANGLE, WIND_SPEED, WIND_TEMPERATURE);
        windAngle = attributes.getDouble(WIND_ANGLE);
        windSpeed = attributes.getDouble(WIND_SPEED);
        windTemperature = attributes.getDouble(WIND_TEMPERATURE);
    }

    public double getWindAngle() {
        return windAngle;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindTemperature() {
        return windTemperature;
    }
}
