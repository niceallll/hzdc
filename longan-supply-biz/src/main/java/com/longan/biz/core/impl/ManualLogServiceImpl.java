package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.ManualLogService;
import com.longan.biz.dao.ManualLogDAO;
import com.longan.biz.dao.ManualOrderDAO;
import com.longan.biz.dataobject.ManualLog;
import com.longan.biz.dataobject.ManualLogQuery;
import com.longan.biz.dataobject.ManualOrder;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class ManualLogServiceImpl extends BaseService implements ManualLogService {
    @Resource
    private ManualLogDAO manualLogDAO;

    @Resource
    private ManualOrderDAO manualOrderDAO;

    @Override
    public Result<ManualLog> createManualLog(ManualLog manualLog) {
	Result<ManualLog> result = new Result<ManualLog>();
	result.setModule(manualLog);
	if (manualLog == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Long id = manualLogDAO.insert(manualLog);
	    if (id != null) {
		manualLog.setId(id);
		result.setSuccess(true);
		result.setModule(manualLog);
	    } else {
		result.setResultMsg("创建人工充值失败");
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("创建人工充值失败  msg: " + ex.getMessage());
	    logger.error("createManualLog error", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateManualLog(ManualLog manualLog) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (manualLog == null || manualLog.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    int row = manualLogDAO.updateByPrimaryKeySelective(manualLog);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新人工充值失败，没有该充值单 manualLogId : " + manualLog.getId());
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("更新人工充值失败  manualLogId : " + manualLog.getId() + " msg: " + ex.getMessage());
	    logger.error("updateManualLog error", ex);
	}
	return result;
    }

    @Override
    public Result<List<ManualLog>> queryManualLogPage(ManualLogQuery manualLogQuery) {
	Result<List<ManualLog>> result = new Result<List<ManualLog>>();
	if (manualLogQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<ManualLog> queryResult = manualLogDAO.queryByPage(manualLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("人工充值查询失败 msg: " + ex.getMessage());
	    logger.error("queryManualLogPage error ", ex);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitManualLog(Long id, Integer itemFacePrice) throws Exception {
	try {
	    ManualLog manualLog = manualLogDAO.selectForUpdate(id);
	    manualLog.setSuccPrice(manualLog.getSuccPrice() + itemFacePrice);
	    manualLog.setSuccCount(manualLog.getSuccCount() + 1);
	    manualLogDAO.updateByPrimaryKeySelective(manualLog);
	} catch (Exception ex) {
	    throw new RuntimeException("commitManualLog error");
	}
    }

    @Override
    public Result<ManualOrder> createManualOrder(ManualOrder manualOrder) {
	Result<ManualOrder> result = new Result<ManualOrder>();
	result.setModule(manualOrder);
	if (manualOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    manualOrder.setGmtCreate(new Date());
	    Long id = manualOrderDAO.insert(manualOrder);
	    if (id != null) {
		manualOrder.setId(id);
		result.setSuccess(true);
		result.setModule(manualOrder);
	    } else {
		result.setResultMsg("创建充值项失败");
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("创建充值项失败  msg: " + ex.getMessage());
	    logger.error("createManualOrder error", ex);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> createManualOrder(List<ManualOrder> orderList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (orderList == null || orderList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	for (ManualOrder manualOrder : orderList) {
	    Result<ManualOrder> orderResult = createManualOrder(manualOrder);
	    if (!orderResult.isSuccess()) {
		throw new RuntimeException(orderResult.getResultMsg());
	    }
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> updateManualOrder(ManualOrder manualOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (manualOrder == null || manualOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    int row = manualOrderDAO.updateByPrimaryKeySelective(manualOrder);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新定单失败，没有该定单 manualOrderId : " + manualOrder.getId());
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("更新定单失败  manualOrderId : " + manualOrder.getId() + " msg: " + ex.getMessage());
	    logger.error("updateManualOrder error", ex);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<ManualOrder> repeatManualOrder(ManualOrder manualOrder, ManualOrder repeatManualOrder) {
	Result<ManualOrder> result = new Result<ManualOrder>();
	if (manualOrder == null || repeatManualOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	manualOrder.setStatus(Constants.ManualOrder.STATUS_REPEAT);
	Result<Boolean> updateResult = updateManualOrder(manualOrder);
	if (!updateResult.isSuccess()) {
	    result.setResultMsg(updateResult.getResultMsg());
	    return result;
	}

	Result<ManualOrder> createResult = createManualOrder(repeatManualOrder);
	if (!createResult.isSuccess()) {
	    throw new RuntimeException(createResult.getResultMsg());
	}

	result.setModule(repeatManualOrder);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<ManualOrder> getManualOrder(Long id) {
	Result<ManualOrder> result = new Result<ManualOrder>();
	if (id == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    ManualOrder manualOrder = manualOrderDAO.selectByPrimaryKey(id);
	    if (manualOrder != null) {
		result.setSuccess(true);
		result.setModule(manualOrder);
	    } else {
		result.setResultMsg("没有该定单");
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("查询定单失败 " + " msg :" + ex.getMessage());
	    logger.error("getManualOrder error id = " + id, ex);
	}
	return result;
    }

    @Override
    public Result<ManualOrder> getManualOrderBySerialno(String serialno) {
	Result<ManualOrder> result = new Result<ManualOrder>();
	if (serialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    ManualOrder manualOrder = manualOrderDAO.selectBySerialno(serialno);
	    if (manualOrder != null) {
		result.setSuccess(true);
		result.setModule(manualOrder);
	    } else {
		result.setResultMsg("没有该定单");
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("查询定单失败 " + " msg :" + ex.getMessage());
	    logger.error("getManualOrderBySerialno error serialno = " + serialno, ex);
	}
	return result;
    }

    @Override
    public Result<List<ManualOrder>> queryManualOrder(Long manualLogId) {
	Result<List<ManualOrder>> result = new Result<List<ManualOrder>>();
	if (manualLogId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<ManualOrder> queryResult = manualOrderDAO.selectByLogId(manualLogId);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("人工充值查询失败 msg: " + ex.getMessage());
	    logger.error("queryManualOrder error ", ex);
	}
	return result;
    }
}
