package com.longan.biz.cached;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.core.AreaInfoService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.domain.Result;

public class LocalAreaInfoCached extends LocalCached<String, AreaInfo> {
    @Resource
    private AreaInfoService areaInfoService;

    @Override
    public void init() {
	super.init(3600L, 3600L);
    }

    @Override
    void reloadFromDb(ConcurrentMap<String, AreaInfo> cached) {
	reloadProvince(cached);
	reloadCity(cached);
    }

    private void reloadProvince(ConcurrentMap<String, AreaInfo> cached) {
	Result<List<AreaInfo>> result = areaInfoService.queryAllProvince();
	if (!result.isSuccess()) {
	    logger.error("reloadFromDb error : " + result.getResultMsg());
	    return;
	}
	List<AreaInfo> list = result.getModule();
	if (list != null) {
	    for (AreaInfo areaInfo : list) {
		cached.put(areaInfo.getProvinceCode(), areaInfo);
	    }
	}
    }

    private void reloadCity(ConcurrentMap<String, AreaInfo> cached) {
	Result<List<AreaInfo>> result = areaInfoService.queryAllCity();
	if (!result.isSuccess()) {
	    logger.error("reloadFromDb error : " + result.getResultMsg());
	    return;
	}
	List<AreaInfo> list = result.getModule();
	if (list != null) {
	    for (AreaInfo areaInfo : list) {
		cached.put(areaInfo.getAreaCode(), areaInfo);
	    }
	}
    }
}
