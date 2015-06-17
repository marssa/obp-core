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

package org.obp.web.route;

import org.obp.data.Vessel;
import org.obp.data.Coordinates;
import org.obp.data.Waypoint;
import org.obp.local.LocalObpInstance;
import org.obp.remote.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-17
 */

@Controller
public class RouteController {

    @Autowired
    private LocalObpInstance localObpInstance;

    @Autowired
    private VesselService vesselService;

    @ResponseBody
    @RequestMapping("/local/ownPath")
    public List<Coordinates> ownPath(@RequestParam(required = false) Boolean randomize) {
        if(randomize!=null && randomize) {
            localObpInstance.randomizeIntendedRoute();
        }
        return localObpInstance.getIntendedRoute().getPath();
    }

    @ResponseBody
    @RequestMapping("/local/otherRoutes")
    public List<VesselRouteDto> otherRoutes() {
        List<VesselRouteDto> list = new ArrayList<>();
        for(Vessel vessel : vesselService.getAll()) {
            VesselRouteDto vesselRouteDto = new VesselRouteDto();
            vesselRouteDto.body = new EntityDto(vessel.getId(), vessel.getName(), vessel.getDescription());
            vesselRouteDto.position = vessel.getCoordinates();
            vesselRouteDto.path = convertFromWaypoints(vessel.getRoute().getWaypoints());
            list.add(vesselRouteDto);
        }
        return list;
    }

    private List<WaypointDto> convertFromWaypoints(List<Waypoint> waypoints) {
        List<WaypointDto> list = new ArrayList<>();
        for(Waypoint wp : waypoints) {
            WaypointDto wdto = new WaypointDto();
            wdto.latitude = wp.getLatitude();
            wdto.longitude = wp.getLongitude();
            list.add(wdto);
        }
        return list;
    }

}
