package com.longan.mng.action.biz;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.SupplyBatchService;
import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyBatchQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

/**
 * 慢充管理
 */
@Controller
public class QuerySupplyBatch extends BaseController {
    @Resource
    private SupplyBatchService supplyBatchService;

    @RequestMapping("biz/querySupplyBatch")
    public String doQuery(@ModelAttribute("supplyBatchQuery") SupplyBatchQuery supplyBatchQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(supplyBatchQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}
	//根据前台条件查询对应的商家名
	model.addAttribute("bizName", Constants.BIZ_MAP.get(supplyBatchQuery.getBizId()));
	model.addAttribute("bizId", supplyBatchQuery.getBizId());
	if (supplyBatchQuery.getStartGmtCreate() == null) {
		//设置到上一个月为开始时间
	    supplyBatchQuery.setStartGmtCreate(DateTool.beforeDay(new Date(), 30));
	}
	if (supplyBatchQuery.getEndGmtCreate() == null) {
		//设置到当前时间为结束时间
	    supplyBatchQuery.setEndGmtCreate(new Date());
	}

	Result<List<SupplyBatch>> result = supplyBatchService.querySupplyBatch(supplyBatchQuery);
	if (result.isSuccess()) {
	    model.addAttribute("supplyBatchList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/querySupplyBatch";
    }

    @ModelAttribute("upStreamList")
    public Map<Long, String> upStreamList() {
	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// Map<Long, String> map = new HashMap<Long, String>();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// for (UserInfo user : usUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// }
	// return map;
	return localCachedService.getUpStreamUser();
    }

    @ModelAttribute("operList")
    public Map<Long, String> operList() {
	// Result<List<UserInfo>> adminUserResult = userService.queryUserInfoAdmin();
	// Map<Long, String> map = new HashMap<Long, String>();
	// if (adminUserResult.isSuccess() && adminUserResult.getModule() != null) {
	// for (UserInfo user : adminUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// }
	// return map;
	return localCachedService.getAdminUser();
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.SupplyBatch.STATUS_INIT + "", Constants.SupplyBatch.STATUS_INIT_DESC);
	map.put(Constants.SupplyBatch.STATUS_SUCCESS + "", Constants.SupplyBatch.STATUS_SUCCESS_DESC);
	map.put(Constants.SupplyBatch.STATUS_FAILED + "", Constants.SupplyBatch.STATUS_FAILED_DESC);
	return map;
    }
	//商品状态的填充
    @ModelAttribute("resultList")
    public Map<String, String> resultList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.SupplyBatch.RESULT_INIT + "", Constants.SupplyBatch.RESULT_INIT_DESC);
	map.put(Constants.SupplyBatch.RESULT_NORMAL + "", Constants.SupplyBatch.RESULT_NORMAL_DESC);
	map.put(Constants.SupplyBatch.RESULT_SUCCESS + "", Constants.SupplyBatch.RESULT_SUCCESS_DESC);
	map.put(Constants.SupplyBatch.RESULT_FAILED + "", Constants.SupplyBatch.RESULT_FAILED_DESC);
	map.put(Constants.SupplyBatch.RESULT_PARTS + "", Constants.SupplyBatch.RESULT_PARTS_DESC);
	map.put(Constants.SupplyBatch.RESULT_CLOSE + "", Constants.SupplyBatch.RESULT_CLOSE_DESC);
	return map;
    }
}
