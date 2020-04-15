package com.longan.mng.action.biz;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

/**
 * 订单查询

 */
@Controller
public class QueryBizOrder extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("biz/queryBizOrderIndex")//获取到订单跟用户信息的判断
    public String index(@ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session, Model model) {
	// 业务权限判断
	UserInfo userInfo = getUserInfo(session);//获取到用户信息
	if (!checkBizAuth(bizOrderQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("userInfo", userInfo);//传递给前台值
	setSelectValue(userInfo, model, bizOrderQuery);
	bizOrderQuery.setStartGmtCreate(new Date());
	bizOrderQuery.setEndGmtCreate(new Date());
	return "biz/queryBizOrder";
    }
	//查询时候的结果集封装
    @RequestMapping("biz/queryBizOrder")
    public String doQuery(@ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(bizOrderQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}
	//用户信息的封装
	model.addAttribute("userInfo", userInfo);
	if (bizOrderQuery.getId() != null || bizOrderQuery.getPayOrderId() != null) {
	    bizOrderQuery.setEndGmtCreate(null);
	    bizOrderQuery.setStartGmtCreate(null);
	} else {
	    Date date = null;
	    try {
	    	//时间类的封装
		date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	    } catch (ParseException e) {
	    }
	    if (bizOrderQuery.getEndGmtCreate() == null) {
		bizOrderQuery.setEndGmtCreate(date);
	    }
	    if (bizOrderQuery.getStartGmtCreate() == null) {
		bizOrderQuery.setStartGmtCreate(date);
	    }
	    if (!check2Date(bizOrderQuery.getStartGmtCreate(), bizOrderQuery.getEndGmtCreate())) {
		alertErrorNoneBack(model, "订单时间区间不能超过2个月");
		return "biz/queryBizOrder";
	    }
	}

	setSelectValue(userInfo, model, bizOrderQuery);
	Result<List<BizOrder>> result = queryService.queryBizOrderPage(bizOrderQuery);
	if (result.isSuccess()) {
	    List<BizOrder> list = result.getModule();//List<BizOrder>
	    for (BizOrder bizOrder : list) {
		UserInfo user = localCachedService.getUserInfo(bizOrder.getUserId());//本地缓存获取
		bizOrder.setUserName(user.getUserName());
		if (bizOrder.getLockOperId() != null) {
		    UserInfo oper = localCachedService.getUserInfo(bizOrder.getLockOperId());
		    bizOrder.setLockOperName(oper == null ? "" : oper.getUserName());
		}
		if (bizOrder.getDealOperId() != null) {
		    UserInfo oper = localCachedService.getUserInfo(bizOrder.getDealOperId());
		    bizOrder.setDealOperName(oper == null ? "" : oper.getUserName());
		}
		if (StringUtils.isNotBlank(bizOrder.getProvinceCode())) {//省域
		    AreaInfo areaInfo = localCachedService.getProvinceByCode(bizOrder.getProvinceCode());
		    if (areaInfo != null) {
			bizOrder.setUidAreaInfo(areaInfo.getProvinceName());
		    }
		}
		if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {//上游
		    UserInfo traderUserInfo = localCachedService.getUserInfo(Long.parseLong(bizOrder.getUpstreamId()));
		    if (traderUserInfo != null) {
			bizOrder.setUpstreamName(traderUserInfo.getUserName());
		    }
		}
		if (bizOrder.getSupplyType() == null && bizOrder.getItemSupplyId() != null) {
		    ItemSupply itemSupply = localCachedService.getItemSupply(bizOrder.getItemSupplyId());
		    if (itemSupply != null) {
			// 之后改成itemSupplyType
			bizOrder.setSupplyType(itemSupply.getItemSupplyType());
		    }
		}
	    }
	    model.addAttribute("bizOrderList", list);//前台返回的参数
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/queryBizOrder";
    }

    private void setSelectValue(UserInfo userInfo, Model model, BizOrderQuery bizOrderQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizOrderQuery.getBizId()));//获取到对应的订单类型
	model.addAttribute("bizId", bizOrderQuery.getBizId());//订单类型状态
	if (userInfo.isDownStreamUser()) { // 下游只能看自己的订单
	    bizOrderQuery.setUserId(userInfo.getId()); // 设置查询条件
	    Map<Long, String> map = new HashMap<Long, String>();
	    map.put(userInfo.getId(), userInfo.getUserName());//用户id跟用户名存到request中
	    model.addAttribute("downStreamUser", map);//代理商
	} else {
	    // Result<List<UserInfo>> dsUserResult = userService.queryUserInfoDownStream();
	    // if (dsUserResult.isSuccess() && dsUserResult.getModule() != null) {
	    // Map<Long, String> map = new HashMap<Long, String>();
	    // for (UserInfo user : dsUserResult.getModule()) {
	    // map.put(user.getId(), user.getUserName());
	    // }
	    // model.addAttribute("downStreamUser", map);
	    // }
	    model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());//map对象Map<Long, String>
	}

	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// Map<Long, String> map = new HashMap<Long, String>();
	// for (UserInfo user : usUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// model.addAttribute("upStreamUser", map);
	// }
	model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());// Map<Long, String>//上游

	// Result<List<Item>> itemResult = itemService.queryItemList(bizOrderQuery.getBizId());
	// if (itemResult.isSuccess() && itemResult.getModule() != null) {
	// Map<Integer, String> map = new HashMap<Integer, String>();
	// for (Item item : itemResult.getModule()) {
	// map.put(item.getId(), item.getItemName());
	// }
	// model.addAttribute("itemList", map);
	// }
	model.addAttribute("itemList", localCachedService.getItemByBizId(bizOrderQuery.getBizId()));//商品 Map<Integer, String>
    }
	//内部用户的封装
    @ModelAttribute("operList")
    public Map<Long, String> operList() {
	// Result<List<UserInfo>> adminUserResult = userService.queryUserInfoAdmin();
	// Map<Long, String> map = new HashMap<Long, String>();
	// if (adminUserResult.isSuccess() && adminUserResult.getModule() != null) {
	// for (UserInfo user : adminUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// }
	// return map;
	return localCachedService.getAdminUser();
    }

    @ModelAttribute("statusList")//对订单处理状态的封装
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	//Constants.BizOrder 订单处理状态的判断
	map.put(Constants.BizOrder.STATUS_INIT + "", Constants.BizOrder.STATUS_INIT_DESC);
	map.put(Constants.BizOrder.STATUS_CHARGING + "", Constants.BizOrder.STATUS_CHARGING_DESC);
	map.put(Constants.BizOrder.STATUS_SUCCESS + "", Constants.BizOrder.STATUS_SUCCESS_DESC);
	map.put(Constants.BizOrder.STATUS_FAILED + "", Constants.BizOrder.STATUS_FAILED_DESC);
	map.put(Constants.BizOrder.STATUS_PENDING + "", Constants.BizOrder.STATUS_PENDING_DESC);
	map.put(Constants.BizOrder.STATUS_UNCONFIRMED + "", Constants.BizOrder.STATUS_UNCONFIRMED_DESC);
	map.put(Constants.BizOrder.STATUS_EXCEPTION + "", Constants.BizOrder.STATUS_EXCEPTION_DESC);
	return map;
    }

    @ModelAttribute("supplyTypeList")//订单的充值方式
    public Map<String, String> supplyTypeList() {
	Map<String, String> mapType = new LinkedHashMap<String, String>();
	mapType.put(Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_DIRECT_CHARGE + "", Constants.ItemSupply.TYPE_DIRECT_CHARGE_DESC);
	mapType.put(Constants.ItemSupply.TYPE_MAN + "", Constants.ItemSupply.TYPE_MAN_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD + "", Constants.ItemSupply.TYPE_CARD_DESC);
	mapType.put(Constants.ItemSupply.TYPE_CARD_CHARGE + "", Constants.ItemSupply.TYPE_CARD_CHARGE_DESC);
	return mapType;
    }

    @ModelAttribute("provinceList")
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();//Map<String, AreaInfo>
    }

    @ModelAttribute("notifyStatusList")//订单状态
    public Map<String, String> notifyStatusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.BizOrder.NOTIFY_INIT + "", Constants.BizOrder.NOTIFY_INIT_DESC);
	map.put(Constants.BizOrder.NOTIFY_CHARGING + "", Constants.BizOrder.NOTIFY_CHARGING_DESC);
	map.put(Constants.BizOrder.NOTIFY_SUCCESS + "", Constants.BizOrder.NOTIFY_SUCCESS_DESC);
	map.put(Constants.BizOrder.NOTIFY_FAILED + "", Constants.BizOrder.NOTIFY_FAILED_DESC);
	map.put(Constants.BizOrder.NOTIFY_NORMAL + "", Constants.BizOrder.NOTIFY_NORMAL_DESC);
	map.put(Constants.BizOrder.NOTIFY_UNKNOWN + "", Constants.BizOrder.NOTIFY_UNKNOWN_DESC);
	return map;
    }
	@ModelAttribute("memo")
    public Map<String,String>memo(){
    	Map<String,String>map=new LinkedHashMap<String, String>();
    	map.put("memo","");
    	map.put("memo","1");
    	return  map;
	}
}
