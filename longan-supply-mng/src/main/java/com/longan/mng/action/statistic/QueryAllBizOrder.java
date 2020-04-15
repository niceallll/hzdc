package com.longan.mng.action.statistic;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.BizOrderStatisic;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryAllBizOrder extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("statistic/queryAllBizOrder")
    public void doQuery(@ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("userInfo", userInfo);
	if (bizOrderQuery.getId() != null || bizOrderQuery.getPayOrderId() != null) {
	    bizOrderQuery.setEndGmtCreate(null);
	    bizOrderQuery.setStartGmtCreate(null);
	} else {
	    Date date = null;
	    try {
		date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	    } catch (ParseException e) {
	    }
	    if (bizOrderQuery.getEndGmtCreate() == null) {
		bizOrderQuery.setEndGmtCreate(date);
	    }
	    if (bizOrderQuery.getStartGmtCreate() == null) {
		bizOrderQuery.setStartGmtCreate(date);
	    }
	    if (!check2Date(bizOrderQuery.getStartGmtCreate(), bizOrderQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "订单时间区间不能超过2个月");
	    }
	}

	setSelectValue(userInfo, model, bizOrderQuery);
	Result<List<BizOrder>> result = queryService.queryBizOrderPage(bizOrderQuery);
	if (result.isSuccess()) {
	    List<BizOrder> list = result.getModule();
	    for (BizOrder bizOrder : list) {
		UserInfo user = localCachedService.getUserInfo(bizOrder.getUserId());
		bizOrder.setUserName(user.getUserName());
		if (bizOrder.getLockOperId() != null) {
		    UserInfo oper = localCachedService.getUserInfo(bizOrder.getLockOperId());
		    bizOrder.setLockOperName(oper == null ? "" : oper.getUserName());
		}
		if (bizOrder.getDealOperId() != null) {
		    UserInfo oper = localCachedService.getUserInfo(bizOrder.getDealOperId());
		    bizOrder.setDealOperName(oper == null ? "" : oper.getUserName());
		}
		if (StringUtils.isNotBlank(bizOrder.getProvinceCode())) {
		    AreaInfo areaInfo = localCachedService.getProvinceByCode(bizOrder.getProvinceCode());
		    if (areaInfo != null) {
			bizOrder.setUidAreaInfo(areaInfo.getProvinceName());
		    }
		}
		if (bizOrder.getSupplyType() == null && bizOrder.getItemSupplyId() != null) {
		    ItemSupply itemSupply = localCachedService.getItemSupply(bizOrder.getItemSupplyId());
		    if (itemSupply != null) {
			// 之后改成itemSupplyType
			bizOrder.setSupplyType(itemSupply.getItemSupplyType());
		    }
		}
		if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
		    UserInfo traderUserInfo = localCachedService.getUserInfo(Long.parseLong(bizOrder.getUpstreamId()));
		    if (traderUserInfo != null) {
			bizOrder.setUpstreamName(traderUserInfo.getUserName());
		    }
		}
	    }
	    model.addAttribute("bizOrderList", list);
	} else {
	    super.setError(model, result.getResultMsg());
	}

	// 订单统计。
	Result<BizOrderStatisic> countBizOrderResult = queryService.countBizOrder(bizOrderQuery);
	if (countBizOrderResult.isSuccess()) {
	    model.addAttribute("bizOrderStatisic", countBizOrderResult.getModule());
	}
    }

    void setSelectValue(UserInfo userInfo, Model model, BizOrderQuery bizOrderQuery) {
	if (userInfo.isDownStreamUser()) { // 下游只能看自己的订单
	    bizOrderQuery.setUserId(userInfo.getId()); // 设置查询条件
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

	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// Map<Long, String> map = new HashMap<Long, String>();
	// for (UserInfo user : usUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// model.addAttribute("upStreamUser", map);
	// }
	model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());
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

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.BizOrder.STATUS_INIT + "", Constants.BizOrder.STATUS_INIT_DESC);
	map.put(Constants.BizOrder.STATUS_CHARGING + "", Constants.BizOrder.STATUS_CHARGING_DESC);
	map.put(Constants.BizOrder.STATUS_SUCCESS + "", Constants.BizOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.BizOrder.STATUS_FAILED + "", Constants.BizOrder.STATUS_FAILED_DESC);
	map.put(Constants.BizOrder.STATUS_PENDING + "", Constants.BizOrder.STATUS_PENDING_DESC);
	map.put(Constants.BizOrder.STATUS_UNCONFIRMED + "", Constants.BizOrder.STATUS_UNCONFIRMED_DESC);
	map.put(Constants.BizOrder.STATUS_EXCEPTION + "", Constants.BizOrder.STATUS_EXCEPTION_DESC);
	return map;
    }

    @ModelAttribute("bizList")
    public Map<Integer, String> bizList() {
	return Constants.getBizMap();
    }

    @ModelAttribute("carrierTypeList")
    public Map<String, String> carrierTypeList() {
	Map<String, String> mapType = new LinkedHashMap<String, String>();
	mapType.put(Constants.Item.CARRIER_TYPE_MOBILE + "", Constants.Item.CARRIER_TYPE_MOBILE_DESC);
	mapType.put(Constants.Item.CARRIER_TYPE_TELECOM + "", Constants.Item.CARRIER_TYPE_TELECOM_DESC);
	mapType.put(Constants.Item.CARRIER_TYPE_UNICOM + "", Constants.Item.CARRIER_TYPE_UNICOM_DESC);
	mapType.put(Constants.Item.CARRIER_TYPE_OTHER + "", Constants.Item.CARRIER_TYPE_OTHER_DESC);
	return mapType;
    }

    @ModelAttribute("supplyTypeList")
    public Map<String, String> supplyTypeList() {
	Map<String, String> mapType = new LinkedHashMap<String, String>();
	mapType.put(Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_DIRECT_CHARGE + "", Constants.ItemSupply.TYPE_DIRECT_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_MAN + "", Constants.ItemSupply.TYPE_MAN_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD + "", Constants.ItemSupply.TYPE_CARD_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_CHARGE_DESC);
	return mapType;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}
