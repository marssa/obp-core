This repository contains pre-release version of Open Bridge Platform (OBP).

NOTE: put RXTX native lib and RXTXcomm.jar somewhere on the path
NOTE: for embedded systems use OS and JDK with hardware floating point support whenever possible

supported hardware:

GlobalSat BU-353 compatible GPS receiver
- accessible via serial port device (originally via internal USB/serial Prolific interface)
- supported NMEA messages: GPGGA, GPGLL, GPGSA, GPGSV, GPRMC, GPVTG
- default connection parameters as for NMEA 0183 (4800 8n1)

LCJ Capteurs CV3Fm6 40 ultrasonic wind-vane - (planned, work in progress...)
- accessible via serial port device, physical serial port (Rx,Tx only) required
- supported NMEA messages: ...
- default connection parameters as for NMEA 0183 (4800 8n1)

For seamless serial communication set of wrapper classes has been implemented.
This gives the consumer code consistent API for retrieving NMEA lines with checksum control.

Units used across the project are unified and base on primitive types whenever possible.
If not stated otherwise all respective values are expressed in following units:
- latitude : angle in degrees with fraction (S is negative angle)
- longitude : angle in degrees with fraction (W is negative angle)
- azimuth : angle in degrees with fraction
- velocity : meters per second
- altitude : meters
- air pressure : pascals
- relative humidity : percents

Set of web-services available according to OBP specification (under construction) can be selectively
protected by authentication mechanisms configured flexibly (through Spring Security)