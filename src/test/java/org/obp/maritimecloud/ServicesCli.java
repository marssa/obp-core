package org.obp.maritimecloud;

import net.maritimecloud.net.ConnectionFuture;
import net.maritimecloud.net.MaritimeCloudClient;
import net.maritimecloud.net.MaritimeCloudClientConfiguration;
import net.maritimecloud.net.service.ServiceEndpoint;
import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-17
 */
public class ServicesCli {
    public static final String SERVER_URI = "test.maritimecloud.net";
    public static final String CLIENT_URI = "mmsi://20140109";

    private static PositionReader positionReader = PositionReader.fixedPosition(
            PositionTime.create(51.16125, 16.89578, System.currentTimeMillis()));

    public static void main(String... args) throws InterruptedException, TimeoutException, ExecutionException {
        MaritimeCloudClientConfiguration conf = MaritimeCloudClientConfiguration.create(CLIENT_URI);
        conf.setPositionReader(positionReader);
        conf.setHost(SERVER_URI);
        conf.properties().setName("test_client");
        conf.properties().setDescription("test client");
        conf.properties().setOrganization("Marsec-XL");

        try(MaritimeCloudClient client = conf.build()) {
            ConnectionFuture<ServiceEndpoint<WeatherService.Request, WeatherService.Response>> locator = client.serviceLocate(WeatherService.SIP).nearest();
            ServiceEndpoint<WeatherService.Request, WeatherService.Response> se = locator.get(1, TimeUnit.SECONDS);
            ConnectionFuture<WeatherService.Response> invoke = se.invoke(new WeatherService.Request());
            WeatherService.Response response = invoke.get(10, TimeUnit.SECONDS);
            System.out.println("response: "+response);
        }
    }
}