package com.longan.mng.action.user;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class CheckChargeQuery extends BaseController {
    @Resource
    private SupplyOrderService supplyOrderService;

    @RequestMapping("user/checkChargeQuery")
    public String doQuery(@RequestParam("supplyTraderId") Long supplyTraderId, Model model) {
	model.addAttribute("supplyTraderId", supplyTraderId);
	SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
	supplyOrderQuery.setStartGmtCreate(new Date());
	supplyOrderQuery.setEndGmtCreate(new Date());
	model.addAttribute("supplyOrderQuery", supplyOrderQuery);
	return "user/checkChargeQuery";
    }

    @RequestMapping("user/checkChargeQueryList")
    public String doQueryList(@ModelAttribute("supplyOrderQuery") SupplyOrderQuery supplyOrderQuery,
	    @RequestParam("supplyTraderId") Long supplyTraderId, Model model) {
	model.addAttribute("supplyTraderId", supplyTraderId);
	model.addAttribute("supplyOrderQuery", supplyOrderQuery);
	if (supplyOrderQuery.getId() != null) {
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
	    if (!check7Day(supplyOrderQuery.getStartGmtCreate(), supplyOrderQuery.getEndGmtCreate())) {
		super.alertErrorNoneBack(model, "订单时间区间不能超过七天 ");
		return "user/checkChargeQuery";
	    }
	}

	Result<List<SupplyOrder>> result = supplyOrderService.querySupplyOrderPage(supplyOrderQuery);
	if (result.isSuccess()) {
	    List<SupplyOrder> list = result.getModule();
	    model.addAttribute("supplyOrderList", list);

	    if (list != null) {
		for (SupplyOrder supplyOrder : list) {
		    if (StringUtils.isNotBlank(supplyOrder.getProvinceCode())) {
			AreaInfo areaInfo = localCachedService.getProvinceByCode(supplyOrder.getProvinceCode());
			if (areaInfo != null) {
			    supplyOrder.setUidAreaInfo(areaInfo.getProvinceName());
			}
		    }
		}
	    }
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "user/checkChargeQuery";
    }

    @ModelAttribute("upstreamStatusList")
    public Map<String, String> upstreamStatusList() {
	Map<String, String> map = new HashMap<String, String>();
	map.put("0", "等待受理");
	map.put("1", "充值中");
	map.put("2", "充值成功");
	map.put("3", "充值失败");
	map.put("4", "数据错误");
	map.put("5", "无法查询");
	return map;
    }
}
