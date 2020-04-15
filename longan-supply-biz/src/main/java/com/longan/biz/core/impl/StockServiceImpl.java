package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.StockService;
import com.longan.biz.dao.CommitStockDAO;
import com.longan.biz.dao.StockDAO;
import com.longan.biz.dao.StockLogDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockExample;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogQuery;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class StockServiceImpl extends BaseService implements StockService {
    @Resource
    private StockDAO stockDAO;

    @Resource
    private StockLogDAO stockLogDAO;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private CommitStockDAO commitStockDAO;

    @Override
    public Result<Stock> lockStock(SupplyOrder supplyOrder) {
	Result<Stock> result = new Result<Stock>();
	if (supplyOrder == null || supplyOrder.getItemSupplyId() == null) {
	    result.setResultMsg("bizOrder is null or itemSupplyId is null");
	    return result;
	}

	Stock stock = null;
	try {
	    stock = commitStockDAO.lockStock(supplyOrder);
	    if (stock == null) {
		result.setResultMsg("出库失败,库存系统繁忙或库存短缺");
		return result;
	    }
	} catch (Exception e) {
	    result.setResultMsg("锁定库存数据库异常");
	    logger.error(
		    "lockStock error bizOrderId : " + supplyOrder.getBizOrderId() + " supllyOrderId : " + supplyOrder.getId()
			    + " itemSupplyId : " + supplyOrder.getItemSupplyId(), e);
	}
	result.setSuccess(true);
	result.setModule(stock);
	return result;
    }

    @Override
    public Result<Boolean> deliveryFromStorage(Stock stock) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (stock == null) {
	    result.setResultMsg("stock is null");
	    return result;
	}
	if (!stock.canDeliveryFromStorage()) {
	    result.setResultMsg("出库操作，库存状态必须是出库中");
	    return result;
	}

	try {
	    Stock stockUpdate = new Stock();
	    stockUpdate.setStatus(Constants.Stock.STATUS_DELIVERY);
	    StockExample example = new StockExample();
	    example.createCriteria().andIdEqualTo(stock.getId()).andStatusEqualTo(Constants.Stock.STATUS_INV_ALLOCATED);
	    Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("出库异常 msg : " + e.getMessage());
	    logger.error("deliveryFromStorage error stockId : " + stock.getId() + " itemId : " + stock.getItemId(), e);
	}
	return result;
    }

    @Override
    public Result<Boolean> putInStorage(List<Stock> stockList, StockLog stockLog) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (stockList == null) {
	    result.setResultMsg("stockList is null");
	    return result;
	}

	try {
	    commitStockDAO.commitPutInStorage(stockList, stockLog);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("批量入库异常 msg : " + e.getMessage());
	    logger.error("batch putInStorage error ", e);

	    // 修改为失败
	    stockLog.setStatus(Constants.StockLog.STATUS_FAILED);
	    try {
		stockLogDAO.updateByPrimaryKeySelective(stockLog);
	    } catch (SQLException e1) {
		logger.error("update stockLog error ", e);
	    }
	}
	return result;
    }

    @Override
    public Result<Boolean> returnToStorage(Stock stock) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (stock == null) {
	    result.setResultMsg("stock is null");
	    return result;
	}
	if (!stock.canReturnToStorage()) {
	    result.setResultMsg("库存返还操作，库存状态必须是出库中 或 已出库 或 异常隔离状态的库存");
	    return result;
	}

	try {
	    commitStockDAO.commiteReturnToStorage(stock);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("库存返还异常 msg : " + e.getMessage());
	    logger.error("returnToStorage error stockId : " + stock.getId() + " itemId : " + stock.getItemId(), e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateStock(Stock stock) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Result<StockLog> createInStockLog(Long itemSupplyId, Integer itemCostPrice, Integer bizId, Long operId) {
	Result<StockLog> result = new Result<StockLog>();
	ItemSupply itemSupply = localCachedService.getItemSupply(itemSupplyId);
	if (itemSupply == null) {
	    result.setResultMsg("没有该供货商品");
	    return result;
	}
	Item item = localCachedService.getItem(itemSupply.getItemId());
	if (item == null) {
	    result.setResultMsg("没有该商品信息");
	    return result;
	}
	UserInfo userInfo = localCachedService.getUserInfo(operId);
	if (userInfo == null) {
	    result.setResultMsg("没有操作员信息");
	    return result;
	}

	StockLog stockLog = new StockLog();
	stockLog.setItemSupplyId(itemSupplyId);
	stockLog.setSupplyTraderId(itemSupply.getSupplyTraderId());
	stockLog.setItemId(item.getId());
	stockLog.setItemName(item.getItemName());
	stockLog.setOperId(operId);
	stockLog.setOperName(userInfo.getUserName());
	stockLog.setItemCostPrice(itemCostPrice);
	stockLog.setType(Constants.StockLog.TYPE_IN);
	stockLog.setBizId(bizId);
	stockLog.setStatus(Constants.StockLog.STATUS_INIT);
	try {
	    Long id = stockLogDAO.insert(stockLog);
	    stockLog.setId(id);
	    result.setSuccess(true);
	    result.setModule(stockLog);
	} catch (SQLException e) {
	    result.setResultMsg("创建入库流水失败 msg : " + e.getMessage());
	    logger.error("createStockLog error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateStockLog(StockLog stockLog) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = stockLogDAO.updateByPrimaryKeySelective(stockLog);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新库存日志异常  msg : " + e.getMessage());
	    logger.error("updateStockLog error stockLogId : " + stockLog.getId(), e);
	}
	return result;
    }

    @Override
    public Result<List<StockLog>> queryStockListPage(StockLogQuery stockLogQuery) {
	Result<List<StockLog>> result = new Result<List<StockLog>>();
	try {
	    List<StockLog> queryResult = stockLogDAO.queryByPage(stockLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("库存日志查询失败    msg: " + e.getMessage());
	    logger.error("queryStockListPage error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> sequestrationStorage(Stock stock) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (stock == null) {
	    result.setResultMsg("stock is null");
	    return result;
	}
	if (stock.getStatus() != Constants.Stock.STATUS_INV_ALLOCATED) {
	    result.setResultMsg("隔离库存操作，库存状态必须是出库中");
	    return result;
	}

	try {
	    Stock stockUpdate = new Stock();
	    stockUpdate.setStatus(Constants.Stock.STATUS_EXCEPTION);
	    stockUpdate.setErrorInfo(stock.getErrorInfo());

	    StockExample example = new StockExample();
	    example.createCriteria().andIdEqualTo(stock.getId()).andStatusEqualTo(Constants.Stock.STATUS_INV_ALLOCATED);
	    Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);

	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("隔离库存失败  msg : " + e.getMessage());
	    logger.error("sequestrationStorage error stockId : " + stock.getId() + " itemId : " + stock.getItemId(), e);
	}
	return result;
    }

    @Override
    public Result<Stock> getStockById(Long stockId) {
	Result<Stock> result = new Result<Stock>();
	try {
	    Stock stock = stockDAO.selectByPrimaryKey(stockId);
	    result.setSuccess(true);
	    result.setModule(stock);
	} catch (SQLException e) {
	    result.setResultMsg("查询库存是失败  msg : " + e.getMessage());
	    logger.error("getStockById error stockId : " + stockId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> adjustStorage(Long stockId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Result<Stock> stockResult = getStockById(stockId);
	if (!stockResult.isSuccess()) {
	    result.setResultMsg(stockResult.getResultMsg());
	    return result;
	}
	Stock stock = stockResult.getModule();
	if (stock == null) {
	    result.setResultMsg("stock is null");
	    return result;
	}
	if (stock.getStatus() != Constants.Stock.STATUS_INV_ALLOCATED && stock.getStatus() != Constants.Stock.STATUS_DELIVERY) {
	    result.setResultMsg("调账库存操作，库存状态必须是 已经出库，或者出库中");
	    return result;
	}

	try {
	    Stock stockUpdate = new Stock();
	    stockUpdate.setStatus(Constants.Stock.STATUS_EXCEPTION);
	    StockExample example = new StockExample();
	    List<Integer> values = new ArrayList<Integer>();
	    values.add(Constants.Stock.STATUS_INV_ALLOCATED);
	    values.add(Constants.Stock.STATUS_DELIVERY);
	    example.createCriteria().andIdEqualTo(stock.getId()).andStatusIn(values);
	    Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("调库存  msg : " + e.getMessage());
	    logger.error("adjustStorage error stockId : " + stock.getId() + " itemId : " + stock.getItemId(), e);
	}
	return result;
    }

    @Override
    public Result<List<Stock>> queryStockPage(StockQuery stockQuery) {
	Result<List<Stock>> result = new Result<List<Stock>>();
	try {
	    List<Stock> queryResult = stockDAO.queryByPage(stockQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("库存列表失败    msg: " + e.getMessage());
	    logger.error("queryStockPage error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> setStorageInvalid(Long stockId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (stockId == null) {
	    result.setResultMsg("stockId is null");
	    return result;
	}
	Result<Stock> stockResult = getStockById(stockId);
	if (!stockResult.isSuccess()) {
	    result.setResultMsg(stockResult.getResultMsg());
	    return result;
	}
	Stock stock = stockResult.getModule();
	if (stock == null) {
	    result.setResultMsg("stock is null");
	    return result;
	}
	if (stock.getStatus() != Constants.Stock.STATUS_EXCEPTION && stock.getStatus() != Constants.Stock.STATUS_INV_ALLOCATED
		&& stock.getStatus() != Constants.Stock.STATUS_NORMAL) {
	    result.setResultMsg("库存状态必须是异常隔离中,正常销售或出库中");
	    return result;
	}

	try {
	    commitStockDAO.commitInvalidStorage(stock);
	} catch (Exception e) {
	    result.setResultMsg("设置库存失效失败  msg : " + e.getMessage());
	    logger.error("setStorageInvalid error stockId : " + stock.getId(), e);
	    return result;
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<Integer> activateStorageByStockLog(Long stockLogId) {
	Result<Integer> result = new Result<Integer>();
	StockLog stockLog = null;
	try {
	    stockLog = stockLogDAO.selectByPrimaryKey(stockLogId);
	} catch (SQLException e) {
	    result.setResultMsg("激活库存失败  msg : " + e.getMessage());
	    logger.error("activateStorage error stockLogId = " + stockLogId, e);
	    return result;
	}

	if (stockLog == null) {
	    result.setResultMsg("入库日志不存在");
	    return result;
	}
	if (stockLog.getStatus() != Constants.StockLog.STATUS_SUCCESS) {
	    result.setResultMsg("入库日志状态必须是 '导入成功'");
	    return result;
	}

	Integer activatedCount = null;
	try {
	    activatedCount = commitStockDAO.commitActivateStorageByStockLog(stockLog);
	} catch (Exception e) {
	    result.setResultMsg("激活库存失败 msg " + e.getMessage());
	    logger.error("activateStorage error stockLogId : " + stockLogId, e);
	    return result;
	}

	if (activatedCount <= 0) {
	    result.setResultMsg("激活失败，激活库存数为零");
	    return result;
	}

	result.setSuccess(true);
	result.setModule(activatedCount);
	return result;
    }

    @Override
    public Result<Integer> invalidStorageByStockLog(Long stockLogId) {
	Result<Integer> result = new Result<Integer>();
	StockLog stockLog = null;
	try {
	    stockLog = stockLogDAO.selectByPrimaryKey(stockLogId);
	} catch (SQLException e) {
	    result.setResultMsg("作废库存失败  msg : " + e.getMessage());
	    logger.error("activateStorage error stockLogId = " + stockLogId, e);
	    return result;
	}

	if (stockLog == null) {
	    result.setResultMsg("入库日志不存在");
	    return result;
	}
	if (stockLog.getStatus() != Constants.StockLog.STATUS_ACTIVATED
		&& stockLog.getStatus() != Constants.StockLog.STATUS_SUCCESS) {
	    result.setResultMsg("入库日志状态必须是 '导入成功' 或 '已激活'");
	    return result;
	}

	Integer invalidCount = null;
	try {
	    invalidCount = commitStockDAO.commitInvalidStorageByStockLog(stockLog);
	} catch (Exception e) {
	    result.setResultMsg("作废库存失败 msg " + e.getMessage());
	    logger.error("invalidStorageByStockLog error stockLogId : " + stockLogId, e);
	    return result;
	}

	result.setSuccess(true);
	result.setModule(invalidCount);
	return result;
    }

    @Override
    public Result<StockLog> getStockLog(Long stockLogId) {
	Result<StockLog> result = new Result<StockLog>();
	try {
	    StockLog stockLog = stockLogDAO.selectByPrimaryKey(stockLogId);
	    if (stockLog != null) {
		result.setSuccess(true);
		result.setModule(stockLog);
	    } else {
		result.setResultMsg("没有该库存日志  stockLogId : " + stockLogId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("查询库存日志失败 ： msg :" + e.getMessage());
	    logger.error("getStockLog error stockLogId : " + stockLogId, e);
	}
	return result;
    }

    @Override
    public Result<StockLog> createOutStockLog(Long itemSupplyId, Integer count, Long operId) {
	Result<StockLog> result = new Result<StockLog>();
	ItemSupply itemSupply = localCachedService.getItemSupply(itemSupplyId);
	if (itemSupply == null) {
	    result.setResultMsg("没有该供货商品");
	    return result;
	}
	Item item = localCachedService.getItem(itemSupply.getItemId());
	if (item == null) {
	    result.setResultMsg("没有该商品信息");
	    return result;
	}
	UserInfo userInfo = localCachedService.getUserInfo(operId);
	if (userInfo == null) {
	    result.setResultMsg("没有操作员信息");
	    return result;
	}

	StockLog stockLog = new StockLog();
	stockLog.setItemSupplyId(itemSupplyId);
	stockLog.setSupplyTraderId(itemSupply.getSupplyTraderId());
	stockLog.setItemId(item.getId());
	stockLog.setItemName(item.getItemName());
	stockLog.setOperId(operId);
	stockLog.setOperName(userInfo.getUserName());
	stockLog.setStockCount(count);
	// stockLog.setItemCostPrice(itemCostPrice);
	stockLog.setType(Constants.StockLog.TYPE_OUT);
	stockLog.setBizId(item.getBizId());
	stockLog.setStatus(Constants.StockLog.STATUS_INIT);
	try {
	    Long id = stockLogDAO.insert(stockLog);
	    stockLog.setId(id);
	    result.setSuccess(true);
	    result.setModule(stockLog);
	} catch (SQLException e) {
	    result.setResultMsg("创建出库流水失败 数据库异常");
	    logger.error("createOutStockLog error ", e);
	}
	return result;
    }

    @Override
    public Result<StockLog> deliveryFromStorageByStockLog(Long stockLogId) {
	Result<StockLog> result = new Result<StockLog>();
	StockLog stockLog = null;
	try {
	    stockLog = stockLogDAO.selectByPrimaryKey(stockLogId);
	} catch (SQLException e) {
	    result.setResultMsg("出库失败，数据库异常");
	    logger.error("deliveryFromStorageByStockLog error stockLogId = " + stockLogId, e);
	    return result;
	}
	if (stockLog == null) {
	    result.setResultMsg("出库日志不存在");
	    return result;
	}
	if (stockLog.getStatus() != Constants.StockLog.STATUS_INIT) {
	    result.setResultMsg("出库日志状态必须是 '初始状态'");
	    return result;
	}

	Integer outCount = null;
	try {
	    outCount = commitStockDAO.commitOutStorageByStockLog(stockLog);
	} catch (Exception e) {
	    stockLog.setMemo("出库数据库异常");
	    stockLog.setStatus(Constants.StockLog.STATUS_FAILED);
	    this.updateStockLog(stockLog);
	    result.setResultMsg("出库失败 msg " + e.getMessage());
	    logger.error("deliveryStorageByStockLog error stockLogId : " + stockLogId, e);
	    return result;
	}
	if (outCount == 0) {
	    result.setResultMsg("出库失败 ，库存不足");
	    return result;
	}

	stockLog.setStockCount(outCount);
	result.setSuccess(true);
	result.setModule(stockLog);
	return result;
    }
}
