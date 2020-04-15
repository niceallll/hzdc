package com.longan.mng.action.user;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longan.biz.core.AcctService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.TraderInfoService;
import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.ItemSales;
import com.longan.biz.dataobject.ItemSalesQuery;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.response.AjaxResponse;
import com.longan.mng.form.ItemSalesForm;

@Controller
public class DownStreamDeal extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private AcctService acctService;

    @Resource
    private TraderInfoService traderInfoService;

    @Resource
    private ItemService itemService;

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=getUnit", method = RequestMethod.GET)
    public void onEditIndex(@RequestParam("id") Long id, Model model) {
	Result<UserInfo> result = userService.getDownStreamById(id);
	if (result.isSuccess()) {
	    UserInfo userInfo = result.getModule();
	    if (userInfo != null) {
		model.addAttribute("userInfo", userInfo);
	    } else {
		super.alertError(model, "没有该用户");
	    }
	} else {
	    super.alertError(model, result.getResultMsg());
	}
    }

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=getUnit", method = RequestMethod.POST)
    public void onPostEdit(@RequestParam("id") Long id, @ModelAttribute("salesPrice") String salesPrice, Model model,
	    HttpSession session) {
	UserInfo userInfom = super.getUserInfo(session);
	if (StringUtils.isEmpty(salesPrice)) {
	    super.alertError(model, "必须选择一个销售价格");
	    return;
	}
	if (id == null) {
	    super.alertError(model, "用户账户信息为空");
	    return;
	}
	Result<AcctInfo> acctInfoResult = acctService.getAcctInfo(id);
	AcctInfo older;
	if (!acctInfoResult.isSuccess()) {
	    super.alertError(model, acctInfoResult.getResultMsg());
	    return;
	} else {
	    AcctInfo acctInfo = acctInfoResult.getModule();
	    if (acctInfo == null) {
		super.alertError(model, "用户账户信息为空");
		return;
	    }
	    older = acctInfo;
	}
	AcctInfo acctInfo = new AcctInfo();
	acctInfo.setId(id);
	acctInfo.setSalesPrice(Integer.parseInt(salesPrice));
	Result<Boolean> result = acctService.updateAcct(acctInfo);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, acctInfo, userInfom, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryDownStreamInfo.do?type=2");
	    return;
	} else {
	    super.alertError(model, result.getResultMsg());
	    return;
	}
    }

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=getList")
    public String getItemPriceList(ItemSalesQuery itemSalesQuery, Model model) {
	if (itemSalesQuery.getUserId() == null) {
	    model.addAttribute("errorMsg", "用户编号不能为空");
	} else if (itemSalesQuery.getBizId() == null) {
	    model.addAttribute("errorMsg", "业务类型不能为空");
	} else {
	    Result<List<ItemSales>> result = itemService.queryItemPriceList(itemSalesQuery);
	    if (result.isSuccess()) {
		model.addAttribute("itemPriceList", result.getModule());
	    } else {
		model.addAttribute("errorMsg", result.getResultMsg());
	    }
	}
	return "user/itemPriceList";
    }

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=open")
    public @ResponseBody
    AjaxResponse openItemPrice(ItemSalesForm itemSalesForm, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	AjaxResponse response = new AjaxResponse();
	Integer itemSalesPrice = BigDecimalUtils.multInteger(itemSalesForm.getItemSalesPrice());
	if (itemSalesPrice <= 0) {
	    response.setErrorMsg("商品价格必须大于零");
	    return response;
	}

	ItemSales itemSales = new ItemSales();
	itemSales.setItemSalesPrice(itemSalesPrice);
	itemSales.setStatus(Constants.ItemSales.STATUS_OPEN);
	if (StringUtils.isBlank(itemSalesForm.getId())) {
	    itemSales.setItemId(Integer.parseInt(itemSalesForm.getItemId()));
	    itemSales.setUserId(Long.parseLong(itemSalesForm.getUserId()));
	    Result<Boolean> result = itemService.createItemPrice(itemSales);
	    if (result.isSuccess()) {
		response.setModule(itemSales.getId());
		response.setSuccess();

		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
		OperationLog operationLog = OperLogUtils.operationLogDeal(null, itemSales, userInfo, "商品售价开通", null,
			map.get("loginIp"));
		operationLogService.createOperationLog(operationLog);
	    } else {
		response.setErrorMsg(result.getResultMsg());
	    }
	} else {
	    Long itemPriceId = Long.parseLong(itemSalesForm.getId());
	    itemSales.setId(itemPriceId);
	    Result<Boolean> result = itemService.updateItemPrice(itemSales);
	    if (result.isSuccess()) {
		response.setModule(itemPriceId);
		response.setSuccess();

		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
		OperationLog operationLog = OperLogUtils.operationLogDeal(null, itemSales, userInfo, "商品售价修改", null,
			map.get("loginIp"));
		operationLogService.createOperationLog(operationLog);
	    } else {
		response.setErrorMsg(result.getResultMsg());
	    }
	}
	return response;
    }

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=close")
    public @ResponseBody
    AjaxResponse closeItemPrice(Long id) {
	AjaxResponse response = new AjaxResponse();
	if (id == null) {
	    response.setErrorMsg("商品价格编号不能是空");
	    return response;
	}

	ItemSales itemSales = new ItemSales();
	itemSales.setId(id);
	itemSales.setStatus(Constants.ItemSales.STATUS_CLOSE);
	Result<Boolean> result = itemService.updateItemPrice(itemSales);
	if (result.isSuccess()) {
	    response.setSuccess();
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(value = "user/downStreamSalePriceEdit", params = "type=del")
    public @ResponseBody
    AjaxResponse delItemPrice(Long id) {
	AjaxResponse response = new AjaxResponse();
	if (id == null) {
	    response.setErrorMsg("商品价格编号不能是空");
	    return response;
	}

	ItemSales itemSales = new ItemSales();
	itemSales.setId(id);
	itemSales.setStatus(Constants.ItemSales.STATUS_DEL);
	Result<Boolean> result = itemService.updateItemPrice(itemSales);
	if (result.isSuccess()) {
	    response.setSuccess();
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @ModelAttribute("salesPriceList")
    public Map<String, String> salesPriceList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.AcctInfo.SALES_PRICE_1 + "", Constants.AcctInfo.SALES_PRICE_1_DESC);
	map.put(Constants.AcctInfo.SALES_PRICE_2 + "", Constants.AcctInfo.SALES_PRICE_2_DESC);
	map.put(Constants.AcctInfo.SALES_PRICE_3 + "", Constants.AcctInfo.SALES_PRICE_3_DESC);
	map.put(Constants.AcctInfo.SALES_PRICE_4 + "", Constants.AcctInfo.SALES_PRICE_4_DESC);
	return map;
    }

    @RequestMapping(value = "user/privateKeyReset", method = RequestMethod.GET)
    public String onPrivateKeyResetIndex(@RequestParam("id") Long id, Model model, HttpSession session) {
	UserInfo userInfom = super.getUserInfo(session);
	Result<TraderInfo> traderInfoResult = traderInfoService.getTraderInfoByUserId(id);
	TraderInfo older;
	if (!traderInfoResult.isSuccess()) {
	    alertError(model, traderInfoResult.getResultMsg());
	    return "user/queryDownStreamInfo";
	} else {
	    TraderInfo traderInfo = traderInfoResult.getModule();
	    if (traderInfo == null) {
		alertError(model, "商户信息为空");
		return "user/queryDownStreamInfo";
	    }
	    older = traderInfo;
	}
	TraderInfo newer = older;
	String show = createPrivateKey();
	if (StringUtils.isEmpty(show)) {
	    alertError(model, "重置下游密钥失败!");
	    return "user/queryDownStreamInfo";
	}
	newer.setDownstreamKey(show);
	Result<Boolean> result = traderInfoService.updateTraderInfo(newer);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, newer, userInfom, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    alertSuccess(model, "queryDownStreamInfo.do?type=2");
	    return "user/queryDownStreamInfo";
	} else {
	    alertError(model, result.getResultMsg());
	    return "user/queryDownStreamInfo";
	}
    }

    @ModelAttribute("bizList")
    public Map<Integer, String> bizList() {
	Map<Integer, String> map = Constants.BIZ_MAP;
	return map;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}
