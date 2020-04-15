package com.longan.biz.core;

import com.longan.biz.domain.Result;

public interface QblibInfoService {
    public Result<Boolean> deletePhoneByUid(String itemUid);

    public Result<String> queryMobileByAreaCode(String areacode);

    public Result<String> queryPhoneByAreaCode(String areacode);
}
