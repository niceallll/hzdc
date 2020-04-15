package com.longan.mng.action.admin;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Md5Encrypt;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.service.SupplyForRemoteService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.TextSmdForm;

@Controller
@RequestMapping("sms/manualSend")
public class ManualSms extends BaseController {
    private static SimpleDateFormat formno = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static SimpleDateFormat formdate = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static String smsUrl = Utils.getProperty("sms.url");

    @Resource
    private Validator validator;

    @Resource
    private SupplyForRemoteService supplyForRemoteService;

    @RequestMapping(params = "type=getText")
    public String getText() {
	return "admin/textSms";
    }

    @RequestMapping(params = "type=postText")
    public String postText(HttpSession session, @ModelAttribute("textSmdForm") TextSmdForm textSmdForm,
	    BindingResult bindingResult, Model model) {
	String textUrl = "admin/textSms";
	validator.validate(textSmdForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return textUrl;
	}

	Integer itemId = Integer.parseInt(textSmdForm.getItemId());
	Item item = localCachedService.getItem(itemId);
	if (item == null) {
	    setError(model, "商品编号不存在");
	    return textUrl;
	}
	if (item.getBizId() != Constants.BizInfo.CODE_PHONE_SMS) {
	    setError(model, "商品不是短信商品");
	    return textUrl;
	}
	if (item.getStatus() != Constants.Item.STATUS_UP) {
	    setError(model, "商品未上架");
	    return textUrl;
	}

	String[] mobileList = textSmdForm.getMobiles().split(Constants.BUY_REQUEST_SPLIT);
	for (String mobile : mobileList) {
	    if (!Utils.checkMobileFormat(mobile)) {
		setError(model, "手机号格式错误");
		return textUrl;
	    }
	}

	UserInfo userInfo = getUserInfo(session);
	if (!userInfo.isDownStreamUser()) {
	    setError(model, "非下游用户禁止发送");
	    return textUrl;
	}

	runAsyncSms(userInfo.getId(), itemId, textSmdForm.getMobiles(), textSmdForm.getTexts());
	alertSuccess(model, "manualSend.do?type=postText");
	return textUrl;
    }

    private void runAsyncSms(final long userId, final Integer itemId, final String uid, final String texts) {
	es.execute(new Runnable() {
	    @Override
	    public void run() {
		String serialNo = formno.format(new Date());
		String dtCreate = formdate.format(new Date());
		String data = new StringBuilder(dtCreate).append(itemId).append(serialNo).append(uid).append(userId)
			.append(getDownStreamKey(userId)).toString();
		String sign = Md5Encrypt.md5(data);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId + "");
		map.put("itemId", itemId + "");
		try {
		    map.put("text", URLEncoder.encode(texts, "UTF-8"));
		} catch (Exception ex) {
		    logger.error("runAsyncSms encode失败", ex);
		    return;
		}
		map.put("uid", uid);
		map.put("serialno", serialNo);
		map.put("dtCreate", dtCreate);
		map.put("sign", sign);
		try {
		    Result<String> result = supplyForRemoteService.manualPost(smsUrl, map);
		    if (result.isSuccess()) {
			logger.warn("manualPost发送成功");
		    } else {
			logger.error("manualPost发送失败：" + result.getResultMsg());
		    }
		} catch (Exception ex) {
		    logger.error("manualPost error ", ex);
		}
	    }
	});
    }

    private String getDownStreamKey(Long userId) {
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
