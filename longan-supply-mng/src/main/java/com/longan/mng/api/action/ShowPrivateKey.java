package com.longan.mng.api.action;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longan.biz.core.TraderInfoService;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.response.AjaxResponse;

@Controller
public class ShowPrivateKey extends BaseController {
    @Resource
    private TraderInfoService traderInfoService;

    @RequestMapping(value = "api/showPrivateKey", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse onshowPrivateKeyIndex(@RequestParam("id") Long id, HttpSession session) {
	UserInfo user = getUserInfo(session);
	if (user.isDownStreamUser()) {
	    // 下游只能看自己的信息
	    id = user.getId();
	}
	Result<TraderInfo> traderInfoResult = traderInfoService.getTraderInfoByUserId(id);

	AjaxResponse response = new AjaxResponse();
	if (!traderInfoResult.isSuccess()) {
	    response.setErrorMsg(traderInfoResult.getResultMsg());
	    return response;
	} else {
	    TraderInfo traderInfo = traderInfoResult.getModule();
	    if (traderInfo == null) {
		response.setErrorMsg("商户信息为空");
		return response;
	    }
	    if (StringUtils.isEmpty(traderInfo.getDownstreamKey())) {
		response.setErrorMsg("密钥为空");
		return response;
	    }
	    response.setSuccess();
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("privateKey", traderInfo.getDownstreamKey());
	    response.setModule(map);
	    return response;
	}
    }
}
