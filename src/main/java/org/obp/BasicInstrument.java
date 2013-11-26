package org.obp;

import org.obp.utils.GpsUtils;
import org.obp.utils.TimeUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.obp.AttributeNames.*;

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
        allAttributeNames.add(UPDATE_TIME);
        allAttributeNames.add(DATA_STALE);
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

    protected void setAttributes(AttributeMap attributeMap) {
        attributeMap.putAll(attributeMap);
    }

    @Override
    public Reliability getReliability() {
        return reliability;
    }

    protected void updateStandardInstrumentData() {
        updateStandardInstrumentData(null);
    }

    protected void updateStandardInstrumentData(AttributeMap am) {
        this.updateTime = TimeUtils.currentUtc();
        this.reliability = GpsUtils.estimateReliability(am);
        setAttribute(UPDATE_TIME, updateTime);
        setAttribute(DATA_STALE, Boolean.FALSE.toString());
        setAttribute(RELIABLILITY, this.reliability);
        if(am!=null) setAttributes(am);
    }

    public Map<String, Object> getAttributes(String... keys) {
        Map<String, Object> map = new HashMap<>();
        for(String key : keys) {
            Object value = attributesMap.get(key);
            if(value!=null) {
                map.put(key,value);
            }
        }
        return map;
    }

    public Map<String, Object> getAllAttributes() {
        return new HashMap<>(attributesMap);
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
