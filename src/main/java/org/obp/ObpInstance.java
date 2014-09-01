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

package org.obp;

import org.obp.data.Body;

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface ObpInstance extends Entity {
    String getOrganization();
    URI getUri();
    List<Body> getBodies();
    Readouts resolveReadouts(String... keys);
    Readouts resolveReadouts();
    Readout resolveReadout(String key);
    List<Readout> getAllReadouts();
    void attachInstrument(Instrument instrument);
    void detachInstrument(Instrument instrument);
    void attachExplorer(Explorer explorer);
    void detachExplorer(Explorer explorer);
    boolean isHub();
    int knownRemotes();
    Collection<Instrument> getInstruments();
    Instrument getInstrument(String id);
}
