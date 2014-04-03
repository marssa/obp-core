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

import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;
import org.apache.log4j.Logger;
import org.obp.Attributes;
import org.obp.ObpInstance;

import java.util.concurrent.TimeUnit;

import static org.obp.AttributeNames.LATITUDE;
import static org.obp.AttributeNames.LONGITUDE;
import static org.obp.AttributeNames.TIME;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-21
 */
public class ObpPositionReader extends PositionReader {

    private static Logger logger = Logger.getLogger(ObpPositionReader.class);
    private ObpInstance obpInstance;

    public ObpPositionReader(ObpInstance obpInstance) {
        this.obpInstance = obpInstance;
    }

    @Override
    public PositionTime getCurrentPosition() {
        Attributes attributes = obpInstance.resolveAttributes(LATITUDE, LONGITUDE, TIME);
        return PositionTime.create(
                attributes.getDouble(LATITUDE),
                attributes.getDouble(LONGITUDE),
                attributes.getLong(TIME));
    }
}
