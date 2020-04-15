package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.AcctService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.PayOrderService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.dao.AcctInfoDAO;
import com.longan.biz.dao.CommitPayDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.Item;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Md5Encrypt;

public class AcctServiceImpl extends BaseService implements AcctService {
    @Resource
    private ItemService itemService;

    @Resource
    private AcctInfoDAO acctInfoDAO;

    @Resource
    private CommitPayDAO commitPayDAO;

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private ChargeOrderService chargeOrderService;

    @Resource
    private RefundOrderService refundOrderService;

    private final static String KEY = "LONGONG";
    private final static String SPLIT = "_";

    private final static ExecutorService es = Executors.newFixedThreadPool(4);

    @Override
    public Result<AcctInfo> getAcctInfo(Long acctId) {
	Result<AcctInfo> result = new Result<AcctInfo>();
	try {
	    AcctInfo acctInfo = acctInfoDAO.selectByPrimaryKey(acctId);
	    if (acctInfo != null) {
		result.setSuccess(true);
		result.setModule(acctInfo);
	    } else {
		result.setResultMsg("没有该账户信息 ");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("查询账户信息失败  " + " msg :" + e.getMessage());
	    logger.error("getAcctInfo error acctId = " + acctId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> checkAcct(AcctInfo acctInfo) {
	Result<Boolean> result = new Result<Boolean>();
	result.setSuccess(true);
	result.setModule(false);
	if (acctInfo == null) {
	    result.setResultMsg("acctInfo is null");
	    return result;
	}
	if (acctInfo.getStatus() != Constants.AcctInfo.STATUS_NORMAL) {
	    result.setResultMsg("账户状态不正常");
	    return result;
	}

	boolean flag = checkVerificationCode(acctInfo);
	result.setModule(flag);
	if (!flag) {
	    result.setResultMsg("账户验证失败");
	    logger.error("verifiy error acctId : " + acctInfo.getId());
	}
	return result;
    }

    private boolean checkVerificationCode(AcctInfo acctInfo) {
	return StringUtils.isNotEmpty(acctInfo.getVerificationCode())
		&& getVerificationCode(acctInfo).equals(acctInfo.getVerificationCode());
    }

    @Override
    public String getVerificationCode(AcctInfo acctInfo) {
	String sourceStr = (acctInfo.getLastTradeBalance() == null ? 0 : acctInfo.getLastTradeBalance()) + SPLIT
		+ (acctInfo.getBalance() == null ? 0 : acctInfo.getBalance()) + SPLIT + KEY;
	return Md5Encrypt.md5(sourceStr);
    }

    @Override
    public Result<Boolean> checkBalanceAcct(AcctInfo acctInfo, Item item) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	Result<Integer> priceResult = itemService.getSalesPrice(item, acctInfo);
	if (!priceResult.isSuccess()) {
	    result.setResultMsg(priceResult.getResultMsg());
	    return result;
	}
	Integer price = priceResult.getModule();
	result.setSuccess(true);

	boolean flag = acctInfo.getBalance() != null && acctInfo.getBalance() > price;
	result.setModule(flag);
	if (!flag) {
	    result.setResultMsg("账户余额不足");
	}
	return result;
    }

    @Override
    public Result<Boolean> deductMoney(Long payOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	try {
	    Result<Boolean> commitResult = commitPayDAO.deductMoney(payOrderId);
	    if (commitResult.getModule() != null && commitResult.getModule()) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg(commitResult.getResultMsg());
	    }
	} catch (Exception e) {
	    result.setResultMsg("扣款失败  " + " msg :" + e.getMessage());
	    logger.error("deductMoney error payOrderId = " + payOrderId, e);
	    // 原先异步更新，改成同步更新
	    payOrderService.updatePayOrderWhenException(payOrderId, e.getMessage());
	}
	return result;
    }

    @Override
    public Result<Boolean> addMoney(ChargeOrder chargeOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Result<Boolean> commitResult = commitPayDAO.addMoney(chargeOrder);
	    if (commitResult.getModule() != null && commitResult.getModule()) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg(commitResult.getResultMsg());
	    }
	} catch (Exception e) {
	    result.setResultMsg("加款失败  " + " msg :" + e.getMessage());
	    logger.error("addMoney error chargeOrderId = " + chargeOrder.getId(), e);
	    AsyncUpdate asyncUpdate = new AsyncUpdate(Constants.AcctLog.BILL_TYPE_CHARGE_ORDER, chargeOrder.getId(),
		    e.getMessage());
	    es.execute(asyncUpdate);
	}
	return result;
    }

