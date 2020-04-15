package com.longan.mng.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("error/authError")
public class AuthError {
    @RequestMapping(method = RequestMethod.GET)
    public String onRequest() {
	return "error/autherror";
    }
}
