package com.longan.biz.dao;

import com.longan.biz.domain.Result;

public interface CommitLimitDAO {
    Result<Boolean> addLimit(Long supplyTraderId, Integer price) throws Exception;

    Result<Boolean> deductLimit(Long supplyTraderId, Integer price) throws Exception;
}
