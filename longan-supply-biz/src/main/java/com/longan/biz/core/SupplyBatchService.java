package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyBatchQuery;
import com.longan.biz.domain.Result;

public interface SupplyBatchService {
    public Result<Boolean> createSupplyBatch(SupplyBatch supplyBatch);

    public Result<Boolean> updateSupplyBatch(SupplyBatch supplyBatch);

    public Result<SupplyBatch> getSupplyBatchById(Long id);

    public Result<List<SupplyBatch>> querySupplyBatch(SupplyBatchQuery supplyBatchQuery);
}
