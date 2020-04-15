package com.longan.biz.utils;

public class CachedUtils {
    public static final Integer COMBINE_ORDER_EXP = 2 * 24 * 60 * 60;// 2天
    public static final Integer MANUAL_CHARGE_EXP = 5 * 60;// 5分钟
    public static final Integer MOBILE_CHECK_TIMES_EXP = 2 * 60 * 60;// 2小时

    private final static String UID_AMOUNT_KEY = "UID_AMOUNT_"; // UID_COUNT_12 加上用户id 手机号当天充值次数
    private final static String CHARGING_LIMIT_KEY = "CHARGING_LIMIT_";
    private final static String ITEM_PRICE_KEY = "ITEM_PRICE_";
    private final static String COMBINE_SUCCS_KEY = "COMBINE_SUCCS_";// 次数减为零时、全成功
    private final static String COMBINE_FAILS_KEY = "COMBINE_FAILS_";// 次数减为零时、全失败
    private final static String COMBINE_HAS_SUCC_KEY = "COMBINE_HAS_SUCC_";
    private final static String COMBINE_IS_BACK_KEY = "COMBINE_IS_BACK_";
    public final static String MANUAL_CHARGE_KEY = "MANUAL_CHARGE_CODE";
    private final static String MOBILE_CHECK_TIMES_KEY = "MOBILE_CHECK_";

    public static String getUidAmountKey(Long userId, String uid) {
	return DateTool.getToday() + UID_AMOUNT_KEY + userId + "_" + uid;
    }

    public static String getCharingLimitKey(Long supplyTraderId) {
	return CHARGING_LIMIT_KEY + supplyTraderId;
    }

    public static String getItemPriceKey(Long userId, Integer itemId) {
	return ITEM_PRICE_KEY + userId + "_" + itemId;
    }

    public static String getCombineSuccsKey(Long bizOrderId) {
	return COMBINE_SUCCS_KEY + bizOrderId;
    }

    public static String getCombineFailsKey(Long bizOrderId) {
	return COMBINE_FAILS_KEY + bizOrderId;
    }

    public static String combineHasSuccKey(Long bizOrderId) {
	return COMBINE_HAS_SUCC_KEY + bizOrderId;
    }

    public static String combineIsBackKey(Long bizOrderId) {
	return COMBINE_IS_BACK_KEY + bizOrderId;
    }

    public static String checkMobileTimesKey(String mobile) {
	return MOBILE_CHECK_TIMES_KEY + mobile;
    }
}
