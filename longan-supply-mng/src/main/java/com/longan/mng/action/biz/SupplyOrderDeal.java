package com.longan.mng.action.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.CachedUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.FuncUtils;
import com.longan.biz.utils.OperLogUtils;
import com.longan.client.remote.service.CallBackService;
import com.longan.client.remote.service.CallMessageService;
import com.longan.client.remote.service.SupplyForRemoteService;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/supplyOrderDeal")
public class SupplyOrderDeal extends BaseController {
    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private CallBackService callBackService;

    @Resource
    private CallMessageService callMessageService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private SupplyForRemoteService supplyForRemoteService;

    @Resource
    private MemcachedService memcachedService;

    @RequestMapping(params = "type=refund")
    public String onRequestRefund(@RequestParam("supplyOrderId") Long supplyOrderId, HttpSession session, Model model,
	    HttpServletRequest request) {
	String returnUrl = "biz/querySupplyOrder";
	boolean flag = request.getHeader("Referer").indexOf("queryAllSupplyOrder") >= 0;
	if (flag) {
	    returnUrl = "statistic/queryAllSupplyOrder";
	}

	UserInfo userInfo = getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行退款操作 供货单号 : " + supplyOrderId);
	Result<SupplyOrder> queryResult = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!queryResult.isSuccess() || queryResult.getModule() == null) {
	    alertError(model, "没有该供货单");
	    return returnUrl;
	}
	SupplyOrder supplyOrder = queryResult.getModule();
	if (!supplyOrder.canDeal()) {
	    alertError(model, "该供货单不允许做此操作");
	    return returnUrl;
	}
	if (supplyOrder.isCombine() && isSuccCombineOrder(supplyOrder.getBizOrderId())) {
	    alertError(model, "拼单已有成功供货、不允许做退款操作");
	    return returnUrl;
	}

	String successUrl = "querySupplyOrder.do?bizId=" + supplyOrder.getBizId() + "&id=" + supplyOrder.getId();
	if (flag) {
	    successUrl = "../statistic/queryAllSupplyOrder.do?id=" + supplyOrder.getId();
	}

