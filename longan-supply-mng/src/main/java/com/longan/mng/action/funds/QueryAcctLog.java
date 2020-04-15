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

import com.longan.biz.core.AcctLogService;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/queryAcctLog")
public class QueryAcctLog extends BaseController {
    @Resource
    private AcctLogService acctLogService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("acctLogQuery") AcctLogQuery acctLogQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("userInfo", userInfo);
	setSelectValue(model, userInfo, acctLogQuery);
	acctLogQuery.setStartGmtCreate(new Date());
	acctLogQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("acctLogQuery") AcctLogQuery acctLogQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(model, userInfo, acctLogQuery);
	model.addAttribute("userInfo", userInfo);
	if (acctLogQuery.getId() != null || acctLogQuery.getBillId() != null || acctLogQuery.getBizOrderId() != null) {
	    acctLogQuery.setEndGmtCreate(null);
	    acctLogQuery.setStartGmtCreate(null);
	} else {
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
	    if (!check2Date(acctLogQuery.getStartGmtCreate(), acctLogQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "资金流水时间区间不能超过2个月");
		return;
	    }
	}

	Result<List<AcctLog>> result = acctLogService.queryAcctLog(acctLogQuery);
	if (result.isSuccess()) {
	    model.addAttribute("acctLogList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    void setSelectValue(Model model, UserInfo userInfo, AcctLogQuery acctLogQuery) {
	Map<Long, String> map = new HashMap<Long, String>();
	if (userInfo.isDownStreamUser()) {
	    acctLogQuery.setUserId(userInfo.getId());
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

    @ModelAttribute("billTypeList")
    public Map<String, String> billTypeList() {
	Map<String, String> mapBill = new LinkedHashMap<String, String>();
	mapBill.put(Constants.AcctLog.BILL_TYPE_PAY_ORDER + "", Constants.AcctLog.BILL_TYPE_PAY_ORDER_DESC);
	mapBill.put(Constants.AcctLog.BILL_TYPE_CHARGE_ORDER + "", Constants.AcctLog.BILL_TYPE_CHARGE_ORDER_DESC);
	mapBill.put(Constants.AcctLog.BILL_TYPE_REFUND_ORDER + "", Constants.AcctLog.BILL_TYPE_REFUND_ORDER_DESC);
	mapBill.put(Constants.AcctLog.BILL_TYPE_CASH_ORDER + "", Constants.AcctLog.BILL_TYPE_CASH_ORDER_DESC);
	return mapBill;
    }

    @ModelAttribute("tradeTypeList")
    public Map<String, String> tradeTypeList() {
	Map<String, String> mapTrade = new HashMap<String, String>();
	mapTrade.put(Constants.AcctLog.TRADE_TYPE_IN + "", Constants.AcctLog.TRADE_TYPE_IN_DESC);
	mapTrade.put(Constants.AcctLog.TRADE_TYPE_OUT + "", Constants.AcctLog.TRADE_TYPE_OUT_DESC);
	return mapTrade;
    }

    @ModelAttribute("upStreamUserList")
    public Map<Long, String> upStreamUserList() {
	// Map<Long, String> result = new HashMap<Long, String>();
	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// for (UserInfo user : usUserResult.getModule()) {
	// result.put(user.getId(), user.getUserName());
	// }
	// }
	// return result;
	return localCachedService.getUpStreamUser();
    }

    @ModelAttribute("itemList")
    public Map<Integer, String> itemList() {
	// Map<Integer, String> result = new HashMap<Integer, String>();
	// Result<List<Item>> itemResult = itemService.queryItemList();
	// if (itemResult.isSuccess() && itemResult.getModule() != null) {
	// for (Item item : itemResult.getModule()) {
	// result.put(item.getId(), item.getItemName());
	// }
	// }
	// return result;
	return localCachedService.getItem();
    }
}
