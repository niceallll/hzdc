package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationLogQuery;
import com.longan.biz.domain.Result;

public interface OperationLogService {
    public Result<OperationLog> createOperationLog(OperationLog operationLog);

    public Result<List<OperationLog>> queryOperationLogList(OperationLogQuery operationLogQuery);
}
