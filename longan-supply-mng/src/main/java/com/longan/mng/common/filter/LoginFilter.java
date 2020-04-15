package com.longan.mng.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.longan.biz.dataobject.UserInfo;
import com.longan.mng.api.response.AjaxResponse;
import com.longan.mng.utils.Constants;

public class LoginFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * 判断是否为Ajax请求
     * 
     * @param request
     *            HttpServletRequest
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
	String path = request.getRequestURI();
	String contextPath = request.getContextPath();
	path = path.replace(contextPath, "");
	return path.startsWith("/api");
	// String requestType = request.getHeader("X-Requested-With");
	// return requestType != null && requestType.equals("XMLHttpRequest");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
	    throw new ServletException("OncePerRequestFilter just supports HTTP requests");
	}

	if (noNeedLogin(request)) {
	    filterChain.doFilter(request, response);
	    return;
	}

	HttpSession session = request.getSession(false);
	boolean isAjaxRequest = isAjaxRequest(request);
	if (session == null) {
	    if (isAjaxRequest) {
		setNoLoginResponse(response);
		return;
	    }
	    PrintWriter out = response.getWriter();
	    String url = request.getContextPath() + "/index.do";
	    out.println("<script language=\"javascript\">");// 利用js跳出iframe。
	    out.println("top.location=\"" + url + "\";");
	    out.println("</script>");
	    return;
	}

	Object object = session.getAttribute(Constants.SESSION_USER);
	UserInfo user = object == null ? null : (UserInfo) object;

	if (user == null) {
	    if (isAjaxRequest) {
		setNoLoginResponse(response);
		return;
	    }
	    PrintWriter out = response.getWriter();
	    String url = request.getContextPath() + "/index.do";
	    out.println("<script language=\"javascript\">");// 利用js跳出iframe。
	    out.println("top.location=\"" + url + "\";");
	    out.println("</script>");
	    return;
	}

	filterChain.doFilter(request, response);
	return;

    }

    public static boolean noNeedLogin(HttpServletRequest request) {
	String url = request.getRequestURI();
	String[] strs = Constants.NO_NEED_LOGIN_URL.split("\\|");
	if (strs != null && strs.length > 0) {
	    for (String str : strs) {
		if (url.indexOf(str) >= 0) {
		    return true;
		}
	    }
	}
	return false;

    }

    public static void setNoLoginResponse(HttpServletResponse response) throws IOException {
	response.setCharacterEncoding("UTF-8");
	// response.sendError(HttpStatus.UNAUTHORIZED.value(), "您的权限不够");
	AjaxResponse ajaxResponse = new AjaxResponse();
	ajaxResponse.setErrorMsg("您已经太长时间没有操作,请刷新页面");
	responseStr(response, ajaxResponse.toString());
    }

    public static void responseStr(HttpServletResponse response, String responseBody) throws IOException {
	response.getWriter().write(responseBody);
	response.getWriter().flush();
    }

}
