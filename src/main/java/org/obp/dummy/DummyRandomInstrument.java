package org.obp.dummy;

import org.obp.Attributes;
import org.obp.BaseInstrument;
import org.obp.Reliability;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */
public class DummyRandomInstrument extends BaseInstrument {

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

    private Map<String, DoubleRange> attributeRanges;

    public DummyRandomInstrument(String name, String description, Map<String, DoubleRange> attributeRanges) {
        super(UUID.randomUUID(), name, description);
        initKeys(attributeRanges.keySet());
        this.attributeRanges = attributeRanges;
        this.reliability = Reliability.DEFAULT;
        setStatus(Status.OPERATIONAL);
    }

    private void update() {
        for(Map.Entry<String, DoubleRange> entry : attributeRanges.entrySet()) {
            attributes.put(entry.getKey(), entry.getValue().getRandomValue());
        }
        updateStandardInstrumentData();
    }

    @Override
    public Attributes getAttributes() {
        // TODO: instead updating here add a timer-task simulating periodical readouts
        update();
        return super.getAttributes();
    }
}
