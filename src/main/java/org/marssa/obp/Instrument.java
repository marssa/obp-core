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

    public static enum Status {
        OFF, MALFUNCTION, UNRELIABLE, OPERATIONAL;
    }

    private long updateTime;
    private ConcurrentMap<String, Object> attributesMap;

    protected volatile Status status = Status.OFF;

    public Instrument(UUID uuid, String name, String description, Collection<String> availableAttributes) {
        super(uuid, name, description);

        attributesMap = new ConcurrentHashMap<>(availableAttributes.size());
        for(String attribute : availableAttributes) {
            attributesMap.put(attribute, NO_VALUE);
        }

        touch();
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

    protected void touch() {
        updateTime = TimeUtils.currentUtc();
    }

    public long getUpdateTime() {
        return updateTime;
    }

}
