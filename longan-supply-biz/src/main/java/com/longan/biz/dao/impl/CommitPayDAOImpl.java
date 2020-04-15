package com.longan.biz.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.core.AcctService;
import com.longan.biz.core.StockService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dao.AcctInfoDAO;
import com.longan.biz.dao.AcctLogDAO;
import com.longan.biz.dao.BizOrderDAO;
import com.longan.biz.dao.CashOrderDAO;
import com.longan.biz.dao.ChargeOrderDAO;
import com.longan.biz.dao.CommitPayDAO;
import com.longan.biz.dao.PayOrderDAO;
import com.longan.biz.dao.RefundOrderDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderExample;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderExample;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderExample;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class CommitPayDAOImpl implements CommitPayDAO {
    Logger logger = LoggerFactory.getLogger(CommitPayDAOImpl.class);

    @Resource
    private AcctInfoDAO acctInfoDAO;

    @Resource
    private AcctLogDAO acctLogDAO;

    @Resource
    AcctService acctService;

    @Resource
    private PayOrderDAO payOrderDAO;

    @Resource
    private ChargeOrderDAO chargeOrderDAO;

    @Resource
    private RefundOrderDAO refundOrderDAO;

    @Resource
    private BizOrderDAO bizOrderDAO;

    @Resource
    private StockService stockService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private CashOrderDAO cashOrderDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> deductMoney(Long payOrderId) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	PayOrder payOrder = payOrderDAO.selectByPrimaryKey(payOrderId);
	if (payOrder == null) {
	    result.setResultMsg("没有该支付单");
	    return result;
	}
	// 校验支付单状态
	if (payOrder.getStatus() != Constants.PayOrder.STATUS_INIT) {
	    result.setResultMsg("支付单状态校验失败");
	    return result;
	}

	// 获取账户,锁定该行
	AcctInfo acctInfo = acctInfoDAO.selectForUpdate(payOrder.getAcctId());
	if (acctInfo == null) {
	    logger.error("获取账户失败  payOrder Id :" + payOrder.getId());
	    result.setResultMsg("获取账户失败");
	    return result;
	}

	Result<Boolean> checkResult = acctService.checkAcct(acctInfo);
	if (!checkResult.isSuccess() || !checkResult.getModule()) {
	    logger.error("账户安全校验失败  acct Id :" + acctInfo.getId());
	    result.setResultMsg("账户安全校验失败");
	    return result;
	}
	if (payOrder.getAmount() <= 0) {
	    logger.warn("扣款金额不能为0或者负数  payOrderId : " + payOrder.getId());
	    throw new RuntimeException("扣款金额不能为0或者负数");
	}

	Long balance = acctInfo.getBalance() - payOrder.getAmount();
	if (balance < 0) {
	    logger.warn("交易余额不足  payOrderId : " + payOrder.getId());
	    // 抛出异常才会执行 rollback 释放掉上面的锁
	    throw new RuntimeException("交易余额不足");
	}
	// 更新账户
	Long oldBalance = acctInfo.getBalance();
	acctInfo.setBalance(balance);
	acctInfo.setLastTradeBalance(oldBalance);
	acctInfo.setLastTradeDate(acctInfo.getGmtModify());
	String verificationCode = acctService.getVerificationCode(acctInfo);
	acctInfo.setVerificationCode(verificationCode);
	acctInfoDAO.updateByPrimaryKeySelective(acctInfo);

	// 记入流水
	AcctLog acctLog = new AcctLog();
	acctLog.setAmtBalance(balance);
	acctLog.setAmtOut(payOrder.getAmount());
	acctLog.setAmtOutEx(payOrder.getAmountDummy());
	acctLog.setBizId(payOrder.getBizId());
	acctLog.setBizOrderId(payOrder.getBizOrderId());
	acctLog.setChannel(payOrder.getChannle());
	acctLog.setUserId(payOrder.getUserId());
	acctLog.setAcctId(payOrder.getAcctId());
	acctLog.setBillId(payOrder.getId());
	acctLog.setBillType(Constants.AcctLog.BILL_TYPE_PAY_ORDER);
	acctLog.setStatus(Constants.AcctLog.STATUS_NORMAL);
	acctLog.setTradeType(Constants.AcctLog.TRADE_TYPE_OUT);
	if (payOrder.getSupplyTraderId() != null)
	    acctLog.setUpStreamId(payOrder.getSupplyTraderId().toString());
	Long acctLogId = acctLogDAO.insert(acctLog);

	// 修改支付单
	payOrder.setAcctLogId(acctLogId);
	payOrder.setStatus(Constants.PayOrder.STATUS_SUCCESS);
	PayOrder payOrderUpdate = new PayOrder();
	payOrderUpdate.setAcctLogId(acctLog.getId());
	payOrderUpdate.setStatus(Constants.PayOrder.STATUS_SUCCESS);
	PayOrderExample example = new PayOrderExample();
	example.createCriteria().andStatusEqualTo(Constants.PayOrder.STATUS_INIT).andIdEqualTo(payOrder.getId());
	int updateCount = payOrderDAO.updateByExampleSelective(payOrderUpdate, example);
	if (updateCount <= 0) {
	    throw new RuntimeException("更新支付单失败，支付单状态异常");
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> addMoney(ChargeOrder chargeOrder) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	// 获取账户,锁定该行
	AcctInfo acctInfo = acctInfoDAO.selectForUpdate(chargeOrder.getAcctId());
	if (acctInfo == null) {
	    logger.error("获取账户失败  chargeOrder Id :" + chargeOrder.getId());
	    result.setResultMsg("获取账户失败");
	    return result;
	}

	Result<Boolean> checkResult = acctService.checkAcct(acctInfo);
	if (!checkResult.isSuccess() || !checkResult.getModule()) {
	    logger.error("账户安全校验失败  acct Id :" + acctInfo.getId());
	    result.setResultMsg("账户安全校验失败");
	    return result;
	}

	// 更新账户
	Long balance = acctInfo.getBalance() + chargeOrder.getAmount();
	Long oldBalance = acctInfo.getBalance();
	acctInfo.setBalance(balance);
	acctInfo.setLastTradeBalance(oldBalance);
	acctInfo.setLastTradeDate(acctInfo.getGmtModify());
	String verificationCode = acctService.getVerificationCode(acctInfo);
	acctInfo.setVerificationCode(verificationCode);
	acctInfoDAO.updateByPrimaryKeySelective(acctInfo);

	// 记入流水
	AcctLog acctLog = new AcctLog();
	acctLog.setAmtBalance(balance);
	acctLog.setAmtIn(chargeOrder.getAmount());
	acctLog.setAmtInEx(acctLog.getAmtIn());
	acctLog.setUserId(chargeOrder.getUserId());
	acctLog.setAcctId(chargeOrder.getAcctId());
	acctLog.setBillId(chargeOrder.getId());
	acctLog.setBillType(Constants.AcctLog.BILL_TYPE_CHARGE_ORDER);
	acctLog.setStatus(Constants.AcctLog.STATUS_NORMAL);
	acctLog.setTradeType(Constants.AcctLog.TRADE_TYPE_IN);
	Long acctLogId = acctLogDAO.insert(acctLog);

	chargeOrder.setStatus(Constants.ChargeOrder.STATUS_SUCCESS);
	chargeOrder.setAcctLogId(acctLogId);
	ChargeOrderExample example = new ChargeOrderExample();
	example.createCriteria().andIdEqualTo(chargeOrder.getId()).andStatusEqualTo(Constants.ChargeOrder.STATUS_INIT);
	int updateCount = chargeOrderDAO.updateByExampleSelective(chargeOrder, example);
	if (updateCount <= 0) {
	    throw new RuntimeException("更新充值单失败，充值单状态异常");
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> refundMoney(Long refundOrderId) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	RefundOrder refundOrder = refundOrderDAO.selectByPrimaryKey(refundOrderId);
	if (refundOrder == null) {
	    result.setResultMsg("没有该退款单");
	    return result;
	}
	// check
	if (refundOrder.getStatus() != Constants.RefundOrder.STATUS_INIT) {
	    result.setResultMsg("退款单状态校验失败");
	    return result;
	}

	// 获取账户,锁定该行
	AcctInfo acctInfo = acctInfoDAO.selectForUpdate(refundOrder.getAcctId());
	if (acctInfo == null) {
	    logger.error("获取账户失败  refundOrder Id :" + refundOrder.getId());
	    result.setResultMsg("获取账户失败");
	    return result;
	}
	Result<Boolean> checkResult = acctService.checkAcct(acctInfo);
	if (!checkResult.isSuccess() || !checkResult.getModule()) {
	    logger.error("账户安全校验失败  acct Id :" + acctInfo.getId());
	    result.setResultMsg("账户安全校验失败");
	    return result;
	}

	// 更新账户
	Long balance = acctInfo.getBalance() + refundOrder.getAmount();
	Long oldBalance = acctInfo.getBalance();
	acctInfo.setBalance(balance);
	acctInfo.setLastTradeBalance(oldBalance);
	acctInfo.setLastTradeDate(acctInfo.getGmtModify());
	String verificationCode = acctService.getVerificationCode(acctInfo);
	acctInfo.setVerificationCode(verificationCode);
	acctInfoDAO.updateByPrimaryKeySelective(acctInfo);

	// 记入流水
	AcctLog acctLog = new AcctLog();
	acctLog.setAmtBalance(balance);
	acctLog.setAmtIn(refundOrder.getAmount());
	acctLog.setAmtInEx(refundOrder.getAmountDummy());
	acctLog.setUserId(refundOrder.getUserId());
	acctLog.setAcctId(refundOrder.getAcctId());
	acctLog.setBillId(refundOrder.getId());
	acctLog.setBillType(Constants.AcctLog.BILL_TYPE_REFUND_ORDER);
	acctLog.setStatus(Constants.AcctLog.STATUS_NORMAL);
	acctLog.setTradeType(Constants.AcctLog.TRADE_TYPE_IN);
	acctLog.setBizOrderId(refundOrder.getBizOrderId());
	if (refundOrder.getSupplyTraderId() != null)
	    acctLog.setUpStreamId(refundOrder.getSupplyTraderId().toString());
	Long acctLogId = acctLogDAO.insert(acctLog);

	// 更新退款单， 更新的时候要额外小心,防止并发
	RefundOrder refundOrderUpdate = new RefundOrder();
	refundOrderUpdate.setAcctLogId(acctLogId);
	refundOrderUpdate.setStatus(Constants.RefundOrder.STATUS_SUCCESS);
	RefundOrderExample example = new RefundOrderExample();
	example.createCriteria().andIdEqualTo(refundOrder.getId()).andStatusEqualTo(Constants.RefundOrder.STATUS_INIT);
	int refundOrderUpdateCount = refundOrderDAO.updateByExampleSelective(refundOrderUpdate, example);
	if (refundOrderUpdateCount <= 0) {
	    throw new RuntimeException("更新退款单失败，退款单状态异常");
	}

	// 更新支付单， 更新的时候要额外小心,防止并发
	PayOrder payOrder = new PayOrder();
	payOrder.setId(refundOrder.getPayOrderId());
	payOrder.setStatus(Constants.PayOrder.STATUS_REFUND);
	PayOrderExample payOrderExample = new PayOrderExample();
	payOrderExample.createCriteria().andIdEqualTo(payOrder.getId()).andStatusEqualTo(Constants.PayOrder.STATUS_SUCCESS);
	int payOrderUpdateCount = payOrderDAO.updateByExampleSelective(payOrder, payOrderExample);
	if (payOrderUpdateCount <= 0) {
	    throw new RuntimeException("更新支付单失败，支付单状态异常");
	}

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> adjustPayOrder(Long refundOrderId, OperationVO operationVO) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	RefundOrder refundOrder = refundOrderDAO.selectByPrimaryKey(refundOrderId);
	if (refundOrder == null) {
	    result.setResultMsg("没有该退款单");
	    return result;
	}
	// check
	if (refundOrder.getStatus() != Constants.RefundOrder.STATUS_INIT) {
	    result.setResultMsg("退款单状态校验失败");
	    return result;
	}

	// 获取账户,锁定该行
	AcctInfo acctInfo = acctInfoDAO.selectForUpdate(refundOrder.getAcctId());
	if (acctInfo == null) {
	    logger.error("获取账户失败  refundOrder Id :" + refundOrder.getId());
	    result.setResultMsg("获取账户失败");
	    return result;
	}

	Result<Boolean> checkResult = acctService.checkAcct(acctInfo);
	if (!checkResult.isSuccess() || !checkResult.getModule()) {
	    logger.error("账户安全校验失败  acct Id :" + acctInfo.getId());
	    result.setResultMsg("账户安全校验失败");
	    return result;
	}

	// 更新账户
	Long balance = acctInfo.getBalance() + refundOrder.getAmount();
	Long oldBalance = acctInfo.getBalance();
	acctInfo.setBalance(balance);
	acctInfo.setLastTradeBalance(oldBalance);
	acctInfo.setLastTradeDate(acctInfo.getGmtModify());
	String verificationCode = acctService.getVerificationCode(acctInfo);
	acctInfo.setVerificationCode(verificationCode);
	acctInfoDAO.updateByPrimaryKeySelective(acctInfo);

	// 记入流水
	AcctLog acctLog = new AcctLog();
	acctLog.setAmtBalance(balance);
	acctLog.setAmtIn(refundOrder.getAmount());
	acctLog.setAmtInEx(refundOrder.getAmountDummy());
	acctLog.setUserId(refundOrder.getUserId());
	acctLog.setAcctId(refundOrder.getAcctId());
	acctLog.setBillId(refundOrder.getId());
	acctLog.setBillType(Constants.AcctLog.BILL_TYPE_REFUND_ORDER);
	acctLog.setStatus(Constants.AcctLog.STATUS_NORMAL);
	acctLog.setTradeType(Constants.AcctLog.TRADE_TYPE_IN);
	acctLog.setBizOrderId(refundOrder.getBizOrderId());
	if (refundOrder.getSupplyTraderId() != null)
	    acctLog.setUpStreamId(refundOrder.getSupplyTraderId().toString());
	Long acctLogId = acctLogDAO.insert(acctLog);

	// 更新退款单， 更新的时候要额外小心,防止并发
	RefundOrder refundOrderUpdate = new RefundOrder();
	refundOrderUpdate.setAcctLogId(acctLogId);
	refundOrderUpdate.setStatus(Constants.RefundOrder.STATUS_SUCCESS);
	RefundOrderExample example = new RefundOrderExample();
	example.createCriteria().andIdEqualTo(refundOrder.getId()).andStatusEqualTo(Constants.RefundOrder.STATUS_INIT);
	int refundOrderUpdateCount = refundOrderDAO.updateByExampleSelective(refundOrderUpdate, example);
	if (refundOrderUpdateCount <= 0) {
	    throw new RuntimeException("更新退款单失败，退款单状态异常");
	}

	// 更新支付单， 更新的时候要额外小心,防止并发
	PayOrder payOrder = new PayOrder();
	payOrder.setId(refundOrder.getPayOrderId());
	payOrder.setStatus(Constants.PayOrder.STATUS_REFUND);
	PayOrderExample payOrderExample = new PayOrderExample();
	payOrderExample.createCriteria().andIdEqualTo(payOrder.getId()).andStatusEqualTo(Constants.PayOrder.STATUS_SUCCESS);
	int payOrderUpdateCount = payOrderDAO.updateByExampleSelective(payOrder, payOrderExample);
	if (payOrderUpdateCount <= 0) {
	    throw new RuntimeException("更新支付单失败，支付单状态异常");
	}

	BizOrder bizOrder = bizOrderDAO.selectByPrimaryKey(refundOrder.getBizOrderId());
	if (bizOrder == null) {
	    throw new RuntimeException("没有该订单");
	}

	Result<List<SupplyOrder>> supplyOrderQuery = supplyOrderService.querySupplyOrderByBizOrder(bizOrder.getId());
	if (!supplyOrderQuery.isSuccess()) {
	    throw new RuntimeException("供货单获取失败,数据库异常");
	}

	List<SupplyOrder> supplyOrderList = supplyOrderQuery.getModule();
	if (supplyOrderList != null) {
	    for (SupplyOrder supplyOrder : supplyOrderList) {
		if (supplyOrder.canAdjust()) {
		    if (supplyOrder.isSupplyFromStock()) {
			Result<Boolean> adjustStockResult = stockService.adjustStorage(supplyOrder.getStockId());
			if (!adjustStockResult.isSuccess()) {
			    // 不影响主流程
			    logger.error("调整库存失败  stockId : " + supplyOrder.getStockId() + " msg : "
				    + adjustStockResult.getResultMsg());
			}
		    }
		    supplyOrder.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
		    Result<Boolean> updateSupplyOrder = supplyOrderService.updateSupplyOrder(supplyOrder);
		    if (!updateSupplyOrder.isSuccess()) {
			throw new RuntimeException("更新供货单失败，请联系管理员");
		    }
		}
	    }
	}

	BizOrder bizOrderUpdate = new BizOrder();
	bizOrderUpdate.setId(bizOrder.getId());
	bizOrderUpdate.setStatus(Constants.BizOrder.STATUS_FAILED);
	bizOrderUpdate.setMemo(operationVO.getOperationMemo());
	int bizOrderUpdateCount = bizOrderDAO.updateByPrimaryKeySelective(bizOrderUpdate);
	if (bizOrderUpdateCount <= 0) {
	    throw new RuntimeException("更新订单失败");
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> subMoney(CashOrder cashOrder) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	// 获取账户,锁定该行
	AcctInfo acctInfo = acctInfoDAO.selectForUpdate(cashOrder.getAcctId());
	if (acctInfo == null) {
	    logger.error("获取账户失败  cashOrder Id :" + cashOrder.getId());
	    result.setResultMsg("获取账户失败");
	    return result;
	}

	Result<Boolean> checkResult = acctService.checkAcct(acctInfo);
	if (!checkResult.isSuccess() || !checkResult.getModule()) {
	    logger.error("账户安全校验失败  acct Id :" + acctInfo.getId());
	    result.setResultMsg("账户安全校验失败");
	    return result;
	}

	// 更新账户
	if (acctInfo.getBalance() < cashOrder.getAmount() || cashOrder.getAmount() < 0) {
	    throw new RuntimeException("提现数额有误");
	}
	Long balance = acctInfo.getBalance() - cashOrder.getAmount();// 扣款
	Long oldBalance = acctInfo.getBalance();
	acctInfo.setBalance(balance);
	acctInfo.setLastTradeBalance(oldBalance);
	acctInfo.setLastTradeDate(acctInfo.getGmtModify());
	String verificationCode = acctService.getVerificationCode(acctInfo);
	acctInfo.setVerificationCode(verificationCode);
	acctInfoDAO.updateByPrimaryKeySelective(acctInfo);

	// 记入流水
	AcctLog acctLog = new AcctLog();
	acctLog.setAmtBalance(balance);
	acctLog.setAmtOut(cashOrder.getAmount());
	acctLog.setAmtOutEx(acctLog.getAmtOut());
	acctLog.setUserId(cashOrder.getUserId());
	acctLog.setAcctId(cashOrder.getAcctId());
	acctLog.setBillId(cashOrder.getId());
	acctLog.setBillType(Constants.AcctLog.BILL_TYPE_CASH_ORDER);
	acctLog.setStatus(Constants.AcctLog.STATUS_NORMAL);
	acctLog.setTradeType(Constants.AcctLog.TRADE_TYPE_OUT);
	Long acctLogId = acctLogDAO.insert(acctLog);

	cashOrder.setStatus(Constants.CashOrder.STATUS_SUCCESS);
	cashOrder.setAcctLogId(acctLogId);
	int updateCount = cashOrderDAO.updateByPrimaryKeyAndStatus(cashOrder);
	if (updateCount <= 0) {
	    throw new RuntimeException("更新 提现单失败，提现单状态异常");
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }
}
