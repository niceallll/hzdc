package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoQuery;
import com.longan.biz.domain.Result;

public interface OperationInfoService {
    public Result<List<OperationInfo>> queryOperationInfoList(OperationInfoQuery operationInfoQuery);

    public Result<OperationInfo> getOperationInfoById(Integer id);

    public Result<Boolean> updateOperationInfo(OperationInfo operationInfo);

    public Result<Boolean> createOperationInfo(OperationInfo operationInfo);

    public Result<List<OperationInfo>> queryOperationInfoByType(Integer type);

    public Result<List<OperationInfo>> queryAllOperationInfo();
}
