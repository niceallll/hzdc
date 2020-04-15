package com.longan.biz.core;

import com.longan.biz.dataobject.BulkOrder;
import com.longan.biz.domain.Result;

public interface BulkOrderService {
    public Result<BulkOrder> createBulkOrder(BulkOrder bulkOrder);

    public Result<Boolean> updateBulkOrder(BulkOrder bulkOrder);

    public Result<BulkOrder> getBulkOrderByIdAndTraderId(Long id, Long traderId);

    public Result<BulkOrder> getBulkOrderByUpstreamSerialno(String upstreamSerialno, Long traderId);
}
