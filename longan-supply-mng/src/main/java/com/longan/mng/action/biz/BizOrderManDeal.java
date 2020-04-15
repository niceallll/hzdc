package com.longan.mng.action.biz;

import java.lang.reflect.Field;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.service.CallBackService;
import com.longan.mng.action.common.BaseController;

@Controller
public class BizOrderManDeal extends BaseController {
    private static final String returnUrl = "redirect:queryBizOrder.do";

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private CallBackService callBackService;

    @Resource
    private MemcachedClient memcachedClient;

    private static final int expTime = 60 * 60 * 24;

    private int posIdLimitCount = Integer.parseInt(Utils.getProperty("unicomAync.posMaxCount"));

    @RequestMapping("biz/bizOrderLock")
    public String lockRequest(@RequestParam("bizOrderId") Long bizOrderId, @RequestParam("posId") String posId,
	    @ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行订单锁定操作 订单号 : " + bizOrderId);
	if (StringUtils.isBlank(posId)) {
	    alertError(model, "请出入pos机编号");
	    return "biz/queryBizOrder";
	}

	posId = posId.trim();

	String url = returnUrl + formToParam(bizOrderQuery);
	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
	    alertError(model, "没有该订单");
	    return "biz/queryBizOrder";
	}

	// 最大限制判断
	if (!checkPosIdLimitAndSet(posId)) {
	    alertError(model, "pos机: " + posId + "今日已经到达最大限制");
	    return "biz/queryBizOrder";
	}

	BizOrder bizOrder = queryReuslt.getModule();
	bizOrder.setLockOperId(userInfo.getId());
	bizOrder.setItemPosId(posId);

