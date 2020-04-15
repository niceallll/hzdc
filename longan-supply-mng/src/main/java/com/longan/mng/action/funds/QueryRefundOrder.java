package com.longan.mng.action.funds;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.RefundOrderService;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/queryRefundOrder")
public class QueryRefundOrder extends BaseController {
    @Resource
    private RefundOrderService refundOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("refundOrderQuery") RefundOrderQuery refundOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(userInfo, model, refundOrderQuery);
	refundOrderQuery.setStartGmtCreate(new Date());
	refundOrderQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("refundOrderQuery") RefundOrderQuery refundOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(userInfo, model, refundOrderQuery);

	if (refundOrderQuery.getPayOrderId() != null || refundOrderQuery.getId() != null
		|| refundOrderQuery.getBizOrderId() != null) {
	    refundOrderQuery.setEndGmtCreate(null);
	    refundOrderQuery.setStartGmtCreate(null);
	} else {
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
	    if (!check2Date(refundOrderQuery.getStartGmtCreate(), refundOrderQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "退款单时间区间不能超过2个月");
		return;
	    }
	}

	Result<List<RefundOrder>> result = refundOrderService.queryRefundOrder(refundOrderQuery);
	if (result.isSuccess()) {
	    model.addAttribute("refundOrderList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    void setSelectValue(UserInfo userInfo, Model model, RefundOrderQuery refundOrderQuery) {
	if (userInfo.isDownStreamUser()) { // 下游只能看自己
	    refundOrderQuery.setUserId(userInfo.getId()); // 设置查询条件
	    Map<Long, String> map = new HashMap<Long, String>();
	    map.put(userInfo.getId(), userInfo.getUserName());
	    model.addAttribute("downStreamUser", map);
	} else {
	    // Result<List<UserInfo>> dsUserResult = userService.queryUserInfoDownStream();
	    // if (dsUserResult.isSuccess() && dsUserResult.getModule() != null) {
	    // Map<Long, String> map = new HashMap<Long, String>();
	    // for (UserInfo user : dsUserResult.getModule()) {
	    // map.put(user.getId(), user.getUserName());
	    // }
	    // model.addAttribute("downStreamUser", map);
	    // }
	    model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());
	}
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.RefundOrder.STATUS_INIT + "", Constants.RefundOrder.STATUS_INIT_DESC);
	map.put(Constants.RefundOrder.STATUS_SUCCESS + "", Constants.RefundOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.RefundOrder.STATUS_FAILED + "", Constants.RefundOrder.STATUS_FAILED_DESC);
	map.put(Constants.RefundOrder.STATUS_UNCONFIRMED + "", Constants.RefundOrder.STATUS_UNCONFIRMED_DESC);
	return map;
    }

    @ModelAttribute("payTypeList")
    public Map<String, String> payTypeList() {
	Map<String, String> map = new HashMap<String, String>();
	map.put(Constants.RefundOrder.PAY_TYPE_OPERATOR + "", Constants.RefundOrder.PAY_TYPE_OPERATOR_DESC);
	map.put(Constants.RefundOrder.PAY_TYPE_SYSTEM + "", Constants.RefundOrder.PAY_TYPE_SYSTEM_DESC);
	return map;
    }

    @ModelAttribute("operList")
    public Map<Long, String> operList() {
	// Result<List<UserInfo>> adminUserResult = userService.queryUserInfoAdmin();
	// Map<Long, String> map = new HashMap<Long, String>();
	// if (adminUserResult.isSuccess() && adminUserResult.getModule() != null) {
	// for (UserInfo user : adminUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// }
	// return map;
	return localCachedService.getAdminUser();
    }
}
