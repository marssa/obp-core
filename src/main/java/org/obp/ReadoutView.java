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

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-9
 */
public class ReadoutView {
    private String value;
    private Reliability reliability;
    private boolean local;

    public static final ReadoutView NA = new ReadoutView("n/a",Reliability.UNDEFINED,true);

    public ReadoutView(String value, Reliability reliability, boolean local) {
        this.value = value;
        this.reliability = reliability;
        this.local = local;
    }

    public ReadoutView(String value, Readout readout) {
        this.value = value;
        this.reliability = readout.getReliability();
        this.local = readout.getDevice().isLocal();
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public Reliability getReliability() {
        return reliability;
    }

    public boolean isLocal() {
        return local;
    }
}
