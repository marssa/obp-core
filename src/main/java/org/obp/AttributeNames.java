package org.obp;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */
public final class AttributeNames {

    private AttributeNames() {
    }

    public static final String
    TIME = "time",
    GPS_FIX_MODE = "gpsFixMode",
    GPS_FIX_TYPE = "gpsFixType",
    GPS_FIX_QUALITY = "gpsFixQuality",
    GPS_EFFECTIVE_SATELLITES = "gpsEffectiveSatellites",
    GPS_VISIBLE_SATELLITES = "gpsVisibleSatellites",
    GPS_SATELLITES = "gpsSatellites",
    GPS_SATELLITE_ID = "id",
    GPS_SATELLITE_ELEVATION = "elevation",
    GPS_SATELLITE_AZIMUTH = "azimuth",
    GPS_SATELLITE_SNR = "snr",
    GPS_MAGNETIC_VARIATION = "gpsMagneticVariation",
    LATITUDE = "latitude",
    LONGITUDE = "longitude",
    TRUE_NORTH_COURSE = "trueNorthCourse",
    SPEED_OVER_GROUND = "speedOverGround",
    ALTITUDE = "altitude",
    AIR_TEMPERATURE = "airTemperature",
    AIR_PRESSURE = "airPressure",
    WIND_ANGLE = "windAngle",
    WIND_SPEED = "windSpeed",
    WIND_TEMPERATURE = "windTemperature",
    RELATIVE_HUMIDITY = "relativeHumidity",
    UPDATE_TIME = "lastUpdate",
    HDOP = "hdop",
    VDOP = "vdop",
    PDOP = "pdop",
    DATA_STALE = "dataStale";
}
