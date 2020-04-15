package com.longan.mng.action.biz;

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

import com.longan.biz.core.ItemService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryItemSupply extends BaseController {
    @Resource
    private ItemService itemService;

    @RequestMapping(value = "biz/queryItemSupply")
    public String index(@ModelAttribute("itemSupplyQuery") ItemSupplyQuery itemSupplyQuery, HttpSession session, Model model) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);

	if (!checkBizAuth(itemSupplyQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}
	model.addAttribute("bizName", Constants.BIZ_MAP.get(itemSupplyQuery.getBizId()));
	model.addAttribute("userInfo", userInfo);

	Result<List<ItemSupply>> result = itemService.queryItemSupplyPage(itemSupplyQuery);
	if (!result.isSuccess()) {
	    super.setError(model, result.getResultMsg());
	    return "biz/queryItemSupply";
	}

	List<ItemSupply> itemSupplyList = result.getModule();
	if (itemSupplyList != null) {
	    for (ItemSupply itemSupply : itemSupplyList) {
		UserInfo supplyTrader = localCachedService.getUserInfo(itemSupply.getSupplyTraderId());
		if (supplyTrader != null) {
		    itemSupply.setSupplyTraderName(supplyTrader.getUserName());
		}
	    }
	}

	model.addAttribute("itemSupplyList", itemSupplyList);
	return "biz/queryItemSupply";
    }

    @ModelAttribute("statusList")
    public Map<Integer, String> statusList() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	map.put(Constants.ItemSupply.STATUS_DEL, Constants.ItemSupply.STATUS_DEL_DESC);
	map.put(Constants.ItemSupply.STATUS_DOWN, Constants.ItemSupply.STATUS_DOWN_DESC);
	map.put(Constants.ItemSupply.STATUS_INIT, Constants.ItemSupply.STATUS_INIT_DESC);
	map.put(Constants.ItemSupply.STATUS_UP, Constants.ItemSupply.STATUS_UP_DESC);
	return map;
    }

    @ModelAttribute("supplyTraderList")
    public Map<Long, String> supplyTraderList() {
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

    @ModelAttribute("itemTypeList")
    public Map<String, String> itemTypeList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.Item.TYPE_ITEM_CARD + "", Constants.Item.TYPE_ITEM_CARD_DESC);
	mapType.put(Constants.Item.TYPE_ITEM_CHARGE + "", Constants.Item.TYPE_ITEM_CHARGE_DESC);
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

    @ModelAttribute("asyncSupplyList")
    public Map<String, String> asyncSupplyList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.TraderInfo.SUPPLY_WAY_SYNC + "", Constants.TraderInfo.SUPPLY_WAY_SYNC_DESC);
	mapType.put(Constants.TraderInfo.SUPPLY_WAY_ASYNC + "", Constants.TraderInfo.SUPPLY_WAY_ASYNC_DESC);
	return mapType;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}