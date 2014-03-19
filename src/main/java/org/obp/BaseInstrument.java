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

    protected volatile Status status = Status.OFF;
    protected Reliability reliability = Reliability.UNDEFINED;

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
    public boolean isWorking() {
        return status!=Status.OFF && status!=Status.MALFUNCTION;
    }

    protected void updateInstrumentAttributes(Attributes attr) {
        updateTime = TimeUtil.currentUtc();

        attributes.putAll(attr);
        attributes.put(UPDATE_TIME, updateTime);
        attributes.put(DATA_STALE, Boolean.FALSE.toString());
        attributes.setReliability(reliability);
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
        return new AttributeInfo(this, reliability, getName(), attributes.get(key));
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
}
