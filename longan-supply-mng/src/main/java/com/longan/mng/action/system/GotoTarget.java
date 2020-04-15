package com.longan.mng.action.system;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzdc.biz.core.GotoTargetService;
import com.hzdc.biz.data.object.TargetCharge;
import com.hzdc.biz.data.object.TargetOrder;
import com.longan.biz.cached.MemcachedService;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.TargetChargeForm;
import com.longan.mng.form.TargetOrderForm;

@Controller
@RequestMapping("/system/gotoTarget")
public class GotoTarget extends BaseController {
    private static final String goto_key = "goto_target_running";
    private static final String goto_page = "/system/gotoTarget";
    private static final Long chargeOrderId = 1l;
    private static final Long acctLogId = 1l;

    @Resource
    private GotoTargetService gotoTargetService;

    @Resource
    private Validator validator;

    @Resource
    private MemcachedService memcachedService;

    @RequestMapping(params = "requestType=list")
    public String gotoList(Model model, HttpSession session) {
	if (!checkAuth(session)) {
	    return "/error/autherror";
	}
	model.addAttribute("running", memcachedService.getBooleanValue(goto_key));
	return goto_page;
    }

    @RequestMapping(params = "requestType=charge")
    public String gotoCharge(@ModelAttribute("chargeForm") TargetChargeForm chargeForm, BindingResult bindingResult, Model model,
	    HttpSession session) {
	if (!checkAuth(session)) {
	    return "/error/autherror";
	}
	validator.validate(chargeForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return goto_page;
	}

	Date sdate = getStartDate(chargeForm.getChargeDate(), chargeForm.getChargeTime());
	if (sdate == null) {
	    alertError(model, "日期时间格式错误");
	    return goto_page;
	}
	Long userId = Long.parseLong(chargeForm.getUserId());
	Long acctId = getAcctId(userId);
	if (acctId == null) {
	    alertError(model, "此下游不存在");
	    return goto_page;
	}

	if (memcachedService.getBooleanValue(goto_key)) {
	    alertError(model, "正在运行中、禁止运行");
	} else {
	    TargetCharge targetCharge = new TargetCharge();
	    targetCharge.setUserId(userId);
	    targetCharge.setAcctId(acctId);
	    targetCharge.setChargeAmount(BigDecimalUtils.multLong(chargeForm.getChargeAmount()));
	    targetCharge.setSdate(sdate);
	    targetCharge.setAcctDate(DateTool.parseDate8(sdate));
	    targetCharge.setChargeOrderId(chargeOrderId);
	    targetCharge.setAcctLogId(acctLogId);
	    targetCharge.setMemo(getAcctMemo(userId));
	    Result<Boolean> result = gotoTargetService.gotoCharge(targetCharge);
	    if (result.isSuccess() && result.getModule()) {
		return "/success/succ";
	    }
	    alertError(model, result.getResultMsg());
	}
	return goto_page;
    }

    @RequestMapping(params = "requestType=order")
    public String gotoOrder(@ModelAttribute("orderForm") TargetOrderForm orderForm, BindingResult bindingResult, Model model,
	    HttpSession session) {
	if (!checkAuth(session)) {
	    return "/error/autherror";
	}
	validator.validate(orderForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return goto_page;
	}

	Date sdate = getStartDate(orderForm.getOrderDate(), orderForm.getOrderTime());
	if (sdate == null) {
	    alertError(model, "日期时间格式错误");
	    return goto_page;
	}
	Long userId = Long.parseLong(orderForm.getUserId());
	Long acctId = getAcctId(userId);
	if (acctId == null) {
	    alertError(model, "此下游不存在");
	    return goto_page;
	}
	Integer term = getTerm(orderForm.getOrderDate());

	if (memcachedService.getBooleanValue(goto_key)) {
	    alertError(model, "正在运行中、禁止运行");
	} else {
	    TargetOrder targetOrder = new TargetOrder();
	    targetOrder.setUserId(userId);
	    targetOrder.setAcctId(acctId);
	    targetOrder.setSdate(sdate);
	    targetOrder.setTerm(term);
	    targetOrder.setItem10(Integer.parseInt(orderForm.getItem10()));
	    targetOrder.setItem20(Integer.parseInt(orderForm.getItem20()));
	    targetOrder.setItem30(Integer.parseInt(orderForm.getItem30()));
	    targetOrder.setItem50(Integer.parseInt(orderForm.getItem50()));
	    targetOrder.setItem100(Integer.parseInt(orderForm.getItem100()));
	    targetOrder.setItem200(Integer.parseInt(orderForm.getItem200()));
	    targetOrder.setItem300(Integer.parseInt(orderForm.getItem300()));
	    targetOrder.setItem500(Integer.parseInt(orderForm.getItem500()));
	    Result<Boolean> result = gotoTargetService.gotoOrder(targetOrder);
	    if (result.isSuccess() && result.getModule()) {
		return "/success/succ";
	    }
	    alertError(model, result.getResultMsg());
	}
	return goto_page;
    }

    private boolean checkAuth(HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	return "robert".equals(userInfo.getLoginId());
    }

    private Long getAcctId(Long userId) {
	if (userId.longValue() == 323l) {
	    // 苏源
	    return 3l;
	} else if (userId.longValue() == 322l) {
	    // 圣密夕
	    return 4l;
	} else {
	    return null;
	}
    }

    private String getAcctMemo(Long userId) {
	if (userId.longValue() == 323l) {
	    // 苏源
	    return "苏源加款";
	} else if (userId.longValue() == 322l) {
	    // 圣密夕
	    return "圣密夕加款";
	} else {
	    return "";
	}
    }

    private Date getStartDate(String date, String time) {
	try {
	    return DateTool.strintToDatetime(date + " " + time);
	} catch (Exception ex) {
	    return null;
	}
    }

    private Integer getTerm(String date) {
	String[] list = date.split("-");
	return Integer.parseInt(list[0] + list[1]);
    }
}
