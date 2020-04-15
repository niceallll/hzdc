package com.longan.mng.utils;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.MapMaker;

/**
 * 验证码超时时间的设置
 */
public class CheckCodeContext {
    //google包的集合过期集合键的设置
    public final static ConcurrentMap<String, String> map = new MapMaker().expiration(120l, TimeUnit.SECONDS)
	    .concurrencyLevel(16).initialCapacity(100).makeMap();
}
