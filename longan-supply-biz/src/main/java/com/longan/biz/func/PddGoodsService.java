package com.longan.biz.func;

import com.longan.biz.domain.Result;

public interface PddGoodsService {
    public Result<Boolean> itemUp(Integer itemId);

    public Result<Boolean> itemDown(Integer itemId);

    public Result<Boolean> batchUp(Integer bizId, String provinceCode);

    public Result<Boolean> batchDown(Integer bizId, String provinceCode);
}
