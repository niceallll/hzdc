package com.longan.mng.action.biz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.ItemService;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.ItemPriceForm;
import com.longan.mng.form.ItemPriceListForm;

@Controller
public class ItemPriceDeal extends BaseController {
    @Resource
    private ItemService itemService;

    @Resource
    private Validator validator;

    @RequestMapping(value = "biz/itemPriceEdit", method = RequestMethod.GET)
    public String onAddIndex(@RequestParam("itemSupplyId") Long itemSupplyId, HttpSession session, Model model) {
	final String errorReturnUrl = "biz/queryItemPrice";
	Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(itemSupplyId);
	if (!itemSupplyResult.isSuccess() || itemSupplyResult.getModule() == null) {
	    super.alertError(model, itemSupplyResult.getResultMsg());
	    return errorReturnUrl;
	}
	ItemSupply itemSupply = itemSupplyResult.getModule();

	Result<Item> itemResult = itemService.getItem(itemSupply.getItemId());
	if (!itemResult.isSuccess() || itemResult.getModule() == null) {
	    super.alertError(model, itemResult.getResultMsg());
	    return errorReturnUrl;
	}
	Item item = itemResult.getModule();

	super.setItemArea(item);
	model.addAttribute("item", item);
	model.addAttribute("itemSupply", itemSupply);
	ItemPrice oldItemPrice = new ItemPrice();
	oldItemPrice.setId(itemSupply.getId());
	oldItemPrice.setItemId(item.getId());
	oldItemPrice.setItemFacePrice(item.getItemFacePrice());
	oldItemPrice.setItemSalesPrice(item.getItemSalesPrice());
	oldItemPrice.setItemSalesPrice2(item.getItemSalesPrice2());
	oldItemPrice.setItemSalesPrice3(item.getItemSalesPrice3());
	oldItemPrice.setItemSalesPrice4(item.getItemSalesPrice4());
	oldItemPrice.setItemCostPrice(itemSupply.getItemCostPrice());
	oldItemPrice.setItemDummyPrice(item.getItemDummyPrice());
	model.addAttribute("oldItemPrice", oldItemPrice);
	model.addAttribute("itemPriceForm", new ItemPriceForm());
	return "biz/itemPriceEdit";
    }

    @RequestMapping(value = "biz/itemPriceEdit", method = RequestMethod.POST)
    public String onAddEdit(@ModelAttribute("itemPriceForm") ItemPriceForm itemPriceForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnUrl = "biz/itemPriceEdit";
	if (itemPriceForm.getItemSupplyId() == null) {
	    FieldError error = new FieldError("itemPriceForm", "itemSupplyId", "供货商品编号不能为空");
	    bindingResult.addError(error);
	    return returnUrl;
	}
	logger.warn(userInfo.getUserName() + "执行商品调价操作 供货商品 id : " + itemPriceForm.getItemSupplyId());

	Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(Long.parseLong(itemPriceForm.getItemSupplyId()));
	if (!itemSupplyResult.isSuccess()) {
	    super.alertError(model, itemSupplyResult.getResultMsg());
	    return returnUrl;
	}
	ItemSupply itemSupply = itemSupplyResult.getModule();

	Result<Item> itemResult = itemService.getItem(itemSupply.getItemId());
	if (!itemResult.isSuccess() || itemResult.getModule() == null) {
	    super.alertError(model, itemResult.getResultMsg());
	    return returnUrl;
	}
	Item item = itemResult.getModule();

	super.setItemArea(item);
	model.addAttribute("item", item);
	model.addAttribute("itemSupply", itemSupply);
	ItemPrice oldItemPrice = new ItemPrice();
	oldItemPrice.setId(itemSupply.getId());
	oldItemPrice.setItemId(item.getId());
	oldItemPrice.setItemFacePrice(item.getItemFacePrice());
	oldItemPrice.setItemSalesPrice(item.getItemSalesPrice());
	oldItemPrice.setItemSalesPrice2(item.getItemSalesPrice2());
	oldItemPrice.setItemSalesPrice3(item.getItemSalesPrice3());
	oldItemPrice.setItemSalesPrice4(item.getItemSalesPrice4());
	oldItemPrice.setItemCostPrice(itemSupply.getItemCostPrice());
	oldItemPrice.setItemDummyPrice(item.getItemDummyPrice());
	model.addAttribute("oldItemPrice", oldItemPrice);

	validator.validate(itemPriceForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return returnUrl;
	}
	if (!item.getId().toString().equals(itemPriceForm.getItemId())) {
	    super.alertError(model, "商品编号和供货商品编号不匹配");
	    return returnUrl;
	}

	ItemPrice itemPrice = new ItemPrice();
	itemPrice.setId(Long.parseLong(itemPriceForm.getItemSupplyId()));
	itemPrice.setItemId(Integer.parseInt(itemPriceForm.getItemId()));
	itemPrice.setItemFacePrice(item.getItemFacePrice());
	setPrice(itemPriceForm, itemPrice);

	OperationLog operationLog = OperLogUtils.operationLogDeal(oldItemPrice, itemPrice, userInfo,
		getModuleNameFromSession(session), item.getBizId(), getIpFromSession(session));
	Result<Long> result = super.submitTaskForm(itemPriceForm, "itemPriceService", "adjustPrice", (Serializable) itemPrice,
		itemPrice.getClass().getName(), operationLog, userInfo, item.getBizId());
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return returnUrl;
	}

