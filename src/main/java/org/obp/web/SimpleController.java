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

package org.obp.web;

import org.obp.Readout;
import org.obp.ReadoutView;
import org.obp.Readouts;
import org.obp.local.LocalObpInstance;
import org.obp.utils.LatitudeUtil;
import org.obp.utils.LongitudeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Controller
public class SimpleController {

    @Autowired
    private LocalObpInstance obp;

    @RequestMapping("/simple/manifest")
    public String manifest(ModelMap model) {
        model.addAttribute("realm", obp);
        return "simple/manifest";
    }

    @RequestMapping("/simple/start")
    public String start() {
        return "simple/start";
    }

    @RequestMapping("/simple/view")
    public String view() {
        return "simple/view";
    }

    @RequestMapping("/simple/position")
    public String position() {
        return "simple/position";
    }

    @RequestMapping("/simple/more")
    public String more() {
        return "simple/more";
    }

    @RequestMapping("/simple/navigation")
    public String navigation(ModelMap model) {
        Readouts readouts = obp.resolveReadouts(SPEED_OVER_GROUND,TRUE_NORTH_COURSE,LONGITUDE,LATITUDE);
        model.addAttribute("sog", readouts.formatKnots(SPEED_OVER_GROUND));
        model.addAttribute("cog", readouts.formatAngle(TRUE_NORTH_COURSE));
        model.addAttribute("position", formatPosition(readouts));
        return "simple/navigation";
    }

    @RequestMapping("/simple/wind")
    public String wind(ModelMap model) {
        Readouts readouts = obp.resolveReadouts(WIND_TEMPERATURE,WIND_SPEED,WIND_ANGLE);
        model.addAttribute("speed", readouts.formatKnots(WIND_SPEED));
        model.addAttribute("angle", readouts.formatAngle(WIND_ANGLE));
        model.addAttribute("temperature", readouts.formatTemperature(WIND_TEMPERATURE));
        return "simple/wind";
    }

    @RequestMapping("/simple/map")
    public String map(ModelMap model) {
        model.addAllAttributes(obp.resolveReadouts(LATITUDE, LONGITUDE));
        model.addAttribute("localName",obp.getName());
        model.addAttribute("localDescription",obp.getDescription());
        return "simple/map-ecc";
    }

    @RequestMapping("/simple/map-gm")
    public String mapEcc(ModelMap model) {
        model.addAllAttributes(obp.resolveReadouts(LATITUDE, LONGITUDE));
        model.addAttribute("localName",obp.getName());
        model.addAttribute("localDescription",obp.getDescription());
        return "simple/map-gm";
    }

    private ReadoutView formatPosition(Readouts readouts) {
        if(readouts.containsAllKeys(LATITUDE,LONGITUDE)) {
            Readout latitude = readouts.get(LATITUDE);
            Readout longitude = readouts.get(LONGITUDE);
            return new ReadoutView(
                    LatitudeUtil.toStringShort(latitude.getDouble())+" "+
                    LongitudeUtil.toStringShort(longitude.getDouble()),
                    latitude.getReliability().combineWith(longitude.getReliability()),
                    latitude.getInstrument().isLocal() && longitude.getInstrument().isLocal());
        } else {
            return ReadoutView.NA;
        }
    }

    @ResponseBody
    @RequestMapping("/simple/viewDataFeed")
    public Map<String,Object> viewDataFeed() {
        Readouts readouts = obp.resolveReadouts(LATITUDE, LONGITUDE, SPEED_OVER_GROUND, TRUE_NORTH_COURSE, WIND_SPEED, WIND_TEMPERATURE, TIME);
        Map<String,Object> map = new HashMap<>();
        map.put("sog", readouts.speedInKnots(SPEED_OVER_GROUND));
        map.put("cog", readouts.angle(TRUE_NORTH_COURSE));
        map.put("wind", readouts.speedInKnots(WIND_SPEED));
        map.put("position", formatPosition(readouts));
        map.put("water",ReadoutView.NA);
        map.put("depth",ReadoutView.NA);
        map.put("dateTime", readouts.dateTime(TIME));
        return map;
    }
}
