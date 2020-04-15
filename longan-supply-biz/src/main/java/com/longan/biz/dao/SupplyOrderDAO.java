package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.sumobject.SupplyOrderAmount;

public interface SupplyOrderDAO {
    Long insert(SupplyOrder supplyOrder) throws SQLException;

    List<SupplyOrder> queryByPage(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    List<SupplyOrder> queryByList(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    SupplyOrderAmount querySumAmount(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    int updateSupplyOrder(SupplyOrder supplyOrder) throws SQLException;

    SupplyOrder getSupplyOrderById(Long supplyOrderId) throws SQLException;

    SupplyOrder selectSupplyOrderById(Long id) throws SQLException;

    Integer queryCount(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    boolean updateSupplyOrderCheckStatus(SupplyOrder supplyOrder, List<Integer> statusList) throws SQLException;

    int countByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    List<SupplyOrder> queryByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    List<SupplyOrder> selectByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    List<SupplyOrder> getSaleExport(SupplyOrderQuery supplyOrderQuery) throws SQLException;

    List<SupplyOrder> selectByCrawl(Long supplyTraderId) throws SQLException;

    int updateByCrawl(Long id) throws SQLException;

    int updateRollbackCrawl(Long id) throws SQLException;
}
