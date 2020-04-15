package com.longan.mng.action.biz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.TraderInfoService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyQuery;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.ItemSupplyForm;
import com.longan.mng.form.ItemSupplyListEditForm;
import com.longan.mng.form.ItemSupplyListForm;
import com.longan.mng.form.UpDownForm;

@Controller
public class ItemSupplyDeal extends BaseController {
    @Resource
    private ItemService itemService;

    @Resource
    private Validator validator;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private TraderInfoService traderInfoService;

    @RequestMapping(value = "biz/itemSupplyDeal", params = "type=up")
    public String onRequestUp(@RequestParam("id") Long id, @RequestParam("bizId") Integer bizId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(id);
	ItemSupply older;
	if (!itemSupplyResult.isSuccess()) {
	    super.alertError(model, itemSupplyResult.getResultMsg());
	    return "biz/queryItemSupply";
	} else {
	    if (itemSupplyResult.getModule() == null) {
		super.alertError(model, "供货商品为空");
		return "biz/queryItemSupply";
	    }
	    older = itemSupplyResult.getModule();
	}
	logger.warn(userInfo.getUserName() + "执行上架操作 供货商品 id : " + id);
	Result<Boolean> result = itemService.upItemSupply(id);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, itemService.getItemSupply(id).getModule(), userInfo,
		    map.get("moduleName") + "(上架)", older.getBizId(), map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    alertSuccess(model, "queryItemSupply.do?bizId=" + bizId + "&id=" + id);
	} else {
	    alertError(model, result.getResultMsg());
	}

