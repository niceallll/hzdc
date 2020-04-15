package com.longan.mng.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.dao.BizOrderDAO;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderExample;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.domain.MobileCheck;
import com.longan.client.remote.service.CallBackService;
import com.longan.client.remote.service.SupplyQueryService;

public class AutoNotifyService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Long mcheck_unicom = Utils.getLong("mcheck.unicom");
    private final static Long mcheck_cmcc = Utils.getLong("mcheck.cmcc");
    private final static Long mcheck_telecom = Utils.getLong("mcheck.telecom");
    private final static boolean initFlag = Utils.getBoolean("auto.notify");
    private final static long TIMEGAP = 10l;
    private final static int s_before = 30;
    private final static int e_before = 20 * 60;
    // private final static long max_times = 3;

    private static List<Integer> no_notify_status = new ArrayList<Integer>();
    static {
	no_notify_status.add(Constants.BizOrder.STATUS_SUCCESS);
	no_notify_status.add(Constants.BizOrder.STATUS_FAILED);
    };
    private static ScheduledExecutorService executorLoop;
    private static Future<?> futureLoop;
    private static ExecutorService executorNotify = Executors.newFixedThreadPool(32);

    @Resource
    private LocalCachedService localCachedService;

    // @Resource
    // private MemcachedService memcachedService;

    @Resource
    private BizOrderDAO bizOrderDAO;

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private SupplyQueryService supplyQueryService;

    @Resource
    private CallBackService callBackService;

    void init() {
	if (initFlag) {
	    executorLoop = new ScheduledThreadPoolExecutor(1);
	    logger.warn("start auto notify thread");
	    futureLoop = executorLoop.scheduleWithFixedDelay(new Runnable() {
		@Override
		public void run() {
		    try {
			List<TraderInfo> traderList = localCachedService.getTraderInfoList();
			for (TraderInfo traderInfo : traderList) {
			    if (traderInfo.isDownstream()) {
				if (traderInfo.isAutoNotify(Constants.Item.CARRIER_TYPE_UNICOM)) {
				    userNotify(traderInfo.getUserId(), Constants.Item.CARRIER_TYPE_UNICOM);
				}
				if (traderInfo.isAutoNotify(Constants.Item.CARRIER_TYPE_TELECOM)) {
				    userNotify(traderInfo.getUserId(), Constants.Item.CARRIER_TYPE_TELECOM);
				}
				if (traderInfo.isAutoNotify(Constants.Item.CARRIER_TYPE_MOBILE)) {
				    userNotify(traderInfo.getUserId(), Constants.Item.CARRIER_TYPE_MOBILE);
				}
			    }
			}
		    } catch (Exception ex) {
			logger.error("auto notify exception", ex);
		    }
		}
	    }, 30l, TIMEGAP, TimeUnit.SECONDS);
	}
    }

    void clean() {
	if (initFlag) {
	    futureLoop.cancel(false);
	    logger.warn("stop auto notify thread");
	}
    }

    private void userNotify(Long userId, Integer carrierType) throws Exception {
	logger.warn("start auto notify for userId：" + userId);
	Date autoSDate = DateTool.beforeSeconds(new Date(), s_before);
	Date autoEDate = DateTool.beforeSeconds(new Date(), e_before);
	BizOrderExample example = new BizOrderExample();
	example.createCriteria().andUserIdEqualTo(userId).andCarrierTypeEqualTo(carrierType)
		.andNotifyStatusEqualTo(Constants.BizOrder.NOTIFY_CHARGING).andStatusNotIn(no_notify_status)
		.andGmtCreateLessThan(autoSDate).andGmtCreateGreaterThan(autoEDate);

	@SuppressWarnings("unchecked")
	List<BizOrder> list = (List<BizOrder>) bizOrderDAO.selectByExample(example);
	if (list != null && list.size() > 0) {
	    for (final BizOrder bizOrder : list) {
		executorNotify.execute(new Runnable() {
		    @Override
		    public void run() {
			autoNotify(bizOrder);
		    }
		});
	    }
	}
	logger.warn("stop auto notify for userId：" + userId);
    }

    private void autoNotify(BizOrder bizOrder) {
	try {
	    Long supplyTraderId = getSupplyTraderId(bizOrder.getBizId());
	    if (supplyTraderId == null) {
		logger.warn("supplyTraderId of mobileCheck is null");
		// checkMobileTimes(bizOrder.getId(), bizOrder.getItemUid());
		notifyToUnknown(bizOrder.getId());
		return;
	    }
	    Result<MobileCheck> checkResult = supplyQueryService.mobileCheck(supplyTraderId, bizOrder.getId(),
		    bizOrder.getItemUid(), bizOrder.getItemFacePrice());
	    if (!checkResult.isSuccess()) {
		logger.warn("result of mobileCheck is error");
		// checkMobileTimes(bizOrder.getId(), bizOrder.getItemUid());
		notifyToUnknown(bizOrder.getId());
		return;
	    }
	    MobileCheck mobileCheck = checkResult.getModule();

	    if (mobileCheck.enableCharge()) {
		if (notifyToSuccess(bizOrder.getId(), bizOrder.getGmtCreate())) {
		    bizOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);
		    bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_SUCCESS);
		    callBackService.callBackAsync(bizOrder);
		}
	    } else if (mobileCheck.disableCharge()) {
		notifyToUnknown(bizOrder.getId());
	    } else {
		// checkMobileTimes(bizOrder.getId(), bizOrder.getItemUid());
		notifyToUnknown(bizOrder.getId());
	    }
	} catch (Exception ex) {
	    logger.error("auto notify error", ex);
	}
    }

    private Long getSupplyTraderId(Integer bizId) {
	Long supplyTraderId = null;
	if (bizId == Constants.BizInfo.CODE_PHONE_UNICOM || bizId == Constants.BizInfo.CODE_FLOW_UNICOM) {
	    if (mcheck_unicom != 0) {
		supplyTraderId = mcheck_unicom;
	    }
	} else if (bizId == Constants.BizInfo.CODE_PHONE_MOBILE || bizId == Constants.BizInfo.CODE_FLOW_MOBILE) {
	    if (mcheck_cmcc != 0) {
		supplyTraderId = mcheck_cmcc;
	    }
	} else if (bizId == Constants.BizInfo.CODE_PHONE_TELECOM || bizId == Constants.BizInfo.CODE_FLOW_TELECOM) {
	    if (mcheck_telecom != 0) {
		supplyTraderId = mcheck_telecom;
	    }
	}
	return supplyTraderId;
    }

    // private void checkMobileTimes(Long bizOrderId, String mobile) {
    // String timesKey = CachedUtils.checkMobileTimesKey(mobile);
    // if (memcachedService.get(timesKey) == null) {
    // memcachedService.initCount(timesKey, CachedUtils.MOBILE_CHECK_TIMES_EXP, 0l);
    // }
    // Long times = memcachedService.inc(timesKey, 1);
    // if (times > max_times) {
    // notifyToUnknown(bizOrderId);
    // }
    // }

    private boolean notifyToSuccess(Long bizOrderId, Date gmtCreate) {
	BizOrder updateBizOrder = new BizOrder();
	updateBizOrder.setId(bizOrderId);
	updateBizOrder.setGmtNotify(new Date());
	updateBizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_SUCCESS);
	// 临时使用itemSize
	updateBizOrder.setItemSize(updateBizOrder.computCostTime(gmtCreate));
	Result<Boolean> result = bizOrderService.updateBizOrder(updateBizOrder);
	if (!result.isSuccess()) {
	    logger.error("更新预回调状态失败 bizorderId:" + bizOrderId);
	}
	return result.isSuccess();
    }

    private void notifyToUnknown(Long bizOrderId) {
	BizOrder updateBizOrder = new BizOrder();
	updateBizOrder.setId(bizOrderId);
	updateBizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_UNKNOWN);
	bizOrderService.updateBizOrder(updateBizOrder);
    }
}