	supplyOrder.setDealOperId(userInfo.getId());
	supplyOrder.setDealOperName(userInfo.getUserName());
	supplyOrder.setSaleStatus(Constants.SupplyOrder.SALE_SUCCESS);
	supplyOrder.setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
	if (supplyOrder.isCombine()) {
	    supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_PARTS);
	    Result<Boolean> updateSupplyOrderResult = supplyOrderService.updateSupplyOrder(supplyOrder);
	    if (!updateSupplyOrderResult.isSuccess()) {
		alertError(model, updateSupplyOrderResult.getResultMsg());
	    }
	} else {
	    Result<Boolean> cancelSupplyOrderResult = supplyOrderService.cancelSupplyOrder(supplyOrder);
	    if (!cancelSupplyOrderResult.isSuccess()) {
		alertError(model, cancelSupplyOrderResult.getResultMsg());
	    }
	}

	Result<BizOrder> bizOrderQueryReuslt = bizOrderService.getBizOrderById(supplyOrder.getBizOrderId());
	if (!bizOrderQueryReuslt.isSuccess()) {
	    alertError(model, bizOrderQueryReuslt.getResultMsg());
	    return returnUrl;
	}
	BizOrder bizOrder = bizOrderQueryReuslt.getModule();
	if (bizOrder == null) {
	    alertError(model, "没有该订单");
	    return returnUrl;
	}

	if (bizOrder.isCombine()) {
	    Long failTimes = memcachedService.dec(CachedUtils.getCombineFailsKey(bizOrder.getId()), 1);
	    if (!isSuccCombineOrder(bizOrder.getId()) && failTimes == 0) {
		cancelSupplyOrderForParts(bizOrder.getId(), model);
	    } else {
		alertError(model, "拼单非最终失败供货单");
		return returnUrl;
	    }
	}
	if (bizOrder.isManNotify()) {
	    // 预失败
	    bizOrder.setStatus(Constants.BizOrder.STATUS_PENDING);
	    bizOrderService.updateBizOrder(bizOrder);
	    alertError(model, "预成功订单只能补单");
	    return returnUrl;
	}

	bizOrder.setDealOperId(userInfo.getId());
	bizOrder.setDealOperName(userInfo.getUserName());
	Integer oNotifyStatus = bizOrder.getNotifyStatus();
	// 这里直接取消订单了、不做补充供货
	Result<Boolean> cancelBizOrderResult = bizOrderService.cancelBizOrder(bizOrder);
	if (!cancelBizOrderResult.isSuccess()) {
	    alertError(model, cancelBizOrderResult.getResultMsg());
	    return returnUrl;
	}

	try {
	    if (FuncUtils.noneNotified(oNotifyStatus)) {
		callBackService.callBackCombine(bizOrder);
	    }
	} catch (Exception ex) {
	    logger.error("callBackAsync error", ex);
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(supplyOrder,
		supplyOrderService.getSupplyOrderById(supplyOrderId).getModule(), userInfo, map.get("moduleName") + "(退款)",
		bizOrder.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);

	alertSuccess(model, successUrl);
	return returnUrl;
    }

    @RequestMapping(params = "type=makeUp")
    public String onRequestMakeUp(@RequestParam("supplyOrderId") Long supplyOrderId, HttpSession session, Model model,
	    HttpServletRequest request) {
	String returnUrl = "biz/querySupplyOrder";
	boolean flag = request.getHeader("Referer").indexOf("queryAllSupplyOrder") >= 0;
	if (flag) {
	    returnUrl = "statistic/queryAllSupplyOrder";
	}

	UserInfo userInfo = getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行确认操作 供货单号 :" + supplyOrderId);
	Result<SupplyOrder> queryResult = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!queryResult.isSuccess() || queryResult.getModule() == null) {
	    alertError(model, "没有该供货单");
	    return returnUrl;
	}
	SupplyOrder supplyOrder = queryResult.getModule();
	Integer supplyStatus = supplyOrder.getSupplyStatus();
	if (!supplyOrder.canDeal() && Constants.SupplyOrder.STATUS_PARTS != supplyStatus) {
	    alertError(model, "该供货单不允许做确认操作");
	    return returnUrl;
	}

	String successUrl = "querySupplyOrder.do?bizId=" + supplyOrder.getBizId() + "&id=" + supplyOrder.getId();
	if (flag) {
	    successUrl = "../statistic/queryAllSupplyOrder.do?id=" + supplyOrder.getId();
	}

	supplyOrder.setDealOperId(userInfo.getId());
	supplyOrder.setDealOperName(userInfo.getUserName());
	supplyOrder.setSaleStatus(Constants.SupplyOrder.SALE_SUCCESS);
	Result<Boolean> confirmSupplyOrderResult = supplyOrderService.confirmSupplyOrder(supplyOrder);
	if (confirmSupplyOrderResult.isSuccess()) {
	    if (Constants.SupplyOrder.STATUS_PARTS == supplyStatus) {
		// 失败时减少过、所以增加失败计数
		memcachedService.inc(CachedUtils.getCombineFailsKey(supplyOrder.getBizOrderId()), 1);
	    }
	} else {
	    alertError(model, confirmSupplyOrderResult.getResultMsg());
	}

	Result<BizOrder> bizOrderQueryReuslt = bizOrderService.getBizOrderById(supplyOrder.getBizOrderId());
	if (!bizOrderQueryReuslt.isSuccess()) {
	    alertError(model, bizOrderQueryReuslt.getResultMsg());
	    return returnUrl;
	}
	BizOrder bizOrder = bizOrderQueryReuslt.getModule();
	if (bizOrder == null) {
	    alertError(model, "没有该订单");
	    return returnUrl;
	}

	bizOrder.setDealOperId(userInfo.getId());
	bizOrder.setDealOperName(userInfo.getUserName());
	if (bizOrder.isCombine()) {
	    // 设置cache成功标识
	    firstSuccCombineOrder(bizOrder.getId());

	    // 减少cache成功计数
	    Long succTimes = memcachedService.dec(CachedUtils.getCombineSuccsKey(bizOrder.getId()), 1);
	    if (succTimes == 0) {
		// 仅全部成功、订单处理
		memcachedService.add(CachedUtils.combineIsBackKey(bizOrder.getId()), CachedUtils.COMBINE_ORDER_EXP, true);
		bizOrder.setUpstreamSerialno("000000");
		bizOrder.setExtend(supplyOrderService.getCombineOrderExtends(bizOrder.getId()));
	    } else {
		alertError(model, "拼单非全部成功供货单");
		return returnUrl;
	    }
	}

	Integer oNotifyStatus = bizOrder.getNotifyStatus();
	Result<Boolean> confirmBizOrderResult = bizOrderService.confirmBizOrder(bizOrder);
	if (!confirmBizOrderResult.isSuccess()) {
	    alertError(model, confirmBizOrderResult.getResultMsg());
	    return returnUrl;
	}

	try {
	    if (FuncUtils.noneNotified(oNotifyStatus)) {
		callBackService.callBackCombine(bizOrder);
	    }
	} catch (Exception ex) {
	    logger.error("callBackAsync error", ex);
	}
	callMessageService.sendSmsNote(bizOrder);

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(supplyOrder,
		supplyOrderService.getSupplyOrderById(supplyOrderId).getModule(), userInfo, map.get("moduleName") + "(确认)",
		bizOrder.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);

	alertSuccess(model, successUrl);
	return returnUrl;
    }

    @RequestMapping(params = "type=repeatCharge")
    public String onRequestRepeatCharge(@RequestParam("supplyOrderId") Long supplyOrderId, HttpSession session, Model model,
	    HttpServletRequest request) {
	String returnUrl = "biz/querySupplyOrder";
	boolean flag = request.getHeader("Referer").indexOf("queryAllSupplyOrder") >= 0;
	if (flag) {
	    returnUrl = "statistic/queryAllSupplyOrder";
	}

	UserInfo userInfo = getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行补充操作 供货单号 :" + supplyOrderId);
	Result<SupplyOrder> queryResult = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!queryResult.isSuccess() || queryResult.getModule() == null) {
	    alertError(model, "没有该供货单");
	    return returnUrl;
	}
	SupplyOrder supplyOrder = queryResult.getModule();
	Integer supplyStatus = supplyOrder.getSupplyStatus();
	if (!supplyOrder.canDeal() && Constants.SupplyOrder.STATUS_PARTS != supplyStatus) {
	    alertError(model, "该供货单不允许做补充操作");
	    return returnUrl;
	}

	supplyOrder.setDealOperId(userInfo.getId());
	supplyOrder.setDealOperName(userInfo.getUserName());
	supplyOrder.setSaleStatus(Constants.SupplyOrder.SALE_SUCCESS);
	Result<Boolean> cancelSupplyOrderResult = supplyOrderService.cancelSupplyOrder(supplyOrder);
	if (cancelSupplyOrderResult.isSuccess()) {
	    if (Constants.SupplyOrder.STATUS_PARTS == supplyStatus) {
		// 失败时减少过、所以增加失败计数
		memcachedService.inc(CachedUtils.getCombineFailsKey(supplyOrder.getBizOrderId()), 1);
	    }
	} else {
	    alertError(model, cancelSupplyOrderResult.getResultMsg());
	    return returnUrl;
	}

	Result<BizOrder> bizOrderQueryReuslt = bizOrderService.getBizOrderById(supplyOrder.getBizOrderId());
	if (!bizOrderQueryReuslt.isSuccess()) {
	    alertError(model, bizOrderQueryReuslt.getResultMsg());
	    return returnUrl;
	}
	BizOrder bizOrder = bizOrderQueryReuslt.getModule();
	if (bizOrder == null) {
	    alertError(model, "没有该订单");
	    return returnUrl;
	}

	bizOrder.setDealOperId(userInfo.getId());
	bizOrder.setDealOperName(userInfo.getUserName());
	bizOrder.setRepeatType(Constants.BizOrder.REPEAT_TYPE_YES);
	// 手动补充，不作最大补充次数、时间限制
	bizOrder.setManualType(Constants.BizOrder.MANUAL_TYPE_YES);
	// 手动补充，仅支持同通道补充
	// bizOrder.setItemSupplyId(supplyOrder.getItemSupplyId());
	// 手动补充，从同通道开始补充
	Integer supplyFilterIndex = bizOrder.getSupplyFilterIndex();
	if (supplyFilterIndex == null) {
	    supplyFilterIndex = 0;
	}
	bizOrder.setSupplyFilterIndex(supplyFilterIndex - 1);

	Result<SupplyOrder> supplyResult = null;
	try {
	    supplyResult = supplyForRemoteService.supply(bizOrder);
	} catch (Exception ex) {
	    logger.error("repeatCharge error ", ex);
	    alertError(model, "补充供货异常");
	    return returnUrl;
	}

	if (!supplyResult.isSuccess()) {
	    if (Constants.ErrorCode.CODE_ERROR_SUPPLY_UNCONFIRM.equals(supplyResult.getResultCode())) {
		alertError(model, "补充供货处理中");
	    } else {
		alertError(model, supplyResult.getResultMsg());
		if (bizOrder.isCombine()) {
		    if (supplyResult.getModule() == null) {
			supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_PARTS);
			supplyOrderService.updateSupplyOrder(supplyOrder);
		    } else {
			supplyResult.getModule().setSupplyStatus(Constants.SupplyOrder.STATUS_PARTS);
			supplyOrderService.updateSupplyOrder(supplyResult.getModule());
		    }

		    // 减少cache失败计数
		    Long failTimes = memcachedService.dec(CachedUtils.getCombineFailsKey(bizOrder.getId()), 1);
		    if (!isSuccCombineOrder(bizOrder.getId()) && failTimes == 0) {
			cancelSupplyOrderForParts(bizOrder.getId(), model);
			Integer oNotifyStatus = bizOrder.getNotifyStatus();
			bizOrderService.cancelBizOrder(bizOrder);

			// 通知下游
			if (FuncUtils.noneNotified(oNotifyStatus)) {
			    bizOrder.setStatus(Constants.BizOrder.STATUS_FAILED);
			    callBackService.callBackCombine(bizOrder);
			}
		    }
		} else {
		    if (supplyResult.getModule() == null) {
			// 如果没创建供货单，则这里将上一个供货单更新为最终供货单
			supplyOrder.setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
			supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
			supplyOrderService.updateSupplyOrder(supplyOrder);
		    } else {
			supplyResult.getModule().setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
			supplyOrderService.cancelSupplyOrder(supplyResult.getModule());
		    }

		    if (bizOrder.isManNotify()) {
			// 预失败
			bizOrder.setStatus(Constants.BizOrder.STATUS_PENDING);
			bizOrderService.updateBizOrder(bizOrder);
			alertError(model, "预成功订单、补充供货失败");
		    } else {
			bizOrderService.cancelBizOrder(bizOrder);
			alertError(model, "补充供货失败");
		    }
		}
	    }
	    return returnUrl;
	}

	String successUrl = "querySupplyOrder.do?bizId=" + supplyOrder.getBizId() + "&id=" + supplyResult.getModule().getId();
	if (flag) {
	    successUrl = "../statistic/queryAllSupplyOrder.do?id=" + supplyResult.getModule().getId();
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(supplyOrder,
		supplyOrderService.getSupplyOrderById(supplyOrderId).getModule(), userInfo, map.get("moduleName") + "(补充)",
		supplyOrder.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);

	alertSuccess(model, successUrl);
	return returnUrl;
    }

    private Boolean firstSuccCombineOrder(Long bizOrderId) {
	if (memcachedService.getBooleanValue(CachedUtils.combineHasSuccKey(bizOrderId))) {
	    // 非首次成功
	    return false;
	}
	// 设置cache首次成功
	return memcachedService.add(CachedUtils.combineHasSuccKey(bizOrderId), CachedUtils.COMBINE_ORDER_EXP, true);
    }

    private Boolean isSuccCombineOrder(Long bizOrderId) {
	return memcachedService.getBooleanValue(CachedUtils.combineHasSuccKey(bizOrderId));
    }

    private void cancelSupplyOrderForParts(Long bizOrderId, Model model) {
	Result<List<SupplyOrder>> result = supplyOrderService.querySupplyOrderByBizOrder(bizOrderId);
	if (!result.isSuccess()) {
	    alertError(model, "供货单获取失败，数据库异常 : " + result.getResultMsg());
	    return;
	}
	List<SupplyOrder> supplyList = result.getModule();
	if (supplyList == null) {
	    alertError(model, "供货单获取是空");
	    return;
	}

	for (SupplyOrder supplyOrder : supplyList) {
	    if (supplyOrder.getSupplyStatus() == Constants.SupplyOrder.STATUS_PARTS) {
		supplyOrder.setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
		supplyOrderService.cancelSupplyOrder(supplyOrder);
	    }
	}
    }
}
