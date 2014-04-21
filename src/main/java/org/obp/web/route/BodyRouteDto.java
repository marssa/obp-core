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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.obp.Identified;
import org.obp.data.Coordinates;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-18
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class BodyRouteDto {
    Identified body;
    Coordinates position;
    List<WaypointDto> path;
}