/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.gps;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-13
 */
public class GpsSatellite {
    private int id;
    private double elevation;
    private double azimuth;
    private double snr;

    public GpsSatellite(int id, double elevation, double azimuth, double snr) {
        this.id = id;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.snr = snr;
    }

    public int getId() {
        return id;
    }

    public double getElevation() {
        return elevation;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getSnr() {
        return snr;
    }
}
