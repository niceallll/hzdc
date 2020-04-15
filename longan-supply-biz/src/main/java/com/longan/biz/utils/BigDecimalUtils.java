package com.longan.biz.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class BigDecimalUtils {
    public static Double doubleMult(String v1, String v2) {
	if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(v2);
	return b1.multiply(b2).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double doubleDiveidPercent(String v1, String v2) {
	if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(100);
	BigDecimal b3 = new BigDecimal(v2);
	BigDecimal value = b1.multiply(b2).setScale(0, BigDecimal.ROUND_HALF_UP);
	return value.divide(b3, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer multDiscount(String v1, String v2) {
	if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(v2);
	BigDecimal b3 = new BigDecimal(100);
	BigDecimal value = b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
	return value.divide(b3, 0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 元转化成厘
     * 
     * @param v1
     * @return
     */
    public static Integer multInteger(String v1) {
	if (StringUtils.isEmpty(v1)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(1000);
	BigDecimal value = b1.multiply(b2).setScale(3, BigDecimal.ROUND_HALF_UP);
	return value.intValue();
    }

    public static Long multLong(String v1) {
	if (StringUtils.isEmpty(v1)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(1000);
	BigDecimal value = b1.multiply(b2).setScale(3, BigDecimal.ROUND_HALF_UP);
	return value.longValue();
    }

    /**
     * 厘转换成元
     * 
     * @param v1
     * @return
     */
    public static Double doubleDiveid(String v1) {
	if (StringUtils.isEmpty(v1)) {
	    return null;
	}
	BigDecimal b1 = new BigDecimal(v1);
	BigDecimal b2 = new BigDecimal(1000);
	return b1.divide(b2).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getAmountDesc(Double db) {
	DecimalFormat df = new DecimalFormat("0");
	df.setMaximumFractionDigits(3);
	return df.format(db);
    }

    public static void main(String[] args) {
	BigDecimal b = new BigDecimal("399980.00");
	BigDecimal b3 = new BigDecimal(100);
	b.divide(b3, 0, BigDecimal.ROUND_HALF_UP).intValue();
	System.out.println(b.divide(b3, 0, BigDecimal.ROUND_HALF_UP).intValue());
    }
}
