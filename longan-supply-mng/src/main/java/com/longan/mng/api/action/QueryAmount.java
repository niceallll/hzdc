package com.longan.mng.api.action;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longan.biz.core.AcctLogService;
import com.longan.biz.core.CashOrderService;
import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.core.PayOrderService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.core.SmsOrderService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.CashOrderQuery;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.AcctLogAmount;
import com.longan.biz.sumobject.CashOrderAmount;
import com.longan.biz.sumobject.ChargeOrderAmount;
import com.longan.biz.sumobject.PayOrderAmount;
import com.longan.biz.sumobject.RefundOrderAmount;
import com.longan.biz.sumobject.SmsOrderCount;
import com.longan.biz.sumobject.SupplyOrderAmount;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.response.AjaxResponse;

@Controller
public class QueryAmount extends BaseController {
    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private SmsOrderService smsOrderService;

    @Resource
    private AcctLogService acctLogService;

    @Resource
    private ChargeOrderService chargeOrderService;

    @Resource
    private CashOrderService cashOrderService;

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private RefundOrderService refundOrderService;

    @RequestMapping(value = "api/queryBizAmount", params = "type=supply")
    public @ResponseBody
    AjaxResponse querySupplyOrder(SupplyOrderQuery supplyOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (supplyOrderQuery.getEndGmtCreate() == null) {
	    supplyOrderQuery.setEndGmtCreate(date);
	}
	if (supplyOrderQuery.getStartGmtCreate() == null) {
	    supplyOrderQuery.setStartGmtCreate(date);
	}

	Result<SupplyOrderAmount> result = supplyOrderService.querySupplyOrderSum(supplyOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/querySmsCount", params = "type=order")
    public @ResponseBody
    AjaxResponse querySmsOrder(SmsOrderQuery smsOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (smsOrderQuery.getEndGmtCreate() == null) {
	    smsOrderQuery.setEndGmtCreate(date);
	}
	if (smsOrderQuery.getStartGmtCreate() == null) {
	    smsOrderQuery.setStartGmtCreate(date);
	}

	Result<SmsOrderCount> result = smsOrderService.querySmsOrderSum(smsOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/querySmsCount", params = "type=supply")
    public @ResponseBody
    AjaxResponse querySmsSupply(SmsSupplyQuery smsSupplyQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (smsSupplyQuery.getEndGmtCreate() == null) {
	    smsSupplyQuery.setEndGmtCreate(date);
	}
	if (smsSupplyQuery.getStartGmtCreate() == null) {
	    smsSupplyQuery.setStartGmtCreate(date);
	}

	Result<SmsOrderCount> result = smsOrderService.querySmsSupplySum(smsSupplyQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/queryFundsAmount", params = "type=acctLog")
    public @ResponseBody
    AjaxResponse queryAcctLog(AcctLogQuery acctLogQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (acctLogQuery.getEndGmtCreate() == null) {
	    acctLogQuery.setEndGmtCreate(date);
	}
	if (acctLogQuery.getStartGmtCreate() == null) {
	    acctLogQuery.setStartGmtCreate(date);
	}

	Result<AcctLogAmount> result = acctLogService.queryAcctLogSum(acctLogQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/queryFundsAmount", params = "type=charge")
    public @ResponseBody
    AjaxResponse queryChargeOrder(ChargeOrderQuery chargeOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (chargeOrderQuery.getEndGmtCreate() == null) {
	    chargeOrderQuery.setEndGmtCreate(date);
	}
	if (chargeOrderQuery.getStartGmtCreate() == null) {
	    chargeOrderQuery.setStartGmtCreate(date);
	}

	Result<ChargeOrderAmount> result = chargeOrderService.queryChargeOrderSum(chargeOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/queryFundsAmount", params = "type=cash")
    public @ResponseBody
    AjaxResponse queryCashOrder(CashOrderQuery cashOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (cashOrderQuery.getEndGmtCreate() == null) {
	    cashOrderQuery.setEndGmtCreate(date);
	}
	if (cashOrderQuery.getStartGmtCreate() == null) {
	    cashOrderQuery.setStartGmtCreate(date);
	}

	Result<CashOrderAmount> result = cashOrderService.queryCashOrderSum(cashOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/queryFundsAmount", params = "type=pay")
    public @ResponseBody
    AjaxResponse queryPayOrder(PayOrderQuery payOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (payOrderQuery.getEndGmtCreate() == null) {
	    payOrderQuery.setEndGmtCreate(date);
	}
	if (payOrderQuery.getStartGmtCreate() == null) {
	    payOrderQuery.setStartGmtCreate(date);
	}

	Result<PayOrderAmount> result = payOrderService.queryPayOrderSum(payOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "api/queryFundsAmount", params = "type=refund")
    public @ResponseBody
    AjaxResponse queryRefundOrder(RefundOrderQuery refundOrderQuery, HttpSession session) {
	AjaxResponse response = new AjaxResponse();
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    response.setErrorMsg("下游不允许操作此功能");
	    return response;
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (refundOrderQuery.getEndGmtCreate() == null) {
	    refundOrderQuery.setEndGmtCreate(date);
	}
	if (refundOrderQuery.getStartGmtCreate() == null) {
	    refundOrderQuery.setStartGmtCreate(date);
	}

	Result<RefundOrderAmount> result = refundOrderService.queryRefundOrderSum(refundOrderQuery);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }
}
