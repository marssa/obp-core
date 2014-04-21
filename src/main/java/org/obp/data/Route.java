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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-22
 */
public class Route {
    private List<Waypoint> waypoints;

    public Route(List<Waypoint> waypoints) {
        this.waypoints = Collections.unmodifiableList(waypoints);
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public List<Coordinates> getPath() {
        List<Coordinates> list = new ArrayList<>();
        for(Waypoint wp : waypoints) {
            list.add(wp.getCoordinates());
        }
        return list;
    }

    public static Route randomStartingAt(Coordinates position) {
        int numPoints = 3 + (int)(Math.random()*2);
        Stack<Waypoint> waypoints = new Stack<>();
        waypoints.push(new Waypoint(position.getLatitude(),position.getLongitude(),20));
        for(int i=0; i<numPoints; i++) {
            Waypoint last = waypoints.peek();
            waypoints.push(new Waypoint(
                    last.getLatitude()-0.001+(Math.random()*0.002),
                    last.getLongitude()-0.001+(Math.random()*0.002),20));
        }
        return new Route(waypoints);
    }
}
