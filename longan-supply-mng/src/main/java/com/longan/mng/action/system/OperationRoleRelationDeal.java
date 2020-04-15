package com.longan.mng.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.AuthService;
import com.longan.biz.core.OperationInfoService;
import com.longan.biz.core.RoleInfoService;
import com.longan.biz.dao.CommitOperationRoleRelationDao;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.MainOperation;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
public class OperationRoleRelationDeal extends BaseController {
    @Resource
    private OperationInfoService operationInfoService;

    @Resource
    private AuthService authService;

    @Resource
    private CommitOperationRoleRelationDao commitOperationRoleRelationDao;

    @Resource
    private RoleInfoService roleInfoService;

    @RequestMapping(value = "system/operationRoleRelationSet", method = RequestMethod.GET)
    public void onEditIndex(@RequestParam("id") Integer roleId, Model model) {
	Result<List<OperationInfo>> operationInfoResult = operationInfoService// 查询出operation_info的所有信息
		.queryAllOperationInfo();
	List<OperationInfo> operationInfoList = null;
	if (operationInfoResult.isSuccess()) {
	    operationInfoList = operationInfoResult.getModule();
	} else {
	    super.setError(model, operationInfoResult.getResultMsg());
	}
	List<OperationInfo> mainOperationInfoList = new ArrayList<OperationInfo>();
	List<OperationInfo> subOperationInfoList = new ArrayList<OperationInfo>();
	for (OperationInfo operationInfo : operationInfoList) {
	    if (operationInfo.getBelongMenu() == null) {
		mainOperationInfoList.add(operationInfo);
	    } else {
		subOperationInfoList.add(operationInfo);
	    }
	}
	List<MainOperation> mainOperationList = new ArrayList<MainOperation>();
	for (OperationInfo mainOperationInfo : mainOperationInfoList) {
	    MainOperation mainOperation = new MainOperation();
	    List<OperationInfo> subList = new ArrayList<OperationInfo>();
	    for (OperationInfo subOperationInfo : subOperationInfoList) {
		if ((subOperationInfo.getBelongMenu() + "").equals(mainOperationInfo.getId() + "")) {
		    subList.add(subOperationInfo);
		}
	    }
	    if (subList.isEmpty()) {
		continue;
	    }
	    mainOperation.setMainOperationInfo(mainOperationInfo);
	    mainOperation.setSubOperationInfoList(subList);
	    mainOperationList.add(mainOperation);
	}
	model.addAttribute("mainOperationList", mainOperationList);
	Result<Set<Integer>> roleOperationSetResult = authService.getAuthIdByRoleId(roleId);
	model.addAttribute("roleOperationSet", roleOperationSetResult.getModule());
	model.addAttribute("roleId", roleId);
	Result<RoleInfo> result = roleInfoService.getRoleInfoById(roleId);
	model.addAttribute("roleDesc", result.getModule().getRoleDesc());
    }

    @RequestMapping(value = "system/operationRoleRelationSet", method = RequestMethod.POST)
    public String onPostAdd(@RequestParam("requestParam") String requestParam, @RequestParam("roleId") Integer roleId,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	List<OperationRoleRelation> operationRoleRelationList = new ArrayList<OperationRoleRelation>();
	String[] str = requestParam.split(",");
	StringBuffer haveOperation = new StringBuffer();
	for (String s : str) {
	    if (StringUtils.isEmpty(s)) {
		continue;
	    }
	    OperationRoleRelation operationRoleRelation = new OperationRoleRelation();
	    operationRoleRelation.setOperationId(Integer.parseInt(s));
	    operationRoleRelation.setRoleId(roleId);
	    operationRoleRelationList.add(operationRoleRelation);

	    haveOperation.append(" " + s);
	}
	OperationRoleRelation newer = new OperationRoleRelation();
	newer.setHaveOperation(haveOperation.toString());
	newer.setRoleId(roleId);
	try {
	    Result<Boolean> result = commitOperationRoleRelationDao.EditOperationRoleRelation(operationRoleRelationList, roleId);
	    if (result.isSuccess()) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
		OperationLog operationLog = OperLogUtils.operationLogDeal(null, newer, userInfo, map.get("moduleName"), null,
			map.get("loginIp"));
		operationLogService.createOperationLog(operationLog);
		super.alertSuccess(model);
	    } else {
		super.alertError(model, result.getResultMsg());
	    }
	} catch (Exception e) {
	    logger.error("EditOperationRoleRelation error ", e);
	}
	return "system/queryRoleInfo";
    }
}