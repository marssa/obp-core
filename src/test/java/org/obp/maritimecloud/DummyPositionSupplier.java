package org.obp.maritimecloud;

import net.maritimecloud.util.function.Supplier;
import net.maritimecloud.util.geometry.PositionTime;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-9
 */
public class DummyPositionSupplier extends Supplier<PositionTime> {

    private double latitude;
    private double longitude;

    public DummyPositionSupplier(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public PositionTime get() {
        return PositionTime.create(latitude,longitude,System.currentTimeMillis());
    }
}
