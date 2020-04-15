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

import com.longan.biz.core.ItemService;
import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryStock extends BaseController {
    @Resource
    private QueryService queryService;

    @Resource
    private ItemService itemService;

    @RequestMapping("biz/queryStockIndex")
    public String index(@ModelAttribute("stockQuery") StockQuery stockQuery, HttpSession session, Model model) {
	// 业务权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (!checkBizAuth(stockQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);
	this.setSelectValue(userInfo, model, stockQuery);
	stockQuery.setStartGmtCreate(new Date());
	stockQuery.setEndGmtCreate(new Date());
	return "biz/queryStock";
    }

    @RequestMapping("biz/queryStock")
    public String doQuery(@ModelAttribute("stockQuery") StockQuery stockQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(stockQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);
	setSelectValue(userInfo, model, stockQuery);

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e1) {
	}

	if (stockQuery.getId() != null || stockQuery.getBizOrderId() != null || stockQuery.getInSerialno() != null
		|| stockQuery.getOutSerialno() != null) {
	    stockQuery.setStartOutTime(null);
	    stockQuery.setEndOutTime(null);
	    stockQuery.setEndGmtCreate(null);
	    stockQuery.setStartGmtCreate(null);
	} else {
	    if (stockQuery.getEndOutTime() != null || stockQuery.getStartOutTime() != null) {
		if (stockQuery.getEndOutTime() == null) {
		    stockQuery.setEndOutTime(date);
		}
		if (stockQuery.getStartOutTime() == null) {
		    stockQuery.setStartOutTime(date);
		}
		stockQuery.setEndGmtCreate(null);
		stockQuery.setStartGmtCreate(null);
		if (!check2Date(stockQuery.getStartOutTime(), stockQuery.getEndOutTime())) {
		    super.alertErrorNoneBack(model, "出库时间区间不能超过2个月");
		    return "biz/queryStock";
		}
	    } else {
		if (stockQuery.getEndGmtCreate() == null) {
		    stockQuery.setEndGmtCreate(date);
		}
		if (stockQuery.getStartGmtCreate() == null) {
		    stockQuery.setStartGmtCreate(date);
		}
		if (!check2Date(stockQuery.getStartGmtCreate(), stockQuery.getEndGmtCreate())) {
		    super.alertErrorNoneBack(model, "创建时间区间不能超过2个月");
		    return "biz/queryStock";
		}
	    }
	}

	Result<List<Stock>> result = queryService.queryStockPage(stockQuery);
	if (result.isSuccess()) {
	    List<Stock> list = result.getModule();
	    model.addAttribute("stockList", list);
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "biz/queryStock";
    }

    void setSelectValue(UserInfo userInfo, Model model, StockQuery stockQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(stockQuery.getBizId()));
	model.addAttribute("bizId", stockQuery.getBizId());
	Result<List<ItemSupply>> itemSupplyResult = itemService.queryItemSupplyByBizId(stockQuery.getBizId());
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

	// Result<List<Item>> itemResult = itemService.queryItemList(stockQuery.getBizId());
	// if (itemResult.isSuccess() && itemResult.getModule() != null) {
	// Map<Integer, String> map = new HashMap<Integer, String>();
	// for (Item item : itemResult.getModule()) {
	// map.put(item.getId(), item.getItemName());
	// }
	// model.addAttribute("itemList", map);
	// }
	model.addAttribute("itemList", localCachedService.getItemByBizId(stockQuery.getBizId()));
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.Stock.STATUS_INIT + "", Constants.Stock.STATUS_INIT_DESC);
	map.put(Constants.Stock.STATUS_NORMAL + "", Constants.Stock.STATUS_NORMAL_DESC);
	map.put(Constants.Stock.STATUS_INV_ALLOCATED + "", Constants.Stock.STATUS_INV_ALLOCATED_DESC);
	map.put(Constants.Stock.STATUS_DELIVERY + "", Constants.Stock.STATUS_DELIVERY_DESC);
	map.put(Constants.Stock.STATUS_INVALID + "", Constants.Stock.STATUS_INVALID_DESC);
	map.put(Constants.Stock.STATUS_EXCEPTION + "", Constants.Stock.STATUS_EXCEPTION_DESC);
	map.put(Constants.Stock.STATUS_DEL + "", Constants.Stock.STATUS_DEL_DESC);
	return map;
    }
}
