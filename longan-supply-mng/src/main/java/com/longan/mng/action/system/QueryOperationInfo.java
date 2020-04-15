package com.longan.mng.action.system;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.OperationInfoService;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping(value = "system/queryOperationInfo")
public class QueryOperationInfo extends BaseController {
    @Resource
    private OperationInfoService operationInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(@ModelAttribute("operationInfoQuery") OperationInfoQuery operationInfoQuery, Model model) {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doQuery(@ModelAttribute("operationInfoQuery") OperationInfoQuery operationInfoQuery, Model model) {
	Map<Integer, String> firstMenuMap = firstMenuList();
	Result<List<OperationInfo>> result = operationInfoService.queryOperationInfoList(operationInfoQuery);
	if (result.isSuccess() && result.getModule() != null) {
	    List<OperationInfo> list = result.getModule();
	    for (OperationInfo operationInfo : list) {
		if (StringUtils.isNotEmpty(operationInfo.getBelongMenu() + "")) {
		    operationInfo.setBelongMenuDesc(firstMenuMap.get(operationInfo.getBelongMenu()));
		}
	    }

	    model.addAttribute("operationInfoList", result.getModule());
	} else {
	    super.setError(model, result.getResultMsg());
	}
    }

    @ModelAttribute("firstMenuList")
    public Map<Integer, String> firstMenuList() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	Result<List<OperationInfo>> result = operationInfoService.queryOperationInfoByType(Constants.OperationInfo.TYPE_CATALOG);
	if (result.isSuccess() && result.getModule() != null) {
	    for (OperationInfo operationInfo : result.getModule()) {
		map.put(operationInfo.getId(), operationInfo.getOperationName());
	    }
	}
	return map;
    }

    @ModelAttribute("typeList")
    public Map<String, String> typeList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.OperationInfo.TYPE_CATALOG + "", Constants.OperationInfo.TYPE_CATALOG_DESC);
	mapType.put(Constants.OperationInfo.TYPE_URL + "", Constants.OperationInfo.TYPE_URL_DESC);
	mapType.put(Constants.OperationInfo.TYPE_BIZ + "", Constants.OperationInfo.TYPE_BIZ_DESC);
	return mapType;
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.OperationInfo.STATUS_NORMAL + "", Constants.OperationInfo.STATUS_NORMAL_DESC);
	map.put(Constants.OperationInfo.STATUS_CANCEL + "", Constants.OperationInfo.STATUS_CANCEL_DESC);
	map.put(Constants.OperationInfo.STATUS_DEL + "", Constants.OperationInfo.STATUS_DEL_DESC);
	return map;
    }
}