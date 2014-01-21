package org.obp.maritimecloud;

import net.maritimecloud.net.broadcast.BroadcastListener;
import net.maritimecloud.net.broadcast.BroadcastMessageHeader;
import net.maritimecloud.util.function.Supplier;
import net.maritimecloud.util.geometry.PositionReader;
import net.maritimecloud.util.geometry.PositionTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Robert Jaremczak
 * Date: 2014-1-9
 */
public class MaritimeCloudConnectorTest {

    public static final String SERVER_URI = "localhost:43234/";
    public static final String CLIENT_ID = "mmsi://20140109";

    private PositionReader positionReader = PositionReader.fixedPosition(
            PositionTime.create(51.16125,16.89578,System.currentTimeMillis()));

    private MaritimeCloudConnector connector = new MaritimeCloudConnector(SERVER_URI, CLIENT_ID, positionReader);

    @Before
    public void setUp() {
        connector.init();
    }

    @After
    public void tearDown() {
        connector.shutdown();
    }

    @Ignore
    @Test
    public void testBroadcastMessageReception() throws InterruptedException {
        connector.addBroadcastListener(ObpBeaconMessage.class, new BroadcastListener<ObpBeaconMessage>() {
            @Override
            public void onMessage(BroadcastMessageHeader header, ObpBeaconMessage broadcast) {
                System.out.println("received: "+broadcast);
            }
        });

        Thread.sleep(30000);
    }
}
