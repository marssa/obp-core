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

import org.obp.utils.TimeUtil;

import java.util.*;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class BaseInstrument extends BaseIdentified implements Instrument {

    private Attributes attributes;
    private long updateTime;
    private List<String> providedKeys;
    private volatile Status status = Status.OFF;

    public BaseInstrument(UUID uuid, String name, String description) {
        super(uuid, name, description);

        this.updateTime = TimeUtil.currentUtc();
        this.attributes = Attributes.newConcurrent();
    }

    protected void initKeys(Collection<String> keys) {
        providedKeys = new ArrayList<>();
        providedKeys.add(UPDATE_TIME);
        providedKeys.add(DATA_STALE);
        providedKeys.addAll(keys);
    }

    protected void initKeys(String... keys) {
        initKeys(Arrays.asList(keys));
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
    public boolean isWorking() {
        return status!=Status.OFF && status!=Status.MALFUNCTION;
    }

    protected void updateInstrumentAttributes(Attributes attr) {
        updateTime = TimeUtil.currentUtc();

        attributes.putAll(attr);
        attributes.put(UPDATE_TIME, updateTime);
        attributes.put(DATA_STALE, Boolean.FALSE.toString());
    }

    @Override
    public Attributes getAttributes() {
        return attributes.clone();
    }

    @Override
    public Attributes getAttributes(String... keys) {
        return attributes.filter(keys);
    }

    @Override
    public AttributeInfo getAttribute(String key) {
        return new AttributeInfo(this, getReliability(), getName(), attributes.get(key));
    }

    @Override
    public Object get(String key) {
        return attributes.get(key);
    }

    @Override
    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    public Reliability getReliability() {
        return attributes.getReliability();
    }

    protected void setReliability(Reliability reliability) {
        attributes.setReliability(reliability);
    }
}
