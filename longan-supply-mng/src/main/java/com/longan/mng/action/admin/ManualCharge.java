package com.longan.mng.action.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.ManualLogService;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ManualLog;
import com.longan.biz.dataobject.ManualLogQuery;
import com.longan.biz.dataobject.ManualOrder;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.CachedUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.Md5Encrypt;
import com.longan.biz.utils.SmsUtils;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.service.CallMessageService;
import com.longan.client.remote.service.SupplyForRemoteService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.response.AjaxResponse;
import com.longan.mng.form.FileChargeForm;
import com.longan.mng.form.TextChargeForm;

@Controller
@RequestMapping("charge/manualDeal")
public class ManualCharge extends BaseController {
    private final static String smsMobile = Utils.getProperty("manual.sms");
    private final static String chargeUserId = Utils.getProperty("manual.userId");
    private final static String chargeUrl = Utils.getProperty("manual.url");
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Resource
    private Validator validator;

    @Resource
    private ManualLogService manualLogService;

    @Resource
    private MemcachedService memcachedService;

    @Resource
    private CallMessageService callMessageService;

    @Resource
    private CommonsMultipartResolver multipartResolver;

    @Resource
    private SupplyForRemoteService supplyForRemoteService;

    @RequestMapping(params = "type=queryList")
    public String doQuery(@ModelAttribute("manualLogQuery") ManualLogQuery manualLogQuery, Model model) {
	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (manualLogQuery.getEndGmtCreate() == null) {
	    manualLogQuery.setEndGmtCreate(date);
	}
	if (manualLogQuery.getStartGmtCreate() == null) {
	    manualLogQuery.setStartGmtCreate(date);
	}

	Result<List<ManualLog>> result = manualLogService.queryManualLogPage(manualLogQuery);
	if (result.isSuccess()) {
	    List<ManualLog> list = result.getModule();
	    for (ManualLog manualLog : list) {
		if (manualLog.getDealOperId() != null) {
		    UserInfo oper = localCachedService.getUserInfo(manualLog.getDealOperId());
		    manualLog.setDealOperName(oper == null ? "" : oper.getUserName());
		}
	    }
	    model.addAttribute("manualLogList", list);
	} else {
	    setError(model, result.getResultMsg());
	}
	return "admin/charge/queryCharge";
    }

    @RequestMapping(params = "type=queryDetail")
    public String queryDetail(Long manualLogId, Model model) {
	Result<List<ManualOrder>> result = manualLogService.queryManualOrder(manualLogId);
	List<ManualOrder> orderList = result.getModule();
	if (result.isSuccess()) {
	    if (orderList != null && orderList.size() > 0) {
		model.addAttribute("manualOrderList", orderList);
	    } else {
		setError(model, "定单列表是空");
	    }
	} else {
	    setError(model, result.getResultMsg());
	}
	return "admin/charge/queryDetails";
    }

    @RequestMapping(params = "type=getFile")
    public String getFile() {
	return "admin/charge/fileCharge";
    }

    @RequestMapping(params = "type=postFile")
    public String postFile(HttpServletRequest request, HttpSession session,
	    @ModelAttribute("fileChargeForm") FileChargeForm fileChargeForm, BindingResult bindingResult, Model model) {
	String fileUrl = "admin/charge/fileCharge";
	validator.validate(fileChargeForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return fileUrl;
	}
	if (!fileChargeForm.getSmsCode().equals(memcachedService.getStringValue(CachedUtils.MANUAL_CHARGE_KEY))) {
	    setError(model, "短信验证码错误");
	    return fileUrl;
	}
	Long userId = fileChargeForm.getUserId();
	String userkey = getUserKey(userId);
	if (StringUtils.isBlank(userkey)) {
	    setError(model, "用户编号错误");
	    return fileUrl;
	}
	if (!checkFile(request, multipartResolver)) {
	    setError(model, "上传文件不能是空");
	    return fileUrl;
	}

	UserInfo userInfo = getUserInfo(session);
	ManualLog manualLog = getManualLog(Constants.ManualLog.TYPE_CHARGE_FILE, userId, fileChargeForm.getMemo(),
		userInfo.getId());
	Result<ManualLog> manualLogResult = manualLogService.createManualLog(manualLog);
	if (!manualLogResult.isSuccess()) {
	    setError(model, manualLogResult.getResultMsg());
	    return fileUrl;
	}

	Long manualLogId = manualLog.getId();
	File file = uploadFile(request, manualLogId);
	if (file == null) {
	    logger.error("上传文件为空 manualLogId : " + manualLogId);
	    setError(model, "上传文件不能是空");
	    manualFailUpdate(manualLogId);
	    return fileUrl;
	}

	List<ManualOrder> chargeList = null;
	try {
	    chargeList = parseFile(userId, manualLogId, file);
	} catch (Exception ex) {
	    logger.error("处理文件失败 manualLogId : " + manualLogId, ex);
	    setError(model, "处理文件失败  msg : " + ex.getMessage());
	    manualFailUpdate(manualLogId);
	    return fileUrl;
	}

	Result<Boolean> manualOrderResult = manualLogService.createManualOrder(chargeList);
	if (!manualOrderResult.isSuccess()) {
	    setError(model, manualOrderResult.getResultMsg());
	    manualFailUpdate(manualLogId);
	    return fileUrl;
	}
	runAsyncCharge(manualLogId, userkey, chargeList);

	memcachedService.delete(CachedUtils.MANUAL_CHARGE_KEY);
	alertSuccess(model, "manualDeal.do?type=postFile");
	return fileUrl;
    }