	return "biz/queryItemSupply";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "type=down")
    public String onRequestDown(@RequestParam("id") Long id, @RequestParam("bizId") Integer bizId, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	ItemSupply older = itemService.getItemSupply(id).getModule();
	logger.warn(userInfo.getUserName() + "执行下架操作 供货商品 id : " + id);
	Result<Boolean> result = itemService.downItemSupply(id);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, itemService.getItemSupply(id).getModule(), userInfo,
		    map.get("moduleName") + "(下架)", older.getBizId(), map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    alertSuccess(model, "queryItemSupply.do?bizId=" + bizId + "&id=" + id);
	} else {
	    alertError(model, result.getResultMsg());
	}
	return "biz/queryItemSupply";
    }

    @RequestMapping(value = "biz/itemSupplyAdd", method = RequestMethod.GET)
    public String onAddIndex(@RequestParam("bizId") Integer bizId, @RequestParam("itemId") Integer itemId, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);
	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	ItemSupplyForm itemSupplyForm = new ItemSupplyForm();
	model.addAttribute("itemSupplyForm", itemSupplyForm);
	Result<Item> result = itemService.getItem(itemId);
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/itemSupplyAdd";
	}
	Item item = result.getModule();
	if (item == null) {
	    super.alertError(model, "没有该商品");
	    return "biz/itemSupplyAdd";
	}
	model.addAttribute("item", item);
	return "biz/itemSupplyAdd";
    }
	//关联供货
    @RequestMapping(value = "biz/itemSupplyAdd", method = RequestMethod.POST)
    public String onPostAdd(@ModelAttribute("itemSupplyForm") ItemSupplyForm itemSupplyForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(Integer.parseInt(itemSupplyForm.getBizId())));
	model.addAttribute("bizId", itemSupplyForm.getBizId());
	//
	Result<Item> itemResult = itemService.getItem(Integer.parseInt(itemSupplyForm.getItemId()));
	if (!itemResult.isSuccess()) {
	    super.alertError(model, itemResult.getResultMsg());
	    return "biz/itemSupplyAdd";
	}
	Item item = itemResult.getModule();
	if (item == null) {
	    super.alertError(model, "没有该商品");
	    return "biz/itemSupplyAdd";
	}
	model.addAttribute("item", item);

	validator.validate(itemSupplyForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return "biz/itemSupplyAdd";
	}
	//损失判断
	if ((Constants.ItemSupply.LOSSTYPE_CAN + "").equals(itemSupplyForm.getLossType())) {
	    if (StringUtils.isEmpty(itemSupplyForm.getLossTime())) {
		List<ObjectError> allErrors = new ArrayList<ObjectError>();
		allErrors.add(new FieldError("itemSupplyForm", "lossTime", "损失笔数不能为空"));
		model.addAttribute("errorList", allErrors);
		return "biz/itemSupplyAdd";
	    }
	}

	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(itemSupplyForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	// 对supplyAreaList做特殊验证
	if (String.valueOf(Constants.Item.SALE_TYPE_AREA).equals(itemSupplyForm.getSupplyAreaType())
		&& ((itemSupplyForm.getSupplyAreaList() == null || itemSupplyForm.getSupplyAreaList().isEmpty()))) {
	    FieldError error = new FieldError("itemSupplyForm", "supplyAreaList", "至少选择一个供货省域");
	    bindingResult.addError(error);
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return "biz/itemSupplyAdd";
	}
	//供货
	ItemSupply itemSupply = formToItemSupply(itemSupplyForm);
	Result<Boolean> result = itemService.createItemSupply(itemSupply);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(null, itemSupply, userInfo, map.get("moduleName"),
		    itemSupply.getBizId(), map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryItemSupply.do?bizId=" + itemSupplyForm.getBizId() + "&id=" + itemSupply.getId());
	} else {
	    super.alertError(model, result.getResultMsg());
	}
	return "biz/itemSupplyAdd";
    }

    @RequestMapping(value = "biz/itemSupplyAdd", params = "requestType=batchAddIndex")
    public String onListAddIndex(@RequestParam("bizId") Integer bizId, @RequestParam("ids") String ids, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);
	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}
	ItemSupplyListForm itemSupplyListForm = new ItemSupplyListForm();
	itemSupplyListForm.setIds(ids);
	itemSupplyListForm.setRequestType("batchAdd");
	model.addAttribute("itemSupplyListForm", itemSupplyListForm);

	return "biz/itemSupplyListAdd";
    }

    @RequestMapping(value = "biz/itemSupplyAdd", params = "requestType=conditionAddIndex", method = RequestMethod.POST)
    public String onRequestConditionAddIndex(@ModelAttribute("itemQuery") ItemQuery itemQuery,
	    @RequestParam("bizId") Integer bizId, @RequestParam("requestType") String requestType, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	itemQuery.setPageSize(10000);
	itemQuery.setCurrentPage(1);
	Result<List<Item>> result = itemService.queryItemList(itemQuery);
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/queryItem";
	}

	StringBuffer ids = new StringBuffer("");
	StringBuffer names = new StringBuffer("");
	List<Item> list = result.getModule();
	if (list == null || list.isEmpty()) {
	    super.alertError(model, "商品列表不能为空");
	    return "biz/queryItem";
	}
	for (int i = 0; i < list.size(); i++) {
	    ids.append(list.get(i).getId());
	    names.append(list.get(i).getItemName());
	    if (i != (list.size() - 1)) {
		ids.append(",");
		names.append(" ，");
	    }
	}
	ItemSupplyListForm itemSupplyListForm = new ItemSupplyListForm();
	itemSupplyListForm.setIds(ids.toString());
	itemSupplyListForm.setNames(names.toString());
	itemSupplyListForm.setRequestType("batchAdd");
	model.addAttribute("itemSupplyListForm", itemSupplyListForm);
	return "biz/itemSupplyListAdd";
    }

    @RequestMapping(value = "biz/itemSupplyAdd", params = "requestType=batchAdd", method = RequestMethod.POST)
    public String onListAdd(@ModelAttribute("itemSupplyListForm") ItemSupplyListForm itemSupplyListForm,
	    BindingResult bindingResult, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnUrl = "biz/itemSupplyListAdd";

	validator.validate(itemSupplyListForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(itemSupplyListForm.getBizId()));
	    model.addAttribute("bizId", itemSupplyListForm.getBizId());
	    return returnUrl;
	}

	logger.warn(userInfo.getUserName() + "执行批量关联供货，  商品 ids : " + itemSupplyListForm.getIds());

	model.addAttribute("bizName", Constants.BIZ_MAP.get(itemSupplyListForm.getBizId()));
	model.addAttribute("bizId", itemSupplyListForm.getBizId());

	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(itemSupplyListForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	String[] strs = itemSupplyListForm.getIds().split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "商品列表不能为空");
	    return returnUrl;
	}

	List<Integer> list = new ArrayList<Integer>();
	for (String str : strs) {
	    if (StringUtils.isNumeric(str)) {
		list.add(Integer.parseInt(str));
	    }
	}
	if (list.isEmpty()) {
	    super.alertError(model, "商品列表不能为空");
	    return returnUrl;
	}
	int i = 0;
	Result<Boolean> result = null;
	for (Integer itemId : list) {
	    ItemSupply itemSupply = formToItemSupply(itemSupplyListForm, itemId);
	    result = itemService.createItemSupply(itemSupply);
	    if (result.isSuccess()) {
		i++;
	    }
	}

	if (i > 0) {
	    String description = itemSupplyListForm.toString();
	    OperationLog operationLog = OperLogUtils.operationLogDeal(description, userInfo, getModuleNameFromSession(session),
		    null, getIpFromSession(session), Constants.OperationLog.TYPE_ADD);
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryItemSupply.do?bizId=" + itemSupplyListForm.getBizId());
	} else {
	    super.alertError(model, "操作失败,可能已经商品已经关联该供货");
	}

	return returnUrl;
    }

    @RequestMapping(value = "biz/itemSupplyEdit", params = "requestType=batchEditIndex")
    public String onListEditIndex(@RequestParam("bizId") Integer bizId, @RequestParam("ids") String ids, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);
	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}
	ItemSupplyListEditForm itemSupplyListEditForm = new ItemSupplyListEditForm();
	itemSupplyListEditForm.setIds(ids.toString());
	itemSupplyListEditForm.setRequestType("batchEdit");
	model.addAttribute("itemSupplyListEditForm", itemSupplyListEditForm);

	return "biz/itemSupplyListEdit";
    }

    @RequestMapping(value = "biz/itemSupplyEdit", params = "requestType=conditionEditIndex", method = RequestMethod.POST)
    public String onRequestConditionEditIndex(@ModelAttribute("itemSupplyQuery") ItemSupplyQuery itemSupplyQuery,
	    @RequestParam("bizId") Integer bizId, @RequestParam("requestType") String requestType, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	itemSupplyQuery.setPageSize(10000);
	itemSupplyQuery.setCurrentPage(1);
	Result<List<ItemSupply>> result = itemService.queryItemSupplyPage(itemSupplyQuery);

	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/queryItemSupply";
	}

	StringBuffer ids = new StringBuffer("");
	StringBuffer names = new StringBuffer("");
	List<ItemSupply> list = result.getModule();
	if (list == null || list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return "biz/queryItemSupply";
	}
	for (int i = 0; i < list.size(); i++) {
	    ItemSupply itemSupply = list.get(i);
	    ids.append(itemSupply.getId());
	    names.append(itemSupply.getItemName());
	    UserInfo traderInfo = localCachedService.getUserInfo(itemSupply.getSupplyTraderId());
	    if (traderInfo != null) {
		names.append("(").append(traderInfo.getUserName()).append(")");
	    }
	    if (i != (list.size() - 1)) {
		ids.append(",");
		names.append(" ，");
	    }
	}

	ItemSupplyListEditForm itemSupplyListEditForm = new ItemSupplyListEditForm();
	itemSupplyListEditForm.setIds(ids.toString());
	itemSupplyListEditForm.setNames(names.toString());
	itemSupplyListEditForm.setRequestType("batchEdit");
	model.addAttribute("itemSupplyListEditForm", itemSupplyListEditForm);
	return "biz/itemSupplyListEdit";
    }

    @RequestMapping(value = "biz/itemSupplyEdit", params = "requestType=batchEdit", method = RequestMethod.POST)
    public String onListEdit(@ModelAttribute("itemSupplyListEditForm") ItemSupplyListEditForm itemSupplyListEditForm,
	    BindingResult bindingResult, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnUrl = "biz/itemSupplyListEdit";

	validator.validate(itemSupplyListEditForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(itemSupplyListEditForm.getBizId()));
	    model.addAttribute("bizId", itemSupplyListEditForm.getBizId());
	    return returnUrl;
	}

	logger.warn(userInfo.getUserName() + "执行批量修改供货，  供货商品 ids : " + itemSupplyListEditForm.getIds());

	model.addAttribute("bizName", Constants.BIZ_MAP.get(itemSupplyListEditForm.getBizId()));
	model.addAttribute("bizId", itemSupplyListEditForm.getBizId());

	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(itemSupplyListEditForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	String[] strs = itemSupplyListEditForm.getIds().split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}

	List<Long> list = new ArrayList<Long>();
	for (String str : strs) {
	    if (StringUtils.isNumeric(str)) {
		list.add(Long.parseLong(str));
	    }
	}
	if (list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}
	int i = 0;
	Result<Boolean> result = null;
	for (Long itemSupplyId : list) {
	    ItemSupply itemSupply = formToItemSupplyEdit(itemSupplyListEditForm, itemSupplyId);
	    result = itemService.updateItemSupply(itemSupply);
	    if (result.isSuccess()) {
		i++;
	    }
	}

	if (i > 0) {
	    String description = itemSupplyListEditForm.toString();
	    OperationLog operationLog = OperLogUtils.operationLogDeal(description, userInfo, getModuleNameFromSession(session),
		    null, getIpFromSession(session), Constants.OperationLog.TYPE_UPDATE);
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryItemSupply.do?bizId=" + itemSupplyListEditForm.getBizId());
	} else {
	    super.alertError(model, "操作失败");
	}

	return returnUrl;
    }

    @RequestMapping(value = "biz/itemSupplyEdit", method = RequestMethod.GET)
    public String onEditIndex(@RequestParam("id") long id, @RequestParam("bizId") int bizId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	Result<ItemSupply> result = itemService.getItemSupply(id);
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/itemSupplyEdit";
	}
	ItemSupply itemSupply = result.getModule();
	if (itemSupply == null) {
	    super.alertError(model, "没有该供货商品");
	    return "biz/itemSupplyEdit";
	}
	model.addAttribute("itemSupply", itemSupply);

	Result<Item> itemResult = itemService.getItem(itemSupply.getItemId());
	if (!itemResult.isSuccess()) {
	    super.alertError(model, itemResult.getResultMsg());
	    return "biz/itemSupplyAdd";
	}
	Item item = itemResult.getModule();
	if (item == null) {
	    super.alertError(model, "没有该商品");
	    return "biz/itemSupplyAdd";
	}
	model.addAttribute("item", item);

	return "biz/itemSupplyEdit";
    }

    @RequestMapping(value = "biz/itemSupplyEdit", method = RequestMethod.POST)
    public String onPostEdit(@ModelAttribute("itemSupplyForm") ItemSupplyForm itemSupplyForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(Integer.parseInt(itemSupplyForm.getBizId())));
	model.addAttribute("bizId", itemSupplyForm.getBizId());

	if (itemSupplyForm.getId() == null) {
	    super.alertError(model, "供货商品编号不能为空");
	    return "biz/itemSupplyEdit";
	}
	Result<ItemSupply> itemSupplyResult = itemService.getItemSupply(Long.parseLong(itemSupplyForm.getId()));
	if (!itemSupplyResult.isSuccess()) {
	    super.alertError(model, itemSupplyResult.getResultMsg());
	    return "biz/itemSupplyEdit";
	}
	if (itemSupplyResult.getModule() == null) {
	    super.alertError(model, "没有该供货商品");
	    return "biz/itemSupplyEdit";
	}
	ItemSupply older = itemSupplyResult.getModule();
	model.addAttribute("itemSupply", itemSupplyResult.getModule());

	Result<Item> itemResult = itemService.getItem(itemSupplyResult.getModule().getItemId());
	if (!itemResult.isSuccess()) {
	    super.alertError(model, itemResult.getResultMsg());
	    return "biz/itemSupplyAdd";
	}
	Item item = itemResult.getModule();
	if (item == null) {
	    super.alertError(model, "没有该商品");
	    return "biz/itemSupplyAdd";
	}
	model.addAttribute("item", item);

	validator.validate(itemSupplyForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return "biz/itemSupplyEdit";
	}
	if ((Constants.ItemSupply.LOSSTYPE_CAN + "").equals(itemSupplyForm.getLossType())) {
	    if (StringUtils.isEmpty(itemSupplyForm.getLossTime())) {
		List<ObjectError> allErrors = new ArrayList<ObjectError>();
		allErrors.add(new FieldError("itemSupplyForm", "lossTime", "损失笔数不能为空"));
		model.addAttribute("errorList", allErrors);
		return "biz/itemSupplyEdit";
	    }
	}
	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(itemSupplyForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}
	//请求域中值存取
	ItemSupply itemSupply = formToItemSupply(itemSupplyForm);
	Result<Boolean> result = itemService.updateItemSupply(itemSupply);
	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/itemSupplyEdit";
	}
	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(older, itemSupply, userInfo, map.get("moduleName"),
		itemSupply.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	super.alertSuccess(model, "queryItemSupply.do?bizId=" + itemSupplyForm.getBizId() + "&id=" + itemSupply.getId());
	return "biz/itemSupplyEdit";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=batchDownIndex")
    public String onRequestBatchDownIndex(@RequestParam("ids") String ids, @RequestParam("bizId") Integer bizId,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	UpDownForm upDownForm = new UpDownForm();
	upDownForm.setIds(ids);
	upDownForm.setUrl("itemSupplyDeal.do");
	upDownForm.setRequestType("batchDown");
	upDownForm.setTypeDown();
	upDownForm.setModuleName("供货商品");
	upDownForm.setBizId(bizId + "");

	model.addAttribute("upDownForm", upDownForm);
	return "biz/batchUpDown";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=conditionDownIndex", method = RequestMethod.POST)
    public String onRequestConditionDownIndex(@ModelAttribute("itemSupplyQuery") ItemSupplyQuery itemSupplyQuery,
	    @RequestParam("bizId") Integer bizId, @RequestParam("requestType") String requestType, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	itemSupplyQuery.setPageSize(10000);
	itemSupplyQuery.setCurrentPage(1);
	Result<List<ItemSupply>> result = itemService.queryItemSupplyPage(itemSupplyQuery);

	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/queryItemSupply";
	}

	StringBuffer ids = new StringBuffer("");
	StringBuffer names = new StringBuffer("");
	List<ItemSupply> list = result.getModule();
	if (list == null || list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return "biz/queryItemSupply";
	}
	for (int i = 0; i < list.size(); i++) {
	    ids.append(list.get(i).getId());
	    names.append(list.get(i).getItemName());
	    if (list.get(i).getSupplyTraderId() != null) {
		UserInfo user = localCachedService.getUserInfo(list.get(i).getSupplyTraderId());
		if (user != null) {
		    names.append("(").append(user.getUserName()).append(")");
		}

	    }
	    if (i != (list.size() - 1)) {
		ids.append(",");
		names.append(" ，");
	    }
	}
	UpDownForm upDownForm = new UpDownForm();
	upDownForm.setIds(ids.toString().trim());
	upDownForm.setNames(names.toString().trim());
	upDownForm.setUrl("itemSupplyDeal.do");
	upDownForm.setRequestType("batchDown");
	upDownForm.setTypeDown();
	upDownForm.setBizId(bizId + "");
	upDownForm.setModuleName("供货商品");
	model.addAttribute("upDownForm", upDownForm);

	return "biz/batchUpDown";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=batchDown", method = RequestMethod.POST)
    public String onRequestBatchDown(@ModelAttribute("upDownForm") UpDownForm upDownForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnUrl = "biz/batchUpDown";

	validator.validate(upDownForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(upDownForm.getBizId()));
	    model.addAttribute("bizId", upDownForm.getBizId());
	    return returnUrl;
	}

	logger.warn(userInfo.getUserName() + "执行批量下架操作 供货商品 ids : " + upDownForm.getIds());

	model.addAttribute("bizName", Constants.BIZ_MAP.get(upDownForm.getBizId()));
	model.addAttribute("bizId", upDownForm.getBizId());

	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(upDownForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	String[] strs = upDownForm.getIds().split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}

	List<Integer> list = new ArrayList<Integer>();
	for (String str : strs) {
	    if (StringUtils.isNumeric(str)) {
		list.add(Integer.parseInt(str));
	    }
	}
	if (list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}

	String description = upDownForm.toString();
	OperationLog operationLog = OperLogUtils.operationLogDeal(description, userInfo, getModuleNameFromSession(session), null,
		getIpFromSession(session), Constants.OperationLog.TYPE_UPDATE);

	Result<Long> result = super.submitTaskForm(upDownForm, "itemService", "downItemSupply", (Serializable) list,
		"java.util.List", operationLog, userInfo, Integer.parseInt(upDownForm.getBizId()));

	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return returnUrl;
	}

	if (upDownForm.isRealTimeCommit()) {
	    alertSuccess(model, "queryItemSupply.do?bizId=" + upDownForm.getBizId());
	} else {
	    if (result.getModule() == null) {
		alertSuccess(model, "../system/queryTask.do");
	    } else {
		alertSuccess(model, "../system/queryTask.do?id=" + result.getModule());
	    }
	}

	return returnUrl;
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=batchUpIndex")
    public String onRequestBatchUpIndex(@RequestParam("ids") String ids, @RequestParam("bizId") Integer bizId,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);

	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	UpDownForm upDownForm = new UpDownForm();
	upDownForm.setIds(ids);
	upDownForm.setUrl("itemSupplyDeal.do");
	upDownForm.setRequestType("batchUp");
	upDownForm.setTypeOn();
	upDownForm.setBizId(bizId + "");
	upDownForm.setModuleName("供货商品");
	model.addAttribute("upDownForm", upDownForm);

	return "biz/batchUpDown";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=conditionUpIndex", method = RequestMethod.POST)
    public String onRequestConditionUpIndex(@ModelAttribute("itemSupplyQuery") ItemSupplyQuery itemSupplyQuery,
	    @RequestParam("bizId") Integer bizId, @RequestParam("requestType") String requestType, HttpSession session,
	    Model model) {
	UserInfo userInfo = super.getUserInfo(session);

	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// 业务权限判断
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	itemSupplyQuery.setPageSize(10000);
	itemSupplyQuery.setCurrentPage(1);
	Result<List<ItemSupply>> result = itemService.queryItemSupplyPage(itemSupplyQuery);

	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return "biz/queryItemSupply";
	}

	StringBuffer ids = new StringBuffer("");
	StringBuffer names = new StringBuffer("");
	List<ItemSupply> list = result.getModule();
	if (list == null || list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return "biz/queryItemSupply";
	}
	for (int i = 0; i < list.size(); i++) {
	    ids.append(list.get(i).getId());
	    names.append(list.get(i).getItemName());
	    if (list.get(i).getSupplyTraderId() != null) {
		UserInfo user = localCachedService.getUserInfo(list.get(i).getSupplyTraderId());
		if (user != null) {
		    names.append("(").append(user.getUserName()).append(")");
		}

	    }
	    if (i != (list.size() - 1)) {
		ids.append(",");
		names.append(" ，");
	    }
	}

	UpDownForm upDownForm = new UpDownForm();
	upDownForm.setIds(ids.toString().trim());
	upDownForm.setNames(names.toString().trim());
	upDownForm.setUrl("itemSupplyDeal.do");
	upDownForm.setRequestType("batchUp");
	upDownForm.setTypeOn();
	upDownForm.setBizId(bizId + "");
	upDownForm.setModuleName("供货商品");
	model.addAttribute("upDownForm", upDownForm);

	return "biz/batchUpDown";
    }

    @RequestMapping(value = "biz/itemSupplyDeal", params = "requestType=batchUp", method = RequestMethod.POST)
    public String onRequestBatchUp(@ModelAttribute("upDownForm") UpDownForm upDownForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnUrl = "biz/batchUpDown";

	validator.validate(upDownForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    model.addAttribute("bizName", Constants.BIZ_MAP.get(upDownForm.getBizId()));
	    model.addAttribute("bizId", upDownForm.getBizId());
	    return returnUrl;
	}

	logger.warn(userInfo.getUserName() + "执行批量上架操作 供货商品 id : " + upDownForm.getIds());

	model.addAttribute("bizName", Constants.BIZ_MAP.get(upDownForm.getBizId()));
	model.addAttribute("bizId", upDownForm.getBizId());

	// 业务权限判断
	if (!checkBizAuth(Integer.parseInt(upDownForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	String[] strs = upDownForm.getIds().split(",");
	if (strs == null || strs.length == 0) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}

	List<Integer> list = new ArrayList<Integer>();
	for (String str : strs) {
	    if (StringUtils.isNumeric(str)) {
		list.add(Integer.parseInt(str));
	    }
	}
	if (list.isEmpty()) {
	    super.alertError(model, "供货商品列表不能为空");
	    return returnUrl;
	}

	String description = upDownForm.toString();
	OperationLog operationLog = OperLogUtils.operationLogDeal(description, userInfo, getModuleNameFromSession(session), null,
		getIpFromSession(session), Constants.OperationLog.TYPE_UPDATE);

	Result<Long> result = super.submitTaskForm(upDownForm, "itemService", "upItemSupply", (Serializable) list,
		"java.util.List", operationLog, userInfo, Integer.parseInt(upDownForm.getBizId()));

	if (!result.isSuccess()) {
	    super.alertError(model, result.getResultMsg());
	    return returnUrl;
	}

	if (upDownForm.isRealTimeCommit()) {
	    alertSuccess(model, "queryItemSupply.do?bizId=" + upDownForm.getBizId());
	} else {
	    if (result.getModule() == null) {
		alertSuccess(model, "../system/queryTask.do");
	    } else {
		alertSuccess(model, "../system/queryTask.do?id=" + result.getModule());
	    }
	}

	return returnUrl;
    }

    private ItemSupply formToItemSupply(ItemSupplyForm itemSupplyForm) {
	ItemSupply result = new ItemSupply();
	if (StringUtils.isNotEmpty(itemSupplyForm.getId())) {
	    result.setId(Long.parseLong(itemSupplyForm.getId()));
	}

	result.setBizId(Integer.parseInt(itemSupplyForm.getBizId()));
	result.setItemId(Integer.parseInt(itemSupplyForm.getItemId()));
	result.setPriority(Integer.parseInt(itemSupplyForm.getPriority()));
	result.setSupplyWay(itemSupplyForm.getSupplyWay());
	result.setSupplyTraderId(Long.parseLong(itemSupplyForm.getSupplyTraderId()));
	if (String.valueOf(Constants.Item.SALE_TYPE_AREA).equals(itemSupplyForm.getSupplyAreaType())) {
	    StringBuffer sb = new StringBuffer("");
	    for (String areaCode : itemSupplyForm.getSupplyAreaList()) {
		sb.append(areaCode).append(Constants.Item.SALES_AREA_SPLIT);
	    }
	    result.setSupplyArea(sb.toString().substring(0, sb.toString().length() - 1));
	} else {
	    result.setSupplyArea(""); // 空表示全国
	}
	if (StringUtils.isNotEmpty(itemSupplyForm.getLossType())) {
	    result.setLossType(Integer.parseInt(itemSupplyForm.getLossType()));
	} else {
	    result.setLossType(Constants.ItemSupply.LOSSTYPE_CANNOT);
	}
	if (StringUtils.isNotEmpty(itemSupplyForm.getLossTime())) {
	    result.setLossTime(Integer.parseInt(itemSupplyForm.getLossTime()));
	}
	if (StringUtils.isNotBlank(itemSupplyForm.getItemCostPrice())) {
	    Integer price = BigDecimalUtils.multInteger(itemSupplyForm.getItemCostPrice());
	    if (price > 0) {
		result.setItemCostPrice(price);
	    }
	}

	if (StringUtils.isBlank(itemSupplyForm.getSupplyProductCode())) {
	    result.setSupplyProductCode("");
	} else {
	    result.setSupplyProductCode(itemSupplyForm.getSupplyProductCode().trim());
	}
	return result;
    }

    private ItemSupply formToItemSupply(ItemSupplyListForm itemSupplyListForm, Integer itemId) {
	ItemSupply result = new ItemSupply();
	result.setBizId(Integer.parseInt(itemSupplyListForm.getBizId()));
	result.setItemId(itemId);
	result.setPriority(Integer.parseInt(itemSupplyListForm.getPriority()));
	result.setSupplyTraderId(Long.parseLong(itemSupplyListForm.getSupplyTraderId()));

	if (StringUtils.isNotEmpty(itemSupplyListForm.getSupplyProductCode())) {
	    result.setSupplyProductCode(itemSupplyListForm.getSupplyProductCode().trim());
	}
	return result;
    }

    private ItemSupply formToItemSupplyEdit(ItemSupplyListEditForm itemSupplyListEditForm, Long itemSupplyId) {
	ItemSupply result = new ItemSupply();
	result.setId(itemSupplyId);
	result.setPriority(Integer.parseInt(itemSupplyListEditForm.getPriority()));
	if (StringUtils.isNotEmpty(itemSupplyListEditForm.getSupplyProductCode())) {
	    result.setSupplyProductCode(itemSupplyListEditForm.getSupplyProductCode().trim());
	}
	return result;
    }

    @ModelAttribute("traderInfoList")
    public Map<Long, TraderInfo> traderInfoList() {
	Map<Long, TraderInfo> result = new HashMap<Long, TraderInfo>();
	Result<List<TraderInfo>> queryResult = traderInfoService.queryTraderInfoList();
	if (queryResult.isSuccess() && queryResult.getModule() != null) {
	    for (TraderInfo traderInfo : queryResult.getModule()) {
		result.put(traderInfo.getUserId(), traderInfo);
	    }
	}
	return result;
    }

    @ModelAttribute("supplyTraderList")
    public Map<Long, String> supplyTraderList() {
	// Map<Long, String> result = new HashMap<Long, String>();
	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// for (UserInfo user : usUserResult.getModule()) {
	// result.put(user.getId(), user.getUserName());
	// }
	// }
	// return result;
	return localCachedService.getUpStreamUser();
    }

    @ModelAttribute("supplyTypeList")
    public Map<String, String> supplyTypeList() {
	Map<String, String> mapType = new LinkedHashMap<String, String>();
	mapType.put(Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_DIRECT_CHARGE + "", Constants.ItemSupply.TYPE_DIRECT_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_MAN + "", Constants.ItemSupply.TYPE_MAN_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD + "", Constants.ItemSupply.TYPE_CARD_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_CHARGE_DESC);
	return mapType;
    }

    @ModelAttribute("asyncSupplyList")
    public Map<String, String> asyncSupplyList() {
	Map<String, String> mapType = new LinkedHashMap<String, String>();
	mapType.put(Constants.TraderInfo.SUPPLY_WAY_SYNC + "", Constants.TraderInfo.SUPPLY_WAY_SYNC_DESC);
	mapType.put(Constants.TraderInfo.SUPPLY_WAY_ASYNC + "", Constants.TraderInfo.SUPPLY_WAY_ASYNC_DESC);
	return mapType;
    }

    @ModelAttribute("lossTypeList")
    public Map<String, String> lossTypeList() {
	Map<String, String> mapLossType = new LinkedHashMap<String, String>();
	mapLossType.put(Constants.ItemSupply.LOSSTYPE_CANNOT + "", Constants.ItemSupply.LOSSTYPE_CANNOT_DESC);
	mapLossType.put(Constants.ItemSupply.LOSSTYPE_CAN + "", Constants.ItemSupply.LOSSTYPE_CAN_DESC);
	return mapLossType;
    }

    @ModelAttribute("commitTypeList")
    public Map<String, String> commitTypeList() {
	Map<String, String> map = new LinkedHashMap<String, String>(2);
	map.put(Constants.Task.COMMIT_TYPE_REAL_TIME + "", Constants.Task.COMMIT_TYPE_REAL_TIME_DESC);
	map.put(Constants.Task.COMMIT_TYPE_TASK + "", Constants.Task.COMMIT_TYPE_TASK_DESC);
	return map;
    }

    @ModelAttribute("supplyAreaTypeList")
    public Map<Integer, String> supplyAreaTypeList() {
	Map<Integer, String> supplyAreaTypeList = new LinkedHashMap<Integer, String>();
	supplyAreaTypeList.put(Constants.Item.SALE_TYPE_NATIONWIDE, Constants.Item.SALE_TYPE_NATIONWIDE_DESC);
	supplyAreaTypeList.put(Constants.Item.SALE_TYPE_AREA, Constants.Item.SALE_TYPE_AREA_DESC);
	return supplyAreaTypeList;
    }

    @ModelAttribute("areaInfoList")
    public Map<String, String> areaInfoList() {
	Map<String, String> result = new HashMap<String, String>();
	Map<String, AreaInfo> map = localCachedService.getProvinceMap();
	if (map == null) {
	    return result;
	}
	for (Entry<String, AreaInfo> e : map.entrySet()) {
	    result.put(e.getKey(), e.getValue().getProvinceName());
	}
	// 市域临时
	result.put("442000", "中山");
	result.put("640300", "吴忠");
	return result;
    }

    @ModelAttribute("supplyWayList")
    public Map<String, String> supplyWayList() {
	Map<String, String> supplyWayList = new LinkedHashMap<String, String>();
	supplyWayList.put("false", Constants.ItemSupply.SUPPLY_WAY_NORMAL_DESC);
	supplyWayList.put("true", Constants.ItemSupply.SUPPLY_WAY_FASTEST_DESC);
	return supplyWayList;
    }

    @Override
	@InitBinder
    public void formInitBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
