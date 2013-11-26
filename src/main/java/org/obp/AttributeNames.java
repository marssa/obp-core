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
    GPS_SATELLITE_ELEVATION = "gpsSatelliteElevation_",
    GPS_SATELLITE_AZIMUTH = "gpsSatelliteAzimuth_",
    GPS_SATELLITE_SNR = "gpsSatelliteSnr_",
    GPS_MAGNETIC_VARIATION = "gpsMagneticVariation",
    LATITUDE = "latitude",
    LONGITUDE = "longitude",
    TRUE_NORTH_COURSE = "trueNorthCourse",
    VELOCITY_OVER_GROUND = "velocityOverGround",
    ALTITUDE = "altitude",
    AIR_TEMPERATURE = "airTemperature",
    AIR_PRESSURE = "airPressure",
    WIND_SPEED = "windSpeed",
    WIND_TEMPERATURE = "windTemperature",
    RELATIVE_HUMIDITY = "relativeHumidity",
    UPDATE_TIME = "lastUpdate",
    HDOP = "hdop",
    VDOP = "vdop",
    PDOP = "pdop",
    RELIABLILITY = "reliability",
    DATA_STALE = "dataStale";
}
