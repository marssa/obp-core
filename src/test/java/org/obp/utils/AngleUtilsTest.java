package org.obp.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class AngleUtilsTest {

    @Test
    public void testDdmmToDegreesConversion() {
        Assert.assertEquals(-49.50, AngleUtils.fromDDMM(-4930.00));
    }
}
