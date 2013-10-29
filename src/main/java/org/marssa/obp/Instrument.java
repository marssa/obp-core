package org.marssa.obp;

import org.marssa.utils.TimeUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public abstract class Instrument extends Identifiable {
    public static final String NO_VALUE = "0";
    public static final byte MIN_RELIABILITY = 0, MAX_RELIABILITY = 100;

    public static enum Status {
        OFF, MALFUNCTION, OPERATIONAL;
    }

    private long updateTime;
    private ConcurrentMap<String, Object> attributesMap;
    private volatile int reliability = MIN_RELIABILITY;
    private volatile Status status = Status.OFF;

    public Instrument(UUID uuid, String name, String description, Collection<String> attributeNames) {
        super(uuid, name, description);
        List<String> allAttributeNames = buildAllAttributeNames(attributeNames);
        attributesMap = new ConcurrentHashMap<>(allAttributeNames.size());
        for(String attributeName : allAttributeNames) {
            attributesMap.put(attributeName, NO_VALUE);
        }

        updateTime = TimeUtils.currentUtc();
    }

    private List<String> buildAllAttributeNames(Collection<String> attributeNames) {
        List<String> allAttributeNames = new ArrayList<>();
        allAttributeNames.add(AttributeNames.UPDATE_TIME);
        allAttributeNames.add(AttributeNames.DATA_STALE);
        allAttributeNames.add(AttributeNames.RELIABILITY);
        allAttributeNames.addAll(attributeNames);
        return allAttributeNames;
    }

    public boolean providesAll(String... attributes) {
        for(String attribute : attributes) {
            if(!attributesMap.containsKey(attribute)) {
                return false;
            }
        }
        return true;
    }

    public Status getStatus() {
        return status;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    public boolean isWorking() {
        return status!=Status.OFF && status!=Status.MALFUNCTION;
    }

    public List<String> getAttributeNames() {
        return new ArrayList(attributesMap.keySet());
    }

    public List<Map.Entry<String, Object>> getAttributeEntries() {
        return new ArrayList(attributesMap.entrySet());
    }

    public String getString(String attribute) {
        return (String)attributesMap.get(attribute);
    }

    public Double getDouble(String attribute) {
        return (Double)attributesMap.get(attribute);
    }

    protected void setAttribute(String key, Object value) {
        attributesMap.replace(key, value);
    }

    public int getReliability() {
        return reliability;
    }

    protected void updateStandardInstrumentData(int reliability) {
        this.updateTime = TimeUtils.currentUtc();
        this.reliability = reliability;
        setAttribute(AttributeNames.UPDATE_TIME, updateTime);
        setAttribute(AttributeNames.RELIABILITY, reliability);
        setAttribute(AttributeNames.DATA_STALE, Boolean.FALSE.toString());
    }

    public long getUpdateTime() {
        return updateTime;
    }

}
