package com.longan.mng.action.biz;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QuerySmsSupply extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("biz/querySmsSupply")
    public String doQuery(@ModelAttribute("smsSupplyQuery") SmsSupplyQuery smsSupplyQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(smsSupplyQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);
	if (smsSupplyQuery.getId() != null || smsSupplyQuery.getBizOrderId() != null) {
	    smsSupplyQuery.setEndGmtCreate(null);
	    smsSupplyQuery.setStartGmtCreate(null);
	} else {
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
	    if (!check2Date(smsSupplyQuery.getStartGmtCreate(), smsSupplyQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "订单时间区间不能超过2个月");
		return "biz/querySmsSupply";
	    }
	}

	setSelectValue(userInfo, model, smsSupplyQuery);
	Result<List<SmsSupply>> result = queryService.querySmsSupplyPage(smsSupplyQuery);
	if (result.isSuccess()) {
	    List<SmsSupply> list = result.getModule();
	    if (list != null) {
		for (SmsSupply smsSupply : list) {
		    if (smsSupply.getSupplyTraderId() != null) {
			UserInfo traderUserInfo = localCachedService.getUserInfo(smsSupply.getSupplyTraderId());
			if (traderUserInfo != null) {
			    smsSupply.setSupplyTraderName(traderUserInfo.getUserName());
			}
		    }
		    if (StringUtils.isNotBlank(smsSupply.getProvinceCode())) {
			AreaInfo areaInfo = localCachedService.getProvinceByCode(smsSupply.getProvinceCode());
			if (areaInfo != null) {
			    smsSupply.setProvinceName(areaInfo.getProvinceName());
			}
		    }
		}
	    }
	    model.addAttribute("smsSupplyList", list);
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "biz/querySmsSupply";
    }

    private void setSelectValue(UserInfo userInfo, Model model, SmsSupplyQuery smsSupplyQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(smsSupplyQuery.getBizId()));
	model.addAttribute("bizId", smsSupplyQuery.getBizId());
	if (userInfo.isDownStreamUser()) { // 下游只能看自己的订单
	    smsSupplyQuery.setUserId(userInfo.getId()); // 设置查询条件
	}

	model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());
    }

    @ModelAttribute("supplyStatusList")
    public Map<String, String> supplyStatusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.SupplyOrder.STATUS_INIT + "", Constants.SupplyOrder.STATUS_INIT_DESC);
	map.put(Constants.SupplyOrder.STATUS_CHARGING + "", Constants.SupplyOrder.STATUS_CHARGING_DESC);
	map.put(Constants.SupplyOrder.STATUS_SUCCESS + "", Constants.SupplyOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.SupplyOrder.STATUS_FAILED + "", Constants.SupplyOrder.STATUS_FAILED_DESC);
	return map;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}
