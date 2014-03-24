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

import net.maritimecloud.net.broadcast.BroadcastMessage;

import java.net.URI;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-8
 */
public class ObpBeaconMessage extends BroadcastMessage {

    private URI uri;
    private UUID uuid;
    private String name;

    ObpBeaconMessage() {
    }

    public ObpBeaconMessage(String name, URI uri, UUID uuid) {
        this.name = name;
        this.uri = uri;
        this.uuid = uuid;
    }

    public URI getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return name+" "+uri+" UUID: "+uuid;
    }
}
