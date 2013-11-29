package org.obp.dummy;

import org.obp.Attributes;
import org.obp.BaseInstrument;
import org.obp.Reliability;

import java.util.Arrays;
import java.util.UUID;

import static org.obp.AttributeNames.TIME;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-28
 */

public class DummyTimeServer extends BaseInstrument {

    public DummyTimeServer() {
        super(UUID.randomUUID(),"timeServer", "dummy time server", Arrays.asList(TIME));
        reliability = Reliability.DUMMY;
    }

    @Override
    public Attributes getAttributes() {
        updateStandardInstrumentData();
        attributes.put(TIME, System.currentTimeMillis());
        return attributes;
    }
}
