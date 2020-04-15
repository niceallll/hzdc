package com.hzdc.biz.core;

import com.hzdc.biz.data.object.TargetCharge;
import com.hzdc.biz.data.object.TargetOrder;
import com.longan.biz.domain.Result;

public interface GotoTargetService {
    public Result<Boolean> gotoCharge(TargetCharge targetCharge);

    public Result<Boolean> gotoOrder(TargetOrder targetOrder);
}
