package org.obp;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-18
 */
public interface Explorer extends Identified {
    List<Body> scan();
}
