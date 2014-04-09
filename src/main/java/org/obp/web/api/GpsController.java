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

package org.obp.web.api;

import org.obp.gps.NmeaGpsReceiver;
import org.obp.local.LocalObpInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.obp.Readout.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
@RequestMapping("/simple")
public class GpsController {

    @Autowired
    private NmeaGpsReceiver gpsReceiver;

    @Autowired
    private LocalObpInstance obpInstance;

    @ResponseBody
    @RequestMapping("/gps/all")
    public Map<String,Object> all() {
        Map<String,Object> map = gpsReceiver.getReadouts().toValueMap();
        map.put("dateTime", gpsReceiver.getReadouts().formatDateTime(TIME));
        return map;
    }

    @ResponseBody
    @RequestMapping("/gps/position")
    public Map<String,Object> position() {
        return obpInstance.resolveReadouts(LATITUDE, LONGITUDE).toValueMap();
    }

    @RequestMapping("/liveGpsData")
    public String liveData() {
        return "liveGpsData";
    }
}
