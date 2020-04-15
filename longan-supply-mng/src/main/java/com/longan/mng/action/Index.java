package com.longan.mng.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.mng.utils.Constants;

@Controller
@RequestMapping("index")
public class Index {
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model) {
	if (Constants.isProduct()) {
	    model.addAttribute("isProduct", true);
	}
	return "index";
    }
}
