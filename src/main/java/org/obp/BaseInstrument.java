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

import java.util.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class BaseInstrument implements Instrument {

    private String id;
    private String name;
    private String description;
    private Readouts readouts;
    private volatile Status status = Status.OFF;

    public BaseInstrument(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.readouts = Readouts.newConcurrent();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    @Override
    public Readouts getReadouts() {
        return readouts;
    }

    @Override
    public void pause() {
        if(status==Status.OPERATIONAL) {
            setStatus(Status.PAUSED);
        } else {
            throw new IllegalStateException("not operational, can't pause");
        }
    }

    @Override
    public void resume() {
        if(status==Status.PAUSED) {
            setStatus(Status.OPERATIONAL);
        } else {
            throw new IllegalStateException("not paused, can't resume");
        }
    }

    protected void updateReadout(String name, Object value) {
        updateReadout(name, value, getReliability());
    }

    protected void updateReadout(String name, Object value, Reliability reliability) {
        readouts.put(name, new Readout(this, reliability, name, value));
    }

    protected void updateReadouts(Map<String, Object> data) {
        updateReadouts(data, getReliability());
    }

    protected void updateReadouts(Map<String, Object> data, Reliability reliability) {
        for(Map.Entry<String,Object> entry : data.entrySet()) {
            updateReadout(entry.getKey(), entry.getValue(), reliability);
        }
    }

}
