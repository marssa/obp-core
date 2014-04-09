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

package org.obp.dummy;

import org.obp.Readouts;
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
        this.attributeRanges = attributeRanges;
        setStatus(Status.OPERATIONAL);
    }

    @Override
    public Reliability getReliability() {
        return Reliability.MANUAL;
    }

    @Override
    public Readouts getReadouts() {
        // TODO: instead updating here add a timer-task simulating periodical readouts
        for(Map.Entry<String, DoubleRange> entry : attributeRanges.entrySet()) {
            updateReadout(entry.getKey(), entry.getValue().getRandomValue());
        }
        return super.getReadouts();
    }
}
