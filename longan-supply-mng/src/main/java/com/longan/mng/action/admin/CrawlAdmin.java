package com.longan.mng.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longan.biz.cached.MemcachedService;
import com.longan.biz.domain.Result;
import com.longan.biz.pojo.PddCacheToken;
import com.longan.biz.pojo.SxydCacheToken;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.client.remote.domain.CrawlLogin;
import com.longan.client.remote.service.CallCrawlService;
import com.longan.client.remote.service.CallMessageService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.response.AjaxResponse;
import com.longan.mng.form.CrawlForm;

@Controller
@RequestMapping(value = "crawl/httpDeal")
public class CrawlAdmin extends BaseController {
    private final static List<String> errorStatus = new ArrayList<String>();
    static {
	for (int i = 0; i < 10; i++) {
	    errorStatus.add("错误");
	}
    }

    @Resource
    private CallCrawlService callCrawlService;

    @Resource
    private CallMessageService callMessageService;

    @RequestMapping(params = "requestType=list")
    public String crawlList(Model model) {
	Result<List<String>> zsydResult = callCrawlService.getStatus(1);
	if (zsydResult.isSuccess()) {
	    model.addAttribute("zsydStatus", zsydResult.getModule());
	} else {
	    model.addAttribute("zsydStatus", errorStatus);
	}

	Result<List<String>> wzydResult = callCrawlService.getStatus(2);
	if (wzydResult.isSuccess()) {
	    model.addAttribute("wzydStatus", wzydResult.getModule());
	} else {
	    model.addAttribute("wzydStatus", errorStatus);
	}

	Result<List<String>> ynltResult = callCrawlService.getStatus(3);
	if (ynltResult.isSuccess()) {
	    model.addAttribute("ynltStatus", ynltResult.getModule());
	} else {
	    model.addAttribute("ynltStatus", errorStatus);
	}
	return "/admin/crawlList";
    }

    @RequestMapping(params = "requestType=index")
    public String crawlIndex(Integer pos, Integer area, Model model) {
	String crawlUrl;
	if (area == 1) {
	    crawlUrl = "/admin/index/zsydIndex";
	} else if (area == 2) {
	    crawlUrl = "/admin/index/nxydIndex";
	} else if (area == 3) {
	    crawlUrl = "/admin/index/ynltIndex";
	} else {
	    alertError(model, "区域编号错误");
	    return crawlList(model);
	}

	Result<CrawlLogin> result = callCrawlService.getRand(area, pos);
	if (result.isSuccess()) {
	    model.addAttribute("crawlLogin", result.getModule());
	} else {
	    alertError(model, result.getResultMsg());
	}
	return crawlUrl;
    }

