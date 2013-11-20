package org.obp.dummy;

import org.obp.BasicInstrument;

import java.util.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */
public class DummyRandomInstrument extends BasicInstrument {

    public static class DoubleRange {
        private double min;
        private double max;

        public static final DoubleRange create(double min, double max) {
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

    public DummyRandomInstrument(UUID parentUuid, String name, String description, Map<String, DoubleRange> attributes) {
        super(parentUuid, UUID.randomUUID(), name, description, attributes.keySet());
        this.attributes = attributes;
        setStatus(Status.OPERATIONAL);
    }

    private void update() {
        updateStandardInstrumentData(Reliability.HIGH);
        for(Map.Entry<String, DoubleRange> entry : attributes.entrySet()) {
            setAttribute(entry.getKey(), entry.getValue().getRandomValue());
        }
    }

    @Override
    public List<Map.Entry<String, Object>> getAttributeEntries() {
        // TODO: instead updating here add a timer-task simulating periodical readouts
        update();
        return super.getAttributeEntries();
    }
}
