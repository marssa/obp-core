package org.obp.utils;

import gnu.io.CommPortIdentifier;
import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-7
 */
public final class RxTxUtils {
    private static final Logger logger = Logger.getLogger(RxTxUtils.class);

    private RxTxUtils() {
    }

    private static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    public static void dumpAvailablePorts() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        logger.info("RXTX compatible ports:");
        while ( portEnum.hasMoreElements() )
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            logger.info(portIdentifier.getName()  +  "(" +  getPortTypeName(portIdentifier.getPortType())+")");
        }
    }
}
