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

package org.obp.data;

import org.obp.Entity;
import org.obp.maritimecloud.MessageConverters;
import org.obp.maritimecloud.WaypointMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public class Body implements Entity {

    private String id;
    private String name;
    private String description;
    private Coordinates coordinates;
    private volatile Route route;

    public Body(String name, double latitude, double longitude) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = "";
        this.coordinates = new Coordinates(latitude,longitude);
        this.route = null;
    }

    public Body(String id, String name, double latitude, double longitude, List<Waypoint> waypoints) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.coordinates = new Coordinates(latitude,longitude);
        this.route = new Route(waypoints);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setRouteWaypoints(List<WaypointMsg> waypoints) {
        this.route = new Route(MessageConverters.messagesToWaypoints(waypoints));
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Coordinates> getPath() {
        List<Coordinates> list = new ArrayList<>();
        for(Waypoint wp : route.getWaypoints()) {
            list.add(new Coordinates(wp.getLatitude(),wp.getLongitude()));
        }
        return list;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