	Result<Boolean> updateResult = bizOrderService.manualLockBizOrder(bizOrder);
	if (updateResult.isSuccess() && updateResult.getModule()) {

	    bizOrderQuery.setStatus(Constants.BizOrder.STATUS_LOCK);
	    bizOrderQuery.setLockOperId(userInfo.getId());
	    url = returnUrl + formToParam(bizOrderQuery);
	    alertSuccessNoneBack(model);
	    return url;
	} else {
	    // 没有锁定成功就减1
	    decrCountInMemcached(posId);
	    alertError(model, updateResult.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    private String getKey(String posId) {
	return DateTool.getToday() + "_" + posId;
    }

    private long incrCountToMemcached(String posId) {
	try {
	    return memcachedClient.incr(getKey(posId), 1, 1, expTime);
	} catch (Exception e) {
	    logger.error("incrCountToMemcached error ", e);
	}
	return -1;
    }

    private void decrCountInMemcached(String posId) {
	if (posIdLimitCount == 0) {
	    return;
	}
	if (StringUtils.isBlank(posId)) {
	    return;
	}
	try {
	    memcachedClient.decr(getKey(posId), 1);
	} catch (Exception e) {
	    logger.error("decrCountInMemcached error ", e);
	}
    }

    private Boolean checkPosIdLimitAndSet(String posId) {
	if (posIdLimitCount == 0) {
	    return true;
	}
	Long count = null;
	try {
	    count = incrCountToMemcached(posId);
	    if (count == -1) {
		// 出错后就不做限制了。
		return true;
	    }

	    if (count > posIdLimitCount) {
		// 达到限制,补偿机制，减回来。
		decrCountInMemcached(posId);
		return false;
	    }

	    return true;
	} catch (Exception e) {
	    logger.error("checkPosIdLimit memcached opertion error ", e);
	}
	// 出错后就不做限制了。
	return true;
    }

    @RequestMapping("biz/bizOrderUnLock")
    public String unlockRequest(@RequestParam("bizOrderId") Long bizOrderId,
	    @ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行订单解锁操作 订单号 : " + bizOrderId);
	String url = returnUrl + formToParam(bizOrderQuery);
	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
	    alertError(model, "没有该订单");
	    return "biz/queryBizOrder";
	}

	BizOrder bizOrder = queryReuslt.getModule();
	bizOrder.setDealOperId(userInfo.getId());

	Result<Boolean> updateResult = bizOrderService.manualUnLockBizOrder(bizOrder);
	if (updateResult.isSuccess() && updateResult.getModule()) {
	    // 解锁就减1
	    decrCountInMemcached(bizOrder.getItemPosId());

	    bizOrderQuery.setStatus(Constants.BizOrder.STATUS_CHARGING);
	    url = returnUrl + formToParam(bizOrderQuery);
	    alertSuccessNoneBack(model);
	    return url;
	} else {
	    alertError(model, updateResult.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    @RequestMapping("biz/bizOrderCancel")
    public String cancelRequest(@RequestParam("bizOrderId") Long bizOrderId,
	    @ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行订单取消操作 订单号 : " + bizOrderId);
	String url = returnUrl + formToParam(bizOrderQuery);

	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
	    alertError(model, "没有该订单");
	    return "biz/queryBizOrder";
	}
	BizOrder bizOrder = queryReuslt.getModule();

	if (bizOrder.getStatus() != Constants.BizOrder.STATUS_CHARGING && bizOrder.getStatus() != Constants.BizOrder.STATUS_LOCK
		&& bizOrder.getStatus() != Constants.BizOrder.STATUS_UNCONFIRMED) {
	    alertError(model, "只能处理状态是'处理中'或'锁定中'的订单。");
	    return "biz/queryBizOrder";
	}

	bizOrder.setDealOperId(userInfo.getId());
	bizOrder.setDealOperName(userInfo.getUserName());

	Result<Boolean> updateResult = bizOrderService.manualCancelBizOrder(bizOrder);
	if (updateResult.isSuccess() && updateResult.getModule()) {
	    bizOrderQuery.setStatus(Constants.BizOrder.STATUS_FAILED);
	    bizOrderQuery.setId(bizOrderId);
	    url = returnUrl + formToParam(bizOrderQuery);

	    alertSuccessNoneBack(model);

	    // 通知下游
	    try {
		bizOrder.setStatus(Constants.BizOrder.STATUS_FAILED);
		Result<Boolean> callBackResult = callBackService.callBackAsync(bizOrder);
		if (!callBackResult.isSuccess() || !callBackResult.getModule()) {
		    logger.error("cancelBizOrder callback failed bizOrderId : " + bizOrderId, callBackResult.getResultMsg());
		}
	    } catch (Exception e) {
		logger.error("cancelBizOrder callback failed bizOrderId : " + bizOrderId, e);
	    }

	    return url;
	} else {
	    alertError(model, updateResult.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    @RequestMapping("biz/bizOrderConfirm")
    public String confirmRequest(@RequestParam("bizOrderId") Long bizOrderId,
	    @ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行订单确认操作 订单号 : " + bizOrderId);
	String url = returnUrl;
	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
	    alertError(model, "没有该订单");
	    return "biz/queryBizOrder";
	}

	BizOrder bizOrder = queryReuslt.getModule();
	bizOrder.setDealOperId(userInfo.getId());
	bizOrder.setDealOperName(userInfo.getUserName());

	Result<Boolean> updateResult = bizOrderService.manualConfirmBizOrder(bizOrder);
	if (updateResult.isSuccess() && updateResult.getModule()) {
	    bizOrderQuery.setStatus(Constants.BizOrder.STATUS_SUCCESS);
	    bizOrderQuery.setId(bizOrderId);
	    url = returnUrl + formToParam(bizOrderQuery);

	    alertSuccessNoneBack(model);
	    bizOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);

	    // 通知下游
	    try {
		Result<Boolean> callBackResult = callBackService.callBackAsync(bizOrder);
		if (!callBackResult.isSuccess() || !callBackResult.getModule()) {
		    logger.error("confirmBizOrder callback failed bizOrderId : " + bizOrderId, callBackResult.getResultMsg());
		}
	    } catch (Exception e) {
		logger.error("confirmBizOrder callback failed bizOrderId : " + bizOrderId, e);
	    }
	    return url;
	} else {
	    alertError(model, updateResult.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    @RequestMapping("biz/bizOrderUnConfirm")
    public String unConfirmRequest(@RequestParam("bizOrderId") Long bizOrderId,
	    @ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行订单未确认操作 订单号 : " + bizOrderId);
	String url = returnUrl;
	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
	    alertError(model, "没有该订单");
	    return "biz/queryBizOrder";
	}

	BizOrder bizOrder = queryReuslt.getModule();
	bizOrder.setDealOperId(userInfo.getId());

	Result<Boolean> updateResult = bizOrderService.manualUnConfirmBizOrder(bizOrder);
	if (updateResult.isSuccess() && updateResult.getModule()) {
	    bizOrderQuery.setStatus(Constants.BizOrder.STATUS_UNCONFIRMED);
	    bizOrderQuery.setId(bizOrderId);
	    url = returnUrl + formToParam(bizOrderQuery);
	    alertSuccessNoneBack(model);
	    return url;
	} else {
	    alertError(model, updateResult.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    private String formToParam(BizOrderQuery bizOrderQuery) {
	Field[] field = BizOrderQuery.class.getDeclaredFields(); // 获取所有属性

	StringBuffer sb = new StringBuffer("?");
	for (int i = 0; i < field.length; i++) {
	    field[i].setAccessible(true); // 设置对私有的访问权限
	    try {
		if (field[i].get(bizOrderQuery) != null && !field[i].getName().equals("serialVersionUID")) {
		    if (field[i].get(bizOrderQuery) instanceof Date) {
			sb.append(field[i].getName()).append("=").append(DateTool.parseDate((Date) field[i].get(bizOrderQuery)))
				.append("&");
		    } else {
			sb.append(field[i].getName()).append("=").append(field[i].get(bizOrderQuery)).append("&");
		    }
		}
	    } catch (IllegalArgumentException e1) {
		logger.error("formToParam error", e1);
	    } catch (IllegalAccessException e1) {
		logger.error("formToParam error", e1);
	    }
	}
	return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
