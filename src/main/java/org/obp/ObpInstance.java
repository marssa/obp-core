package org.obp;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface ObpInstance extends Identified {
    List<Body> getBodies();
    Attributes getAttributes(String... keys);
    Attributes getAttributes();
    AttributeInfo getAttributeInfo(String key);
    List<AttributeInfo> getAttributeInfos();
    void attachInstrument(Instrument instrument);
    void detachInstrument(Instrument instrument);
    void attachExplorer(Explorer explorer);
    void detachExplorer(Explorer explorer);
}
