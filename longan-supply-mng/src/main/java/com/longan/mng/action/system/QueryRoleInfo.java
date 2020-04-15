package com.longan.mng.action.system;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.RoleInfoService;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.RoleInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping(value = "system/queryRoleInfo")
public class QueryRoleInfo extends BaseController {
    @Resource
    private RoleInfoService roleInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("roleInfoQuery") RoleInfoQuery roleInfoQuery, Model model) {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("roleInfoQuery") RoleInfoQuery roleInfoQuery, Model model) {
	Result<List<RoleInfo>> result = roleInfoService.queryRoleInfoList(roleInfoQuery);
	if (result.isSuccess()) {
	    model.addAttribute("roleInfoList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.RoleInfo.STATUS_NORMAL + "", Constants.RoleInfo.STATUS_NORMAL_DESC);
	map.put(Constants.RoleInfo.STATUS_CANCEL + "", Constants.RoleInfo.STATUS_CANCEL_DESC);
	map.put(Constants.RoleInfo.STATUS_DEL + "", Constants.RoleInfo.STATUS_DEL_DESC);
	return map;
    }
}
