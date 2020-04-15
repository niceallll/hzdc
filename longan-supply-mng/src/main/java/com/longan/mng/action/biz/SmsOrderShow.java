package com.longan.mng.action.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.SmsOrderService;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class SmsOrderShow extends BaseController {
    @Resource
    private SmsOrderService smsOrderService;

    @RequestMapping("biz/smsOrderShow")
    public String smsOrderShow(@RequestParam("smsOrderId") Long smsOrderId, @RequestParam("bizId") Integer bizId, Model model) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	Result<SmsOrder> queryReuslt = smsOrderService.getSmsOrderById(smsOrderId);
	if (!queryReuslt.isSuccess()) {
	    model.addAttribute("errorMsg", queryReuslt.getResultMsg());
	    return "biz/bizOrdesmsOrderShowrShow";
	}

	model.addAttribute("smsOrder", queryReuslt.getModule());
	return "biz/smsOrderShow";
    }

    @RequestMapping("biz/smsSupplyShow")
    public String smsSupplyShow(@RequestParam("smsSupplyId") Long smsSupplyId, @RequestParam("bizId") Integer bizId, Model model) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	Result<SmsSupply> queryReuslt = smsOrderService.getSmsSupplyById(smsSupplyId);
	if (!queryReuslt.isSuccess()) {
	    model.addAttribute("errorMsg", queryReuslt.getResultMsg());
	    return "biz/smsSupplyShow";
	}

	model.addAttribute("smsSupply", queryReuslt.getModule());
	return "biz/smsSupplyShow";
    }
}
