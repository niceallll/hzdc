package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.AreaInfoService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.SmsOrderService;
import com.longan.biz.core.UserService;
import com.longan.biz.dao.SmsNotifyDAO;
import com.longan.biz.dao.SmsOrderDAO;
import com.longan.biz.dao.SmsSupplyDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderExample;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.SmsOrderCount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.SmsUtils;
import com.longan.biz.utils.SwitchConstant;

public class SmsOrderServiceImpl extends BaseService implements SmsOrderService {
    @Resource
    private SmsOrderDAO smsOrderDAO;

    @Resource
    private SmsSupplyDAO smsSupplyDAO;

    @Resource
    private SmsNotifyDAO smsNotifyDAO;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private ItemService itemService;

    @Resource
    private UserService userService;

    @Resource
    private AreaInfoService areaInfoService;

    @Override
    public Result<SmsOrder> smsOrderPreCheck(SmsOrder smsOrder) {
	Result<SmsOrder> result = new Result<SmsOrder>();
	result.setModule(smsOrder);

	// 用户相关查询和校验
	UserInfo userInfo = localCachedService.getUserInfo(smsOrder.getUserId());
	if (userInfo == null || !userInfo.isDownStreamUser()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_USER);
	    result.setResultMsg(Constants.ErrorCode.DESC_ERROR_USER);
	    return result;
	}

