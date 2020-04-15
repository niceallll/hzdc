package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogQuery;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;

public interface StockService {

    /**
     * 锁定库存
     * 
     * @param item
     * @return
     */
    public Result<Stock> lockStock(SupplyOrder supplyOrder);

    /**
     * 出库
     * 
     * @param stock
     * @return
     */
    public Result<Boolean> deliveryFromStorage(Stock stock);

    /**
     * 返回库存。
     * 
     * @param stock
     * @return
     */
    public Result<Boolean> returnToStorage(Stock stock);

    /**
     * 隔离库存,可能该库存有异常,失效，或者不存在。
     * 
     * @param stock
     * @return
     */
    public Result<Boolean> sequestrationStorage(Stock stock);

    /**
     * 放入库存，但是是未激活
     * 
     * @param stockList
     * @return
     */
    public Result<Boolean> putInStorage(List<Stock> stockList, StockLog stockLog);

    public Result<Stock> getStockById(Long stockId);

    public Result<Boolean> updateStock(Stock stock);

    public Result<StockLog> createInStockLog(Long itemSupplyId, Integer itemCostPrice, Integer bizId, Long operId);

    public Result<Boolean> updateStockLog(StockLog stockLog);

    public Result<List<StockLog>> queryStockListPage(StockLogQuery stockLogQuery);

    /**
     * 把已出库的库存，修改为异常状态。
     * 
     * @param
     * @return
     */
    public Result<Boolean> adjustStorage(Long stockId);

    /**
     * 分页查询
     * 
     * @param stockQuery
     * @return
     */
    public Result<List<Stock>> queryStockPage(StockQuery stockQuery);

    /**
     * 设置成失效
     * 
     * @param stock
     * @return
     */
    public Result<Boolean> setStorageInvalid(Long stockId);

    /**
     * 按批次激活
     * 
     * @param stockLog
     * @return
     */
    public Result<Integer> activateStorageByStockLog(Long stockLogId);

    /**
     * 按批次失效
     * 
     * @param stockList
     * @return
     */
    public Result<Integer> invalidStorageByStockLog(Long stockLogId);

    public Result<StockLog> getStockLog(Long stockLogId);

    public Result<StockLog> createOutStockLog(Long itemSupplyId, Integer count, Long operId);

    public Result<StockLog> deliveryFromStorageByStockLog(Long stockLogId);
}
