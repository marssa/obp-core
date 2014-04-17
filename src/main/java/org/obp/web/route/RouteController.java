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

import org.obp.data.Coordinate;
import org.obp.local.LocalObpInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    LocalObpInstance localObpInstance;

    @ResponseBody
    @RequestMapping("/local/route/intended")
    public List<Coordinate> intendedRoute() {
        Coordinate position = localObpInstance.getLocalPosition();
        List<Coordinate> list = new ArrayList<>();
        list.add(position);
        list.add(new Coordinate(position.getLatitude() + 0.001, position.getLongitude()));
        return list;
    }
}
