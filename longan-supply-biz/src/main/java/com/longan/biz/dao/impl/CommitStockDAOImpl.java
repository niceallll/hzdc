package com.longan.biz.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.dao.CommitStockDAO;
import com.longan.biz.dao.ItemSupplyDAO;
import com.longan.biz.dao.StockDAO;
import com.longan.biz.dao.StockLogDAO;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockExample;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogExample;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.utils.Constants;

public class CommitStockDAOImpl implements CommitStockDAO {
    @Resource
    private StockDAO stockDAO;

    @Resource
    private ItemSupplyDAO itemSupplyDAO;

    @Resource
    private StockLogDAO stockLogDAO;

    private static final int countLimit = 30;

    // @Transactional(readOnly = false, propagation = Propagation.REQUIRED,
    // rollbackFor = Exception.class)
    // @Override
    // public Stock lockStock(Long itemSupplyId, BizOrder bizOrder) throws
    // Exception {
    // // 从select for update 到 update 不能有过多逻辑，减少锁的时间
    // Stock stock = stockDAO.selectForUpdate(itemSupplyId);
    // if (stock == null) {
    // return null;
    // }
    //
    // stock.setStatus(Constants.Stock.STATUS_INV_ALLOCATED);
    // stock.setOutTime(new Date());
    // stock.setBizOrderId(bizOrder.getId());
    // stockDAO.updateByPrimaryKeySelective(stock);
    //
    // // 减库存
    // ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(itemSupplyId);
    // int quantity = itemSupply.getQuantity() - 1;
    // if (quantity < 0) {
    // throw new RuntimeException("库存数不能小于零");
    // }
    // int numDay = itemSupply.getNumDay() + 1;
    // int numMounth = itemSupply.getNumMounth() + 1;
    // itemSupply.setQuantity(quantity);
    // itemSupply.setNumDay(numDay);
    // itemSupply.setNumMounth(numMounth);
    // itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
    // return stock;
    //
    // }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Stock lockStock(SupplyOrder supplyOrder) throws Exception {
	// 从select for update 到 update 不能有过多逻辑，减少锁的时间
	long startTime = System.currentTimeMillis();
	Stock enableStock = null;
	while ((System.currentTimeMillis() - 5000) <= startTime) {
	    List<Stock> stockList = stockDAO.selectInSales(supplyOrder.getItemSupplyId(), countLimit);
	    if (stockList == null || stockList.size() == 0) {
		return null;
	    }

	    Collections.shuffle(stockList);
	    for (Stock stock : stockList) {
		enableStock = stockDAO.selectForUpdateById(stock.getId());
		if (enableStock != null) {
		    enableStock.setStatus(Constants.Stock.STATUS_INV_ALLOCATED);
		    enableStock.setOutTime(new Date());
		    enableStock.setBizOrderId(supplyOrder.getBizOrderId());
		    enableStock.setSupplyOrderId(supplyOrder.getId());
		    stockDAO.updateByPrimaryKeySelective(enableStock);
		    // 减库存
		    ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(supplyOrder.getItemSupplyId());
		    int quantity = itemSupply.getQuantity() - 1;
		    if (quantity < 0) {
			throw new RuntimeException("库存数不能小于零");
		    }
		    // int numDay = itemSupply.getNumDay() + 1;
		    // int numMounth = itemSupply.getNumMounth() + 1;
		    itemSupply.setQuantity(quantity);
		    // itemSupply.setNumDay(numDay);
		    // itemSupply.setNumMounth(numMounth);
		    itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
		    break;
		}
	    }

	    if (enableStock != null) {
		// 锁定库存成功，跳出循环
		break;
	    }

	    Thread.sleep(10);
	}
	return enableStock;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitPutInStorage(List<Stock> stockList, StockLog stockLog) throws Exception {
	stockDAO.batchInsert(stockList);
	stockLog.setStockCount(stockList.size());
	if (stockList.size() > 0) {
	    if (stockList.size() == 1) {
		stockLog.setStockMemo(stockList.get(0).getCardId());
	    } else {
		stockLog.setStockMemo(stockList.get(0).getCardSerialNo() + "--"
			+ stockList.get(stockList.size() - 1).getCardSerialNo());
	    }
	}

	// 更新库存流水日志
	stockLog.setStatus(Constants.StockLog.STATUS_SUCCESS);
	int row = stockLogDAO.updateByPrimaryKeySelective(stockLog);
	if (row <= 0) {
	    throw new RuntimeException("更新库存日志失败");
	}
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commiteReturnToStorage(Stock stock) throws Exception {
	Stock stockUpdate = new Stock();
	stockUpdate.setStatus(Constants.Stock.STATUS_NORMAL);
	stockUpdate.setErrorInfo(stock.getErrorInfo());
	StockExample example = new StockExample();
	List<Integer> values = new ArrayList<Integer>();
	values.add(Constants.Stock.STATUS_INV_ALLOCATED);
	values.add(Constants.Stock.STATUS_EXCEPTION);
	values.add(Constants.Stock.STATUS_DELIVERY);
	example.createCriteria().andIdEqualTo(stock.getId()).andStatusIn(values);
	Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	if (row <= 0) {
	    throw new RuntimeException("更新库存失败,库存状态非正常");
	}
	// 加库存数
	ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(stock.getItemSupplyId());
	int quantity = itemSupply.getQuantity() + row;
	itemSupply.setQuantity(quantity);
	itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer commitActivateStorageByStockLog(StockLog stockLog) throws Exception {
	Stock stockUpdate = new Stock();
	stockUpdate.setStatus(Constants.Stock.STATUS_NORMAL);
	StockExample example = new StockExample();
	example.createCriteria().andStatusEqualTo(Constants.Stock.STATUS_INIT).andInSerialnoEqualTo(stockLog.getId());
	Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	if (row <= 0) {
	    throw new RuntimeException("激活库存失败,激活数为0");
	}
	// 加库存数
	ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(stockLog.getItemSupplyId());
	int quantity = itemSupply.getQuantity() + row;
	itemSupply.setQuantity(quantity);
	itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);

	StockLog stockLogUpdate = new StockLog();
	stockLogUpdate.setStatus(Constants.StockLog.STATUS_ACTIVATED);
	StockLogExample stockLogExample = new StockLogExample();
	stockLogExample.createCriteria().andIdEqualTo(stockLog.getId()).andStatusEqualTo(Constants.StockLog.STATUS_SUCCESS);
	Integer stockLogRow = stockLogDAO.updateByExampleSelective(stockLogUpdate, stockLogExample);
	if (stockLogRow <= 0) {
	    throw new RuntimeException("更新库存日志失败");
	}
	return row;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer commitInvalidStorageByStockLog(StockLog stockLog) throws Exception {
	Integer row = null;
	if (stockLog.getStatus() == Constants.StockLog.STATUS_ACTIVATED) {
	    Stock stockUpdate = new Stock();
	    stockUpdate.setStatus(Constants.Stock.STATUS_INVALID);
	    StockExample example = new StockExample();
	    example.createCriteria().andStatusEqualTo(Constants.Stock.STATUS_NORMAL).andInSerialnoEqualTo(stockLog.getId());
	    row = stockDAO.updateByExampleSelective(stockUpdate, example);
	    if (row > 0) {
		// 减库存数
		ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(stockLog.getItemSupplyId());
		int quantity = itemSupply.getQuantity() - row;
		if (quantity < 0) {
		    throw new RuntimeException("减库存数失败，库存数不能小于0");
		}
		itemSupply.setQuantity(quantity);
		itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
	    }

	    StockLog stockLogUpdate = new StockLog();
	    stockLogUpdate.setStatus(Constants.StockLog.STATUS_INVALID);
	    StockLogExample stockLogExample = new StockLogExample();
	    stockLogExample.createCriteria().andIdEqualTo(stockLog.getId()).andStatusEqualTo(Constants.StockLog.STATUS_ACTIVATED);
	    Integer stockLogRow = stockLogDAO.updateByExampleSelective(stockLogUpdate, stockLogExample);
	    if (stockLogRow <= 0) {
		throw new RuntimeException("更新库存日志失败");
	    }
	    return row;
	} else if (stockLog.getStatus() == Constants.StockLog.STATUS_SUCCESS) {
	    Stock stockUpdate = new Stock();
	    stockUpdate.setStatus(Constants.Stock.STATUS_INVALID);
	    StockExample example = new StockExample();
	    example.createCriteria().andStatusEqualTo(Constants.Stock.STATUS_INIT).andInSerialnoEqualTo(stockLog.getId());
	    row = stockDAO.updateByExampleSelective(stockUpdate, example);

	    StockLog stockLogUpdate = new StockLog();
	    stockLogUpdate.setStatus(Constants.StockLog.STATUS_INVALID);
	    StockLogExample stockLogExample = new StockLogExample();
	    stockLogExample.createCriteria().andIdEqualTo(stockLog.getId()).andStatusEqualTo(Constants.StockLog.STATUS_SUCCESS);
	    Integer stockLogRow = stockLogDAO.updateByExampleSelective(stockLogUpdate, stockLogExample);
	    if (stockLogRow <= 0) {
		throw new RuntimeException("更新库存日志失败");
	    }
	}

	return row;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitInvalidStorage(Stock stock) throws Exception {
	Stock stockUpdate = new Stock();
	stockUpdate.setStatus(Constants.Stock.STATUS_INVALID);
	StockExample example = new StockExample();
	if (stock.getStatus() == Constants.Stock.STATUS_EXCEPTION || stock.getStatus() == Constants.Stock.STATUS_INV_ALLOCATED) {
	    List<Integer> values = new ArrayList<Integer>();
	    values.add(Constants.Stock.STATUS_EXCEPTION);
	    values.add(Constants.Stock.STATUS_INV_ALLOCATED);
	    example.createCriteria().andIdEqualTo(stock.getId()).andStatusIn(values);
	    Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	    if (row <= 0) {
		throw new RuntimeException("更新库存状态失败");
	    }
	} else if (stock.getStatus() == Constants.Stock.STATUS_NORMAL) {
	    example.createCriteria().andIdEqualTo(stock.getId()).andStatusEqualTo(Constants.Stock.STATUS_NORMAL);
	    Integer row = stockDAO.updateByExampleSelective(stockUpdate, example);
	    if (row <= 0) {
		throw new RuntimeException("更新库存状态失败");
	    }

	    ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(stock.getItemSupplyId());
	    int quantity = itemSupply.getQuantity() - row;
	    if (quantity < 0) {
		throw new RuntimeException("减库存数失败，库存数不能小于0");
	    }
	    itemSupply.setQuantity(quantity);
	    itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
	}
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer commitOutStorageByStockLog(StockLog stockLog) throws Exception {
	int row = stockDAO.outStockByCount(stockLog.getStockCount(), stockLog.getItemSupplyId(), stockLog.getId());
	if (row <= 0) {
	    stockLog.setMemo("库存不足或数据异常");
	    stockLog.setStatus(Constants.StockLog.STATUS_FAILED);

	    StockLogExample stockLogExample = new StockLogExample();
	    stockLogExample.createCriteria().andIdEqualTo(stockLog.getId()).andStatusEqualTo(Constants.StockLog.STATUS_INIT);
	    Integer stockLogRow = stockLogDAO.updateByExampleSelective(stockLog, stockLogExample);
	    if (stockLogRow <= 0) {
		throw new RuntimeException("更新库存日志失败");
	    }
	    return row;
	}

	// 减库存数
	ItemSupply itemSupply = itemSupplyDAO.selectForUpdate(stockLog.getItemSupplyId());
	int quantity = itemSupply.getQuantity() - row;
	if (quantity < 0) {
	    throw new RuntimeException("减库存数失败，库存数不能小于0");
	}
	itemSupply.setQuantity(quantity);
	itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);

	StockLog stockLogUpdate = new StockLog();
	stockLogUpdate.setStatus(Constants.StockLog.STATUS_SUCCESS);
	stockLogUpdate.setStockCount(row);
	StockLogExample stockLogExample = new StockLogExample();
	stockLogExample.createCriteria().andIdEqualTo(stockLog.getId()).andStatusEqualTo(Constants.StockLog.STATUS_INIT);
	Integer stockLogRow = stockLogDAO.updateByExampleSelective(stockLogUpdate, stockLogExample);
	if (stockLogRow <= 0) {
	    throw new RuntimeException("更新库存日志失败");
	}

	return row;
    }
}
