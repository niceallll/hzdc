package com.longan.mng.action.biz;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.ItemService;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.pojo.PddItemStatus;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryItem extends BaseController {
    private static final Long pddUserId = Utils.getLong("pdd.id");

    @Resource
    private ItemService itemService;

    @RequestMapping("biz/queryItem")
    public String doQuery(@ModelAttribute("itemQuery") ItemQuery itemQuery, Model model, HttpSession session) {
//	List countList = new ArrayList();
//	List resultList = new ArrayList();
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (!checkBizAuth(itemQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	model.addAttribute("bizName", Constants.BIZ_MAP.get(itemQuery.getBizId()));
	model.addAttribute("userInfo", userInfo);
	model.addAttribute("hasCombine", hasCombine(itemQuery.getBizId()));
//	if(itemQuery.getSalesAreas()==null) {
		Result<List<Item>> result = itemService.queryItemList(itemQuery);
		if (result.isSuccess()) {
			List<Item> itemList = result.getModule();
			// 代理商显示价格特殊处理//下游代理商处理
			if (userInfo.isDownStreamUser()) {
				//
				AcctInfo acctInfo = localCachedService.getAcctInfoNot4Trade(userInfo.getAcctId());
				if (itemList != null) {
					for (Item item : itemList) {
						//获取商品的状态（）
						Result<Integer> priceResult = itemService.getSalesPrice(item, acctInfo);
						if (priceResult.isSuccess()) {
							//设置商品状态
							item.setItemSalesPrice(priceResult.getModule());
						}
					}
				}
			}

			if (itemList != null) {
				for (Item item : itemList) {
					//拼接操作
					setItemArea(item);
					if (item.getUserId() != null && item.getUserId() == pddUserId.longValue()) {
						//添加一个有过期时间的map
						item.setPddStatus(PddItemStatus.map.get(item.getId()));
					}
				}
			}
//	    //存储的总数据
//		for (Item item : result.getModule()) {
//			countList.add(item);
//		}
//		//分页的数据
//		for (int page = itemQuery.getCurrentPage()*itemQuery.getPageSize();page<(itemQuery.getCurrentPage()+1)*itemQuery.getPageSize(); page++) {
//			resultList.add(countList.get(page));
//		}

			model.addAttribute("itemList", result.getModule());
		} else {
			super.setError(model, result.getResultMsg());
		}
//	}else{
//		for (int i = 0 ; i < itemQuery.getSalesAreas().size(); i++) {
//			itemQuery.setSalesArea(itemQuery.getSalesAreas().get(i));
//			Result<List<Item>> result = itemService.queryItemList(itemQuery);
//			if (result.isSuccess()) {
//				List<Item> itemList = result.getModule();
//				// 代理商显示价格特殊处理//下游代理商处理
//				if (userInfo.isDownStreamUser()) {
//					//
//					AcctInfo acctInfo = localCachedService.getAcctInfoNot4Trade(userInfo.getAcctId());
//					if (itemList != null) {
//						for (Item item : itemList) {
//							//获取商品的状态（）
//							Result<Integer> priceResult = itemService.getSalesPrice(item, acctInfo);
//							if (priceResult.isSuccess()) {
//								//设置商品状态
//								item.setItemSalesPrice(priceResult.getModule());
//							}
//						}
//					}
//				}
//
//				if (itemList != null) {
//					for (Item item : itemList) {
//						//拼接操作
//						setItemArea(item);
//						if (item.getUserId() != null && item.getUserId() == pddUserId.longValue()) {
//							//添加一个有过期时间的map
//							item.setPddStatus(PddItemStatus.map.get(item.getId()));
//						}
//					}
//				}
//			}
//			for (Item item : result.getModule()) {
//				countList.add(item);
//			}
//		}
//		//分页的数据
//		int pageSize =0;
//		if (countList.size()<itemQuery.getPageSize()) {
//			 pageSize = countList.size();
//		}
////		itemQuery
//		int currentPage =itemQuery.getCurrentPage()*itemQuery.getPageSize();
//		System.out.println(currentPage);
//		for (int page =currentPage ;page<pageSize; page++) {
//			resultList.add(countList.get(page));
//		}
//		model.addAttribute("itemList", countList);
//	}
	return "biz/queryItem";
    }


	private Boolean hasCombine(int bizId) {
    	//200 201 202
	return bizId == Constants.BizInfo.CODE_PHONE_UNICOM || bizId == Constants.BizInfo.CODE_PHONE_MOBILE
		|| bizId == Constants.BizInfo.CODE_PHONE_TELECOM;
    }
	//请求前操作的添加
    @ModelAttribute("statusList")
    public Map<Integer, String> statusList() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	map.put(Constants.Item.STATUS_DEL, Constants.Item.STATUS_DEL_DESC);
	map.put(Constants.Item.STATUS_DOWN, Constants.Item.STATUS_DOWN_DESC);
	map.put(Constants.Item.STATUS_INIT, Constants.Item.STATUS_INIT_DESC);
	map.put(Constants.Item.STATUS_UP, Constants.Item.STATUS_UP_DESC);
	return map;
    }

    @ModelAttribute("provinceList")
	//省份集合
    public Map<String, AreaInfo> provinceList() {
	return localCachedService.getProvinceMap();
    }
}