    @RequestMapping(params = "type=getText")
    public String getText() {
	return "admin/charge/textCharge";
    }

    @RequestMapping(params = "type=postText")
    public String postText(HttpSession session, @ModelAttribute("textChargeForm") TextChargeForm textChargeForm,
	    BindingResult bindingResult, Model model) {
	String textUrl = "admin/charge/textCharge";
	validator.validate(textChargeForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return textUrl;
	}
	if (!textChargeForm.getSmsCode().equals(memcachedService.getStringValue(CachedUtils.MANUAL_CHARGE_KEY))) {
	    setError(model, "短信验证码错误");
	    return textUrl;
	}

	Long userId = textChargeForm.getUserId();
	String userkey = getUserKey(userId);
	if (StringUtils.isBlank(userkey)) {
	    setError(model, "用户编号错误");
	    return textUrl;
	}
	Integer itemId = Integer.parseInt(textChargeForm.getItemId());
	Item item = localCachedService.getItem(itemId);
	if (item == null) {
	    setError(model, "商品编号不存在");
	    return textUrl;
	}

	String[] mobileList = textChargeForm.getMobiles().split(Constants.BUY_REQUEST_SPLIT);
	for (String mobile : mobileList) {
	    if (!Utils.checkMobileFormat(mobile)) {
		setError(model, "手机号格式错误");
		return textUrl;
	    }
	}

	String memo = itemId + "(" + item.getItemName() + ")" + textChargeForm.getMemo();
	UserInfo userInfo = getUserInfo(session);
	Integer itemFacePrice = item.getItemFacePrice();
	ManualLog manualLog = getManualLog(Constants.ManualLog.TYPE_CHARGE_TEXT, userId, memo, userInfo.getId());
	manualLog.setTotalPrice(mobileList.length * itemFacePrice + 0l);
	manualLog.setTotalCount(mobileList.length);
	Result<ManualLog> manualLogResult = manualLogService.createManualLog(manualLog);
	if (!manualLogResult.isSuccess()) {
	    setError(model, manualLogResult.getResultMsg());
	    return textUrl;
	}

	Long manualLogId = manualLog.getId();
	List<ManualOrder> chargeList = new ArrayList<ManualOrder>();
	for (String mobile : mobileList) {
	    ManualOrder manualOrder = getManualOrder(userId, manualLogId, mobile, item);
	    manualOrder.setRepeatType(Constants.ManualOrder.REPEAT_TYPE_NO);
	    chargeList.add(manualOrder);
	}

	Result<Boolean> manualOrderResult = manualLogService.createManualOrder(chargeList);
	if (!manualOrderResult.isSuccess()) {
	    setError(model, manualOrderResult.getResultMsg());
	    manualFailUpdate(manualLogId);
	    return textUrl;
	}
	runAsyncCharge(manualLogId, userkey, chargeList);

	memcachedService.delete(CachedUtils.MANUAL_CHARGE_KEY);
	alertSuccess(model, "manualDeal.do?type=postText");
	return textUrl;
    }

