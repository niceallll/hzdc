package com.longan.mng.action.biz;

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

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QuerySmsOrder extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("biz/querySmsOrder")
    public String doQuery(@ModelAttribute("smsOrderQuery") SmsOrderQuery smsOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(smsOrderQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);
	if (smsOrderQuery.getId() != null) {
	    smsOrderQuery.setEndGmtCreate(null);
	    smsOrderQuery.setStartGmtCreate(null);
	} else {
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
	    if (!check2Date(smsOrderQuery.getStartGmtCreate(), smsOrderQuery.getEndGmtCreate())) {
		alertErrorNoneBack(model, "订单时间区间不能超过2个月");
		return "biz/querySmsOrder";
	    }
	}

	setSelectValue(userInfo, model, smsOrderQuery);
	Result<List<SmsOrder>> result = queryService.querySmsOrderPage(smsOrderQuery);
	if (result.isSuccess()) {
	    model.addAttribute("smsOrderList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/querySmsOrder";
    }

    private void setSelectValue(UserInfo userInfo, Model model, SmsOrderQuery smsOrderQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(smsOrderQuery.getBizId()));
	model.addAttribute("bizId", smsOrderQuery.getBizId());
	if (userInfo.isDownStreamUser()) { // 下游只能看自己的订单
	    smsOrderQuery.setUserId(userInfo.getId()); // 设置查询条件
	    Map<Long, String> map = new HashMap<Long, String>();
	    map.put(userInfo.getId(), userInfo.getUserName());
	    model.addAttribute("downStreamUser", map);
	} else {
	    model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());
	}

	model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());
	model.addAttribute("itemList", localCachedService.getItemByBizId(smsOrderQuery.getBizId()));
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.BizOrder.STATUS_INIT + "", Constants.BizOrder.STATUS_INIT_DESC);
	map.put(Constants.BizOrder.STATUS_CHARGING + "", Constants.BizOrder.STATUS_CHARGING_DESC);
	map.put(Constants.BizOrder.STATUS_SUCCESS + "", Constants.BizOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.BizOrder.STATUS_FAILED + "", Constants.BizOrder.STATUS_FAILED_DESC);
	map.put(Constants.BizOrder.STATUS_PARTS + "", Constants.BizOrder.STATUS_PARTS_DESC);
	return map;
    }
}
