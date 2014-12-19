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

package org.obp;

import org.obp.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
@Service
public class Configuration {

    @Value("${build.id}")
    private String buildId;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${obp.local.hub}")
    private boolean hub;

    @Value("${obp.local.uri}")
    private URI uri;

    @Value("${obp.local.name}")
    private String name;

    @Value("${obp.local.description}")
    private String description;

    @Value("${obp.local.organization}")
    private String organization;

    @Value("${obp.maritimecloud.client.uri}")
    private String clientUri;

    @Value("${obp.maritimecloud.server.uri}")
    private String serverUri;

    @Value("${obp.maritimecloud.service.enabled}")
    private boolean serviceEnabled;

    @Value("${obp.maritimecloud.broadcast.beacon.enabled}")
    private boolean broadcastBeaconEnabled;

    @Value("${obp.maritimecloud.broadcast.beacon.period}")
    private int broadcastBeaconPeriod;

    @Value("${obp.maritimecloud.broadcast.beacon.listener.enabled}")
    private boolean broadcastBeaconListenerEnabled;

    @Value("${obp.maritimecloud.remote.scanner.weather.enabled}")
    private boolean remoteWeatherScanner;

    public boolean isHub() {
        return hub;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public Object getJavaInfo() {
        return MapUtil.filterByKeyPrefix(System.getProperties(),"java.");
    }

    public Object getOsInfo() {
        return MapUtil.filterByKeyPrefix(System.getProperties(),"os.");
    }

    public boolean isRemoteWeatherScanner() {
        return remoteWeatherScanner;
    }

    public String getShortOsInfo() {
        return System.getProperty("os.name")+" "+System.getProperty("os.version")+" ("+System.getProperty("os.arch")+")";
    }

    public String getShortJavaInfo() {
        return System.getProperty("java.version")+" "+System.getProperty("java.vendor")+" ("+System.getProperty("java.vm.name")+")";
    }

    public String getShortHostInfo() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unable to determine host name";
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganization() {
        return organization;
    }

    public String getClientUri() {
        return clientUri;
    }

    public String getServerUri() {
        return serverUri;
    }

    public boolean isServiceEnabled() {
        return serviceEnabled;
    }

    public boolean isBroadcastBeaconEnabled() {
        return broadcastBeaconEnabled;
    }

    public int getBroadcastBeaconPeriod() {
        return broadcastBeaconPeriod;
    }

    public boolean isBroadcastBeaconListenerEnabled() {
        return broadcastBeaconListenerEnabled;
    }

    public URI getUri() {
        return uri;
    }

}
