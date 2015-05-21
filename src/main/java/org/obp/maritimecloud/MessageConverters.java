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

import org.obp.data.Waypoint;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-22
 */
public final class MessageConverters {

    private MessageConverters() {
    }

    public static WaypointMsg waypointToMessage(Waypoint waypoint) {
        WaypointMsg msg = new WaypointMsg();
        msg.setLatitude(waypoint.getLatitude());
        msg.setLongitude(waypoint.getLongitude());
        msg.setEta(waypoint.getEta());
        return msg;
    }

    public static List<WaypointMsg> waypointsToMessages(List<Waypoint> waypoints) {
        return waypoints.stream().map(MessageConverters::waypointToMessage).collect(Collectors.toList());
    }

    public static Waypoint messageToWaypoint(WaypointMsg msg) {
        return new Waypoint(msg.getLatitude(), msg.getLongitude(), msg.getEta());
    }

    public static List<Waypoint> messagesToWaypoints(List<WaypointMsg> messages) {
        return messages.stream().map(MessageConverters::messageToWaypoint).collect(Collectors.toList());
    }
}
