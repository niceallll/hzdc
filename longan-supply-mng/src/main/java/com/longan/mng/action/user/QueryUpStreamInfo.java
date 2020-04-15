package com.longan.mng.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryUpStreamInfo extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("user/queryUpStreamInfo")
    public String doQuery(@ModelAttribute("userInfoQuery") UserInfoQuery userInfoQuery, @ModelAttribute("type") Integer type,
	    Model model) {
	model.addAttribute("type", type);
	Result<List<UserInfo>> result = userService.queryUserInfoList(userInfoQuery);
	if (result.isSuccess()) {
	    model.addAttribute("userInfoList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "user/queryUpStreamInfo";
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
