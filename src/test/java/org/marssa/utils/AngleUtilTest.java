package org.marssa.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public class AngleUtilTest {

    @Test
    public void testDdmmToDegreesConversion() {
        Assert.assertEquals(-49.50, AngleUtil.fromDDMM(-4930.00));
    }
}
