package org.marssa.obp;

import org.marssa.utils.TimeUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class BasicInstrument extends BasicIdentified implements Instrument {
    private long updateTime;
    private ConcurrentMap<String, Object> attributesMap;
    private volatile Status status = Status.OFF;
    private UUID obpInstanceUuid;
    private Reliability reliability = Reliability.NONE;

    public BasicInstrument(UUID parentUuid, UUID uuid, String name, String description, Collection<String> attributeNames) {
        super(uuid, name, description);
        List<String> allAttributeNames = buildAllAttributeNames(attributeNames);
        this.updateTime = TimeUtils.currentUtc();
        this.attributesMap = new ConcurrentHashMap<>(allAttributeNames.size());
        this.obpInstanceUuid = parentUuid;

        for(String attributeName : allAttributeNames) {
            attributesMap.put(attributeName, NO_VALUE);
        }
    }

    private List<String> buildAllAttributeNames(Collection<String> attributeNames) {
        List<String> allAttributeNames = new ArrayList<>();
        allAttributeNames.add(AttributeNames.UPDATE_TIME);
        allAttributeNames.add(AttributeNames.DATA_STALE);
        allAttributeNames.add(AttributeNames.RELIABILITY);
        allAttributeNames.addAll(attributeNames);
        return allAttributeNames;
    }

    @Override
    public boolean providesAll(String... attributes) {
        for(String attribute : attributes) {
            if(!attributesMap.containsKey(attribute)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean provides(String name) {
        return attributesMap.containsKey(name);
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

    @Override
    public List<String> getAttributeNames() {
        return new ArrayList(attributesMap.keySet());
    }

    @Override
    public List<Map.Entry<String, Object>> getAttributeEntries() {
        return new ArrayList(attributesMap.entrySet());
    }

    @Override
    public String getString(String attribute) {
        return (String)attributesMap.get(attribute);
    }

    @Override
    public Double getDouble(String attribute) {
        return (Double)attributesMap.get(attribute);
    }

    @Override
    public Object getValue(String name) {
        return attributesMap.get(name);
    }

    @Override
    public Attribute getAttribute(String name) {
        Object value = attributesMap.get(name);
        return new Attribute(getUuid(),reliability,name,value);
    }

    protected void setAttribute(String key, Object value) {
        attributesMap.replace(key, value);
    }

    @Override
    public Reliability getReliability() {
        return reliability;
    }

    protected void updateStandardInstrumentData(Reliability reliability) {
        this.updateTime = TimeUtils.currentUtc();
        this.reliability = reliability;
        setAttribute(AttributeNames.UPDATE_TIME, updateTime);
        setAttribute(AttributeNames.RELIABILITY, reliability);
        setAttribute(AttributeNames.DATA_STALE, Boolean.FALSE.toString());
    }

    @Override
    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    public UUID getObpInstanceUuid() {
        return obpInstanceUuid;
    }

}
