package com.longan.mng.action.statistic;

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
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.domain.SupplyOrderStatisic;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryAllSupplyOrder extends BaseController {
    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private QueryService queryService;

    @RequestMapping("statistic/queryAllSupplyOrder")
    public void doQuery(@ModelAttribute("supplyOrderQuery") SupplyOrderQuery supplyOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("userInfo", userInfo);
	if (supplyOrderQuery.getId() != null || supplyOrderQuery.getBizOrderId() != null) {
	    supplyOrderQuery.setEndGmtCreate(null);
	    supplyOrderQuery.setStartGmtCreate(null);
	} else {
	    Date date = null;
	    try {
		date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	    } catch (ParseException e) {
	    }
	    if (supplyOrderQuery.getEndGmtCreate() == null) {
		supplyOrderQuery.setEndGmtCreate(date);
	    }
	    if (supplyOrderQuery.getStartGmtCreate() == null) {
		supplyOrderQuery.setStartGmtCreate(date);
	    }
	    if (!check2Date(supplyOrderQuery.getStartGmtCreate(), supplyOrderQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "供货单时间区间不能超过2个月");
	    }
	}
	setSelectValue(userInfo, model, supplyOrderQuery);

	Result<List<SupplyOrder>> result = supplyOrderService.querySupplyOrderPage(supplyOrderQuery);
	if (result.isSuccess()) {
	    List<SupplyOrder> list = result.getModule();
	    if (list != null) {
		for (SupplyOrder supplyOrder : list) {
		    UserInfo user = localCachedService.getUserInfo(supplyOrder.getUserId());
		    supplyOrder.setUserName(user.getUserName());
		    if (supplyOrder.getSupplyTraderId() != null) {
			UserInfo traderUserInfo = localCachedService.getUserInfo(supplyOrder.getSupplyTraderId());
			if (traderUserInfo != null) {
			    supplyOrder.setSupplyTraderName(traderUserInfo.getUserName());
			}
		    }
		    if (supplyOrder.getSupplyType() == null && supplyOrder.getItemSupplyId() != null) {
			ItemSupply itemSupply = localCachedService.getItemSupply(supplyOrder.getItemSupplyId());
			if (itemSupply != null) {
			    supplyOrder.setSupplyType(itemSupply.getItemSupplyType());
			}
		    }
		    if (StringUtils.isNotBlank(supplyOrder.getProvinceCode())) {
			AreaInfo areaInfo = localCachedService.getProvinceByCode(supplyOrder.getProvinceCode());
			if (areaInfo != null) {
			    supplyOrder.setUidAreaInfo(areaInfo.getProvinceName());
			}
		    }
		}
	    }
	    model.addAttribute("supplyOrderList", list);
	} else {
	    super.setError(model, result.getResultMsg());
	}

	// 订单统计。
	Result<SupplyOrderStatisic> countSupplyOrderResult = queryService.countSupplyOrder(supplyOrderQuery);
	if (countSupplyOrderResult.isSuccess()) {
	    model.addAttribute("supplyOrderStatisic", countSupplyOrderResult.getModule());
	}
    }

    void setSelectValue(UserInfo userInfo, Model model, SupplyOrderQuery supplyOrderQuery) {
	if (userInfo.isDownStreamUser()) { // 下游不能查看
	    return;
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

    @ModelAttribute("supplyStatusList")
    public Map<String, String> supplyStatusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.SupplyOrder.STATUS_INIT + "", Constants.SupplyOrder.STATUS_INIT_DESC);
	map.put(Constants.SupplyOrder.STATUS_CHARGING + "", Constants.SupplyOrder.STATUS_CHARGING_DESC);
	map.put(Constants.SupplyOrder.STATUS_SUCCESS + "", Constants.SupplyOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.SupplyOrder.STATUS_FAILED + "", Constants.SupplyOrder.STATUS_FAILED_DESC);
	map.put(Constants.SupplyOrder.STATUS_LOCK + "", Constants.SupplyOrder.STATUS_LOCK_DESC);
	map.put(Constants.SupplyOrder.STATUS_PARTS + "", Constants.SupplyOrder.STATUS_PARTS_DESC);
	map.put(Constants.SupplyOrder.STATUS_UNCONFIRMED + "", Constants.SupplyOrder.STATUS_UNCONFIRMED_DESC);
	map.put(Constants.SupplyOrder.STATUS_EXCEPTION + "", Constants.SupplyOrder.STATUS_EXCEPTION_DESC);
	return map;
    }

    @ModelAttribute("bizList")
    public Map<Integer, String> bizList() {
	return Constants.getBizMap();
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
}