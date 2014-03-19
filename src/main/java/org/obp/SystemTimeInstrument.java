package org.obp;

import java.util.Arrays;
import java.util.UUID;

import static org.obp.AttributeNames.TIME;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */

public class SystemTimeInstrument extends BaseInstrument {

    public SystemTimeInstrument() {
        super(UUID.randomUUID(),"timeServer", "system time server");
        initKeys(Arrays.asList(TIME));
        reliability = Reliability.DEFAULT;
    }

    @Override
    public Attributes getAttributes() {
        Attributes attr = new Attributes();
        attr.put(TIME, System.currentTimeMillis());
        updateInstrumentAttributes(attr);
        return getAttributes();
    }
}
