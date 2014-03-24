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

package org.obp.web.gps;

import org.obp.AttributeNames;
import org.obp.Attributes;
import org.obp.gps.NmeaGpsReceiver;
import org.obp.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
@Controller
public class GpsController {

    @Autowired
    private NmeaGpsReceiver gpsReceiver;

    @ResponseBody
    @RequestMapping("/api/gps/position")
    public Map<String,Object> position() {
        return gpsReceiver.getAttributes().filter(LATITUDE, LONGITUDE);
    }

    @ResponseBody
    @RequestMapping(value = {"/api/gps/all","/simple/gps/all"})
    public Map<String,Object> all() {
        Attributes attributes = gpsReceiver.getAttributes();
        attributes.put("dateTime", attributes.formatDateTime(TIME));
        return attributes;
    }

    @RequestMapping("/liveGpsData")
    public String liveData() {
        return "liveGpsData";
    }
}
