package com.longan.biz.core;

import com.longan.biz.dataobject.PddOrder;
import com.longan.biz.domain.Result;

public interface PddOrderService {
    public Result<PddOrder> selectOrder(Long uid) throws Exception;


}