    @RequestMapping(params = "type=smsCode")
    public @ResponseBody
    AjaxResponse smsCode() {
	AjaxResponse response = new AjaxResponse();
	String code = SmsUtils.getSmsCode();
	memcachedService.set(CachedUtils.MANUAL_CHARGE_KEY, CachedUtils.MANUAL_CHARGE_EXP, code);
	Result<Boolean> result = callMessageService.sendSmsNotify(smsMobile, "您的充值验证码是" + code + "，五分钟内有效。");
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(true);
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(params = "type=repeatCharge")
    public String repeatCharge(Long userId, Long manualLogId, Long manualOrderId, Model model) {
	String returnUrl = "admin/charge/queryCharge";
	String directUrl = "manualDeal.do?type=queryDetail&manualLogId=" + manualLogId;
	String userkey = getUserKey(userId);
	if (StringUtils.isBlank(userkey)) {
	    alertMsgRedirect(model, "用户编号错误", directUrl);
	    return returnUrl;
	}
	Result<ManualOrder> queryResult = manualLogService.getManualOrder(manualOrderId);
	if (!queryResult.isSuccess() || queryResult.getModule() == null) {
	    alertMsgRedirect(model, "没有该供货单", directUrl);
	    return returnUrl;
	}

	ManualOrder manualOrder = queryResult.getModule();
	if (!manualOrder.canRepeatCharge()) {
	    alertMsgRedirect(model, "该定单不允许做补充操作", directUrl);
	    return returnUrl;
	}

	Item item = localCachedService.getItem(manualOrder.getItemId());
	if (item == null) {
	    alertMsgRedirect(model, "商品编号不存在，不允许做补充操作", directUrl);
	    return returnUrl;
	}

	String itemUid = manualOrder.getItemUid();
	if (!Utils.checkMobileFormat(itemUid)) {
	    alertMsgRedirect(model, "手机号格式错误，不允许做补充操作", directUrl);
	    return returnUrl;
	}

	manualOrder.setStatus(Constants.ManualOrder.STATUS_REPEAT);
	ManualOrder repeatManualOrder = getManualOrder(userId, manualLogId, itemUid, item);
	repeatManualOrder.setRepeatType(Constants.ManualOrder.REPEAT_TYPE_YES);
	Result<ManualOrder> repeatResult = manualLogService.repeatManualOrder(manualOrder, repeatManualOrder);
	if (!repeatResult.isSuccess()) {
	    alertMsgRedirect(model, repeatResult.getResultMsg(), directUrl);
	    return returnUrl;
	}

	runAsyncCharge(manualLogId, userkey, repeatManualOrder);
	alertMsgRedirect(model, "补充成功", directUrl);
	return returnUrl;
    }

    @ModelAttribute("userList")
    public Map<String, String> userList() {
	String[] list = chargeUserId.split(Constants.BUY_REQUEST_SPLIT);
	Map<String, String> map = new LinkedHashMap<String, String>();
	for (String userId : list) {
	    UserInfo userInfo = localCachedService.getUserInfo(Long.parseLong(userId));
	    if (userInfo == null) {
		map.put(userId, "用户编号：" + userId);
	    } else {
		map.put(userId, userInfo.getUserName());
	    }
	}
	return map;
    }

    private ManualLog getManualLog(Integer type, Long userId, String memo, Long operId) {
	ManualLog manualLog = new ManualLog();
	manualLog.setStatus(Constants.ManualLog.STATUS_CHARGING);
	manualLog.setUserId(userId);
	manualLog.setTotalPrice(0l);
	manualLog.setSuccPrice(0l);
	manualLog.setTotalCount(0);
	manualLog.setSuccCount(0);
	manualLog.setDealOperId(operId);
	manualLog.setChargeType(type);
	manualLog.setMemo(memo);
	return manualLog;
    }

    private void manualFailUpdate(Long id) {
	ManualLog manualLogUpdate = new ManualLog();
	manualLogUpdate.setId(id);
	manualLogUpdate.setStatus(Constants.ManualLog.STATUS_FAILED);
	manualLogService.updateManualLog(manualLogUpdate);
    }

    private File uploadFile(HttpServletRequest request, Long id) {
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	File file = null;
	if (multipartResolver.isMultipart(multipartRequest)) {// 判断request是否有文件上传
	    // srcfname是指文件上传标签的name=值
	    MultiValueMap<String, MultipartFile> multfiles = multipartRequest.getMultiFileMap();
	    for (String srcfname : multfiles.keySet()) {
		MultipartFile mfile = multfiles.getFirst(srcfname);
		try {
		    file = new File(com.longan.mng.utils.Constants.UPLOAD_PATH + id + ".txt");
		    if (mfile.isEmpty()) {
			return null;
		    }
		    mfile.transferTo(file);
		} catch (IllegalStateException e) {
		    logger.error("uploadFile error manualLogId:" + id, e);
		    return null;
		} catch (IOException e) {
		    logger.error("uploadFile error manualLogId:" + id, e);
		    return null;
		}
	    }
	} else {
	    logger.error("uploadFile error file is null");
	    return null;
	}
	return file;
    }

    private List<ManualOrder> parseFile(Long userId, Long manualLogId, File file) throws Exception {
	List<ManualOrder> chargeList = new ArrayList<ManualOrder>();
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	    String data = null;
	    while ((data = reader.readLine()) != null && !("").equals(data)) {
		String[] lines = data.split(Constants.BUY_REQUEST_SPLIT);
		ManualOrder manualOrder = getManualOrder(userId, manualLogId, lines[0].trim(), Integer.parseInt(lines[1].trim()));
		manualOrder.setRepeatType(Constants.ManualOrder.REPEAT_TYPE_NO);
		chargeList.add(manualOrder);
	    }
	} finally {
	    try {
		if (reader != null) {
		    reader.close();
		}
	    } catch (IOException ex) {
		logger.error("io close error ", ex);
	    }
	}
	return chargeList;
    }

