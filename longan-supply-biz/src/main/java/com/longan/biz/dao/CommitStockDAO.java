package com.longan.biz.dao;

import java.util.List;

import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.SupplyOrder;

public interface CommitStockDAO {
    public Stock lockStock(SupplyOrder supplyOrder) throws Exception;

    public void commitPutInStorage(List<Stock> stockList, StockLog stockLog) throws Exception;

    public void commiteReturnToStorage(Stock stock) throws Exception;

    public Integer commitActivateStorageByStockLog(StockLog stockLog) throws Exception;

    public Integer commitInvalidStorageByStockLog(StockLog stockLog) throws Exception;

    public void commitInvalidStorage(Stock stock) throws Exception;

    public Integer commitOutStorageByStockLog(StockLog stockLog) throws Exception;
}
