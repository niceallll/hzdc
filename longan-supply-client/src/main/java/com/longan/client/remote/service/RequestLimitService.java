package com.longan.client.remote.service;

import com.longan.biz.domain.Result;

public interface RequestLimitService {
    public Result<Boolean> isMaxLimit(Integer bizId);

    public Result<Boolean> putInQueue(Integer bizId);

    public Result<Boolean> outQueue(Integer bizId);

    public Result<Integer> getQueueCount(Integer bizId);
}
