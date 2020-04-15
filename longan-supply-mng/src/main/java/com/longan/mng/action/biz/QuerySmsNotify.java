package com.longan.mng.action.biz;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsNotifyQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QuerySmsNotify extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("biz/querySmsNotify")
    public String doQuery(@ModelAttribute("smsNotifyQuery") SmsNotifyQuery smsNotifyQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(smsNotifyQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);
	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (smsNotifyQuery.getEndGmtCreate() == null) {
	    smsNotifyQuery.setEndGmtCreate(date);
	}
	if (smsNotifyQuery.getStartGmtCreate() == null) {
	    smsNotifyQuery.setStartGmtCreate(date);
	}
	if (!check2Date(smsNotifyQuery.getStartGmtCreate(), smsNotifyQuery.getEndGmtCreate())) {
	    alertErrorNoneBack(model, "订单时间区间不能超过2个月");
	    return "biz/querySmsNotify";
	}

	setSelectValue(userInfo, model, smsNotifyQuery);
	Result<List<SmsNotify>> result = queryService.querySmsNotifyPage(smsNotifyQuery);
	if (result.isSuccess()) {
	    List<SmsNotify> list = result.getModule();
	    if (list != null) {
		for (SmsNotify smsNotify : list) {
		    if (smsNotify.getUpstreamId() != null) {
			UserInfo upstreamUserInfo = localCachedService.getUserInfo(smsNotify.getUpstreamId());
			if (upstreamUserInfo != null) {
			    smsNotify.setUpstreamName(upstreamUserInfo.getUserName());
			}
		    }
		}
	    }
	    model.addAttribute("smsNotifyList", list);
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/querySmsNotify";
    }

    private void setSelectValue(UserInfo userInfo, Model model, SmsNotifyQuery smsNotifyQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(smsNotifyQuery.getBizId()));
	model.addAttribute("bizId", smsNotifyQuery.getBizId());
	if (userInfo.isDownStreamUser()) { // 下游只能看自己的订单
	    smsNotifyQuery.setUserId(userInfo.getId()); // 设置查询条件
	    Map<Long, String> map = new HashMap<Long, String>();
	    map.put(userInfo.getId(), userInfo.getUserName());
	    model.addAttribute("downStreamUser", map);
	} else {
	    model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());
	}

	model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());
    }
}
