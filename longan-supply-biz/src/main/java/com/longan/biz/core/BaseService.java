package com.longan.biz.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longan.biz.cached.LocalCachedService;

public class BaseService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected LocalCachedService localCachedService;
}