    private ManualOrder getManualOrder(Long userId, Long manualLogId, String itemUid, Integer itemId) {
	ManualOrder manualOrder = new ManualOrder();
	manualOrder.setStatus(Constants.ManualOrder.STATUS_INIT);
	manualOrder.setUserId(userId);
	manualOrder.setLogId(manualLogId);
	manualOrder.setItemUid(itemUid);
	manualOrder.setItemId(itemId);

	Item item = localCachedService.getItem(itemId);
	if (item != null) {
	    manualOrder.setItemName(item.getItemName());
	    manualOrder.setItemFacePrice(item.getItemFacePrice());
	}
	return manualOrder;
    }

    private ManualOrder getManualOrder(Long userId, Long manualLogId, String itemUid, Item item) {
	ManualOrder manualOrder = new ManualOrder();
	manualOrder.setStatus(Constants.ManualOrder.STATUS_INIT);
	manualOrder.setUserId(userId);
	manualOrder.setLogId(manualLogId);
	manualOrder.setItemUid(itemUid);
	manualOrder.setItemId(item.getId());
	manualOrder.setItemName(item.getItemName());
	manualOrder.setItemFacePrice(item.getItemFacePrice());
	return manualOrder;
    }

    private void runAsyncCharge(final long manualLogId, final String userkey, final List<ManualOrder> chargeList) {
	es.execute(new Runnable() {
	    @Override
	    public void run() {
		Long totalPrice = 0l;
		for (ManualOrder manualOrder : chargeList) {
		    totalPrice += manualOrder.getItemFacePrice();

		    Integer itemId = manualOrder.getItemId();
		    if (localCachedService.getItem(itemId) == null) {
			logger.error("商品编号不存在 itemId : " + itemId);
			orderFailUpdate(manualOrder.getId(), null, "商品编号不存在");
			continue;
		    }

		    String itemUid = manualOrder.getItemUid();
		    if (!Utils.checkMobileFormat(itemUid)) {
			logger.error("手机号格式错误 itemUid : " + itemUid);
			orderFailUpdate(manualOrder.getId(), null, "手机号格式错误");
			continue;
		    }

		    String serialNo = manualLogId + "-" + SmsUtils.getSmsCode();
		    String data = getChargeData(manualOrder.getUserId(), userkey, serialNo, itemId, itemUid);
		    try {
			Result<String> result = supplyForRemoteService.manualGet(data);
			if (result.isSuccess()) {
			    orderChargeUpdate(manualOrder.getId(), serialNo, result.getModule());
			} else {
			    orderFailUpdate(manualOrder.getId(), serialNo, result.getResultMsg());
			}
		    } catch (Exception ex) {
			logger.error("manualGet error ", ex);
			orderFailUpdate(manualOrder.getId(), serialNo, "manualGet error");
		    }
		}

		ManualLog manualLogUpdate = new ManualLog();
		manualLogUpdate.setId(manualLogId);
		manualLogUpdate.setTotalPrice(totalPrice);
		manualLogUpdate.setTotalCount(chargeList.size());
		manualLogUpdate.setStatus(Constants.ManualLog.STATUS_FIN);
		manualLogService.updateManualLog(manualLogUpdate);
	    }
	});
    }

