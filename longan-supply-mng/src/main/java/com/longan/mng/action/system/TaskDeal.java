package com.longan.mng.action.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.dataobject.Task;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.TaskForm;

@Controller
public class TaskDeal extends BaseController {
    private static final String returnUrl = "system/queryTask";

    @RequestMapping(value = "system/taskDeal", params = "requestType=cancel")
    public String onCancel(@RequestParam("id") Long id, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行任务取消操作 任务 id : " + id);
	Result<Boolean> result = taskService.cancelTask(id);
	if (!result.isSuccess()) {
	    super.alertError(model, "操作失败  msg : " + result.getResultMsg());
	    return returnUrl;
	}
	alertSuccess(model, "queryTask.do?id=" + id);
	return returnUrl;
    }

    @RequestMapping(value = "system/taskDeal", params = "requestType=editIndex")
    public String onEditIndex(@RequestParam("id") Long id, HttpSession session, Model model) {
	Result<Task> result = taskService.getTaskById(id);
	if (!result.isSuccess()) {
	    super.alertError(model, "任务查询失败 msg : " + result.getResultMsg());
	    return returnUrl;
	}

	Task task = result.getModule();
	if (task == null) {
	    super.alertError(model, "没有该任务，任务编号: " + id);
	    return returnUrl;
	}
	model.addAttribute("task", task);
	return "system/taskEdit";
    }

    @RequestMapping(value = "system/taskDeal", params = "requestType=edit")
    public String onEditIndex(@ModelAttribute("taskForm") TaskForm taskForm, HttpSession session, Model model) {
	Task taskUpdate = new Task();
	if (!StringUtils.isNumeric(taskForm.getId())) {
	    super.alertError(model, "任务编号格式不对");
	    return returnUrl;
	}
	taskUpdate.setId(Long.parseLong(taskForm.getId()));
	if (taskForm.isRealTimeCommit()) {
	    taskUpdate.setCommitTime(new Date());
	} else {
	    taskUpdate.setCommitTime(taskForm.getCommitTime());
	}

	Result<Boolean> result = taskService.updateTask(taskUpdate);
	if (!result.isSuccess()) {
	    super.alertError(model, "任务更新失败 msg : " + result.getResultMsg());
	    return returnUrl;
	}
	alertSuccess(model, "queryTask.do?id=" + taskForm.getId());
	return "system/taskEdit";
    }

    @ModelAttribute("commitTypeList")
    public Map<String, String> commitTypeList() {
	Map<String, String> map = new HashMap<String, String>(2);
	map.put(Constants.Task.COMMIT_TYPE_REAL_TIME + "", Constants.Task.COMMIT_TYPE_REAL_TIME_DESC);
	map.put(Constants.Task.COMMIT_TYPE_TASK + "", Constants.Task.COMMIT_TYPE_TASK_DESC);
	return map;
    }

    @Override
    @InitBinder
    public void formInitBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
