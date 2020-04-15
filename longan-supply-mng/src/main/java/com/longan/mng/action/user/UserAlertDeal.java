package com.longan.mng.action.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.UserAlertService;
import com.longan.biz.domain.Result;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping(value = "user/alertDeal")
public class UserAlertDeal extends BaseController {
    @Resource
    private UserAlertService userAlertService;

    @RequestMapping(params = "requestType=add")
    public String addAlert(Long id, Integer type, Model model) {
	Result<Boolean> result = userAlertService.alertRequest(id, "/alert/user", "add");
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return getErrorResult(type, model);
	}
	return getRightResult(type);
    }

    @RequestMapping(params = "requestType=set")
    public String setAlert(Long id) {
	String data = userAlertService.getRequestData(id, "/alert/user", "edit");
	return "redirect:" + data;
    }

    @RequestMapping(params = "requestType=sets")
    public String setArea(Long id) {
	String data = userAlertService.getRequestData(id, "/alert/userArea", "sets");
	return "redirect:" + data;
    }

    @RequestMapping(params = "requestType=open")
    public String openAlert(Long id, Integer type, Model model) {
	Result<Boolean> result = userAlertService.alertRequest(id, "/alert/user", "open");
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return getErrorResult(type, model);
	}
	return getRightResult(type);
    }

    @RequestMapping(params = "requestType=close")
    public String closeAlert(Long id, Integer type, Model model) {
	Result<Boolean> result = userAlertService.alertRequest(id, "/alert/user", "close");
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return getErrorResult(type, model);
	}
	return getRightResult(type);
    }

    private String getErrorResult(Integer type, Model model) {
	model.addAttribute("type", type);
	if (type == 2) {
	    return "user/queryDownStreamInfo";
	} else {
	    return "user/queryUpStreamInfo";
	}
    }

    private String getRightResult(Integer type) {
	if (type == 2) {
	    return "forward:queryDownStreamInfo.do";
	} else {
	    return "forward:queryUpStreamInfo.do";
	}
    }
}
