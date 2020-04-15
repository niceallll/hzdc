package com.longan.mng.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.RoleInfoService;
import com.longan.biz.core.UserRoleRelationService;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("system/queryUserRoleRelation")
public class QueryUserRoleRelation extends BaseController {
    @Resource
    private UserRoleRelationService userRoleRelationService;
    @Resource
    private RoleInfoService roleInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("userInfoQuery") UserInfoQuery userInfoQuery, Model model) {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("userInfoQuery") UserInfoQuery userInfoQuery, Model model) {
	Map<Integer, String> roleInfoMap = roleInfoMap();
	Result<List<UserInfo>> result = userRoleRelationService.queryUserRoleRelationList(userInfoQuery);
	if (result.isSuccess() && result.getModule() != null) {
	    List<UserInfo> list = result.getModule();
	    for (UserInfo userInfo : list) {
		if (StringUtils.isNotEmpty(userInfo.getRoleId() + "")) {
		    userInfo.setRoleDesc(roleInfoMap.get(userInfo.getRoleId()));
		}
	    }

	    model.addAttribute("userInfoList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    @ModelAttribute("roleInfoList")
    public Map<Integer, String> roleInfoMap() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	Result<List<RoleInfo>> result = roleInfoService.queryAllRoleInfo();
	if (result.isSuccess() && result.getModule() != null) {
	    for (RoleInfo operationInfo : result.getModule()) {
		map.put(operationInfo.getId(), operationInfo.getRoleDesc());
	    }
	}
	return map;
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new HashMap<String, String>();
	map.put(Constants.UserInfo.STATUS_CANCEL + "", Constants.UserInfo.STATUS_CANCEL_DESC);
	map.put(Constants.UserInfo.STATUS_NORMAL + "", Constants.UserInfo.STATUS_NORMAL_DESC);
	map.put(Constants.UserInfo.STATUS_DEL + "", Constants.UserInfo.STATUS_DEL_DESC);
	return map;
    }

    @ModelAttribute("typeList")
    public Map<String, String> typeList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.UserInfo.TYPE_ADMIN + "", Constants.UserInfo.TYPE_ADMIN_DESC);
	mapType.put(Constants.UserInfo.TYPE_PARTNER + "", Constants.UserInfo.TYPE_PARTNER_DESC);
	mapType.put(Constants.UserInfo.TYPE_DOWNSTREAM + "", Constants.UserInfo.TYPE_DOWNSTREAM_DESC);
	mapType.put(Constants.UserInfo.TYPE_UPSTREAM_DESC + "", Constants.UserInfo.TYPE_UPSTREAM_DESC);
	return mapType;
    }
}