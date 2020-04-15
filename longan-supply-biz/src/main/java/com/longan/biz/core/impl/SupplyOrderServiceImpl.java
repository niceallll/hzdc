package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.ChargingLimitService;
import com.longan.biz.core.StockService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.core.UserAlertService;
import com.longan.biz.dao.SupplyOrderDAO;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.SupplyOrderAmount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class SupplyOrderServiceImpl extends BaseService implements SupplyOrderService {
    @Resource
    private SupplyOrderDAO supplyOrderDAO;

    @Resource
    private ChargingLimitService chargingLimitService;

    @Resource
    private StockService stockService;

    @Resource
    private UserAlertService userAlertService;
	//供货单查询
    @Override
    public Result<List<SupplyOrder>> querySupplyOrderPage(SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SupplyOrder> queryResult = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货单查询失败    msg: " + e.getMessage());
	    logger.error("querySupplyOrderPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySupplyOrderList(SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SupplyOrder> queryResult = supplyOrderDAO.queryByList(supplyOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货列表查询失败    msg: " + e.getMessage());
	    logger.error("querySupplyOrderList error ", e);
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> createSupplyOrder(SupplyOrder supplyOrder) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	result.setModule(supplyOrder);
	if (supplyOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Long id = supplyOrderDAO.insert(supplyOrder);
	    if (id != null) {
		supplyOrder.setId(id);
		result.setSuccess(true);
		result.setModule(supplyOrder);
		userAlertService.supplyOrderRequest(supplyOrder);// 预警
	    } else {
		result.setResultMsg("创建供货单失败");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建供货单失败,数据库异常");
	    logger.error("createSupplyOrder error", e);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> createSupplyOrder(List<SupplyOrder> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyList == null || supplyList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	for (SupplyOrder supplyOrder : supplyList) {
	    Result<SupplyOrder> supplyResult = createSupplyOrder(supplyOrder);
	    if (!supplyResult.isSuccess()) {
		throw new RuntimeException(supplyResult.getResultMsg());
	    }
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> updateSupplyOrder(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    int id = supplyOrderDAO.updateSupplyOrder(supplyOrder);
	    if (id > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新供货单失败");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("更新供货单失败 ,数据库异常");
	    logger.error("updateSupplyOrder error", e);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> updateSupplyOrder(List<SupplyOrder> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyList == null || supplyList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	for (SupplyOrder supplyOrder : supplyList) {
	    Result<Boolean> supplyResult = updateSupplyOrder(supplyOrder);
	    if (!supplyResult.isSuccess()) {
		throw new RuntimeException(supplyResult.getResultMsg());
	    }
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> updateSupplyOrderCheckStatus(SupplyOrder supplyOrder, List<Integer> statusList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null || statusList == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    boolean flag = supplyOrderDAO.updateSupplyOrderCheckStatus(supplyOrder, statusList);
	    if (flag) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		logger.warn("供货单已被其他线程处理,supplyOrderId : " + supplyOrder.getId());
		result.setResultMsg("更新供货单失败,供货单已被其他线程处理");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("更新供货单失败 ,数据库异常");
	    logger.error("updateSupplyOrderCheckStatus error", e);
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrder(Long supplyOrderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (supplyOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SupplyOrder supplyOrder = supplyOrderDAO.getSupplyOrderById(supplyOrderId);
	    result.setSuccess(true);
	    result.setModule(supplyOrder);
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询供货单失败 ,数据库异常");
	    logger.error("getSupplyOrder error", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> confirmSupplyOrder(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	if (supplyOrder.isSupplyFromStock() && supplyOrder.getStockId() != null) {
	    Result<Stock> getStockResult = stockService.getStockById(supplyOrder.getStockId());
	    // 不影响主流程。
	    if (!getStockResult.isSuccess()) {
		logger.error("confirmSupplyOrder getStockById error stockId:" + supplyOrder.getStockId() + " msg : "
			+ getStockResult.getResultMsg());
	    } else {
		Stock stock = getStockResult.getModule();
		if (stock != null) {
		    stockService.deliveryFromStorage(stock);
		}
	    }
	}

	// 记录供货时间。
	if (supplyOrder.getGmtCreate() == null) {
	    Result<SupplyOrder> queryResult = getSupplyOrder(supplyOrder.getId());
	    if (queryResult.getModule() == null) {
		result.setResultMsg("找不到该供货单");
		return result;
	    }
	    supplyOrder.setSupplyCostTime(supplyOrder.computCostTime(queryResult.getModule().getGmtCreate()));
	} else {
	    supplyOrder.setSupplyCostTime(supplyOrder.computCostTime(supplyOrder.getGmtCreate()));
	}

	supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_SUCCESS);
	// 如果以后是组合供货，这个逻辑要改。
	supplyOrder.setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);

	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_EXCEPTION);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	statusList.add(Constants.SupplyOrder.STATUS_PARTS);
	result = updateSupplyOrderCheckStatus(supplyOrder, statusList);
	userAlertService.supplyOrderRequest(supplyOrder);// 预警
	if (!result.isSuccess()) {
	    return result;
	}

	// // 处理中限制 加加
	// chargingLimitService.incCount(supplyOrder.getSupplyTraderId());
	return result;
    }

    @Override
    public Result<Boolean> cancelSupplyOrder(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	if (supplyOrder.isSupplyFromStock() && supplyOrder.getStockId() != null) {
	    Result<Stock> getStockResult = stockService.getStockById(supplyOrder.getStockId());
	    // 不影响主流程。
	    if (!getStockResult.isSuccess()) {
		logger.error("cancelSupplyOrder getStockById error stockId:" + supplyOrder.getStockId() + " msg : "
			+ getStockResult.getResultMsg());
	    } else {
		Stock stock = getStockResult.getModule();
		if (stock != null && supplyOrder.isFlagCardValid()) {
		    stock.setErrorInfo(supplyOrder.getUpstreamMemo());
		    stockService.sequestrationStorage(stock);
		} else if (stock != null && !supplyOrder.isFlagCardValid()) {
		    stockService.returnToStorage(stock);
		}
	    }
	}

	// 记录失败时间。
	if (supplyOrder.getGmtCreate() != null) {
	    supplyOrder.setSupplyCostTime(supplyOrder.computCostTime(supplyOrder.getGmtCreate()));
	}
	// supplyOrder.setDealOperId(null); // 这里不记录处理人。
	supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	statusList.add(Constants.SupplyOrder.STATUS_PARTS);
	statusList.add(Constants.SupplyOrder.STATUS_EXCEPTION);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	result = updateSupplyOrderCheckStatus(supplyOrder, statusList);
	userAlertService.supplyOrderRequest(supplyOrder);// 预警
	if (!result.isSuccess()) {
	    return result;
	}

	// 处理中限制 加加
	chargingLimitService.incLimit(supplyOrder.getSupplyTraderId(), supplyOrder.getItemFacePrice());
	// // 处理中限制 减减
	// chargingLimitService.decCount(supplyOrder.getSupplyTraderId());
	return result;
    }

    @Override
    public Result<Boolean> unConfirmSupplyOrder(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	result = updateSupplyOrder(supplyOrder);
	return result;
    }

    @Override
    public Result<Boolean> chargingSupplyOrderAndLimit(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyOrder == null || supplyOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	// 处理中限制 减减
	result = chargingLimitService.decLimit(supplyOrder.getSupplyTraderId(), supplyOrder.getItemFacePrice());
	if (!result.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_LIMIT);
	    result.setResultMsg(Constants.ErrorCode.DESC_ERROR_SUPPLY_LIMIT);
	    return result;
	}

	supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_CHARGING);
	result = updateSupplyOrder(supplyOrder);
	if (!result.isSuccess()) {
	    return result;
	}

	// // 处理中限制 加加
	// chargingLimitService.incCount(supplyOrder.getSupplyTraderId());
	return result;
    }

    @Override
    public Result<Boolean> chargingSupplyOrderAndLimit(Long supplyTraderId, Integer itemFacePrice, List<SupplyOrder> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyList == null || supplyList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	// 处理中限制 减减
	result = chargingLimitService.decLimit(supplyTraderId, itemFacePrice);
	if (!result.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_LIMIT);
	    result.setResultMsg(Constants.ErrorCode.DESC_ERROR_SUPPLY_LIMIT);
	    return result;
	}

	for (SupplyOrder supplyOrder : supplyList) {
	    supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_CHARGING);
	}

	result = updateSupplyOrder(supplyList);
	if (!result.isSuccess()) {
	    return result;
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrderById(Long supplyOrderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (supplyOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SupplyOrder supplyOrder = supplyOrderDAO.selectSupplyOrderById(supplyOrderId);
	    if (supplyOrder != null) {
		result.setSuccess(true);
		result.setModule(supplyOrder);
	    } else {
		result.setResultMsg("没有该供货单。供货单id : " + supplyOrderId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("供货单查询失败  id : " + supplyOrderId + " msg: " + e.getMessage());
	    logger.error("getSupplyOrderById error", e);
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInChargingByTraderId(Long traderId) {
	Result<Integer> result = new Result<Integer>();
	if (traderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	supplyOrderQuery.setSupplyTraderId(traderId);
	supplyOrderQuery.setStatusList(statusList);

	Integer count = null;
	try {
	    count = supplyOrderDAO.queryCount(supplyOrderQuery);
	} catch (SQLException e) {
	    result.setResultMsg("getCountInChargingByTraderId error 查询数据库异常");
	    logger.error("getCountInChargingByTraderId error", e);
	    return result;
	}

	result.setModule(count);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrderByIdAndTraderId(Long id, Long traderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (traderId == null || id == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setSupplyTraderId(traderId);
	supplyOrderQuery.setId(id);

	try {
	    List<SupplyOrder> list = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException e) {
	    result.setResultMsg("getSupplyOrderByIdAndTraderId error 查询数据库异常");
	    logger.error("getSupplyOrderByIdAndTraderId error", e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrderByUpstreamSerialno(String upstreamSerialno, Long traderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (traderId == null || upstreamSerialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setSupplyTraderId(traderId);
	supplyOrderQuery.setUpstreamSerialno(upstreamSerialno);

	try {
	    List<SupplyOrder> list = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException e) {
	    result.setResultMsg("getSupplyOrderByUpstreamSerialno error 查询数据库异常");
	    logger.error("getSupplyOrderByUpstreamSerialno error", e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrderByItemUid(String upstreamSerialno, String itemUid, Long traderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (traderId == null || itemUid == null || upstreamSerialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setSupplyTraderId(traderId);
	supplyOrderQuery.setUpstreamSerialno(upstreamSerialno);
	supplyOrderQuery.setItemUid(itemUid);

	try {
	    List<SupplyOrder> list = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("getSupplyOrderByItemUid error 查询数据库异常");
	    logger.error("getSupplyOrderByItemUid error", ex);
	    return result;
	}
	return result;
    }

    @Override
    public Result<SupplyOrder> getSupplyOrderByBizOrderId(Long bizOrderId) {
	Result<SupplyOrder> result = new Result<SupplyOrder>();
	if (bizOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setBizOrderId(bizOrderId);

	try {
	    List<SupplyOrder> list = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException e) {
	    result.setResultMsg("getSupplyOrderByBizOrderId error 查询数据库异常");
	    logger.error("getSupplyOrderByBizOrderId error", e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySupplyOrderByBizOrder(Long bizOrderId) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (bizOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setBizOrderId(bizOrderId);
	supplyOrderQuery.setPageSize(200);

	try {
	    List<SupplyOrder> list = supplyOrderDAO.queryByPage(supplyOrderQuery);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("querySupplyOrderByBizOrder error 查询数据库异常");
	    logger.error("querySupplyOrderByBizOrder error", e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInExport(SupplyOrderQuery supplyOrderQuery) {
	Result<Integer> result = new Result<Integer>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Integer count = supplyOrderDAO.countByExport(supplyOrderQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("getCountInExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySupplyOrderExport(SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SupplyOrder> queryResult = supplyOrderDAO.queryByExport(supplyOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货查询失败    msg: " + e.getMessage());
	    logger.error("querySupplyOrderPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> getSupplyOrderExport(SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SupplyOrder> queryResult = supplyOrderDAO.selectByExport(supplyOrderQuery);
	    if (queryResult.size() > 0) {
		result.setSuccess(true);
		result.setModule(queryResult);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("供货查询失败    msg: " + e.getMessage());
	    logger.error("getSupplyOrderExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySaleOrderExport(SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	SupplyOrderQuery saleOrderQuery = new SupplyOrderQuery();
	saleOrderQuery.setBizId(supplyOrderQuery.getBizId());
	saleOrderQuery.setItemId(supplyOrderQuery.getItemId());
	saleOrderQuery.setSupplyTraderId(supplyOrderQuery.getSupplyTraderId());
	saleOrderQuery.setSupplyStatus(Constants.SupplyOrder.STATUS_CHARGING);
	saleOrderQuery.setSupplyType(Constants.TraderInfo.TYPE_MAN);
	saleOrderQuery.setSaleStatus(Constants.SupplyOrder.SALE_INIT);
	saleOrderQuery.setUserId(supplyOrderQuery.getUserId());
	saleOrderQuery.setProvinceCode(supplyOrderQuery.getProvinceCode());
	saleOrderQuery.setStartGmtCreate(supplyOrderQuery.getStartGmtCreate());
	saleOrderQuery.setEndGmtCreate(supplyOrderQuery.getEndGmtCreate());

	try {
	    List<SupplyOrder> queryResult = supplyOrderDAO.getSaleExport(saleOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货查询失败  msg: " + e.getMessage());
	    logger.error("querySaleOrderExport error ", e);
	}
	return result;
    }

    @Override
    public Boolean checkRepeatUid(SupplyOrder supplyOrder) {
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_SUCCESS);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	supplyOrderQuery.setSupplyTraderId(supplyOrder.getSupplyTraderId());
	supplyOrderQuery.setItemUid(supplyOrder.getItemUid());
	supplyOrderQuery.setStatusList(statusList);
	supplyOrderQuery.setSupplyTermPeriod(DateTool.getTodayYearMonth());
	Integer count = null;
	try {
	    count = supplyOrderDAO.queryCount(supplyOrderQuery);
	    if (count >= 2)
		return true;
	} catch (SQLException e) {
	    logger.error("checkRepeatUid error", e);
	    return false;
	}
	return false;
    }

    @Override
    public Result<SupplyOrderAmount> querySupplyOrderSum(SupplyOrderQuery supplyOrderQuery) {
	Result<SupplyOrderAmount> result = new Result<SupplyOrderAmount>();
	if (supplyOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SupplyOrderAmount amount = supplyOrderDAO.querySumAmount(supplyOrderQuery);
	    if (supplyOrderQuery.getUserId() != null) {
		TraderInfo traderInfo = localCachedService.getTraderInfo(supplyOrderQuery.getUserId());
		if (traderInfo != null) {
		    amount.setFeeAmount(traderInfo.getServiceFee(), traderInfo.getCashFee());
		}
	    }
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("供货单统计失败 msg: " + e.getMessage());
	    logger.error("querySupplyOrderSum error ", e);
	}
	return result;
    }

    @Override
    public String getCombineOrderExtends(Long bizOrderId) {
	Result<List<SupplyOrder>> result = querySupplyOrderByBizOrder(bizOrderId);
	if (!result.isSuccess()) {
	    logger.error("供货单获取失败，数据库异常 : " + result.getResultMsg());
	    return null;
	}
	List<SupplyOrder> supplyList = result.getModule();
	if (supplyList == null) {
	    logger.error("供货单获取是空 bizOrderId : " + bizOrderId);
	    return null;
	}

	String extend = null;
	for (SupplyOrder supplyOrder : supplyList) {
	    if (StringUtils.isNotBlank(supplyOrder.getExtend())) {
		if (extend == null) {
		    extend = supplyOrder.getExtend();
		} else {
		    extend += "," + supplyOrder.getExtend();
		}
	    }
	}
	return extend;
    }
}
