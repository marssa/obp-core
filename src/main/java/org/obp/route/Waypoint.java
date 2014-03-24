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

package org.obp.route;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-22
 */
public class Waypoint {
    private UUID uuid;
    private int revision;
    private double latitude;
    private double longitude;
    private double portsideXtd;
    private double starboardXtd;
    private double turnRadius;
    private long departureTime;
    private long arrivalTime;
    private double speed;

    public Waypoint(double latitude, double longitude) {
        this(null,0,latitude,longitude,Double.NaN,Double.NaN,Double.NaN,0,0,Double.NaN);
    }

    public Waypoint(UUID uuid, int revision, double latitude, double longitude,
                    double portsideXtd, double starboardXtd, double turnRadius) {
        this(uuid,revision,latitude,longitude,portsideXtd,starboardXtd,turnRadius,0,0,Double.NaN);
    }

    public Waypoint(UUID uuid, int revision, double latitude, double longitude,
                    double portsideXtd, double starboardXtd, double turnRadius,
                    long departureTime, long arrivalTime, double speed) {
        this.uuid = uuid;
        this.revision = revision;
        this.latitude = latitude;
        this.longitude = longitude;
        this.portsideXtd = portsideXtd;
        this.starboardXtd = starboardXtd;
        this.turnRadius = turnRadius;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.speed = speed;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getRevision() {
        return revision;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getPortsideXtd() {
        return portsideXtd;
    }

    public double getStarboardXtd() {
        return starboardXtd;
    }

    public double getTurnRadius() {
        return turnRadius;
    }
}
