package com.longan.biz.core.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.QbOrderService;
import com.longan.biz.dao.QbOrderDAO;
import com.longan.biz.dataobject.QbOrder;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class QbOrderServiceImpl extends BaseService implements QbOrderService {
    @Resource
    private QbOrderDAO qbOrderDAO;

    @Override
    public Result<QbOrder> createQbOrder(QbOrder qbOrder) {
	Result<QbOrder> result = new Result<QbOrder>();
	result.setModule(qbOrder);
	if (qbOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	// 创建订单
	try {
	    qbOrderDAO.insert(qbOrder);
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建Q币订单失败  msg: " + e.getMessage());
	    logger.error("createQbOrder error", e);
	}
	return result;
    }

    @Override
    public Result<QbOrder> getQbOrderBySerialNum(String serialNum) {
	Result<QbOrder> result = new Result<QbOrder>();
	if (StringUtils.isEmpty(serialNum)) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    QbOrder qbOrder = qbOrderDAO.selectBySerialNum(serialNum);
	    if (qbOrder != null) {
		result.setSuccess(true);
		result.setModule(qbOrder);
	    } else {
		result.setResultMsg("没有该订单。订单id : " + serialNum);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败  id : " + serialNum + " msg: " + e.getMessage());
	    logger.error("queryQbOrderBySerialNum error", e);
	}
	return result;
    }

}
