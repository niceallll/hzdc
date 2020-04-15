package com.longan.mng.action.system;

import java.sql.SQLException;
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
import com.longan.biz.core.RoleInfoService;
import com.longan.biz.dao.CommitUserRoleRelationDao;
import com.longan.biz.dao.UserInfoDAO;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
public class UserRoleRelationDeal extends BaseController {
    @Resource
    private RoleInfoService roleInfoService;

    @Resource
    private AuthService authService;

    @Resource
    private CommitUserRoleRelationDao commitUserRoleRelationDao;

    @Resource
    private UserInfoDAO userInfoDAO;

    @RequestMapping(value = "system/userRoleRelationSet", method = RequestMethod.GET)
    public void onEditIndex(@RequestParam("id") Long userId, Model model) {
	Result<List<RoleInfo>> roleInfoResult = roleInfoService.queryAllRoleInfo();// 查询出所有role_info的信息
	List<RoleInfo> roleInfoList = null;
	if (roleInfoResult.isSuccess()) {
	    roleInfoList = roleInfoResult.getModule();
	} else {
	    super.setError(model, roleInfoResult.getResultMsg());
	}
	Result<Set<Integer>> userRoleSetResult = authService.getAuthIdByUserId(userId);
	model.addAttribute("roleInfoList", roleInfoList);
	model.addAttribute("userRoleSet", userRoleSetResult.getModule());
	model.addAttribute("userId", userId);
	try {
	    UserInfo userInfo = userInfoDAO.selectByPrimaryKey(userId);
	    model.addAttribute("userName", userInfo.getUserName());
	} catch (SQLException e) {
	    logger.error("selectUserInfoByPrimaryKey error ", e);
	}
    }

    @RequestMapping(value = "system/userRoleRelationSet", method = RequestMethod.POST)
    public String onPostAdd(@RequestParam("requestParam") String requestParam, @RequestParam("userId") Long userId,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	List<UserRoleRelation> userRoleRelationList = new ArrayList<UserRoleRelation>();
	String[] str = requestParam.split(",");
	StringBuffer haveRole = new StringBuffer();
	for (String s : str) {
	    if (StringUtils.isEmpty(s)) {
		continue;
	    }
	    UserRoleRelation userRoleRelation = new UserRoleRelation();
	    userRoleRelation.setRoleId(Integer.parseInt(s));
	    userRoleRelation.setUserId(userId);
	    userRoleRelation.setStatus(1);// 默认为正常状态
	    userRoleRelationList.add(userRoleRelation);

	    haveRole.append(" " + s);
	}
	UserRoleRelation newer = new UserRoleRelation();
	newer.setHaveRole(haveRole.toString());
	newer.setUserId(userId);
	try {
	    Result<Boolean> result = commitUserRoleRelationDao.EditUserRoleRelation(userRoleRelationList, userId);
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
	    logger.error("EditUserRoleRelation error ", e);
	}
	return "user/queryUserInfo";
    }
}