    @RequestMapping(params = "requestType=message")
    public @ResponseBody
    AjaxResponse crawlMessage(CrawlForm crawlForm) {
	AjaxResponse response = new AjaxResponse();
	CrawlLogin crawlLogin = new CrawlLogin();
	crawlLogin.setPos(crawlForm.getPos());
	crawlLogin.setUser(crawlForm.getUser());
	crawlLogin.setPwd(crawlForm.getPwd());
	crawlLogin.setRand(crawlForm.getRand());
	Result<Boolean> result = callCrawlService.getMessage(crawlForm.getArea(), crawlLogin);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(params = "requestType=balance")
    public @ResponseBody
    AjaxResponse crawlBalance(Integer pos, Integer area) {
	AjaxResponse response = new AjaxResponse();
	Result<String> result = callCrawlService.getBalance(area, pos);
	if (result.isSuccess()) {
	    response.setSuccess();
	    response.setModule(result.getModule());
	} else {
	    response.setErrorMsg(result.getResultMsg());
	}
	return response;
    }

    @RequestMapping(params = "requestType=login")
    public String crawlLogin(CrawlForm crawlForm, Model model) {
	CrawlLogin crawlLogin = new CrawlLogin();
	crawlLogin.setPos(crawlForm.getPos());
	crawlLogin.setUser(crawlForm.getUser());
	crawlLogin.setPwd(crawlForm.getPwd());
	crawlLogin.setRand(crawlForm.getRand());
	crawlLogin.setMsg(crawlForm.getMsg());

	Result<Boolean> result = callCrawlService.login(crawlForm.getArea(), crawlLogin);
	if (result.isSuccess() && result.getModule()) {
	    model.addAttribute("resultMsg", "操作成功、爬网开始");
	} else {
	    model.addAttribute("resultMsg", result.getResultMsg());
	}
	return "/admin/crawlResult";
    }

    @RequestMapping(params = "requestType=logout")
    public String crawlLogout(Integer area, Model model) {
	Result<Boolean> result = callCrawlService.logout(area);
	if (result.isSuccess() && result.getModule()) {
	    alertSuccess(model, "httpDeal.do?requestType=list");
	} else {
	    alertError(model, result.getResultMsg());
	}
	return "/admin/crawlList";
    }

    @Resource
    private MemcachedService memcachedService;

    @RequestMapping(params = "requestType=cachedInit")
    public @ResponseBody
    AjaxResponse cachedIn() {
	AjaxResponse response = new AjaxResponse();
	Long count = memcachedService.getLongValue(Constants.CacheKey.MOBILE_CHECK_KEY);
	if (count == null) {
	    count = 0l;
	    memcachedService.initCount(Constants.CacheKey.MOBILE_CHECK_KEY, 0, count);
	}
	response.setSuccess();
	response.setModule(count);
	return response;
    }

    @RequestMapping(params = "requestType=cachedInc")
    public @ResponseBody
    AjaxResponse cachedInc(Integer icount, String cacheCheck) {
	AjaxResponse response = new AjaxResponse();
	if (!checkCachePwd(cacheCheck)) {
	    response.setErrorMsg("非法增加");
	    return response;
	}
	if (icount == null) {
	    response.setErrorMsg("数字是空");
	    return response;
	}

	memcachedService.inc(Constants.CacheKey.MOBILE_CHECK_KEY, icount);
	response.setSuccess();
	response.setModule(memcachedService.get(Constants.CacheKey.MOBILE_CHECK_KEY));
	return response;
    }

    @RequestMapping(params = "requestType=cachedDec")
    public @ResponseBody
    AjaxResponse cachedDec(Integer dcount, String cacheCheck) {
	AjaxResponse response = new AjaxResponse();
	if (!checkCachePwd(cacheCheck)) {
	    response.setErrorMsg("非法减少");
	    return response;
	}
	if (dcount == null) {
	    response.setErrorMsg("数字是空");
	    return response;
	}

	memcachedService.dec(Constants.CacheKey.MOBILE_CHECK_KEY, dcount);
	response.setSuccess();
	response.setModule(memcachedService.get(Constants.CacheKey.MOBILE_CHECK_KEY));
	return response;
    }

    @RequestMapping(params = "requestType=cachedPdd")
    public @ResponseBody
    AjaxResponse cachedPdd(String cacheCheck) {
	AjaxResponse response = new AjaxResponse();
	if (!checkCachePwd(cacheCheck)) {
	    response.setErrorMsg("非法查询");
	    return response;
	}

	Object object = memcachedService.get(Constants.CacheKey.PDD_TOKEN_KEY);
	if (object == null) {
	    response.setErrorMsg("pdd token是空");
	} else {
	    PddCacheToken token = (PddCacheToken) object;
	    response.setSuccess();
	    response.setModule("token：" + token.getAccessToken() + "，refreshToken：" + token.getRefreshToken() + "，deadline："
		    + DateTool.parseDates(token.getDeadline()) + "，expiresIn：" + token.getExpiresIn());
	}
	return response;
    }

    @RequestMapping(params = "requestType=cachedSxyd")
    public @ResponseBody
    AjaxResponse cachedSxyd(String cacheCheck) {
	AjaxResponse response = new AjaxResponse();
	if (!checkCachePwd(cacheCheck)) {
	    response.setErrorMsg("非法查询");
	    return response;
	}

	Object object = memcachedService.get(Constants.CacheKey.SXYD_TOKEN_KEY);
	if (object == null) {
	    response.setErrorMsg("sxyd token是空");
	} else {
	    SxydCacheToken token = (SxydCacheToken) object;
	    response.setSuccess();
	    response.setModule("token：" + token.getAccessToken() + "，deadline：" + DateTool.parseDates(token.getDeadline())
		    + "，expiresTime：" + token.getExpiresTime());
	}
	return response;
    }

    @RequestMapping(params = "requestType=setPddToken")
    public @ResponseBody
    AjaxResponse setPddToken(String cacheCheck, String accessToken, String refreshToken) {
	AjaxResponse response = new AjaxResponse();
	if (!checkCachePwd(cacheCheck)) {
	    response.setErrorMsg("非法设置");
	    return response;
	}
	if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(refreshToken)) {
	    response.setErrorMsg("参数是空");
	    return response;
	}

	PddCacheToken token = new PddCacheToken();
	token.setAccessToken(accessToken);
	token.setRefreshToken(refreshToken);
	token.setExpiresIn(18000);
	token.setDeadline(18000);
	memcachedService.set(Constants.CacheKey.PDD_TOKEN_KEY, 0, token);
	response.setSuccess();
	response.setModule("设置成功");
	return response;
    }

    @RequestMapping(params = "requestType=smsTest")
    public @ResponseBody
    AjaxResponse smsTest(String smsCheck, String smsMobile, String smsText) {
	AjaxResponse response = new AjaxResponse();
	if (!checkSmsPwd(smsCheck)) {
	    response.setErrorMsg("非法测试");
	    return response;
	}
	if (StringUtils.isBlank(smsMobile) || StringUtils.isBlank(smsText)) {
	    response.setErrorMsg("参数是空");
	}

	try {
	    callMessageService.sendSmsOnly(smsMobile, smsText);
	    response.setSuccess();
	    response.setModule("发送成功");
	} catch (Exception ex) {
	    response.setModule("发送异常");
	}
	return response;
    }

    private boolean checkCachePwd(String cacheCheck) {
	if ("51cachedata".equals(cacheCheck)) {
	    return true;
	}
	return false;
    }

    private boolean checkSmsPwd(String smsCheck) {
	if ("51testsms".equals(smsCheck)) {
	    return true;
	}
	return false;
    }
}
