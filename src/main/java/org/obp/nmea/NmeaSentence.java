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

package org.obp.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */
public class NmeaSentence {
    private String name;
    private String[] data;

    public NmeaSentence(String name, String[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getData(int index) {
        return data[index];
    }

    public NmeaSentenceScanner scanner() {
        return new NmeaSentenceScanner(data);
    }

    public int getDataSize() {
        return data==null ? 0 : data.length;
    }

    public String getDataAsString() {
        StringBuilder sb = new StringBuilder();
        for(String str : data) {
            String s = str.trim();
            sb.append(s.isEmpty() ? "_" : s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public String toString() {
        return name+" "+getDataAsString();
    }
}
