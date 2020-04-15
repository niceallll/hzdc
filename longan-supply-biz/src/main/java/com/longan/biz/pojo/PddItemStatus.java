package com.longan.biz.pojo;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.MapMaker;

public class PddItemStatus {
    public final static ConcurrentMap<Integer, Integer> map = new MapMaker().expiration(24, TimeUnit.HOURS).concurrencyLevel(8)
	    .initialCapacity(500).makeMap();
}
