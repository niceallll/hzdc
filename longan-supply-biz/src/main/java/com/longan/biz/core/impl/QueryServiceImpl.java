package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.utils.DateTool;
import org.springframework.beans.BeanUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.QueryService;
import com.longan.biz.dao.ExportDAO;
import com.longan.biz.dao.QueryAcctLogDAO;
import com.longan.biz.dao.QueryBizOrderDAO;
import com.longan.biz.dao.QueryPayOrderDAO;
import com.longan.biz.dao.QueryStockDAO;
import com.longan.biz.dao.SmsNotifyDAO;
import com.longan.biz.dao.SmsOrderDAO;
import com.longan.biz.dao.SmsSupplyDAO;
import com.longan.biz.dao.SupplyOrderDAO;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.ExportQuery;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsNotifyQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.BizOrderStatisic;
import com.longan.biz.domain.Result;
import com.longan.biz.domain.SupplyOrderStatisic;
import com.longan.biz.utils.Constants;
import org.springframework.util.StringUtils;

public class QueryServiceImpl extends BaseService implements QueryService {
    @Resource
    private QueryBizOrderDAO queryBizOrderDAO;

    @Resource
    private QueryAcctLogDAO queryAcctLogDAO;

    @Resource
    private QueryStockDAO queryStockDAO;

    @Resource
    private QueryPayOrderDAO queryPayOrderDAO;

    @Resource
    private SupplyOrderDAO supplyOrderDAO;

    @Resource
    private ExportDAO exportDAO;

    @Resource
    private SmsOrderDAO smsOrderDAO;

    @Resource
    private SmsSupplyDAO smsSupplyDAO;

