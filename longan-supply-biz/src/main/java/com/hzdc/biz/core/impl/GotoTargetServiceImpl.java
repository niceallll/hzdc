package com.hzdc.biz.core.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.hzdc.biz.core.GotoTargetService;
import com.hzdc.biz.dao.AcctLogSDao;
import com.hzdc.biz.dao.BizOrderSDao;
import com.hzdc.biz.dao.ChargeOrderSDao;
import com.hzdc.biz.dao.PayOrderSDao;
import com.hzdc.biz.dao.SupplyOrderSDao;
import com.hzdc.biz.data.object.TargetArea;
import com.hzdc.biz.data.object.TargetCharge;
import com.hzdc.biz.data.object.TargetData;
import com.hzdc.biz.data.object.TargetDate;
import com.hzdc.biz.data.object.TargetMobile;
import com.hzdc.biz.data.object.TargetOrder;
import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.AcctService;
import com.longan.biz.core.BaseService;
import com.longan.biz.dao.MobileBelongDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.MobileBelongExample;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.Utils;

public class GotoTargetServiceImpl extends BaseService implements GotoTargetService {
    private static final String goto_key = "goto_target_running";
    private static final Boolean goto_target = Utils.getBoolean("goto.target");
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    private List<TargetArea> areaList = new ArrayList<TargetArea>();

    @Resource
    private AcctService acctService;

    @Resource
    private AcctLogSDao acctLogSDao;

    @Resource
    private BizOrderSDao bizOrderSDao;

    @Resource
    private ChargeOrderSDao chargeOrderSDao;

    @Resource
    private PayOrderSDao payOrderSDao;

    @Resource
    private SupplyOrderSDao supplyOrderSDao;

    @Resource
    private MobileBelongDAO mobileBelongDAO;

    @Resource
    private MemcachedService memcachedService;

    void init() {
	if (goto_target) {
	    MobileBelongExample example = new MobileBelongExample();
	    example.createCriteria().andCarrierTypeEqualTo(Constants.MobileBelong.CARRIER_TYPE_MOBILE);
	    try {
		@SuppressWarnings("unchecked")
		List<MobileBelong> list = (List<MobileBelong>) mobileBelongDAO.selectByExample(example);
		for (MobileBelong mobile : list) {
		    TargetArea area = new TargetArea();
		    area.setMobilePart(mobile.getMobilePart());
		    area.setProvinceCode(mobile.getProvinceCode());
		    areaList.add(area);
		}
		list.clear();
	    } catch (Exception ex) {
		logger.error("获取area list异常：", ex);
	    }
	}
    }

    void clear() {
	if (goto_target) {
	    areaList.clear();
	}
    }

