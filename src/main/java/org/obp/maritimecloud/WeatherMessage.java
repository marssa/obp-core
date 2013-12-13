package org.obp.maritimecloud;

import dk.dma.enav.maritimecloud.broadcast.BroadcastMessage;
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
