package org.marssa.obp;

import java.util.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */
public class RandomDummyInstrument extends Instrument {

    public static class DoubleRange {
        private double min;
        private double max;

        static final DoubleRange create(double min, double max) {
            DoubleRange attr = new DoubleRange();
            attr.min = min;
            attr.max = max;
            return attr;
        }

        double getRandomValue() {
            return min+(Math.random() * (max - min));
        }
    }

    private Map<String, DoubleRange> attributes;

    public RandomDummyInstrument(String name, String description, Map<String, DoubleRange> attributes) {
        super(UUID.randomUUID(), name, description, attributes.keySet());
        this.attributes = attributes;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public String getString(String attribute) {
        return Double.toString(getDouble(attribute));
    }

    @Override
    public Double getDouble(String attribute) {
        return attributes.get(attribute).getRandomValue();
    }

    @Override
    public List<Map.Entry<String, Object>> getAttributeEntries() {
        List<Map.Entry<String, Object>> entries = new ArrayList<>();
        for(Map.Entry<String, DoubleRange> entry : attributes.entrySet()) {
            entries.add(new AbstractMap.SimpleImmutableEntry<String,Object>(
                    entry.getKey(),
                    entry.getValue().getRandomValue()));
        }
        return entries;
    }
}
