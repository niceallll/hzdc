package com.longan.mng.action.funds;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryChargeOrder extends BaseController {
    @Resource
    private ChargeOrderService chargeOrderService;

    @RequestMapping(value = "funds/queryChargeOrder", params = "requestType=index")
    public void index(@ModelAttribute("chargeOrderQuery") ChargeOrderQuery chargeOrderQuery, HttpSession session, Model model) {
	chargeOrderQuery.setStartGmtCreate(DateTool.beforeDay(new Date(), 30));
	chargeOrderQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(value = "funds/queryChargeOrder")
    public void doQuery(@ModelAttribute("chargeOrderQuery") ChargeOrderQuery chargeOrderQuery, Model model, HttpSession session) {
	Result<List<ChargeOrder>> result = chargeOrderService.queryChargeOrder(chargeOrderQuery);
	if (result.isSuccess()) {
	    for (ChargeOrder chargeOrder : result.getModule()) {
		if (chargeOrder.getUserId() != null) {
		    UserInfo userInfo = localCachedService.getUserInfo(chargeOrder.getUserId());
		    if (userInfo != null) {
			chargeOrder.setUserName(userInfo.getUserName());
		    }
		}
	    }
	    model.addAttribute("chargeOrderList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.ChargeOrder.STATUS_INIT + "", Constants.ChargeOrder.STATUS_INIT_DESC);
	map.put(Constants.ChargeOrder.STATUS_SUCCESS + "", Constants.ChargeOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.ChargeOrder.STATUS_FAILED + "", Constants.ChargeOrder.STATUS_FAILED_DESC);
	map.put(Constants.ChargeOrder.STATUS_UNCONFIRMED + "", Constants.ChargeOrder.STATUS_UNCONFIRMED_DESC);
	return map;
    }

    @ModelAttribute("downStreamUser")
    public Map<Long, String> downStreamUser() {
	// Result<List<UserInfo>> dsUserResult = userService.queryUserInfoDownStream();
	// if (dsUserResult.isSuccess() && dsUserResult.getModule() != null) {
	// Map<Long, String> map = new HashMap<Long, String>();
	// for (UserInfo user : dsUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// return map;
	// } else {
	// return null;
	// }
	return localCachedService.getDownStreamUser();
    }
}