    @Override
    public Result<Boolean> gotoCharge(TargetCharge targetCharge) {
	Result<Boolean> result = new Result<Boolean>();
	Long chargeAmount = targetCharge.getChargeAmount();
	if (chargeAmount <= 0) {
	    result.setResultMsg("充值金额必需大于零");
	    return result;
	}
	Long userId = targetCharge.getUserId();
	Long acctId = targetCharge.getAcctId();
	Result<AcctInfo> getAcctResult = acctService.getAcctInfo(targetCharge.getAcctId());
	if (!getAcctResult.isSuccess()) {
	    result.setResultMsg(getAcctResult.getResultMsg());
	    return result;
	}

	try {
	    ChargeOrder chargeOrder = chargeOrderSDao.selectByPrimaryKey(targetCharge.getChargeOrderId());
	    AcctLog acctLog = acctLogSDao.selectByPrimaryKey(targetCharge.getAcctLogId());
	    if (chargeOrder == null || acctLog == null) {
		result.setResultMsg("获取原始数据是空");
		return result;
	    }

	    chargeOrder.setId(null);
	    chargeOrder.setUserId(userId);
	    chargeOrder.setAcctId(acctId);
	    chargeOrder.setAmount(chargeAmount);
	    chargeOrder.setAcctDate(targetCharge.getAcctDate());
	    chargeOrder.setGmtCreate(targetCharge.getSdate());
	    chargeOrder.setGmtModify(targetCharge.getSdate());
	    chargeOrder.setMemo(targetCharge.getMemo());
	    chargeOrderSDao.insert(chargeOrder);

	    AcctInfo acctInfo = getAcctResult.getModule();
	    Long balance = acctInfo.getBalance() + chargeAmount;
	    Long oldBalance = acctInfo.getBalance();
	    acctInfo.setBalance(balance);
	    acctInfo.setLastTradeBalance(oldBalance);
	    acctInfo.setLastTradeDate(targetCharge.getSdate());
	    String verificationCode = acctService.getVerificationCode(acctInfo);
	    acctInfo.setVerificationCode(verificationCode);
	    Result<Boolean> updateAcctResult = acctService.updateAcct(acctInfo);
	    if (!updateAcctResult.isSuccess()) {
		result.setResultMsg(updateAcctResult.getResultMsg());
		return result;
	    }

	    acctLog.setId(null);
	    acctLog.setGmtCreate(targetCharge.getSdate());
	    acctLog.setGmtModify(targetCharge.getSdate());
	    acctLog.setUserId(userId);
	    acctLog.setAcctId(acctId);
	    acctLog.setBillId(chargeOrder.getId());
	    acctLog.setAmtIn(targetCharge.getChargeAmount());
	    acctLog.setAmtInEx(targetCharge.getChargeAmount());
	    acctLog.setAmtBalance(oldBalance);
	    acctLogSDao.insert(acctLog);

	    chargeOrder.setAcctLogId(acctLog.getId());
	    chargeOrderSDao.updateByPrimaryKeySelective(chargeOrder);
	} catch (Exception ex) {
	    result.setResultMsg("获取原始数据失败");
	    return result;
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> gotoOrder(final TargetOrder targetOrder) {
	Result<Boolean> result = new Result<Boolean>();
	Result<AcctInfo> getAcctResult = acctService.getAcctInfo(targetOrder.getAcctId());
	if (!getAcctResult.isSuccess()) {
	    result.setResultMsg(getAcctResult.getResultMsg());
	    return result;
	}

	final AcctInfo acctInfo = getAcctResult.getModule();
	memcachedService.set(goto_key, 0, true);
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		try {
		    int itemMax = 0;
		    List<TargetOrder.Items> itemList = targetOrder.getItemList();
		    Map<Integer, TargetData> dataMap = new HashMap<Integer, TargetData>();
		    for (TargetOrder.Items item : itemList) {
			TargetData orders = new TargetData();
			orders.setBizOrder(bizOrderSDao.selectByPrimaryKey(item.getBizOrderId()));
			orders.setSupplyOrder(supplyOrderSDao.selectByPrimaryKey(item.getSupplyOrderId()));
			orders.setPayOrder(payOrderSDao.selectByPrimaryKey(item.getPayOrderId()));
			orders.setAcctLog(acctLogSDao.selectByPrimaryKey(item.getAcctLogId()));
			dataMap.put(item.getItemId(), orders);
			if (item.getMax().intValue() > itemMax) {
			    itemMax = item.getMax();
			}
		    }

		    int plus = 0;
		    Long balance = acctInfo.getBalance();
		    Long oldBalance = balance;
		    for (int i = 0; i < itemMax; i++) {
			for (TargetOrder.Items item : itemList) {
			    if (item.getMax() > i) {
				BizOrder bizOrder = dataMap.get(item.getItemId()).getBizOrder();
				SupplyOrder supplyOrder = dataMap.get(item.getItemId()).getSupplyOrder();
				PayOrder payOrder = dataMap.get(item.getItemId()).getPayOrder();
				AcctLog acctLog = dataMap.get(item.getItemId()).getAcctLog();
				if (balance - acctLog.getAmtOut() < 0) {
				    continue;
				}

				plus++;
				TargetDate dates = getDates(targetOrder);
				TargetMobile mobiles = getMobiles();
				String upstreamSerialno = getUpstreamSerialno();
				bizOrder.setId(null);
				bizOrder.setGmtCreate(dates.getCreate());
				bizOrder.setGmtModify(dates.getModify());
				bizOrder.setDownstreamDate(dates.getCreate());
				bizOrder.setUserId(targetOrder.getUserId());
				bizOrder.setItemUid(mobiles.getMobile());
				bizOrder.setUpstreamSerialno(upstreamSerialno);
				bizOrder.setDownstreamSerialno(getDownstreamSerialno());
				bizOrder.setCostTime(dates.getCostTime());
				// 临时使用itemSize
				bizOrder.setItemSize(dates.getCostTime());
				bizOrder.setProvinceCode(mobiles.getProvinceCode());
				bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_NORMAL);
				bizOrderSDao.insert(bizOrder);

				supplyOrder.setId(null);
				supplyOrder.setGmtCreate(dates.getCreate());
				supplyOrder.setGmtModify(dates.getModify());
				supplyOrder.setUpstreamDate(dates.getModify());
				supplyOrder.setBizOrderId(bizOrder.getId());
				supplyOrder.setUserId(targetOrder.getUserId());
				supplyOrder.setSupplyCostTime(dates.getCostTime());
				supplyOrder.setUpstreamSerialno(upstreamSerialno);
				supplyOrder.setItemUid(mobiles.getMobile());
				supplyOrder.setProvinceCode(mobiles.getProvinceCode());
				supplyOrder.setSupplyTermPeriod(targetOrder.getTerm());
				supplyOrder.setSupplyCostTime(dates.getCostTime());
				supplyOrderSDao.insert(supplyOrder);

				payOrder.setId(null);
				payOrder.setAcctDate(DateTool.parseDate8(dates.getCreate()));
				payOrder.setGmtCreate(dates.getCreate());
				payOrder.setGmtModify(dates.getCreate());
				payOrder.setUserId(targetOrder.getUserId());
				payOrder.setAcctId(targetOrder.getAcctId());
				payOrder.setBizOrderId(bizOrder.getId());
				payOrderSDao.insert(payOrder);

				acctLog.setId(null);
				acctLog.setGmtCreate(dates.getCreate());
				acctLog.setGmtModify(dates.getCreate());
				acctLog.setUserId(targetOrder.getUserId());
				acctLog.setAcctId(targetOrder.getAcctId());
				acctLog.setBillId(payOrder.getId());
				acctLog.setBizOrderId(bizOrder.getId());
				acctLog.setAmtBalance(balance);
				oldBalance = balance;
				balance -= acctLog.getAmtOut();
				acctLogSDao.insert(acctLog);

				payOrder.setAcctLogId(acctLog.getId());
				payOrderSDao.updateByPrimaryKeySelective(payOrder);
				bizOrder.setPayOrderId(payOrder.getId());
				bizOrderSDao.updateByPrimaryKeySelective(bizOrder);
			    }
			}

			if (plus > 10) {
			    targetOrder.setInc(targetOrder.getInc() + 1);
			    plus = 0;
			}
			TimeUnit.SECONDS.sleep(1);
		    }

		    acctInfo.setBalance(balance);
		    acctInfo.setLastTradeBalance(oldBalance);
		    String verificationCode = acctService.getVerificationCode(acctInfo);
		    acctInfo.setVerificationCode(verificationCode);
		    acctService.updateAcct(acctInfo);
		} catch (Exception ex) {
		    logger.error("定期维护运行失败", ex);
		} finally {
		    memcachedService.set(goto_key, 0, false);
		}
	    }
	});
	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    private TargetDate getDates(TargetOrder targetOrder) {
	TargetDate dates = new TargetDate();
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(targetOrder.getSdate());
	calendar.add(Calendar.SECOND, targetOrder.getInc());
	dates.setCreate(calendar.getTime());

	calendar.setTime(targetOrder.getSdate());
	Integer costTime = new Random().nextInt(30);
	dates.setCostTime(costTime);
	calendar.add(Calendar.SECOND, targetOrder.getInc() + costTime);
	dates.setModify(calendar.getTime());
	return dates;
    }

    private TargetMobile getMobiles() {
	TargetMobile mobiles = new TargetMobile();
	TargetArea areas = areaList.get(new Random().nextInt(areaList.size() - 1));
	String num = "0123456789";
	char[] rands = new char[4];
	for (int i = 0; i < 4; i++) {
	    int rand = (int) (Math.random() * 10);
	    rands[i] = num.charAt(rand);
	}
	mobiles.setMobile(areas.getMobilePart() + new String(rands));
	mobiles.setProvinceCode(areas.getProvinceCode());
	return mobiles;
    }

    private String getUpstreamSerialno() {
	String num = "0123456789abcdefghijklmnopqrstuvwxyz";
	char[] rands = new char[12];
	for (int i = 0; i < 12; i++) {
	    int rand = (int) (Math.random() * 36);
	    rands[i] = num.charAt(rand);
	}
	return "dc" + new String(rands);
    }

    private static String getDownstreamSerialno() {
	return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
