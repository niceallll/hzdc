package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.ManualLog;
import com.longan.biz.dataobject.ManualLogQuery;
import com.longan.biz.dataobject.ManualOrder;
import com.longan.biz.domain.Result;

public interface ManualLogService {
    public Result<ManualLog> createManualLog(ManualLog manualLog);

    public Result<Boolean> updateManualLog(ManualLog manualLog);

    public Result<List<ManualLog>> queryManualLogPage(ManualLogQuery manualLogQuery);

    public void commitManualLog(Long id, Integer itemFacePrice) throws Exception;

    public Result<ManualOrder> createManualOrder(ManualOrder manualOrder);

    public Result<Boolean> createManualOrder(List<ManualOrder> orderList);

    public Result<Boolean> updateManualOrder(ManualOrder manualOrder);

    public Result<ManualOrder> repeatManualOrder(ManualOrder manualOrder, ManualOrder repeatManualOrder);

    public Result<ManualOrder> getManualOrder(Long id);

    public Result<ManualOrder> getManualOrderBySerialno(String serialno);

    public Result<List<ManualOrder>> queryManualOrder(Long manualLogId);
}
