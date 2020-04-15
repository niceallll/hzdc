package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.BulkOrder;
import com.longan.biz.dataobject.BulkOrderQuery;

public interface BulkOrderDAO {
    Long insert(BulkOrder bulkOrder) throws SQLException;

    int updateBulkOrder(BulkOrder bulkOrder) throws SQLException;

    BulkOrder selectBulkOrderById(Long id) throws SQLException;

    List<BulkOrder> queryByPage(BulkOrderQuery bulkOrderQuery) throws SQLException;
}