    @Resource
    private SmsNotifyDAO smsNotifyDAO;
	//订单查询
    @Override
    public Result<List<BizOrder>> queryBizOrderPage(BizOrderQuery bizOrderQuery) {

	Result<List<BizOrder>> result = new Result<List<BizOrder>>();
	try {
//		if (StringUtils.hasText(bizOrderQuery.getStartHour())||StringUtils.hasText(bizOrderQuery.getEndHour())||StringUtils.hasText(bizOrderQuery.getStartMinute())||StringUtils.hasText(bizOrderQuery.getEndMinute())||StringUtils.hasText(bizOrderQuery.getStartSecond())||StringUtils.hasText(bizOrderQuery.getEndSecond())) {
//			if (!StringUtils.hasText(bizOrderQuery.getStartHour())) {
//				bizOrderQuery.setStartHour(Constants.constTime);
//			}
//			if (!StringUtils.hasText(bizOrderQuery.getEndHour())) {
//				bizOrderQuery.setEndHour(Constants.constTime);
//			}
//			if (!StringUtils.hasText(bizOrderQuery.getStartMinute())) {
//				bizOrderQuery.setStartMinute(Constants.constTime);
//			}
//			if (!StringUtils.hasText(bizOrderQuery.getEndMinute())) {
//				bizOrderQuery.setEndMinute(Constants.constTime);
//			}
//			if (!StringUtils.hasText(bizOrderQuery.getStartSecond())) {
//				bizOrderQuery.setStartSecond(Constants.constTime);
//			}
//			if (!StringUtils.hasText(bizOrderQuery.getEndSecond())) {
//				bizOrderQuery.setEndSecond(Constants.constTime);
//			}
//			//存新值
//			bizOrderQuery.setStartGmtCreate(DateTool.formatStartDate(bizOrderQuery.getStartGmtCreate(),bizOrderQuery.getStartHour(),bizOrderQuery.getStartMinute(),bizOrderQuery.getStartSecond()));
//			bizOrderQuery.setEndGmtCreate(DateTool.formatStartDate(bizOrderQuery.getEndGmtCreate(),bizOrderQuery.getEndHour(),bizOrderQuery.getEndMinute(),bizOrderQuery.getEndSecond()));
//		}
		bizOrderQuery = DateTool.dateFilter(bizOrderQuery);
		//formatStartDate
	    List<BizOrder> queryResult = queryBizOrderDAO.queryByPage(bizOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("queryBizOrderPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<BizOrder>> queryBizOrderExport(BizOrderQuery bizOrderQuery) {
	Result<List<BizOrder>> result = new Result<List<BizOrder>>();
	try {
	    List<BizOrder> queryResult = queryBizOrderDAO.queryByExport(bizOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("queryBizOrderPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<Export>> queryExport(ExportQuery exportQuery) {
	Result<List<Export>> result = new Result<List<Export>>();
	try {
	    List<Export> queryResult = exportDAO.queryByPage(exportQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("queryExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<AcctLog>> queryAcctLog(AcctLogQuery acctLogQuery) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	try {
	    List<AcctLog> queryResult = queryAcctLogDAO.queryByPage(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("资金流水查询失败    msg: " + e.getMessage());
	    logger.error("queryAcctLog error ", e);
	}
	return result;
    }

    @Override
    public Result<List<AcctLog>> queryAcctLogExport(AcctLogQuery acctLogQuery) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	try {
	    List<AcctLog> queryResult = queryAcctLogDAO.queryByExport(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("账户资金流水查询失败    msg: " + e.getMessage());
	    logger.error("queryAcctLogPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<Stock>> queryStockPage(StockQuery stockQuery) {
	Result<List<Stock>> result = new Result<List<Stock>>();
	try {
	    List<Stock> queryResult = queryStockDAO.queryByPage(stockQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("库存列表失败    msg: " + e.getMessage());
	    logger.error("queryStockPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<PayOrder>> queryPayOrder(PayOrderQuery payOrderQuery) {
	Result<List<PayOrder>> result = new Result<List<PayOrder>>();
	try {
		//真正的调用dao层的方法
	    List<PayOrder> queryResult = queryPayOrderDAO.queryByPage(payOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("支付单查询失败    msg: " + e.getMessage());
	    logger.error("queryPayOrder error ", e);
	}
	return result;
    }

    @Override
    public Result<BizOrderStatisic> countBizOrder(BizOrderQuery bizOrderQuery) {
	Result<BizOrderStatisic> result = new Result<BizOrderStatisic>();
	BizOrderStatisic bizOrderStatisic = new BizOrderStatisic();
	result.setModule(bizOrderStatisic);
	BizOrderQuery newBizOrderQuery = new BizOrderQuery();
	BeanUtils.copyProperties(bizOrderQuery, newBizOrderQuery);
	if (newBizOrderQuery.getId() != null) {
	    result.setSuccess(true);
	    return result;
	}
	if (newBizOrderQuery.getStartGmtCreate() == null) {
	    newBizOrderQuery.setStartGmtCreate(new Date());
	}

	if (newBizOrderQuery.getEndGmtCreate() == null) {
	    newBizOrderQuery.setEndGmtCreate(new Date());
	}

	try {
	    newBizOrderQuery.setStatus(Constants.BizOrder.STATUS_SUCCESS);
	    bizOrderStatisic.setSuccessCount(queryBizOrderDAO.queryCount(newBizOrderQuery));

	    newBizOrderQuery.setStatus(Constants.BizOrder.STATUS_CHARGING);
	    bizOrderStatisic.setChargingCount(queryBizOrderDAO.queryCount(newBizOrderQuery));

	    newBizOrderQuery.setStatus(Constants.BizOrder.STATUS_FAILED);
	    bizOrderStatisic.setFailedCount(queryBizOrderDAO.queryCount(newBizOrderQuery));

	    newBizOrderQuery.setStatus(Constants.BizOrder.STATUS_UNCONFIRMED);
	    bizOrderStatisic.setUnConfirmCount(queryBizOrderDAO.queryCount(newBizOrderQuery));

	    newBizOrderQuery.setStatus(Constants.BizOrder.STATUS_EXCEPTION);
	    bizOrderStatisic.setExceptionCount(queryBizOrderDAO.queryCount(newBizOrderQuery));
	} catch (SQLException e) {
	    result.setResultMsg("订单数量统计失败 ，数据库异常");
	    logger.error("countBizOrder error ", e);
	    return result;
	}
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<SupplyOrderStatisic> countSupplyOrder(SupplyOrderQuery supplyOrderQuery) {
	Result<SupplyOrderStatisic> result = new Result<SupplyOrderStatisic>();
	SupplyOrderStatisic supplyOrderStatisic = new SupplyOrderStatisic();
	result.setModule(supplyOrderStatisic);
	SupplyOrderQuery newSupplyOrderQuery = new SupplyOrderQuery();
	BeanUtils.copyProperties(supplyOrderQuery, newSupplyOrderQuery);
	if (newSupplyOrderQuery.getId() != null || newSupplyOrderQuery.getBizOrderId() != null) {
	    result.setSuccess(true);
	    return result;
	}
	if (newSupplyOrderQuery.getStartGmtCreate() == null) {
	    supplyOrderQuery.setStartGmtCreate(new Date());
	}

	if (newSupplyOrderQuery.getEndGmtCreate() == null) {
	    supplyOrderQuery.setEndGmtCreate(new Date());
	}

	try {
	    newSupplyOrderQuery.setSupplyStatus(Constants.SupplyOrder.STATUS_SUCCESS);
	    supplyOrderStatisic.setSuccessCount(supplyOrderDAO.queryCount(newSupplyOrderQuery));

	    newSupplyOrderQuery.setSupplyStatus(Constants.BizOrder.STATUS_CHARGING);
	    supplyOrderStatisic.setChargingCount(supplyOrderDAO.queryCount(newSupplyOrderQuery));

	    newSupplyOrderQuery.setSupplyStatus(Constants.BizOrder.STATUS_FAILED);
	    supplyOrderStatisic.setFailedCount(supplyOrderDAO.queryCount(newSupplyOrderQuery));

	    newSupplyOrderQuery.setSupplyStatus(Constants.BizOrder.STATUS_UNCONFIRMED);
	    supplyOrderStatisic.setUnConfirmCount(supplyOrderDAO.queryCount(newSupplyOrderQuery));

	    newSupplyOrderQuery.setSupplyStatus(Constants.BizOrder.STATUS_EXCEPTION);
	    supplyOrderStatisic.setExceptionCount(supplyOrderDAO.queryCount(newSupplyOrderQuery));
	} catch (SQLException e) {
	    result.setResultMsg("供货单数量统计失败 ，数据库异常");
	    logger.error("countSupplyOrder error ", e);
	    return result;
	}
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<List<SmsOrder>> querySmsOrderPage(SmsOrderQuery smsOrderQuery) {
	Result<List<SmsOrder>> result = new Result<List<SmsOrder>>();
	try {
	    List<SmsOrder> queryResult = smsOrderDAO.queryByPage(smsOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("querySmsOrderPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsSupply>> querySmsSupplyPage(SmsSupplyQuery smsSupplyQuery) {
	Result<List<SmsSupply>> result = new Result<List<SmsSupply>>();
	try {
	    List<SmsSupply> queryResult = smsSupplyDAO.queryByPage(smsSupplyQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供应单查询失败    msg: " + e.getMessage());
	    logger.error("querySmsSupplyPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsNotify>> querySmsNotifyPage(SmsNotifyQuery smsNotifyQuery) {
	Result<List<SmsNotify>> result = new Result<List<SmsNotify>>();
	try {
	    List<SmsNotify> queryResult = smsNotifyDAO.queryByPage(smsNotifyQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供应单查询失败    msg: " + e.getMessage());
	    logger.error("querySmsNotifyPage error ", e);
	}
	return result;
    }
}
