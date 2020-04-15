package com.longan.biz.core;

import java.io.File;
import java.util.List;

import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.domain.Result;

public interface AreaInfoService {
    public Result<MobileBelong> queryProvinceCodeByMobile(String mobile);

    public Result<Boolean> importDb(File file);

    public Result<List<AreaInfo>> queryAllProvince();

    public Result<List<AreaInfo>> queryAllCity();
}
