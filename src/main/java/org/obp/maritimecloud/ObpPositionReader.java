package org.obp.maritimecloud;

import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;
import org.obp.Attributes;
import org.obp.ObpInstance;

import static org.obp.AttributeNames.LATITUDE;
import static org.obp.AttributeNames.LONGITUDE;
import static org.obp.AttributeNames.TIME;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-21
 */
public class ObpPositionReader extends PositionReader {

    private ObpInstance obpInstance;

    public ObpPositionReader(ObpInstance obpInstance) {
        this.obpInstance = obpInstance;
    }

    @Override
    public PositionTime getCurrentPosition() {
        Attributes attributes = obpInstance.resolveAttributes(LATITUDE, LONGITUDE, TIME);
        return PositionTime.create(
                attributes.getDouble(LATITUDE),
                attributes.getDouble(LONGITUDE),
                attributes.getLong(TIME));
    }
}
