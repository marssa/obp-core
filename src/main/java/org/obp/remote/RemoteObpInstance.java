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
import org.obp.data.Body;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-17
 */
public class RemoteObpInstance extends StringIdentified implements ObpInstance {

    private URI uri;

    public RemoteObpInstance(URI uri) {
        super(UUID.randomUUID(),"remote obp at "+uri,"");
        this.uri = uri;
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
    public Collection<Instrument> getInstruments() {
        return null;
    }

    @Override
    public Instrument getInstrument(String id) {
        return null;
    }
}
