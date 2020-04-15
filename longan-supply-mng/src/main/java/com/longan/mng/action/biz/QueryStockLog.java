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
import com.longan.biz.core.StockService;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryStockLog extends BaseController {
    @Resource
    private StockService stockService;

    @Resource
    private ItemService itemService;

    @RequestMapping(value = "biz/queryStockLog", params = "requestType=index")
    public String index(@ModelAttribute("stockLogQuery") StockLogQuery stockLogQuery, HttpSession session, Model model) {
	// 业务权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (!checkBizAuth(stockLogQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	setSelectValue(userInfo, model, stockLogQuery);
	return "biz/queryStockLog";
    }

    @RequestMapping(value = "biz/queryStockLog")
    public String doQuery(@ModelAttribute("stockLogQuery") StockLogQuery stockLogQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(stockLogQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	setSelectValue(userInfo, model, stockLogQuery);
	Result<List<StockLog>> result = stockService.queryStockListPage(stockLogQuery);
	if (result.isSuccess()) {
	    model.addAttribute("stockLogList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "biz/queryStockLog";
    }

    void setSelectValue(UserInfo userInfo, Model model, StockLogQuery stockLogQuery) {
	Result<List<ItemSupply>> itemSupplyResult = itemService.queryItemSupplyByBizId(stockLogQuery.getBizId());
	if (itemSupplyResult.isSuccess() && itemSupplyResult.getModule() != null) {
	    Map<Long, String> map = new HashMap<Long, String>();
	    for (ItemSupply itemSupply : itemSupplyResult.getModule()) {
		Item item = localCachedService.getItem(itemSupply.getItemId());
		if (item == null) {
		    continue;
		}
		UserInfo supplyTraderInfo = localCachedService.getUserInfo(itemSupply.getSupplyTraderId());
		map.put(itemSupply.getId(), item.getItemName() + "(" + supplyTraderInfo.getUserName() + ")");
	    }
	    model.addAttribute("itemSupplyList", map);
	}

	// Result<List<Item>> itemResult = itemService.queryItemList(stockLogQuery.getBizId());
	// if (itemResult.isSuccess() && itemResult.getModule() != null) {
	// Map<Integer, String> map = new HashMap<Integer, String>();
	// for (Item item : itemResult.getModule()) {
	// map.put(item.getId(), item.getItemName());
	// }
	// model.addAttribute("itemList", map);
	// }
	model.addAttribute("itemList", localCachedService.getItemByBizId(stockLogQuery.getBizId()));
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>(5);
	map.put(Constants.StockLog.STATUS_ACTIVATED + "", Constants.StockLog.STATUS_ACTIVATED_DESC);
	map.put(Constants.StockLog.STATUS_FAILED + "", Constants.StockLog.STATUS_FAILED_DESC);
	map.put(Constants.StockLog.STATUS_INIT + "", Constants.StockLog.STATUS_INIT_DESC);
	map.put(Constants.StockLog.STATUS_INVALID + "", Constants.StockLog.STATUS_INVALID_DESC);
	map.put(Constants.StockLog.STATUS_SUCCESS + "", Constants.StockLog.STATUS_SUCCESS_DESC);
	return map;
    }

    @ModelAttribute("typeList")
    public Map<String, String> typeList() {
	Map<String, String> map = new HashMap<String, String>(2);
	map.put(Constants.StockLog.TYPE_IN + "", Constants.StockLog.TYPE_IN_DESC);
	map.put(Constants.StockLog.TYPE_OUT + "", Constants.StockLog.TYPE_OUT_DESC);
	return map;
    }
}
