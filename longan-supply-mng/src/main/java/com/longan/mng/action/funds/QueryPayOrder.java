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

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/queryPayOrder")
public class QueryPayOrder extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("payOrderQuery") PayOrderQuery payOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(model, userInfo, payOrderQuery);
	payOrderQuery.setStartGmtCreate(new Date());
	payOrderQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("payOrderQuery") PayOrderQuery payOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(model, userInfo, payOrderQuery);

	if (payOrderQuery.getId() != null || payOrderQuery.getBizOrderId() != null) {
	    payOrderQuery.setEndGmtCreate(null);
	    payOrderQuery.setStartGmtCreate(null);
	} else {
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
	    if (!check2Date(payOrderQuery.getStartGmtCreate(), payOrderQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "支付单时间区间不能超过2个月");
		return;
	    }
	}

	Result<List<PayOrder>> result = queryService.queryPayOrder(payOrderQuery);
	if (result.isSuccess()) {
	    model.addAttribute("payOrderList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    void setSelectValue(Model model, UserInfo userInfo, PayOrderQuery payOrderQuery) {
	Map<Long, String> map = new HashMap<Long, String>();
	model.addAttribute("userInfo", userInfo);
	if (userInfo.isDownStreamUser()) {
	    payOrderQuery.setUserId(userInfo.getId());
	    map.put(userInfo.getId(), userInfo.getUserName());
	    model.addAttribute("downStreamUser", map);
	    return;
	}

	// Result<List<UserInfo>> dsUserResult = userService.queryUserInfoDownStream();
	// if (dsUserResult.isSuccess() && dsUserResult.getModule() != null) {
	// for (UserInfo user : dsUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// model.addAttribute("downStreamUser", map);
	// }
	model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.PayOrder.STATUS_INIT + "", Constants.PayOrder.STATUS_INIT_DESC);
	map.put(Constants.PayOrder.STATUS_SUCCESS + "", Constants.PayOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.PayOrder.STATUS_REFUND + "", Constants.PayOrder.STATUS_REFUND_DESC);
	map.put(Constants.PayOrder.STATUS_FAILED + "", Constants.PayOrder.STATUS_FAILED_DESC);
	map.put(Constants.PayOrder.STATUS_UNCONFIRMED + "", Constants.PayOrder.STATUS_UNCONFIRMED_DESC);
	return map;
    }
}