	Result<Boolean> checkUserInfo = userService.checkUserInfo(userInfo);
	if (!checkUserInfo.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_USER);
	    result.setResultMsg(checkUserInfo.getResultMsg());
	    return result;
	}

	// 权限判断
	Set<Integer> bizSet = localCachedService.getAuthBizByUserId(userInfo.getId());
	if (bizSet == null || !bizSet.contains(smsOrder.getBizId())) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_AUTH);
	    result.setResultMsg(Constants.ErrorCode.DESC_ERROR_AUTH);
	    return result;
	}

	// 重复流水号校验
	Result<Boolean> checkDownStreamSerialno = checkDownstreamSerialno(smsOrder);
	if (!checkDownStreamSerialno.isSuccess() || !checkDownStreamSerialno.getModule()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SERIALNO);
	    result.setResultMsg(checkDownStreamSerialno.getResultMsg());
	    return result;
	}

	// 充值手机号码查询
	Result<Boolean> checkUidResult = checkUid(smsOrder);
	if (!checkUidResult.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_PHONE_AREA);
	    result.setResultMsg(checkUidResult.getResultMsg());
	    return result;
	}

	// 检查商品以及供货商品
	Result<Boolean> itemCheckResult = itemService.checkItemStatus(smsOrder.getItemId());
	if (!itemCheckResult.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM);
	    result.setResultMsg(itemCheckResult.getResultMsg());
	    return result;
	}

	// 短信数量先按标准规则计算、上游接口提交后再更新
	int smsCount = SmsUtils.totalCount(smsOrder.getUids(), smsOrder.getTexts());
	smsOrder.setStatus(Constants.BizOrder.STATUS_CHARGING);
	smsOrder.setAmount(smsOrder.getItemPrice() * smsCount + 0l);
	smsOrder.setUidCount(SmsUtils.itemCount(smsOrder.getUids()));
	smsOrder.setTotalCount(smsCount);
	smsOrder.setSuccCount(smsCount);
	smsOrder.setFailCount(0);

	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<SmsOrderCount> querySmsOrderSum(SmsOrderQuery smsOrderQuery) {
	Result<SmsOrderCount> result = new Result<SmsOrderCount>();
	if (smsOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsOrderCount count = smsOrderDAO.querySumCount(smsOrderQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单统计失败    msg: " + e.getMessage());
	    logger.error("querySmsOrderSum error ", e);
	}
	return result;
    }

    @Override
    public Result<SmsOrderCount> querySmsSupplySum(SmsSupplyQuery smsSupplyQuery) {
	Result<SmsOrderCount> result = new Result<SmsOrderCount>();
	if (smsSupplyQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsOrderCount count = smsSupplyDAO.querySumCount(smsSupplyQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("供货单统计失败    msg: " + e.getMessage());
	    logger.error("querySmsSupplySum error ", e);
	}
	return result;
    }

    @Override
    public Result<SmsOrder> createSmsOrder(SmsOrder smsOrder) {
	Result<SmsOrder> result = new Result<SmsOrder>();
	result.setModule(smsOrder);
	if (smsOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    smsOrder.setGmtCreate(new Date());
	    Long id = smsOrderDAO.insert(smsOrder);
	    if (id != null) {
		smsOrder.setId(id);
		result.setSuccess(true);
		result.setModule(smsOrder);
	    } else {
		result.setResultMsg("创建订单失败");
	    }
	} catch (SQLException ex) {
	    if (ex.getMessage().contains("index_user_serialno")) {
		result.setResultCode(Constants.ErrorCode.CODE_ERROR_SERIALNO);
		result.setResultMsg("重复流水号");
	    } else {
		result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
		result.setResultMsg("创建订单失败  msg: " + ex.getMessage());
	    }
	    logger.error("createSmsOrder error", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateSmsOrder(SmsOrder smsOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null || smsOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    if (smsOrder.getStatus() != null && smsOrder.getStatus() == Constants.BizOrder.STATUS_SUCCESS) {
		// 记录充值时间。
		if (smsOrder.getGmtCreate() == null) {
		    Result<SmsOrder> queryResult = getSmsOrderById(smsOrder.getId());
		    if (queryResult.getModule() != null) {
			smsOrder.setCostTime(SmsUtils.computCostTime(queryResult.getModule().getGmtCreate()));
		    }
		} else {
		    smsOrder.setCostTime(SmsUtils.computCostTime(smsOrder.getGmtCreate()));
		}
	    }
	    int row = smsOrderDAO.updateByPrimaryKeySelective(smsOrder);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新定单失败 。订单id : " + smsOrder.getId());
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("更新定单失败  smsOrderId : " + smsOrder.getId());
	    logger.error("updateSmsOrder error", ex);
	}
	return result;
    }

    @Override
    public Result<SmsOrder> getSmsOrderById(Long smsOrderId) {
	Result<SmsOrder> result = new Result<SmsOrder>();
	if (smsOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsOrder smsOrder = smsOrderDAO.selectByPrimaryKey(smsOrderId);
	    result.setSuccess(true);
	    result.setModule(smsOrder);
	} catch (Exception ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询定单失败 ,数据库异常");
	    logger.error("getSmsOrder error", ex);
	}
	return result;
    }

    @Override
    public Result<SmsOrder> getSmsOrderBySerialno(Long userId, String downstreamSerialno) {
	Result<SmsOrder> result = new Result<SmsOrder>();
	if (userId == null || downstreamSerialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsOrderExample example = new SmsOrderExample();
	    example.createCriteria().andDownstreamSerialnoEqualTo(downstreamSerialno).andUserIdEqualTo(userId);
	    @SuppressWarnings("unchecked")
	    List<SmsOrder> list = smsOrderDAO.selectByExample(example);
	    result.setSuccess(true);
	    if (list != null && list.size() > 0) {
		result.setModule(list.get(0));
	    } else {
		result.setResultMsg("没有该订单信息 下游流水号 ：" + downstreamSerialno);
	    }
	} catch (Exception e) {
	    result.setResultMsg("订单查询失败   下游流水号 : " + downstreamSerialno);
	    logger.error("getSmsOrderBySerialno error", e);
	}
	return result;
    }

    @Override
    public Result<SmsOrder> getSmsOrderByUpstreamSerialno(Long upstreamId, String upstreamSerialno) {
	Result<SmsOrder> result = new Result<SmsOrder>();
	if (upstreamId == null || upstreamSerialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsOrderExample example = new SmsOrderExample();
	    example.createCriteria().andUpstreamSerialnoEqualTo(upstreamSerialno).andUpstreamIdEqualTo(upstreamId);
	    @SuppressWarnings("unchecked")
	    List<SmsOrder> list = smsOrderDAO.selectByExample(example);
	    result.setSuccess(true);
	    if (list != null && list.size() > 0) {
		result.setModule(list.get(0));
	    } else {
		result.setResultMsg("没有该订单信息 上游流水号 ：" + upstreamSerialno);
	    }
	} catch (Exception e) {
	    result.setResultMsg("订单查询失败   上游流水号 : " + upstreamSerialno);
	    logger.error("getSmsOrderByUpstreamSerialno error", e);
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInExport(SmsOrderQuery smsOrderQuery) {
	Result<Integer> result = new Result<Integer>();
	if (smsOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Integer count = smsOrderDAO.countByExport(smsOrderQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("getCountInExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsOrder>> querySmsOrderExport(SmsOrderQuery smsOrderQuery) {
	Result<List<SmsOrder>> result = new Result<List<SmsOrder>>();
	if (smsOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SmsOrder> queryResult = smsOrderDAO.queryByExport(smsOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("querySmsOrderExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsOrder>> getSmsOrderExport(SmsOrderQuery smsOrderQuery) {
	Result<List<SmsOrder>> result = new Result<List<SmsOrder>>();
	if (smsOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SmsOrder> queryResult = smsOrderDAO.selectByExport(smsOrderQuery);
	    if (queryResult.size() > 0) {
		result.setSuccess(true);
		result.setModule(queryResult);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("getSmsOrderExport error ", e);
	}
	return result;
    }

    @Override
    public Result<SmsSupply> createSmsSupply(SmsSupply smsSupply) {
	Result<SmsSupply> result = new Result<SmsSupply>();
	result.setModule(smsSupply);
	if (smsSupply == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    smsSupply.setGmtCreate(new Date());
	    Long id = smsSupplyDAO.insert(smsSupply);
	    if (id != null) {
		smsSupply.setId(id);
		result.setSuccess(true);
		result.setModule(smsSupply);
	    } else {
		result.setResultMsg("创建子定单失败");
	    }
	} catch (SQLException ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建子定单失败  msg: " + ex.getMessage());
	    logger.error("createSmsSupply error", ex);
	}
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> createSmsSupply(List<SmsSupply> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyList == null || supplyList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	for (SmsSupply smsSupply : supplyList) {
	    Result<SmsSupply> supplyResult = createSmsSupply(smsSupply);
	    if (!supplyResult.isSuccess()) {
		throw new RuntimeException(supplyResult.getResultMsg());
	    }
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> updateSmsSupply(SmsSupply smsSupply) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsSupply == null || smsSupply.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    int row = smsSupplyDAO.updateByPrimaryKeySelective(smsSupply);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新子定单失败 。订单id : " + smsSupply.getId());
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("更新子定单失败  smsSupplyId : " + smsSupply.getId());
	    logger.error("updateSmsSupply error", ex);
	}
	return result;
    }

    @Override
    public Result<SmsSupply> getSmsSupplyById(Long smsSupplyId) {
	Result<SmsSupply> result = new Result<SmsSupply>();
	if (smsSupplyId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsSupply smsSupply = smsSupplyDAO.selectByPrimaryKey(smsSupplyId);
	    result.setSuccess(true);
	    result.setModule(smsSupply);
	} catch (Exception ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询子定单失败 ,数据库异常");
	    logger.error("getSmsSupply error", ex);
	}
	return result;
    }

    @Override
    public Result<SmsSupply> getSmsSupplyBySerialno(Long userId, String upstreamSerialno) {
	Result<SmsSupply> result = new Result<SmsSupply>();
	if (userId == null || StringUtils.isBlank(upstreamSerialno)) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsSupply smsSupply = smsSupplyDAO.selectBySerialno(userId, upstreamSerialno);
	    result.setSuccess(true);
	    result.setModule(smsSupply);
	} catch (Exception ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询子定单失败 ,数据库异常");
	    logger.error("getSmsSupplyBySerialno error", ex);
	}
	return result;
    }

    @Override
    public Result<List<SmsSupply>> getSmsSupplyList(Long smsOrderId) {
	Result<List<SmsSupply>> result = new Result<List<SmsSupply>>();
	if (smsOrderId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SmsSupply> supplyList = smsSupplyDAO.selectBySmsOrderId(smsOrderId);
	    result.setSuccess(true);
	    if (supplyList != null && supplyList.size() > 0) {
		result.setModule(supplyList);
	    } else {
		result.setResultMsg("没有该供货单信息 smsOrderId ：" + smsOrderId);
	    }
	} catch (Exception ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询子定单失败 ,数据库异常");
	    logger.error("getSmsSupply error", ex);
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInExport(SmsSupplyQuery smsSupplyQuery) {
	Result<Integer> result = new Result<Integer>();
	if (smsSupplyQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Integer count = smsSupplyDAO.countByExport(smsSupplyQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("供货单查询失败    msg: " + e.getMessage());
	    logger.error("getCountInExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsSupply>> querySmsSupplyExport(SmsSupplyQuery smsSupplyQuery) {
	Result<List<SmsSupply>> result = new Result<List<SmsSupply>>();
	if (smsSupplyQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SmsSupply> queryResult = smsSupplyDAO.queryByExport(smsSupplyQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货单查询失败    msg: " + e.getMessage());
	    logger.error("querySmsSupplyExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<SmsSupply>> getSmsSupplyExport(SmsSupplyQuery smsSupplyQuery) {
	Result<List<SmsSupply>> result = new Result<List<SmsSupply>>();
	if (smsSupplyQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SmsSupply> queryResult = smsSupplyDAO.selectByExport(smsSupplyQuery);
	    if (queryResult.size() > 0) {
		result.setSuccess(true);
		result.setModule(queryResult);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("供货单查询失败    msg: " + e.getMessage());
	    logger.error("getSmsSupplyExport error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> confirmSmsOrder(SmsOrder smsOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null || smsOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	if (smsOrder.getStatus() != Constants.BizOrder.STATUS_SUCCESS && smsOrder.getStatus() != Constants.BizOrder.STATUS_PARTS) {
	    smsOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);
	}

	try {
	    // 记录成功时间。
	    if (smsOrder.getGmtCreate() != null) {
		smsOrder.setCostTime(SmsUtils.computCostTime(smsOrder.getGmtCreate()));
	    }

	    int row = smsOrderDAO.updateByPrimaryKeySelective(smsOrder);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("确认定单失败 。订单id : " + smsOrder.getId());
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("确认定单失败  smsOrderId : " + smsOrder.getId());
	    logger.error("confirmSmsOrder error", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> confirmSmsSupply(SmsSupply smsSupply) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsSupply == null || smsSupply.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	// 记录成功时间。
	if (smsSupply.getGmtCreate() != null) {
	    smsSupply.setCostTime(SmsUtils.computCostTime(smsSupply.getGmtCreate()));
	}
	smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_SUCCESS);
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	statusList.add(Constants.SupplyOrder.STATUS_EXCEPTION);
	result = updateSmsSupplyCheckStatus(smsSupply, statusList);
	return result;
    }

    @Override
    public Result<Boolean> cancelSmsOrder(SmsOrder smsOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null || smsOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	// 记录失败时间。
	if (smsOrder.getGmtCreate() != null) {
	    smsOrder.setCostTime(SmsUtils.computCostTime(smsOrder.getGmtCreate()));
	}
	smsOrder.setStatus(Constants.BizOrder.STATUS_FAILED);
	smsOrder.setFailCount(smsOrder.getFailCount() + smsOrder.getSuccCount());
	smsOrder.setSuccCount(0);
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.BizOrder.STATUS_INIT);
	statusList.add(Constants.BizOrder.STATUS_CHARGING);
	statusList.add(Constants.BizOrder.STATUS_LOCK);
	statusList.add(Constants.BizOrder.STATUS_UNCONFIRMED);
	statusList.add(Constants.BizOrder.STATUS_EXCEPTION);
	result = updateSmsOrderCheckStatus(smsOrder, statusList);
	return result;
    }

    @Override
    public Result<Boolean> cancelSmsSupply(List<SmsSupply> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (supplyList == null || supplyList.size() == 0) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	for (SmsSupply smsSupply : supplyList) {
	    Result<Boolean> updateResult = cancelSmsSupply(smsSupply);
	    if (!updateResult.isSuccess()) {
		logger.error("cancelSmsSupply error smsSupplyId:" + smsSupply.getId());
	    }
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> cancelSmsSupply(SmsSupply smsSupply) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsSupply == null || smsSupply.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	// 记录失败时间。
	if (smsSupply.getGmtCreate() != null) {
	    smsSupply.setCostTime(SmsUtils.computCostTime(smsSupply.getGmtCreate()));
	}
	smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
	List<Integer> statusList = new ArrayList<Integer>();
	statusList.add(Constants.SupplyOrder.STATUS_INIT);
	statusList.add(Constants.SupplyOrder.STATUS_CHARGING);
	statusList.add(Constants.SupplyOrder.STATUS_LOCK);
	statusList.add(Constants.SupplyOrder.STATUS_UNCONFIRMED);
	statusList.add(Constants.SupplyOrder.STATUS_EXCEPTION);
	result = updateSmsSupplyCheckStatus(smsSupply, statusList);
	return result;
    }

    @Override
    public Result<Boolean> updateOrder(SmsOrder smsOrder, List<SmsSupply> supplyList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	updateSmsOrder(smsOrder);
	for (SmsSupply smsSupply : supplyList) {
	    updateSmsSupply(smsSupply);
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    private Result<Boolean> checkUid(SmsOrder smsOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null || smsOrder.getItemId() == null || smsOrder.getUids() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	Item item = localCachedService.getItem(smsOrder.getItemId());
	if (item == null) {
	    result.setResultMsg("不存在该商品");
	    return result;
	}

	StringBuffer sbCode = new StringBuffer();
	String[] uidList = SmsUtils.itemList(smsOrder.getUids());
	for (int i = 0; i < uidList.length; i++) {
	    MobileBelong mobileBelong = null;
	    Result<MobileBelong> mobileBelongResult = areaInfoService.queryProvinceCodeByMobile(uidList[i]);
	    if (mobileBelongResult.isSuccess()) {
		mobileBelong = mobileBelongResult.getModule();
		if (SwitchConstant.CHECK_CARRIER_TYPE_SWITCH) {
		    if (mobileBelong != null) {
			if (!mobileBelong.getCarrierType().equals(item.getCarrierType())) {
			    // logger.warn("手机号和商品运营商不匹配 uid: " + uidList[i]);
			    // result.setResultMsg("手机号和商品运营商不匹配");
			    // return result;

			    // 手机号和商品运营商不匹配，暂不影响主流程，只记录一下
			    logger.warn("手机号和商品运营商不匹配 uid: " + uidList[i]);
			}
		    } else {
			// 查询不到结果，不影响主流程，只记录一下
			logger.warn("checkCarrierType mobileBelong is null uid : " + uidList[i]);
		    }
		}

		if (SwitchConstant.CHECK_AREA_SWITCH && !item.isNationwide()) {
		    if (mobileBelong != null) {
			if (item.getSalesAreaList() != null && !item.getSalesAreaList().contains(mobileBelong.getProvinceCode())) {
			    logger.warn("手机号和商品省域不匹配 手机号: " + uidList[i] + " 手机归属地 ：" + mobileBelong.getProvinceName()
				    + " 商品编号 : " + item.getId());
			    result.setResultMsg("手机号和商品省域不匹配");
			    return result;
			}
		    } else {
			// 查询不到结果，不影响主流程，只记录一下
			logger.warn("checkArea mobileBelong is null uid : " + uidList[i]);
		    }
		}
	    }

	    if (mobileBelong == null) {
		sbCode.append("000000" + Constants.BUY_REQUEST_SPLIT);
	    } else {
		sbCode.append(mobileBelong.getProvinceCode() + Constants.BUY_REQUEST_SPLIT);
	    }
	}

	smsOrder.setProvinces(sbCode.replace(sbCode.length() - 1, sbCode.length(), "").toString());
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @SuppressWarnings("unchecked")
    private Result<Boolean> checkDownstreamSerialno(SmsOrder smsOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null) {
	    result.setResultMsg("smsOrder is null");
	    return result;
	}

	String serialno = smsOrder.getDownstreamSerialno();
	Long userId = smsOrder.getUserId();
	if (serialno == null || userId == null || smsOrder.getBizId() == null) {
	    result.setResultMsg("serialno or userId or bizId is null");
	    return result;
	}

	SmsOrderExample smsOrderExample = new SmsOrderExample();
	smsOrderExample.createCriteria().andDownstreamSerialnoEqualTo(serialno).andUserIdEqualTo(userId);
	try {
	    List<SmsOrder> list = (List<SmsOrder>) smsOrderDAO.selectByExample(smsOrderExample);
	    if (list.size() == 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("重复流水号");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商户流水号重复校验失败    msg: " + e.getMessage());
	    logger.error("checkDownStreamSerialno error userId : " + userId, e);
	}
	return result;
    }

    private Result<Boolean> updateSmsOrderCheckStatus(SmsOrder smsOrder, List<Integer> statusList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsOrder == null || smsOrder.getId() == null || statusList == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    boolean flag = smsOrderDAO.updateSmsOrderCheckStatus(smsOrder, statusList);
	    if (flag) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		logger.warn("订单已被其他线程处理,smsOrderId : " + smsOrder.getId());
		result.setResultMsg("更新订单失败,订单已被其他线程处理");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("更新订单失败 ,数据库异常");
	    logger.error("updateSmsOrderCheckStatus error", e);
	}
	return result;
    }

    private Result<Boolean> updateSmsSupplyCheckStatus(SmsSupply smsSupply, List<Integer> statusList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (smsSupply == null || smsSupply.getId() == null || statusList == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    boolean flag = smsSupplyDAO.updateSmsSupplyCheckStatus(smsSupply, statusList);
	    if (flag) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		logger.warn("供货单已被其他线程处理,smsSupplyId : " + smsSupply.getId());
		result.setResultMsg("更新供货单失败,供货单已被其他线程处理");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("更新供货单失败 ,数据库异常");
	    logger.error("updateSmsSupplyCheckStatus error", e);
	}
	return result;
    }

    @Override
    public Result<SmsNotify> createSmsNotify(SmsNotify smsNotify) {
	Result<SmsNotify> result = new Result<SmsNotify>();
	result.setModule(smsNotify);
	if (smsNotify == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    smsNotify.setGmtCreate(new Date());
	    Long id = smsNotifyDAO.insert(smsNotify);
	    if (id != null) {
		smsNotify.setId(id);
		result.setSuccess(true);
		result.setModule(smsNotify);
	    } else {
		result.setResultMsg("创建通知单失败");
	    }
	} catch (SQLException ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建通知单失败  msg: " + ex.getMessage());
	    logger.error("createSmsNotify error", ex);
	}
	return result;
    }

    @Override
    public Result<SmsNotify> getSmsNotifyById(Long smsNotifyId) {
	Result<SmsNotify> result = new Result<SmsNotify>();
	if (smsNotifyId == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    SmsNotify smsNotify = smsNotifyDAO.selectByPrimaryKey(smsNotifyId);
	    result.setSuccess(true);
	    result.setModule(smsNotify);
	} catch (Exception ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("查询通知单失败 ,数据库异常");
	    logger.error("getSmsNotifyById error", ex);
	}
	return result;
    }
}