    private void runAsyncCharge(final long manualLogId, final String userkey, final ManualOrder manualOrder) {
	es.execute(new Runnable() {
	    @Override
	    public void run() {
		String serialNo = manualLogId + "-" + SmsUtils.getSmsCode();
		String data = getChargeData(manualOrder.getUserId(), userkey, serialNo, manualOrder.getItemId(),
			manualOrder.getItemUid());
		try {
		    Result<String> result = supplyForRemoteService.manualGet(data);
		    if (result.isSuccess()) {
			orderChargeUpdate(manualOrder.getId(), serialNo, result.getModule());
		    } else {
			orderFailUpdate(manualOrder.getId(), serialNo, result.getResultMsg());
		    }
		} catch (Exception ex) {
		    logger.error("manualGet error ", ex);
		    orderFailUpdate(manualOrder.getId(), serialNo, "manualGet error");
		}
	    }
	});
    }

    private String getChargeData(Long userId, String key, String serialNo, Integer itemId, String mobile) {
	String dtCreate = dateFormat.format(new Date());
	StringBuilder sb = new StringBuilder(dtCreate).append(itemId).append(serialNo).append(mobile).append(userId).append(key);
	String sign = Md5Encrypt.md5(sb.toString());
	String data = new StringBuilder(chargeUrl).append("?").append("userId=").append(userId).append("&itemId=").append(itemId)
		.append("&uid=").append(mobile).append("&serialno=").append(serialNo).append("&dtCreate=").append(dtCreate)
		.append("&sign=").append(sign).toString();
	return data;
    }

    private void orderFailUpdate(Long id, String serialno, String memo) {
	ManualOrder manualOrderUpdate = new ManualOrder();
	manualOrderUpdate.setId(id);
	manualOrderUpdate.setSerialno(serialno);
	manualOrderUpdate.setStatus(Constants.ManualOrder.STATUS_FAILED);
	manualOrderUpdate.setUpstreamMemo(memo);
	manualLogService.updateManualOrder(manualOrderUpdate);
    }

    private void orderChargeUpdate(Long id, String serialno, String xml) {
	ManualOrder manualOrderUpdate = new ManualOrder();
	manualOrderUpdate.setId(id);
	manualOrderUpdate.setSerialno(serialno);
	if (StringUtils.isBlank(xml)) {
	    manualOrderUpdate.setStatus(Constants.ManualOrder.STATUS_CHARGING);
	    manualOrderUpdate.setUpstreamMemo("未确认");
	} else {
	    SAXReader reader = new SAXReader();
	    try {
		Document document = reader.read(new StringReader(xml));
		Element root = document.getRootElement();
		String code = root.elementText("code");
		if ("00".equals(code)) {
		    manualOrderUpdate.setStatus(Constants.ManualOrder.STATUS_CHARGING);
		} else {
		    manualOrderUpdate.setStatus(Constants.ManualOrder.STATUS_FAILED);
		    manualOrderUpdate.setUpstreamMemo(root.elementText("desc"));
		}
		manualOrderUpdate.setBizOrderId(getUpstreamSerialno(root.elementText("bizOrderId")));
	    } catch (DocumentException ex) {
		manualOrderUpdate.setStatus(Constants.ManualOrder.STATUS_CHARGING);
		manualOrderUpdate.setUpstreamMemo("未确认");
	    }
	}
	manualLogService.updateManualOrder(manualOrderUpdate);
    }

    private Long getUpstreamSerialno(String upstreamSerialno) {
	Long bizOrderId = null;
	try {
	    bizOrderId = Long.parseLong(upstreamSerialno);
	} catch (Exception ex) {
	}
	return bizOrderId;
    }

    private String getUserKey(Long userId) {
	if (userId == null) {
	    return null;
	}

	TraderInfo traderInfo = localCachedService.getTraderInfo(userId);
	if (traderInfo == null || traderInfo.getTraderType() != Constants.TraderInfo.TRADER_TYPE_DOWNSTREAM) {
	    return null;
	}
	return traderInfo.getDownstreamKey();
    }
}
