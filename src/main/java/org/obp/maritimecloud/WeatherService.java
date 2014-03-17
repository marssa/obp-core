package org.obp.maritimecloud;

import net.maritimecloud.net.service.invocation.InvocationCallback;
import net.maritimecloud.net.service.spi.Service;
import net.maritimecloud.net.service.spi.ServiceInitiationPoint;
import net.maritimecloud.net.service.spi.ServiceMessage;
import org.obp.Attributes;
import org.obp.ObpInstance;
import org.obp.utils.AngleUtil;
import org.obp.utils.SpeedUtil;
import org.obp.utils.TemperatureUtil;

import static org.obp.AttributeNames.WIND_ANGLE;
import static org.obp.AttributeNames.WIND_SPEED;
import static org.obp.AttributeNames.WIND_TEMPERATURE;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-11
 */
public class WeatherService extends Service {

    public static InvocationCallback<Request, Response> callback(final ObpInstance obpInstance) {
        return new InvocationCallback<Request, Response>() {
            @Override
            public void process(Request message, Context<Response> context) {
                Attributes attributes = obpInstance.resolveAttributes(WIND_SPEED, WIND_ANGLE, WIND_TEMPERATURE);
                Response response = new Response();
                response.windSpeed = attributes.getDouble(WIND_SPEED);
                response.windAngle = attributes.getDouble(WIND_ANGLE);
                response.windTemperature = attributes.getDouble(WIND_TEMPERATURE);
                context.complete(response);
            }
        };
    }

    public static final ServiceInitiationPoint<Request> SIP = new ServiceInitiationPoint<>(Request.class);

    public static class Request extends ServiceMessage<Response> {};

    public static class Response extends ServiceMessage<Void> {

        public double windSpeed;
        public double windAngle;
        public double windTemperature;

        @Override
        public String toString() {
            return SpeedUtil.format(windSpeed)+" "+ AngleUtil.format(windAngle)+" "+ TemperatureUtil.format(windTemperature);
        }
    }
}
