package com.longan.mng.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("user/queryUserInfo")
public class QueryUserInfo extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("userInfoQuery") UserInfoQuery userInfoQuery, Model model) {
	// userInfoQuery.setStartGmtCreate(new Date());
	// userInfoQuery.setEndGmtCreate(new Date());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("userInfoQuery") UserInfoQuery userInfoQuery, Model model) {
	setSelectValue(model);
	Result<List<UserInfo>> result = userService.queryUserInfoList(userInfoQuery);
	if (result.isSuccess()) {
	    model.addAttribute("userInfoList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    void setSelectValue(Model model) {
    }

    @ModelAttribute("typeList")
    public Map<String, String> typeList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.UserInfo.TYPE_ADMIN + "", Constants.UserInfo.TYPE_ADMIN_DESC);
	mapType.put(Constants.UserInfo.TYPE_PARTNER + "", Constants.UserInfo.TYPE_PARTNER_DESC);
	mapType.put(Constants.UserInfo.TYPE_DOWNSTREAM + "", Constants.UserInfo.TYPE_DOWNSTREAM_DESC);
	mapType.put(Constants.UserInfo.TYPE_UPSTREAM + "", Constants.UserInfo.TYPE_UPSTREAM_DESC);
	return mapType;
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new HashMap<String, String>();
	map.put(Constants.UserInfo.STATUS_CANCEL + "", Constants.UserInfo.STATUS_CANCEL_DESC);
	map.put(Constants.UserInfo.STATUS_NORMAL + "", Constants.UserInfo.STATUS_NORMAL_DESC);
	map.put(Constants.UserInfo.STATUS_DEL + "", Constants.UserInfo.STATUS_DEL_DESC);
	return map;
    }
}
