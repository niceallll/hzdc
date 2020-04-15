package com.longan.biz.core;

import com.longan.biz.domain.Result;

public interface BlackListService {
    public Result<Integer> getCountByUid(String itemUid);
}
