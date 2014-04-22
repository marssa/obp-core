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

package org.obp.utils;

import dk.dma.epd.common.prototype.enavcloud.intendedroute.Waypoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-22
 */
public class DmaUtil {

    private DmaUtil() {
    }

    public static List<Waypoint> convertWaypointsToDmaFormat(List<org.obp.data.Waypoint> waypoints) {
        List<Waypoint> list = new ArrayList<>();
        for(org.obp.data.Waypoint waypoint : waypoints) {
            Waypoint wp = new Waypoint();
            wp.setLatitude(waypoint.getLatitude());
            wp.setLongitude(waypoint.getLongitude());
            wp.setTurnRad(waypoint.getTurnRadius());
            list.add(wp);
        }
        return list;
    }

    public static List<org.obp.data.Waypoint> convertWaypointsFromDmaFormat(List<Waypoint> waypoints) {
        List<org.obp.data.Waypoint> list = new ArrayList<>();
        for(dk.dma.epd.common.prototype.enavcloud.intendedroute.Waypoint wp : waypoints) {
            list.add(new org.obp.data.Waypoint(wp.getLatitude(),wp.getLongitude(), wp.getTurnRad()));
        }
        return list;
    }
}
