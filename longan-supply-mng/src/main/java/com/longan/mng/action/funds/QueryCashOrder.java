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

import com.longan.biz.core.CashOrderService;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.CashOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryCashOrder extends BaseController {
    @Resource
    private CashOrderService cashOrderService;

    @RequestMapping(value = "funds/queryCashOrder", params = "requestType=index")
    public void index(@ModelAttribute("cashOrderQuery") CashOrderQuery cashOrderQuery, HttpSession session, Model model) {
	cashOrderQuery.setStartGmtCreate(DateTool.beforeDay(new Date(), 30));
	cashOrderQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(value = "funds/queryCashOrder")
    public void doQuery(@ModelAttribute("cashOrderQuery") CashOrderQuery cashOrderQuery, Model model, HttpSession session) {
	Result<List<CashOrder>> result = cashOrderService.queryCashOrder(cashOrderQuery);
	if (result.isSuccess()) {
	    for (CashOrder cashOrder : result.getModule()) {
		if (cashOrder.getUserId() != null) {
		    UserInfo userInfo = localCachedService.getUserInfo(cashOrder.getUserId());
		    if (userInfo != null) {
			cashOrder.setUserName(userInfo.getUserName());
		    }
		}
	    }
	    model.addAttribute("cashOrderList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.CashOrder.STATUS_INIT + "", Constants.CashOrder.STATUS_INIT_DESC);
	map.put(Constants.CashOrder.STATUS_SUCCESS + "", Constants.CashOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.CashOrder.STATUS_FAILED + "", Constants.CashOrder.STATUS_FAILED_DESC);
	map.put(Constants.CashOrder.STATUS_UNCONFIRMED + "", Constants.CashOrder.STATUS_UNCONFIRMED_DESC);
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
