package com.longan.mng.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.dataobject.Task;
import com.longan.biz.dataobject.TaskQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryTask extends BaseController {
    @RequestMapping(value = "system/queryTask")
    public void doQuery(@ModelAttribute("taskQuery") TaskQuery taskQuery, Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	setSelectValue(userInfo, model, taskQuery);

	Result<List<Task>> result = taskService.queryTaskByPage(taskQuery);
	if (result.isSuccess()) {
	    model.addAttribute("taskList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    void setSelectValue(UserInfo userInfo, Model model, TaskQuery taskQuery) {

    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new HashMap<String, String>(4);
	map.put(Constants.Task.STATUS_UNDONE + "", Constants.Task.STATUS_UNDONE_DESC);
	map.put(Constants.Task.STATUS_DONE + "", Constants.Task.STATUS_DONE_DESC);
	map.put(Constants.Task.STATUS_CANCEL + "", Constants.Task.STATUS_CANCEL_DESC);
	map.put(Constants.Task.STATUS_EXCEPTION + "", Constants.Task.STATUS_EXCEPTION_DESC);
	return map;
    }

    @ModelAttribute("bizList")
    public Map<Integer, String> bizList() {
	return Constants.getBizMap();
    }
}
