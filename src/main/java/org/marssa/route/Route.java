package org.marssa.route;

import java.util.Collections;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-22
 */
public class Route {
    private String name;
    private List<Waypoint> waypoints;

    public Route(String name, List<Waypoint> waypoints) {
        this.name = name;
        this.waypoints = Collections.unmodifiableList(waypoints);
    }

    public String getName() {
        return name;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
}
