package com.longan.mng.action.third;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.func.PddTokenService;
import com.longan.biz.utils.Utils;
import com.longan.mng.action.common.BaseController;

@Controller
public class AccessCode extends BaseController {
    private static Boolean pdd_code = Utils.getBoolean("pdd.code");

    @Resource
    private PddTokenService pddTokenService;

    @RequestMapping("third/pddCode")
    public void pddCode(String code) {
	if (pdd_code) {
	    boolean success = pddTokenService.accessToken(code);
	    if (success) {
		pdd_code = false;
		logger.warn("获得pdd token成功");
	    } else {
		logger.warn("获得pdd token失败");
	    }
	} else {
	    logger.error("已获得pdd token、非正常请求");
	}
    }
}
