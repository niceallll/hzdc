package com.longan.mng.action.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class SupplyOrderShow extends BaseController {
    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    protected LocalCachedService localCachedService;

    @RequestMapping("biz/supplyOrderShow")
    public String orderShow(@RequestParam("supplyOrderId") Long supplyOrderId, @RequestParam("bizId") int bizId, Model model) {
	Result<SupplyOrder> queryReuslt = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!queryReuslt.isSuccess()) {
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	    model.addAttribute("errorMsg", queryReuslt.getResultMsg());
	    return "biz/supplyOrderShow";
	}
	SupplyOrder supplyOrder = queryReuslt.getModule();
	if (supplyOrder.getLockOperId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(supplyOrder.getLockOperId());
	    supplyOrder.setLockOperName(oper == null ? "" : oper.getUserName());
	}
	if (supplyOrder.getDealOperId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(supplyOrder.getDealOperId());
	    supplyOrder.setDealOperName(oper == null ? "" : oper.getUserName());
	}
	if (supplyOrder.getUserId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(supplyOrder.getUserId());
	    supplyOrder.setDownstreamName(oper == null ? "" : oper.getUserName());
	}
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("supplyOrder", supplyOrder);
	if (supplyOrder.getSupplyTraderId() != null) {
	    TraderInfo traderInfo = localCachedService.getTraderInfo(supplyOrder.getSupplyTraderId());
	    model.addAttribute("traderInfo", traderInfo);
	}
	return "biz/supplyOrderShow";
    }
}
