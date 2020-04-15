package com.longan.mng.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.mng.api.response.AjaxResponse;
import com.longan.mng.utils.Constants;

public class AuthFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Resource
    private LocalCachedService localCachedService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
	    throw new ServletException("系统仅支持http请求！");
	}

	if (LoginFilter.noNeedLogin(request)) {
	    filterChain.doFilter(request, response);
	    return;
	}

	HttpSession session = request.getSession(false);
	boolean isAjaxRequest = LoginFilter.isAjaxRequest(request);
	if (session == null) {
	    if (isAjaxRequest) {
		LoginFilter.setNoLoginResponse(response);
		return;
	    }
	    response.sendRedirect(request.getContextPath() + "/index.do");
	    return;
	}

	Object object = session.getAttribute(Constants.SESSION_USER);
	UserInfo user = object == null ? null : (UserInfo) object;
	if (user == null) {
	    if (isAjaxRequest) {
		LoginFilter.setNoLoginResponse(response);
		return;
	    }
	    response.sendRedirect(request.getContextPath() + "/index.do");
	    return;
	}

	if (!hasPermission(user.getId(), request)) {
	    if (isAjaxRequest) {
		setNoAuthResponse(response);
		return;
	    }
	    response.sendRedirect(request.getContextPath() + "/error/authError.do");
	    return;
	}

	session.setAttribute("requestInfoMap", getModuleNameAndIp(request));
	filterChain.doFilter(request, response);
	return;

    }

    private void setNoAuthResponse(HttpServletResponse response) throws IOException {
	response.setCharacterEncoding("UTF-8");
	// response.sendError(HttpStatus.UNAUTHORIZED.value(), "您的权限不够");
	AjaxResponse ajaxResponse = new AjaxResponse();
	ajaxResponse.setErrorMsg("您的权限不够");
	LoginFilter.responseStr(response, ajaxResponse.toString());
    }

    public boolean hasPermission(Long userId, HttpServletRequest request) {
	String path = request.getRequestURI();
	String contextPath = request.getContextPath();
	path = path.replace(contextPath, "");
	if (path.indexOf("/main.do") >= 0 || "/".equals(path)) {
	    return true;
	}

	Set<String> set = localCachedService.getAuthUrlByUserId(userId);
	if (set == null) {
	    return false;
	}
	return set.contains(path);
    }

    public HashMap<String, String> getModuleNameAndIp(HttpServletRequest request) {
	HashMap<String, String> map = new HashMap<String, String>();
	String operationUrl = request.getRequestURI();
	OperationInfo operationInfo = localCachedService.getOperationInfo(operationUrl.substring(4));
	if (operationInfo != null) {
	    map.put("moduleName", operationInfo.getOperationName());// 模块名
	}
	map.put("loginIp", getRemoteIp(request));// 请求的IP
	return map;
    }

    public static String getRemoteIp(HttpServletRequest request) {
	String result = request.getHeader("X-Real-IP");
	if (StringUtils.isEmpty(result)) {
	    result = request.getRemoteAddr();
	}
	return result;
    }
}
