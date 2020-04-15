package com.longan.mng.action.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationLogQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("system/queryOperationLog")
public class QueryOperationLog extends BaseController {
    @Resource
    private OperationLogService operationLogService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("operationLogQuery") OperationLogQuery operationLogQuery, Model model) {
	return "system/queryOperationLog";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doQuery(@ModelAttribute("operationLogQuery") OperationLogQuery operationLogQuery, Model model) {
	Result<List<OperationLog>> result = operationLogService.queryOperationLogList(operationLogQuery);
	if (result.isSuccess() && result.getModule() != null) {
	    List<OperationLog> operationLogList = result.getModule();
	    for (OperationLog operationLog : operationLogList) {
		if (operationLog.getBizId() != null) {
		    operationLog.setBizName(Constants.BIZ_MAP.get(operationLog.getBizId()) + ":");
		}
	    }
	    model.addAttribute("operationLogList", operationLogList);
	} else {
	    super.setError(model, result.getResultMsg());
	}
	return "system/queryOperationLog";
    }

    @ModelAttribute("userList")
    public Map<Long, String> userList() {
	// Map<Long, String> map = new HashMap<Long, String>();
	// Result<List<UserInfo>> userResult = userService.queryUserInfoAdmin();
	// if (userResult.isSuccess() && userResult.getModule() != null) {
	// for (UserInfo user : userResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// }
	// return map;
	return localCachedService.getAdminUser();
    }

    @ModelAttribute("bizList")
    public Map<Integer, String> bizList() {
	Map<Integer, String> map = Constants.BIZ_MAP;
	return map;
    }
}
