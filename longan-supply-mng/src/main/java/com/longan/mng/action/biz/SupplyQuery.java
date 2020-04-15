package com.longan.mng.action.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.domain.BalanceQueryInfo;
import com.longan.client.remote.domain.CardChargeInfo;
import com.longan.client.remote.domain.CardCheck;
import com.longan.client.remote.domain.ChargeInfo;
import com.longan.client.remote.domain.MobileCheck;
import com.longan.client.remote.service.SupplyQueryService;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/supplyQuery")
public class SupplyQuery extends BaseController {
    private final static Long mcheck_unicom = Utils.getLong("mcheck.unicom");
    private final static Long mcheck_cmcc = Utils.getLong("mcheck.cmcc");
    private final static Long mcheck_telecom = Utils.getLong("mcheck.telecom");

    @Resource
    private SupplyQueryService supplyQueryService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @RequestMapping(params = "type=cardCheck")
    public void cardCheck(@RequestParam("stockId") Long stockId, Model model) {
	Result<CardCheck> result = null;
	model.addAttribute("result", result);
	model.addAttribute("stockId", stockId);
	try {
	    result = supplyQueryService.cardCheck(stockId);
	} catch (Exception e) {
	    logger.error("cardCheck 查询失败", e);
	    return;
	}
	model.addAttribute("result", result);
	model.addAttribute("stockId", stockId);
    }

    @RequestMapping(params = "type=cardChargeInfoQuery")
    public void cardChargeInfoQuery(@RequestParam("stockId") Long stockId, Model model) {
	Result<List<CardChargeInfo>> result = null;
	try {
	    result = supplyQueryService.cardChargeInfoQuery(stockId);
	} catch (Exception e) {
	    logger.error("cardInfoQuery 查询失败", e);
	    model.addAttribute("msg", "查询失败");
	    return;
	}

	String msg = "";
	if (result.isSuccess()) {
	    if (result.getModule() != null) {
		for (CardChargeInfo cardInfo : result.getModule()) {
		    msg = msg + cardInfo;
		}
	    }
	} else {
	    msg = msg + result.getResultMsg();
	}
	model.addAttribute("msg", msg);
	model.addAttribute("result", result);
	model.addAttribute("stockId", stockId);
    }

    @RequestMapping(params = "type=chargeQuery")
    public void chargeQuery(@RequestParam("supplyOrderId") Long supplyOrderId, Model model) {
	Result<ChargeInfo> result = new Result<ChargeInfo>();
	model.addAttribute("result", result);

	Result<SupplyOrder> supplyOrderQuery = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!supplyOrderQuery.isSuccess()) {
	    result.setResultMsg(supplyOrderQuery.getResultMsg());
	    return;
	}
	SupplyOrder supplyOrder = supplyOrderQuery.getModule();
	if (supplyOrder == null) {
	    result.setResultMsg("没有该供货单");
	    return;
	}

	try {
	    result = supplyQueryService.chargeInfoQuery(supplyOrder);
	} catch (Exception e) {
	    logger.error("chargeInfoQuery 查询失败", e);
	    return;
	}
	model.addAttribute("result", result);
    }

    @RequestMapping(params = "type=balanceQuery")
    public void balanceQuery(@RequestParam("userId") String userId, Model model) {
	Result<BalanceQueryInfo> result = new Result<BalanceQueryInfo>();
	model.addAttribute("result", result);
	try {
	    result = supplyQueryService.balanceQuery(userId);
	} catch (Exception e) {
	    logger.error("balanceQuery 查询失败", e);
	    return;
	}
	model.addAttribute("result", result);
    }

    @RequestMapping(params = "type=mobileCheck")
    public String mobileCheck(@RequestParam("supplyOrderId") Long supplyOrderId, Model model) {
	String returnUrl = "/biz/mobileCheck";
	Result<MobileCheck> result = new Result<MobileCheck>();
	model.addAttribute("result", result);

	Result<SupplyOrder> supplyOrderQuery = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!supplyOrderQuery.isSuccess()) {
	    result.setResultMsg(supplyOrderQuery.getResultMsg());
	    return returnUrl;
	}
	SupplyOrder supplyOrder = supplyOrderQuery.getModule();
	if (supplyOrder == null) {
	    result.setResultMsg("没有该供货单");
	    return returnUrl;
	}

	try {
	    Integer bizId = supplyOrder.getBizId();
	    Long supplyTraderId = supplyOrder.getSupplyTraderId();
	    if (bizId == Constants.BizInfo.CODE_PHONE_UNICOM) {
		if (mcheck_unicom != 0) {
		    supplyTraderId = mcheck_unicom;
		}
	    } else if (bizId == Constants.BizInfo.CODE_PHONE_MOBILE) {
		if (mcheck_cmcc != 0) {
		    supplyTraderId = mcheck_cmcc;
		}
	    } else if (bizId == Constants.BizInfo.CODE_PHONE_TELECOM) {
		if (mcheck_telecom != 0) {
		    supplyTraderId = mcheck_telecom;
		}
	    }

	    result = supplyQueryService.mobileCheck(supplyTraderId, supplyOrderId, supplyOrder.getItemUid(),
		    supplyOrder.getItemFacePrice());
	} catch (Exception e) {
	    logger.error("mobileCheck 查询失败", e);
	    return returnUrl;
	}
	model.addAttribute("result", result);
	return returnUrl;
    }
}
