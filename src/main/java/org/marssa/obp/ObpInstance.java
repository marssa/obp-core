package org.marssa.obp;

import java.util.List;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface ObpInstance extends Identified {
    List<Body> getBodies();
    Map<String,Object> getAttributeValues(String... names);
    Map<String,Object> getAllAttributeValues();
    Attribute getAttribute(String name);
    void attachInstrument(Instrument instrument);
    void detachInstrument(Instrument instrument);
    void attachExplorer(Explorer explorer);
    void detachExplorer(Explorer explorer);
}