    @Override
    public Result<Boolean> refundMoney(Long refundOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Result<Boolean> commitResult = commitPayDAO.refundMoney(refundOrderId);
	    if (commitResult.getModule() != null && commitResult.getModule()) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg(commitResult.getResultMsg());
	    }
	} catch (Exception e) {
	    result.setResultMsg("退款失败  " + " msg :" + e.getMessage());
	    logger.error("refundMoney error refundOrderId = " + refundOrderId, e);
	    AsyncUpdate asyncUpdate = new AsyncUpdate(Constants.AcctLog.BILL_TYPE_REFUND_ORDER, refundOrderId, e.getMessage());
	    es.execute(asyncUpdate);
	}
	return result;
    }

    @Override
    public Result<Long> addAcct(AcctInfo acctInfo) {
	Result<Long> result = new Result<Long>();
	if (acctInfo == null) {
	    result.setResultMsg("acctInfo is null");
	    return result;
	}

	// 初始化余额
	acctInfo.setBalance(0L);
	acctInfo.setLastTradeBalance(0L);
	acctInfo.setVerificationCode(getVerificationCode(acctInfo));
	try {
	    Long acctInfoId = acctInfoDAO.insert(acctInfo);
	    result.setSuccess(true);
	    result.setModule(acctInfoId);
	} catch (SQLException e) {
	    result.setResultMsg("创建账户失败  msg : " + e.getMessage());
	    logger.error("addAcct error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateAcct(AcctInfo acctInfo) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (acctInfo == null) {
	    result.setResultMsg("acctInfo is null");
	    return result;
	}
	try {
	    acctInfoDAO.updateByPrimaryKeySelective(acctInfo);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    result.setResultMsg("更新账户失败  msg : " + e.getMessage());
	    logger.error("addAcct error acctInfoId = " + acctInfo.getId(), e);
	}
	return result;
    }

    class AsyncUpdate implements Runnable {
	private Integer updateType;
	private Long id;
	private String errorMsg;

	public Integer getUpdateType() {
	    return updateType;
	}

	public void setUpdateType(Integer updateType) {
	    this.updateType = updateType;
	}

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public String getErrorMsg() {
	    return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
	    this.errorMsg = errorMsg;
	}

	public AsyncUpdate(Integer updateType, Long id, String errorMsg) {
	    this.updateType = updateType;
	    this.id = id;
	    this.errorMsg = errorMsg;
	}

	@Override
	public void run() {
	    if (updateType == Constants.AcctLog.BILL_TYPE_PAY_ORDER) {
		payOrderService.updatePayOrderWhenException(id, errorMsg);
	    } else if (updateType == Constants.AcctLog.BILL_TYPE_CHARGE_ORDER) {
		chargeOrderService.updateChargeOrderWhenException(id, errorMsg);
	    } else if (updateType == Constants.AcctLog.BILL_TYPE_REFUND_ORDER) {
		refundOrderService.updateRefundOrderWhenException(id, errorMsg);
	    }
	}

    }

    @Override
    public Result<Boolean> subMoney(CashOrder cashOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Result<Boolean> commitResult = commitPayDAO.subMoney(cashOrder);
	    if (commitResult.getModule() != null && commitResult.getModule()) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg(commitResult.getResultMsg());
	    }
	} catch (Exception e) {
	    result.setResultMsg("扣款失败  " + " msg :" + e.getMessage());
	    logger.error("subMoney error cashOrderId = " + cashOrder.getId(), e);
	    AsyncUpdate asyncUpdate = new AsyncUpdate(Constants.AcctLog.BILL_TYPE_CASH_ORDER, cashOrder.getId(), e.getMessage());
	    es.execute(asyncUpdate);
	}
	return result;
    }
}
