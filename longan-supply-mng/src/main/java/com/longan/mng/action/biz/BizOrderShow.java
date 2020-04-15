package com.longan.mng.action.biz;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class BizOrderShow extends BaseController {
    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private LocalCachedService localCachedService;

    @RequestMapping("biz/bizOrderShow")
    public String orderShow(@RequestParam("bizOrderId") Long bizOrderId, @RequestParam("bizId") int bizId, Model model) {
	Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
	if (!queryReuslt.isSuccess()) {
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	    model.addAttribute("errorMsg", queryReuslt.getResultMsg());
	    return "biz/bizOrderShow";
	}
	BizOrder bizOrder = queryReuslt.getModule();
	if (bizOrder.getLockOperId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(bizOrder.getLockOperId());
	    bizOrder.setLockOperName(oper == null ? "" : oper.getUserName());
	}
	if (bizOrder.getDealOperId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(bizOrder.getDealOperId());
	    bizOrder.setDealOperName(oper == null ? "" : oper.getUserName());
	}
	if (bizOrder.getUserId() != null) {
	    UserInfo oper = localCachedService.getUserInfo(bizOrder.getUserId());
	    bizOrder.setDownstreamName(oper == null ? "" : oper.getUserName());
	}
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizOrder", bizOrder);

	if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
	    UserInfo traderUserInfo = localCachedService.getUserInfo(Long.parseLong(bizOrder.getUpstreamId()));
	    TraderInfo traderInfo = localCachedService.getTraderInfo(Long.parseLong(bizOrder.getUpstreamId()));
	    model.addAttribute("traderUserInfo", traderUserInfo);
	    model.addAttribute("traderInfo", traderInfo);
	}

	return "biz/bizOrderShow";
    }
}