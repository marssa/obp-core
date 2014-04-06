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

package org.obp.remote;

import org.obp.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-17
 */
public class RemoteObpInstance implements ObpInstance {

    private UUID uuid;
    private URI uri;
    private String name;
    private String organization;

    public RemoteObpInstance(URI uri) {
        this.uuid = UUID.randomUUID();
        this.name = "manually defined at "+uri;
        this.uri = uri;
    }

    public RemoteObpInstance(UUID uuid, URI uri, String name, String organization) {
        this.uuid = uuid;
        this.uri = uri;
        this.name = name;
        this.organization = organization;
    }

    @Override
    public String getOrganization() {
        return null;
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Body> getBodies() {
        return null;
    }

    @Override
    public Readouts resolveReadouts(String... keys) {
        return null;
    }

    @Override
    public Readouts resolveReadouts() {
        return null;
    }

    @Override
    public Readout resolveReadout(String key) {
        return null;
    }

    @Override
    public List<Readout> getAllReadouts() {
        return null;
    }

    @Override
    public void attachInstrument(Instrument instrument) {
    }

    @Override
    public void detachInstrument(Instrument instrument) {
    }

    @Override
    public void attachExplorer(Explorer explorer) {
    }

    @Override
    public void detachExplorer(Explorer explorer) {
    }

    @Override
    public boolean isHub() {
        return false;
    }

    @Override
    public int knownRemotes() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
