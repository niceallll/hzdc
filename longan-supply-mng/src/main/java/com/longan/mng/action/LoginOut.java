package com.longan.mng.action;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.mng.action.common.BaseController;
import com.longan.mng.utils.Constants;

@Controller
@RequestMapping("loginOut")
public class LoginOut extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public String onRequest(HttpSession session) {
	session.removeAttribute(Constants.SESSION_USER);
	return "redirect:index.do";
    }
}