	if (itemPriceForm.isRealTimeCommit()) {
	    alertSuccess(model, "queryItemPrice.do?id=" + itemSupply.getId());
	} else {
	    alertSuccess(model, "../system/queryTask.do?id=" + result.getModule());
	}
	return returnUrl;
    }

    @RequestMapping(value = "biz/itemPriceListEdit", method = RequestMethod.GET)
    public String onListAddIndex(@RequestParam("itemSupplyIdList") String itemSupplyIdList, HttpSession session, Model model) {
	final String errorReturnUrl = "biz/queryItemPrice";
	String[] strs = itemSupplyIdList.split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "供货商品列表不能为空");
	    return errorReturnUrl;
	}
	List<Long> list = new ArrayList<Long>();
	for (int i = 0; i < strs.length; i++) {
	    if (!StringUtils.isNumeric(strs[i])) {
		super.alertError(model, "供货商品编号格式不对");
		return errorReturnUrl;
	    }
	    list.add(Long.parseLong(strs[i]));
	}

	String itemNameList = "";
	for (Long itemSupplyId : list) {
	    Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(itemSupplyId);
	    if (!itemSupplyResult.isSuccess() || itemSupplyResult.getModule() == null) {
		break;
	    }
	    ItemSupply itemSupply = itemSupplyResult.getModule();
	    Result<Item> itemResult = itemService.getItem(itemSupply.getItemId());
	    if (!itemResult.isSuccess() || itemResult.getModule() == null) {
		break;
	    }
	    Item item = itemResult.getModule();
	    itemNameList = itemNameList + item.getItemName() + "(" + itemSupply.getSupplyTraderName() + ")" + " 、";
	}
	model.addAttribute("itemNameList", itemNameList);
	model.addAttribute("itemSupplyIdList", itemSupplyIdList);
	model.addAttribute("itemPriceListForm", new ItemPriceListForm());
	return "biz/itemPriceListEdit";
    }

    @RequestMapping(value = "biz/itemPriceListEdit", method = RequestMethod.POST)
    public String onListAddEdit(@ModelAttribute("itemPriceListForm") ItemPriceListForm itemPriceListForm,
	    BindingResult bindingResult, HttpSession session, Model model) {
	final String returnUrl = "biz/itemPriceListEdit";
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行商品调价操作 供货商品编号列表 : " + itemPriceListForm.getItemSupplyIdList());

	String itemSupplyIdList = itemPriceListForm.getItemSupplyIdList();
	model.addAttribute("itemSupplyIdList", itemSupplyIdList);
	String[] strs = itemSupplyIdList.split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}
	List<Long> list = new ArrayList<Long>();
	for (int i = 0; i < strs.length; i++) {
	    if (!StringUtils.isNumeric(strs[i])) {
		super.alertError(model, "供货商品编号格式不对");
		return returnUrl;
	    }
	    list.add(Long.parseLong(strs[i]));
	}
	List<ItemPrice> itemPriceList = new ArrayList<ItemPrice>();
	String itemNameList = "";
	for (Long itemSupplyId : list) {
	    Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(itemSupplyId);
	    if (!itemSupplyResult.isSuccess() || itemSupplyResult.getModule() == null) {
		break;
	    }
	    ItemSupply itemSupply = itemSupplyResult.getModule();

	    Result<Item> itemResult = itemService.getItem(itemSupply.getItemId());
	    if (!itemResult.isSuccess() || itemResult.getModule() == null) {
		break;
	    }
	    Item item = itemResult.getModule();

	    ItemPrice itemPrice = new ItemPrice();
	    itemPrice.setId(itemSupply.getId());
	    itemPrice.setItemId(item.getId());
	    itemPrice.setItemFacePrice(item.getItemFacePrice());
	    itemPriceList.add(itemPrice);
	    itemNameList = itemNameList + item.getItemName() + "(" + itemSupply.getSupplyTraderName() + ")";
	}

	model.addAttribute("itemNameList", itemNameList);
	validator.validate(itemPriceListForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return returnUrl;
	}

	if (StringUtils.isEmpty(itemPriceListForm.getItemCostPriceDiscount())
		&& StringUtils.isEmpty(itemPriceListForm.getItemSalesPriceDiscount())
		&& StringUtils.isEmpty(itemPriceListForm.getItemSalesPrice2Discount())
		&& StringUtils.isEmpty(itemPriceListForm.getItemSalesPrice3Discount())
		&& StringUtils.isEmpty(itemPriceListForm.getItemSalesPrice4Discount())
		&& StringUtils.isEmpty(itemPriceListForm.getItemDummyPriceDiscount())) {
	    super.alertError(model, "必须填写需要修改的价格折扣");
	    return returnUrl;
	}

	// 设置价格
	for (ItemPrice itemPrice : itemPriceList) {
	    setPrice(itemPriceListForm, itemPrice);
	}

	String description = itemPriceListForm.toString();
	OperationLog operationLog = OperLogUtils.operationLogDeal(description, userInfo, getModuleNameFromSession(session), null,
		getIpFromSession(session), Constants.OperationLog.TYPE_UPDATE);
	Result<Long> result = super.submitTaskForm(itemPriceListForm, "itemPriceService", "batchAdjustPrice",
		(Serializable) itemPriceList, "java.util.List", operationLog, userInfo, null);
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return returnUrl;
	}

	if (itemPriceListForm.isRealTimeCommit()) {
	    alertSuccess(model, "queryItemPrice.do");
	} else {
	    alertSuccess(model, "../system/queryTask.do?id=" + result.getModule());
	}
	return returnUrl;
    }

    private void setPrice(ItemPriceForm itemPriceForm, ItemPrice itemPrice) {
	if (itemPrice == null || itemPriceForm == null || itemPrice.getItemFacePrice() == null) {
	    return;
	}
	Integer itemFacePrice = itemPrice.getItemFacePrice();
	if (StringUtils.isNotBlank(itemPriceForm.getItemSalesPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemSalesPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceForm.getItemSalesPrice2Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemSalesPrice2Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice2(price);
	    }

	}
	if (StringUtils.isNotBlank(itemPriceForm.getItemSalesPrice3Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemSalesPrice3Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice3(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceForm.getItemSalesPrice4Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemSalesPrice4Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice4(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceForm.getItemCostPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemCostPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemCostPrice(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceForm.getItemDummyPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceForm.getItemDummyPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemDummyPrice(price);
	    }
	}
    }

    private void setPrice(ItemPriceListForm itemPriceListForm, ItemPrice itemPrice) {
	if (itemPrice == null || itemPriceListForm == null || itemPrice.getItemFacePrice() == null) {
	    return;
	}
	Integer itemFacePrice = itemPrice.getItemFacePrice();
	if (StringUtils.isNotBlank(itemPriceListForm.getItemSalesPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemSalesPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceListForm.getItemSalesPrice2Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemSalesPrice2Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice2(price);
	    }

	}
	if (StringUtils.isNotBlank(itemPriceListForm.getItemSalesPrice3Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemSalesPrice3Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice3(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceListForm.getItemSalesPrice4Discount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemSalesPrice4Discount());
	    if (price >= 0) {
		itemPrice.setItemSalesPrice4(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceListForm.getItemCostPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemCostPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemCostPrice(price);
	    }
	}
	if (StringUtils.isNotBlank(itemPriceListForm.getItemDummyPriceDiscount())) {
	    Integer price = getPrice(itemFacePrice, itemPriceListForm.getItemDummyPriceDiscount());
	    if (price >= 0) {
		itemPrice.setItemDummyPrice(price);
	    }
	}
    }

    private static Integer getPrice(Integer itemFacePrice, String discount) {
	if (itemFacePrice == null || StringUtils.isEmpty(discount)) {
	    return null;
	}
	return BigDecimalUtils.multDiscount(itemFacePrice.toString(), discount);
    }

    @ModelAttribute("commitTypeList")
    public Map<String, String> commitTypeList() {
	Map<String, String> map = new HashMap<String, String>(2);
	map.put(Constants.Task.COMMIT_TYPE_REAL_TIME + "", Constants.Task.COMMIT_TYPE_REAL_TIME_DESC);
	map.put(Constants.Task.COMMIT_TYPE_TASK + "", Constants.Task.COMMIT_TYPE_TASK_DESC);
	return map;
    }

    @Override
	@InitBinder
    public void formInitBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
