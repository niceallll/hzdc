package com.longan.biz.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    public final static Map<Integer, String> BIZ_MAP = new HashMap<Integer, String>();

    static {
	BIZ_MAP.put(BizInfo.CODE_FLOW_UNICOM, "联通流量包");
	BIZ_MAP.put(BizInfo.CODE_PHONE_UNICOM, "联通话费");
	BIZ_MAP.put(BizInfo.CODE_FLOW_MOBILE, "移动流量包");
	BIZ_MAP.put(BizInfo.CODE_PHONE_MOBILE, "移动话费");
	BIZ_MAP.put(BizInfo.CODE_FLOW_TELECOM, "电信流量包");
	BIZ_MAP.put(BizInfo.CODE_PHONE_TELECOM, "电信话费");
	BIZ_MAP.put(BizInfo.CODE_QB_TENCENT, "QB充值");
	BIZ_MAP.put(BizInfo.CODE_JFDH_VOUCHER, "卡券兑换");
	BIZ_MAP.put(BizInfo.CODE_CNPC_CARD, "中石油");
	BIZ_MAP.put(BizInfo.CODE_SINOPEC_CARD, "中石化");
	BIZ_MAP.put(BizInfo.CODE_PHONE_SMS, "短信");
    }

    public static Map<Integer, String> getBizMap() {
	return BIZ_MAP;
    }

    public interface BizInfo {
	public final static int CODE_FLOW_UNICOM = 100;
	public final static int CODE_PHONE_UNICOM = 200;
	public final static int CODE_FLOW_MOBILE = 102;
	public final static int CODE_PHONE_MOBILE = 201;
	public final static int CODE_FLOW_TELECOM = 101;
	public final static int CODE_PHONE_TELECOM = 202;
	public final static int CODE_QB_TENCENT = 400;
	public final static int CODE_JFDH_VOUCHER = 300;
	public final static int CODE_CNPC_CARD = 301;
	public final static int CODE_SINOPEC_CARD = 302;
	public final static int CODE_PHONE_SMS = 600;

	public final static int CODE_ACCT_LOG = 501;
	public final static int CODE_REFUND_ORDER = 500;
    }

    public interface Item {
	public final static int STATUS_INIT = 1; // 初始
	public final static int STATUS_UP = 2; // 上架
	public final static int STATUS_DOWN = 3; // 下架
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_INIT_DESC = "初始";
	public final static String STATUS_UP_DESC = "上架";
	public final static String STATUS_DOWN_DESC = "下架";
	public final static String STATUS_DEL_DESC = "删除";

	public final static int TYPE_ITEM_CHARGE = 1; // 直充
	public final static int TYPE_ITEM_CARD = 2; // 卡密直充
	public final static int TYPE_ITEM_COMBINE = 3;// 拆分

	public final static String TYPE_ITEM_CHARGE_DESC = "直充";
	public final static String TYPE_ITEM_CARD_DESC = "卡密";
	public final static String TYPE_ITEM_COMBINE_DESC = "拆分";

	public final static int COMBINE_TYPE_NO = 0;
	public final static int COMBINE_TYPE_YES = 1;

	public final static String COMBINE_TYPE_NO_DESC = "否";
	public final static String COMBINE_TYPE_YES_DESC = "是";// 拆单

	public final static int CARRIER_TYPE_UNICOM = 1; // 联通
	public final static int CARRIER_TYPE_TELECOM = 2; // 电信
	public final static int CARRIER_TYPE_MOBILE = 3; // 移动
	public final static int CARRIER_TYPE_OTHER = 4; // 其他、QB、积分兑换、加油卡

	public final static String CARRIER_TYPE_UNICOM_DESC = "联通";
	public final static String CARRIER_TYPE_TELECOM_DESC = "电信";
	public final static String CARRIER_TYPE_MOBILE_DESC = "移动";
	public final static String CARRIER_TYPE_OTHER_DESC = "其他";

	public final static String SALES_AREA_SPLIT = ",";

	public final static int SALE_TYPE_NATIONWIDE = 1;
	public final static int SALE_TYPE_AREA = 2;

	public final static String SALE_TYPE_NATIONWIDE_DESC = "全国";
	public final static String SALE_TYPE_AREA_DESC = "区域";

	public final static int SUPPLY_FILTER_TYPE_PRIORITY = 0;
	public final static int SUPPLY_FILTER_TYPE_COSTPRICE = 1;
    }

    public interface ItemSales {
	public final static int STATUS_OPEN = 1; // 正常
	public final static int STATUS_CLOSE = 2; // 下架
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_OPEN_DESC = "已开通";
	public final static String STATUS_CLOSE_DESC = "已下架";
	public final static String STATUS_DEL_DESC = "已删除";
    }

    public interface BizOrder {
	// 1创建 处理中 2未确认 3成功 4失败
	public final static int STATUS_INIT = 0; // 创建
	public final static int STATUS_CHARGING = 1; // 处理中
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_LOCK = 4; // 锁定充值中
	public final static int STATUS_PARTS = 5;// 部分成功、仅短信接口支持
	public final static int STATUS_PENDING = 6;// 预成功失败
	public final static int STATUS_EXCEPTION = 8; // 异常
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_CHARGING_DESC = "处理中";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_LOCK_DESC = "充值中";
	public final static String STATUS_PARTS_DESC = "部分成功";
	public final static String STATUS_PENDING_DESC = "预失败";
	public final static String STATUS_EXCEPTION_DESC = "异常"; // 未确认
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";

	public final static int NOTIFY_INIT = 0; // 创建
	public final static int NOTIFY_CHARGING = 1; // 未回调
	public final static int NOTIFY_SUCCESS = 2; // 成功
	public final static int NOTIFY_FAILED = 3; // 失败
	public final static int NOTIFY_NORMAL = 4; // 正常、非预处理
	public final static int NOTIFY_UNKNOWN = 5; // 不可充值号码、未回调

	public final static String NOTIFY_INIT_DESC = "创建";
	public final static String NOTIFY_CHARGING_DESC = "未回调";
	public final static String NOTIFY_SUCCESS_DESC = "成功";
	public final static String NOTIFY_FAILED_DESC = "失败";
	public final static String NOTIFY_NORMAL_DESC = "正常";
	public final static String NOTIFY_UNKNOWN_DESC = "未确认";

	public final static int CHANNEL_SUPPLY = 1; // 接口外放

	public static final int DOWNSTREAM_SUPPLY_WAY_SYNC = 0;
	public static final int DOWNSTREAM_SUPPLY_WAY_ASYNC = 1;

	public final static int COMBINE_TYPE_NO = 0;
	public final static int COMBINE_TYPE_YES = 1;

	public final static String COMBINE_TYPE_NO_DESC = "否";
	public final static String COMBINE_TYPE_YES_DESC = "是";// 拆单

	public final static int REPEAT_TYPE_NO = 0;
	public final static int REPEAT_TYPE_YES = 1;

	public final static String REPEAT_TYPE_NO_DESC = "否";
	public final static String REPEAT_TYPE_YES_DESC = "是";

	public final static int MANUAL_TYPE_NO = 0;
	public final static int MANUAL_TYPE_YES = 1;

	public final static String MANUAL_TYPE_NO_DESC = "否";
	public final static String MANUAL_TYPE_YES_DESC = "是";
    }

    public interface PayOrder {
	public final static int STATUS_INIT = 1; // 创建
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_REFUND = 4; // 已退款
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_REFUND_DESC = "已退款";
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";

	public final static int MODE_TRUST_PAY = 1; // 信任支付
	public final static int MODE_PWD_PAY = 2; // 密码验证支付

	public final static int TYPE_PAY_BALANCE = 1; // 余额支付

	public final static String CODE_NO_NEED_REFUND = "NO_NEED_REFUND"; // 不用退款
    }

    public interface ChargeOrder {
	public final static int STATUS_INIT = 1; // 待审核
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 取消
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static int PAY_TYPE_MAN = 1; // 手动充值
	public final static int PAY_TYPE_SYSTEM = 2; // 系统充值

	public final static String STATUS_INIT_DESC = "待审核";
	public final static String STATUS_SUCCESS_DESC = "通过";
	public final static String STATUS_FAILED_DESC = "取消";
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";
    }

    public interface CashOrder {
	public final static int STATUS_INIT = 1; // 待审核
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 取消
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static int PAY_TYPE_MAN = 1; // 手动充值
	public final static int PAY_TYPE_SYSTEM = 2; // 系统充值

	public final static String STATUS_INIT_DESC = "待审核";
	public final static String STATUS_SUCCESS_DESC = "通过";
	public final static String STATUS_FAILED_DESC = "取消";
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";
    }

    public interface RefundOrder {
	public final static int STATUS_INIT = 1; // 创建
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";

	public final static int PAY_TYPE_SYSTEM = 1;// 系统自动退款
	public final static int PAY_TYPE_OPERATOR = 2;// 操作员退款

	public final static String PAY_TYPE_SYSTEM_DESC = "失败自动退款";
	public final static String PAY_TYPE_OPERATOR_DESC = "操作员退款";
    }

    public interface AcctInfo {
	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_FREEZE = 2; // 冻结
	public final static int STATUS_DEL = 9; // 删除

	public final static String STATUS_NORMAL_DESC = "正常";
	public final static String STATUS_FREEZE_DESC = "冻结";
	public final static String STATUS_DEL_DESC = "删除";

	public final static int SALES_PRICE_1 = 1; // 价格1
	public final static int SALES_PRICE_2 = 2; // 价格2
	public final static int SALES_PRICE_3 = 3; // 价格3
	public final static int SALES_PRICE_4 = 4;

	public final static String SALES_PRICE_1_DESC = "价格1";
	public final static String SALES_PRICE_2_DESC = "价格2";
	public final static String SALES_PRICE_3_DESC = "价格3";
	public final static String SALES_PRICE_4_DESC = "价格4";
    }

    public interface AcctLog {
	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_EXCEPTION = 2; // 异常

	public final static String STATUS_NORMAL_DESC = "正常"; // 正常
	public final static String STATUS_EXCEPTION_DESC = "异常"; // 异常

	public final static int TRADE_TYPE_IN = 1; // 入账
	public final static int TRADE_TYPE_OUT = 2; // 出账

	public final static String TRADE_TYPE_IN_DESC = "入账"; // 入账
	public final static String TRADE_TYPE_OUT_DESC = "出账"; // 出账

	public final static int BILL_TYPE_PAY_ORDER = 1; // 支付单
	public final static int BILL_TYPE_CHARGE_ORDER = 2; // 充值单
	public final static int BILL_TYPE_REFUND_ORDER = 3; // 退款单
	public final static int BILL_TYPE_CASH_ORDER = 4; // 提现单

	public final static String BILL_TYPE_PAY_ORDER_DESC = "支付单";
	public final static String BILL_TYPE_CHARGE_ORDER_DESC = "充值单";
	public final static String BILL_TYPE_REFUND_ORDER_DESC = "退款单";
	public final static String BILL_TYPE_CASH_ORDER_DESC = "提现单";
    }

    public interface UserInfo {
	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_CANCEL = 2; // 注销
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_NORMAL_DESC = "正常";
	public final static String STATUS_CANCEL_DESC = "注销";
	public final static String STATUS_DEL_DESC = "删除";

	public final static int TYPE_ADMIN = 1; // 内部用户
	public final static int TYPE_DOWNSTREAM = 2; // 下游代理商
	public final static int TYPE_UPSTREAM = 3; // 上游供货商
	public final static int TYPE_PARTNER = 4;// 外部用户

	public final static String TYPE_ADMIN_DESC = "内部用户";
	public final static String TYPE_DOWNSTREAM_DESC = "下游代理商";
	public final static String TYPE_UPSTREAM_DESC = "上游供货商";
	public final static String TYPE_PARTNER_DESC = "外部用户";

	public final static int ASTATUS_NO = 0; // 未配置
	public final static int ASTATUS_YES = 1; // 已配置
	public final static int ASTATUS_OPEN = 2; // 启用
	public final static int ASTATUS_CLOSE = 3; // 暂停

	public final static String ASTATUS_NO_DESC = "未配置";
	public final static String ASTATUS_YES_DESC = "已配置";
	public final static String ASTATUS_OPEN_DESC = "启用";
	public final static String ASTATUS_CLOSE_DESC = "暂停";
    }

    public interface Stock {
	public final static int STATUS_INIT = 0; // 初始化
	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_INV_ALLOCATED = 2; // 锁定，或者出库中
	public final static int STATUS_DELIVERY = 3; // 已出库
	public final static int STATUS_EXCEPTION = 4; // 异常
	public final static int STATUS_INVALID = 5; // 失效
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_INIT_DESC = "未激活";
	public final static String STATUS_NORMAL_DESC = "正常销售";
	public final static String STATUS_INV_ALLOCATED_DESC = "锁定(出库中)";
	public final static String STATUS_DELIVERY_DESC = "已出库";
	public final static String STATUS_EXCEPTION_DESC = "异常隔离";
	public final static String STATUS_INVALID_DESC = "失效";
	public final static String STATUS_DEL_DESC = "删除";

	public final static String FLAG_CARD_VALID = "_InValid";
    }

    public interface StockLog {
	public final static int TYPE_IN = 1; // 入库;
	public final static int TYPE_OUT = 2; // 出库;

	public final static String TYPE_IN_DESC = "入库";
	public final static String TYPE_OUT_DESC = "出库";

	public final static int STATUS_INIT = 0;
	public final static int STATUS_SUCCESS = 1;
	public final static int STATUS_FAILED = 2;
	public final static int STATUS_ACTIVATED = 3;
	public final static int STATUS_INVALID = 4;

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_ACTIVATED_DESC = "已激活";
	public final static String STATUS_INVALID_DESC = "已失效";
    }

    public interface OperationInfo {
	public final static int TYPE_CATALOG = 1; // 目录操作
	public final static int TYPE_URL = 2; // 链接操作
	public final static int TYPE_BIZ = 3; // 业务操作

	public final static String TYPE_CATALOG_DESC = "目录操作";
	public final static String TYPE_URL_DESC = "链接操作";
	public final static String TYPE_BIZ_DESC = "业务操作";

	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_CANCEL = 2; // 注销
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_NORMAL_DESC = "正常";
	public final static String STATUS_CANCEL_DESC = "注销";
	public final static String STATUS_DEL_DESC = "删除";
    }

    public interface AdminCatalog {
	public final static String CATALOG_FLOW = "adminFlow"; // 联通流量
	public final static String CATALOG_UNICOMAYNC = "adminUnicomAync"; // 联通话费
	public final static String CATALOG_MONEY = "adminMoney"; // 资金管理
	public final static String CATALOG_USER = "adminUser"; // 用户管理
	public final static String CATALOG_STOCK = "adminStock"; // 库存管理
	public final static String CATALOG_MOBILE = "adminMobile"; // 移动话费
	public final static String CATALOG_TELECOM = "adminTelecom"; // 电信话费
	public final static String CATALOG_SYSTEMOPERATION = "adminSystemOperation"; // 系统管理
	public final static String CATALOG_TELECOMFLOW = "adminTelecomFlow"; // 电信流量
	public final static String CATALOG_MOBILEFLOW = "adminMobileFlow"; // 移动流量
	public final static String CATALOG_PRICE = "adminPrice";// 价格管理
	public final static String CATALOG_STATISTIC = "adminStatistic";// 统计分析
	public final static String CATALOG_QB = "adminQb"; // Q币充值
	public final static String CATALOG_VOUCHER = "adminVoucher";// 卡券兑换
	public final static String CATALOG_CNPC = "adminCnpc";// 中石油
	public final static String CATALOG_SINOPEC = "adminSinopec";// 中石化
	public final static String CATALOG_SMS = "adminSms";// 短信
	public final static String CATALOG_MANUAL = "adminManual";// 手动充值
    }

    public interface ItemSupply {
	public final static int STATUS_INIT = 1; // 初始
	public final static int STATUS_UP = 2; // 上架
	public final static int STATUS_DOWN = 3; // 下架
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_INIT_DESC = "初始";
	public final static String STATUS_UP_DESC = "上架";
	public final static String STATUS_DOWN_DESC = "下架";
	public final static String STATUS_DEL_DESC = "删除";

	public final static int TYPE_CARD_FORWARD_CHARGE = 1; // 卡密转直充
	public final static int TYPE_DIRECT_CHARGE = 2; // 直充
	public final static int TYPE_MAN = 3; // 人工
	public final static int TYPE_CARD = 4; // 开密
	public final static int TYPE_CARD_CHARGE = 5; // 卡密充值

	public final static String TYPE_CARD_FORWARD_CHARGE_DESC = "卡密转直充";
	public final static String TYPE_DIRECT_CHARGE_DESC = "直充";
	public final static String TYPE_MAN_DESC = "人工充值";
	public final static String TYPE_CARD_DESC = "卡密";
	public final static String TYPE_CARD_CHARGE_DESC = "卡密充值";

	public final static int LOSSTYPE_CANNOT = 0;// 不允许损失
	public final static int LOSSTYPE_CAN = 1;// 允许损失

	public final static String LOSSTYPE_CANNOT_DESC = "不允许损失";
	public final static String LOSSTYPE_CAN_DESC = "允许损失";

	public final static int SUPPLY_WAY_NORMAL = 0;// 标准通道
	public final static int SUPPLY_WAY_FASTEST = 1;// 快充通道

	public final static String SUPPLY_WAY_NORMAL_DESC = "标准通道";
	public final static String SUPPLY_WAY_FASTEST_DESC = "快充通道";
    }

    public interface ErrorCode {
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";

	public final static String CODE_SUCCESS = "00";
	public final static String DESC_SUCCESS = "交易成功";

	public final static String CODE_ERROR_VALIDATE = "10";
	public final static String CODE_ERROR_SIGN = "11";
	public final static String DESC_ERROR_SIGN = "校验码验证失败";
	public final static String DESC_ERROR_SIGN_NONE = "用户校验码验证获取失败";

	public final static String CODE_ERROR_ITEM = "20";
	public final static String DESC_ERROR_ITEM = "商品查询失败";

	public final static String CODE_ERROR_ITEM_PRICE = "21";

	public final static String CODE_ERROR_BIZORDER = "22";

	public final static String CODE_ERROR_SERIALNO = "23";

	public final static String CODE_ERROR_UID_AMOUNT = "24";

	public final static String CODE_ERROR_PHONE_AREA = "25";

	public final static String CODE_ERROR_QB_AREA = "26";

	public final static String CODE_ERROR_JF_AREA = "26";

	public final static String CODE_ERROR_FREE_ITEM = "27";

	public final static String CODE_ERROR_BLACK_LIST = "28";
	public final static String DESC_ERROR_BLACK_LIST = "非正常失败";// 黑名单

	public final static String CODE_ERROR_SUPPLY_FAILD = "30";
	public final static String DESC_ERROR_SUPPLY_FAILD = "供货失败";

	public final static String CODE_ERROR_SUPPLY_UNCONFIRM = "31";
	public final static String DESC_ERROR_SUPPLY_UNCONFIRM = "供货未确认(此状态下有可能成功也有可能失败)";

	public final static String CODE_ERROR_SUPPLY_LIMIT = "32";
	public final static String DESC_ERROR_SUPPLY_LIMIT = "上游供货已经到达限额";

	public final static String CODE_ERROR_USER = "40";
	public final static String DESC_ERROR_USER = "代理商用户不存在或代理商用户类型不对";

	public final static String CODE_ERROR_ACCT = "41";

	public final static String CODE_ERROR_BALANCE = "42";

	public final static String CODE_ERROR_PAYORDER = "43";

	public final static String CODE_ERROR_AUTH = "44";
	public final static String DESC_ERROR_AUTH = "没有该业务权限";

	public final static String CODE_ERROR_IP = "45";
	public final static String DESC_ERROR_IP = "提交IP不在白名单中";

	public final static String CODE_ERROR_ITEM_SALES = "46";
	public final static String DESC_ERROR_ITEM_SALES = "没有该商品充值权限（商品未开通）";

	public final static String CODE_ERROR_SYSTEM = "50";
	public final static String DESC_ERROR_SYSTEM = "系统异常";

	public final static String CODE_ERROR_BUSI = "51";
	public final static String DESC_ERROR_BUSI = "交易繁忙";
    }

    public interface MobileBelong {
	public final static int CARRIER_TYPE_UNICOM = 1; // 联通
	public final static int CARRIER_TYPE_TELECOM = 2; // 电信
	public final static int CARRIER_TYPE_MOBILE = 3; // 移动

	public final static String CARRIER_TYPE_UNICOM_DESC = "联通";
	public final static String CARRIER_TYPE_TELECOM_DESC = "电信";
	public final static String CARRIER_TYPE_MOBILE_DESC = "移动";
    }

    public interface AreaInfo {
	public final static int AREA_TYPE_PROVINCE = 1; // 省
	public final static int AREA_TYPE_CITY = 2; // 市
    }

    public interface TraderInfo {
	public final static int STATUS_NORMAL = 0;
	public final static int STATUS_CANCEL = 1;

	public final static String STATUS_NORMAL_DESC = "正常";
	public final static String STATUS_CANCEL_DESC = "注销";

	public final static int TRADER_TYPE_UPSTREAM = 1; // 上游
	public final static int TRADER_TYPE_DOWNSTREAM = 2; // 下游

	public final static int SUPPLY_WAY_SYNC = 0; // 同步
	public final static int SUPPLY_WAY_ASYNC = 1; // 异步

	public final static String SUPPLY_WAY_SYNC_DESC = "同步"; // 同步
	public final static String SUPPLY_WAY_ASYNC_DESC = "异步"; // 异步

	public final static int TYPE_CARD_FORWARD_CHARGE = 1; // 卡密直充
	public final static int TYPE_DIRECT_CHARGE = 2; // 直充
	public final static int TYPE_MAN = 3; // 人工
	public final static int TYPE_CARD = 4; // 开密
	public final static int TYPE_CARD_CHARGE = 5; // 开密

	public final static String TYPE_CARD_FORWARD_CHARGE_DESC = "卡密转直充";
	public final static String TYPE_DIRECT_CHARGE_DESC = "直充";
	public final static String TYPE_MAN_DESC = "人工充值";
	public final static String TYPE_CARD_DESC = "卡密";
	public final static String TYPE_CARD_CHARGE_DESC = "卡密充值";

	public final static int SUPPLY_BULK_ONE = 1;// 每次监听只能发一个报文
	public final static int SUPPLY_BULK_MULTI = 2;// 每次监听可发多个报文

	public final static String SUPPLY_BULK_ONE_DESC = "单报文";
	public final static String SUPPLY_BULK_MULTI_DESC = "多报文";

	public final static int NEED_SMS_NONE = 0; // 无短信通知
	public final static int NEED_SMS_NOTE = 1; // 有短信通知

	public final static String NEED_SMS_NONE_DESC = "不需要短信通知";
	public final static String NEED_SMS_NOTE_DESC = "成功短信通知";

	public final static int NOTIFY_WAY_NORMAL = 0;// 实际结果回调
	public final static int NOTIFY_WAY_MANUAL = 1;// 人工预成功回调
	public final static int NOTIFY_WAY_AUTO = 2;// 系统预成功回调

	public final static String NOTIFY_WAY_NORMAL_DESC = "实际结果回调";
	public final static String NOTIFY_WAY_MANUAL_DESC = "人工预成功回调";
	public final static String NOTIFY_WAY_AUTO_DESC = "系统预成功回调";
    }

    public interface OperationLog {
	public static final int TYPE_ADD = 1;
	public static final int TYPE_UPDATE = 2;
	public static final int TYPE_OTHER = 3;
	public static final int TYPE_DELETE = -1;

	public static final String TYPE_ADD_DESC = "新增";
	public static final String TYPE_UPDATE_DESC = "修改";
	public static final String TYPE_OTHER_DESC = "其它";
	public static final String TYPE_DELETE_DESC = "删除";
    }

    public interface Task {
	public static final int STATUS_UNDONE = 1; // 未执行
	public static final int STATUS_DONE = 2; // 已执行
	public static final int STATUS_CANCEL = 3; // 已取消
	public static final int STATUS_EXCEPTION = 4; // 异常

	public static final String STATUS_UNDONE_DESC = "未执行";
	public static final String STATUS_DONE_DESC = "已执行";
	public static final String STATUS_CANCEL_DESC = "已取消";
	public static final String STATUS_EXCEPTION_DESC = "异常";

	public static final int COMMIT_TYPE_REAL_TIME = 1; // 即时生效
	public static final int COMMIT_TYPE_TASK = 2; // 定时生效
	public static final String COMMIT_TYPE_REAL_TIME_DESC = "即时生效";
	public static final String COMMIT_TYPE_TASK_DESC = "定时生效";
    }

    public interface RoleInfo {
	public final static int STATUS_NORMAL = 1; // 正常
	public final static int STATUS_CANCEL = 2; // 注销
	public final static int STATUS_DEL = -1; // 删除

	public final static String STATUS_NORMAL_DESC = "正常";
	public final static String STATUS_CANCEL_DESC = "注销";
	public final static String STATUS_DEL_DESC = "删除";
    }

    public interface SupplyOrder {
	public final static int STATUS_INIT = 0; // 创建
	public final static int STATUS_CHARGING = 1; // 处理中
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_LOCK = 4; // 锁定充值中
	public final static int STATUS_FIN = 5;// 批量处理完成、停用
	public final static int STATUS_PARTS = 5;// 拼单待补充
	public final static int STATUS_EXCEPTION = 8; // 异常
	public final static int STATUS_UNCONFIRMED = 9; // 未确认

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_CHARGING_DESC = "处理中";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_LOCK_DESC = "充值中";
	public final static String STATUS_FIN_DESC = "处理完成";// 批量处理完成、停用
	public final static String STATUS_PARTS_DESC = "待补充";
	public final static String STATUS_EXCEPTION_DESC = "异常"; // 未确认
	public final static String STATUS_UNCONFIRMED_DESC = "未确认";

	public final static int REPEAT_TYPE_NO = 0;
	public final static int REPEAT_TYPE_YES = 1;

	public final static String REPEAT_TYPE_NO_DESC = "否";
	public final static String REPEAT_TYPE_YES_DESC = "是";

	public final static int COMBINE_TYPE_NO = 0;
	public final static int COMBINE_TYPE_YES = 1;

	public final static String COMBINE_TYPE_NO_DESC = "否";
	public final static String COMBINE_TYPE_YES_DESC = "是";// 拆单

	public final static int FINAL_TYPE_NO = 0;
	public final static int FINAL_TYPE_YES = 1;

	public final static String FINAL_TYPE_NO_DESC = "否";
	public final static String FINAL_TYPE_YES_DESC = "是";

	public final static int MANUAL_TYPE_NO = 0;
	public final static int MANUAL_TYPE_YES = 1;

	public final static String MANUAL_TYPE_NO_DESC = "否";
	public final static String MANUAL_TYPE_YES_DESC = "是";

	public final static int SALE_INIT = 0; // 创建
	public final static int SALE_EXPORT = 1; // 已导出
	public final static int SALE_SUCCESS = 2; // 完成
	public final static int SALE_FAILED = 3; // 异常

	public final static String SALE_INIT_DESC = "创建";
	public final static String SALE_EXPORT_DESC = "已导出";
	public final static String SALE_SUCCESS_DESC = "完成";
	public final static String SALE_FAILED_DESC = "异常";
    }

    public interface SupplyBatch {
	public final static int STATUS_INIT = 1; // 待审核
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 取消

	public final static String STATUS_INIT_DESC = "待审核";
	public final static String STATUS_SUCCESS_DESC = "通过";
	public final static String STATUS_FAILED_DESC = "取消";

	public final static int RESULT_INIT = 0; // 待处理
	public final static int RESULT_NORMAL = 1; // 处理中
	public final static int RESULT_SUCCESS = 2; // 全成功
	public final static int RESULT_FAILED = 3; // 全失败
	public final static int RESULT_PARTS = 4; // 部分成功
	public final static int RESULT_CLOSE = 5; // 关闭

	public final static String RESULT_INIT_DESC = "待处理";
	public final static String RESULT_NORMAL_DESC = "处理中";
	public final static String RESULT_SUCCESS_DESC = "全成功";
	public final static String RESULT_FAILED_DESC = "全失败";
	public final static String RESULT_PARTS_DESC = "部分成功";
	public final static String RESULT_CLOSE_DESC = "关闭";
    }

    public interface Export {
	// 1 处理中 2 成功 3 失败 9 异常
	public final static int STATUS_EXPORT = 1; // 处理中
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_EXCEPTION = 9; // 异常

	public final static String STATUS_EXPORT_DESC = "处理中";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_EXCEPTION_DESC = "异常";

	public final static int TYPE_BIZ_ORDER = 1;
	public final static int TYPE_SUPPLY_ORDER = 2;
	public final static int TYPE_REFUND_ORDER = 3;
	public final static int TYPE_ACCT_LOG = 4;
	public final static int TYPE_SMS_ORDER = 5;
	public final static int TYPE_SMS_SUPPLY = 6;
    }

    public interface ManualLog {
	public final static int STATUS_CHARGING = 1; // 提交中
	public final static int STATUS_FIN = 2; // 提交完成
	public final static int STATUS_FAILED = 3; // 提交失败

	public final static String STATUS_CHARGING_DESC = "提交中";
	public final static String STATUS_FIN_DESC = "提交完成";
	public final static String STATUS_FAILED_DESC = "提交失败";

	public final static int TYPE_CHARGE_FILE = 1; // 文件充值
	public final static int TYPE_CHARGE_TEXT = 2; // 输入充值

	public final static String TYPE_CHARGE_FILE_DESC = "文件充值";
	public final static String TYPE_CHARGE_TEXT_DESC = "输入充值";
    }

    public interface ManualOrder {
	public final static int STATUS_INIT = 0; // 创建
	public final static int STATUS_CHARGING = 1; // 充值中
	public final static int STATUS_SUCCESS = 2; // 成功
	public final static int STATUS_FAILED = 3; // 失败
	public final static int STATUS_REPEAT = 4;// 已补充

	public final static String STATUS_INIT_DESC = "创建";
	public final static String STATUS_CHARGING_DESC = "充值中";
	public final static String STATUS_SUCCESS_DESC = "成功";
	public final static String STATUS_FAILED_DESC = "失败";
	public final static String STATUS_REPEAT_DESC = "已补充";

	public final static int REPEAT_TYPE_NO = 0;
	public final static int REPEAT_TYPE_YES = 1;

	public final static String REPEAT_TYPE_NO_DESC = "否";
	public final static String REPEAT_TYPE_YES_DESC = "是";
    }

    public interface CacheKey {
	public final static String PDD_TOKEN_KEY = "pdd_token_cache";
	public final static String SXYD_TOKEN_KEY = "sxyd_token_cache";
	public final static String SXYD_SMS_KEY = "sxyd_sms_cache";
	public final static String MOBILE_CHECK_KEY = "mobile_check_cache";
    }

    public final static Map<String, String> ITEM_SIZE_MAP = new LinkedHashMap<String, String>();
    static {
	ITEM_SIZE_MAP.put("5", "5M");
	ITEM_SIZE_MAP.put("10", "10M");
	ITEM_SIZE_MAP.put("30", "30M");
	ITEM_SIZE_MAP.put("50", "50M");
	ITEM_SIZE_MAP.put("70", "70M");
	ITEM_SIZE_MAP.put("100", "100M");
	ITEM_SIZE_MAP.put("150", "150M");
	ITEM_SIZE_MAP.put("200", "200M");
	ITEM_SIZE_MAP.put("300", "300M");
	ITEM_SIZE_MAP.put("500", "500M");
	ITEM_SIZE_MAP.put("700", "700M");
	ITEM_SIZE_MAP.put("1024", "1G");
	ITEM_SIZE_MAP.put("2048", "2G");
	ITEM_SIZE_MAP.put("3072", "3G");
	ITEM_SIZE_MAP.put("4096", "4G");
	ITEM_SIZE_MAP.put("5120", "5G");
	ITEM_SIZE_MAP.put("6144", "6G");
	ITEM_SIZE_MAP.put("10240", "10G");
	ITEM_SIZE_MAP.put("11264", "11G");
	ITEM_SIZE_MAP.put("15360", "15G");
	ITEM_SIZE_MAP.put("30720", "30G");
    }

    public final static Map<String, String> ITEM_RANGE_MAP = new LinkedHashMap<String, String>();
    static {
	ITEM_RANGE_MAP.put("0", "月包(漫游)");
	ITEM_RANGE_MAP.put("1", "月包(省内)");
	ITEM_RANGE_MAP.put("2", "红包(省内)");
	ITEM_RANGE_MAP.put("5", "一日包(漫游)");
	ITEM_RANGE_MAP.put("6", "一日包(省内)");
	ITEM_RANGE_MAP.put("7", "三日包(漫游)");
	ITEM_RANGE_MAP.put("8", "三日包(省内)");
	ITEM_RANGE_MAP.put("9", "七日包(漫游)");
	ITEM_RANGE_MAP.put("10", "七日包(省内)");
	ITEM_RANGE_MAP.put("14", "四小时包(漫游)");
	ITEM_RANGE_MAP.put("15", "二十四小时包(漫游)");
	ITEM_RANGE_MAP.put("16", "二十四小时包(省内)");
    }

    public final static int[] MOBILE_UNICOM__KEY = { 130, 131, 132, 145, 146, 155, 156, 166, 167, 1704, 1707, 1708, 1709, 171,
	    175, 176, 185, 186 };// 联通

    public final static int[] MOBILE_CMCC__KEY = { 1340, 1341, 1342, 1343, 1344, 1345, 1346, 1347, 1348, 135, 136, 137, 138, 139,
	    147, 148, 150, 151, 152, 157, 158, 159, 165, 1703, 1705, 1706, 172, 178, 182, 183, 184, 187, 188, 198 };// 移动

    public final static int[] MOBILE_TELECOM__KEY = { 133, 1349, 149, 153, 162, 1700, 1701, 1702, 173, 17400, 17401, 17402,
	    17403, 17404, 17405, 177, 180, 181, 189, 191, 193, 199 };// 电信

    public final static String BUY_REQUEST_SPLIT = ",";

    public interface Ltkd {
	public final static String LTKD_ID_KEY = "SHJG2X";
	public final static long LTKD_USER_ID = Utils.getLong("ltkd.userId");
	public final static long SLS_USER_ID = Utils.getLong("sls.userId");
	public final static long SLSX_USER_ID = Utils.getLong("slsx.userId");
	public final static long HZXS_USER_ID = Utils.getLong("hzxs.userId");
	public final static long LTDK_USER_ID = Utils.getLong("ltdk.userId");
    }
    public static  final String  constTime = "00";
}
