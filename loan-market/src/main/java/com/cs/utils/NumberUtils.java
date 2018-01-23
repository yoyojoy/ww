package com.cs.utils;

import java.text.DecimalFormat;

/**
 * Created by joy on 2017/7/14.
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{

    public static final DecimalFormat NUMBER_FORMAT   = new DecimalFormat("######0.00");

    public static final DecimalFormat RATE_FORMAT   = new DecimalFormat("######0.00%");
}
