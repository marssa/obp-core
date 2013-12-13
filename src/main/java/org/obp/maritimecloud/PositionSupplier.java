package org.obp.maritimecloud;

import dk.dma.enav.model.geometry.PositionTime;
import dk.dma.enav.util.function.Supplier;
import org.obp.Attributes;
import org.obp.ObpInstance;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-13
 */
class PositionSupplier extends Supplier<PositionTime> {

    private ObpInstance obpInstance;

    PositionSupplier(ObpInstance obpInstance) {
        this.obpInstance = obpInstance;
    }

    @Override
    public PositionTime get() {
        Attributes attributes = obpInstance.resolveAttributes(LATITUDE, LONGITUDE, TIME);
        return PositionTime.create(
                attributes.getDouble(LATITUDE),
                attributes.getDouble(LONGITUDE),
                attributes.getLong(TIME));
    }
}
