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

import com.longan.biz.core.ItemPriceService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryItemPrice extends BaseController {
    @Resource
    private ItemPriceService itemPriceService;

    @RequestMapping(value = "biz/queryItemPrice")
    public String index(@ModelAttribute("itemPriceQuery") ItemPriceQuery itemPriceQuery, HttpSession session, Model model) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(itemPriceQuery.getBizId()));
	model.addAttribute("userInfo", userInfo);
	Result<List<ItemPrice>> result = itemPriceService.queryItemPricePage(itemPriceQuery);
	if (!result.isSuccess()) {
	    super.setError(model, result.getResultMsg());
	    return "biz/queryItemPrice";
	}

	List<ItemPrice> itemPriceList = result.getModule();
	if (itemPriceList != null) {
	    for (ItemPrice itemPrice : itemPriceList) {
		UserInfo supplyTrader = localCachedService.getUserInfo(itemPrice.getSupplyTraderId());
		if (supplyTrader != null) {
		    itemPrice.setSupplyTraderName(supplyTrader.getUserName());
		}
		if (itemPrice.isNationwide()) {
		    itemPrice.setItemSalesAreaDesc(Constants.Item.SALE_TYPE_NATIONWIDE_DESC);
		} else {
		    StringBuffer sb = new StringBuffer("");
		    for (String areaCode : itemPrice.getSalesAreaList()) {
			AreaInfo areaInfo = localCachedService.getProvinceByCode(areaCode);
			if (areaInfo != null) {
			    sb.append(areaInfo.getProvinceName()).append("、");
			}
		    }
		    itemPrice.setItemSalesAreaDesc(sb.toString().substring(0, sb.toString().length() - 1));
		}
	    }
	}

	model.addAttribute("itemPriceList", itemPriceList);
	return "biz/queryItemPrice";
    }

    @ModelAttribute("statusList")
    public Map<Integer, String> statusList() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	map.put(Constants.Item.STATUS_DEL, Constants.Item.STATUS_DEL_DESC);
	map.put(Constants.Item.STATUS_DOWN, Constants.Item.STATUS_DOWN_DESC);
	map.put(Constants.Item.STATUS_INIT, Constants.Item.STATUS_INIT_DESC);
	map.put(Constants.Item.STATUS_UP, Constants.Item.STATUS_UP_DESC);
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

    @ModelAttribute("exceTypeList")
    public Map<Integer, String> exceTypeList() {
	Map<Integer, String> mapType = new HashMap<Integer, String>();
	mapType.put(1, "价格异常");
	return mapType;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}