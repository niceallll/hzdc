package com.longan.mng.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.AcctService;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("main")
public class Main extends BaseController {
    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private AcctService acctService;

    @RequestMapping(method = RequestMethod.GET)
    public String onRequest(HttpSession session, Model model) {
	UserInfo userInfo = getUserInfo(session);
	model.addAttribute("userInfo", userInfo);

	if (userInfo.isDownStreamUser()) {
	    Result<AcctInfo> result = acctService.getAcctInfo(userInfo.getAcctId());
	    if (result.isSuccess()) {
		AcctInfo acctInfo = result.getModule();
		model.addAttribute("acctInfo", acctInfo);
	    }
	}

	Set<String> catalogSet = localCachedService.getAuthCatalogByUserId(userInfo.getId());
	if (catalogSet != null) {
	    model.addAttribute("hasFlowAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_FLOW));
	    model.addAttribute("hasTelecomFlowAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_TELECOMFLOW));
	    model.addAttribute("hasMobileFlowAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_MOBILEFLOW));
	    model.addAttribute("hasUnicomAyncAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_UNICOMAYNC));
	    model.addAttribute("hasMobileAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_MOBILE));
	    model.addAttribute("hasTelecomAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_TELECOM));
	    model.addAttribute("hasMoneyAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_MONEY));
	    model.addAttribute("hasUserAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_USER));
	    model.addAttribute("hasStockAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_STOCK));
	    model.addAttribute("hasSystemOperationAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_SYSTEMOPERATION));
	    model.addAttribute("hasPriceAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_PRICE));
	    model.addAttribute("hasStatisticAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_STATISTIC));
	    model.addAttribute("hasQbAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_QB));
	    model.addAttribute("hasVoucherAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_VOUCHER));
	    model.addAttribute("hasCnpcAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_CNPC));
	    model.addAttribute("hasSinopecAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_SINOPEC));
	    model.addAttribute("hasSmsAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_SMS));
	    model.addAttribute("hasManualAuth", catalogSet.contains(Constants.AdminCatalog.CATALOG_MANUAL));
	}

	Set<Integer> bizSet = localCachedService.getAuthBizByUserId(userInfo.getId());
	if (bizSet != null && bizSet.size() > 0) {
	    List<Integer> bizList = new ArrayList<Integer>(bizSet);
	    model.addAttribute("defaultBizId", bizList.get(0));
	}

	if (com.longan.mng.utils.Constants.isProduct()) {
	    model.addAttribute("isProduct", true);
	}
	return "main";
    }
}
