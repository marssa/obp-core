Pre-release version of Open Bridge Platform (OBP).
This is a complete, self contained web-application artifact.

Refer to release-notes.txt for more detailed information about version changes.

current set-up:

- hardware: embedded ARM7 compatible platform with <= 1GB RAM
- OS: Ubuntu Precise Linux 12.x LTS with soft or hard floating point support ABI
- Oracle JDK 7 with hard-fp or OpenJDK 7 with soft-fp
- USB/serial converters based on Prolific PL2303 chipset, supported natively by the kernel
- serial communication provided with jSSC library (proven its supremacy over RXTX :-))
- servlet container: Apache Tomcat 7.x
- database: file based H2 embedded engine

supported hardware:

GlobalSat BU-353 compatible GPS receiver
- accessible via serial port device (originally via internal USB/serial Prolific interface)
- supported NMEA messages: GPGGA, GPGLL, GPGSA, GPGSV, GPRMC, GPVTG
- default connection parameters as for NMEA 0183 (4800 8n1)

LCJ Capteurs CV3Fm6 40 ultrasound wind-vane
- accessible via serial port device, physical serial port (Rx,Tx only) required
- supported NMEA messages: IIMWV, WIXDR
- LCJ specific messages ignored as they don't have a valid NMEA format
- default connection parameters as for NMEA 0183 (4800 8n1)

For seamless serial communication set of wrapper classes has been implemented.
This gives the consumer code consistent API for retrieving NMEA lines with checksum control.

Units used across the project are unified and base on primitive types whenever possible.
If not stated otherwise all respective values are expressed in following units:

- latitude : angle in degrees with fraction, S is negative angle, unit: °
- longitude : angle in degrees with fraction, W is negative angle, unit: °
- azimuth : angle in degrees with fraction, measured from N clockwise, unit: °
- velocity : meters per second, unit: m/s
- altitude : meters, unit: m
- air pressure : pascals, unit: Pa
- relative humidity : percents, unit: %
- temperature : degrees centigrade (celsius), symbol: °C

Set of web-services available according to OBP specification (under construction) can be selectively
protected by authentication mechanisms configured flexibly (through Spring Security)