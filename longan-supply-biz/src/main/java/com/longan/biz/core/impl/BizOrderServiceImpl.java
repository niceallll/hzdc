package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.AcctService;
import com.longan.biz.core.AreaInfoService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.ChargingLimitService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.PayService;
import com.longan.biz.core.UserAlertService;
import com.longan.biz.core.UserService;
import com.longan.biz.dao.BizOrderDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderExample;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.CachedUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.SwitchConstant;
import com.longan.biz.utils.Utils;

public class BizOrderServiceImpl extends BaseService implements BizOrderService {
    @Resource
    private BizOrderDAO bizOrderDAO;

    @Resource
    private ItemService itemService;

    @Resource
    private UserService userService;

    @Resource
    private AcctService acctService;

    @Resource
    private AreaInfoService areaInfoService;

    @Resource
    private PayService payService;

    @Resource
    private ChargingLimitService chargingLimitService;

    @Resource
    private MemcachedService memcachedService;

    @Resource
    private UserAlertService userAlertService;

    public Result<BizOrder> bizOrderPreCheck(BizOrder bizOrder) {
        Result<BizOrder> result = new Result<BizOrder>();
        Result<Boolean> checkBizOrderResult = checkBizOrder(bizOrder);
        if (!checkBizOrderResult.isSuccess() || !checkBizOrderResult.getModule()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
            result.setResultMsg(checkBizOrderResult.getResultMsg());
            return result;
        }
        result.setModule(bizOrder);

        // 用户和账户相关查询和校验
        UserInfo userInfo = localCachedService.getUserInfo(bizOrder.getUserId());
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
        TraderInfo traderInfo = localCachedService.getTraderInfo(bizOrder.getUserId());
        if (traderInfo == null) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_USER);
            result.setResultMsg("代理商信息错误");
            return result;
        }

        Result<AcctInfo> acctInfoResult = acctService.getAcctInfo(userInfo.getAcctId());
        if (!acctInfoResult.isSuccess()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_ACCT);
            result.setResultMsg(acctInfoResult.getResultMsg());
            return result;
        }
        AcctInfo acctInfo = acctInfoResult.getModule();
        Result<Boolean> acctCheckResult = acctService.checkAcct(acctInfo);
        if (!acctCheckResult.isSuccess()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_ACCT);
            result.setResultMsg(acctCheckResult.getResultMsg());
            return result;
        }

        // 获取商品
        Result<Item> itemResult = itemService.getItem(bizOrder.getItemId());
        if (!itemResult.isSuccess()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM);
            result.setResultMsg(itemResult.getResultMsg());
            return result;
        }
        Item item = itemResult.getModule();
        if (item.getBizId() == null) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM);
            result.setResultMsg("商品业务编号是空");
            return result;
        }

        // 拆分商品
        if (item.isCombine()) {
            if (item.getItemCategoryId() == null || item.getItemCategoryId() == 0) {
                result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM);
                result.setResultMsg("商品关联未配置");
                return result;
            }
            bizOrder.setItemCategoryId(item.getItemCategoryId());
            bizOrder.setCombineType(Constants.BizOrder.COMBINE_TYPE_YES);
        }

        // 权限判断
        Set<Integer> bizSet = localCachedService.getAuthBizByUserId(userInfo.getId());
        if (bizSet == null || !bizSet.contains(item.getBizId())) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_AUTH);
            result.setResultMsg(Constants.ErrorCode.DESC_ERROR_AUTH);
            return result;
        }
        bizOrder.setBizId(item.getBizId());

        // 重复流水号校验
        Result<Boolean> checkDownStreamSerialno = checkDownstreamSerialno(bizOrder);
        if (!checkDownStreamSerialno.isSuccess() || !checkDownStreamSerialno.getModule()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_SERIALNO);
            result.setResultMsg(checkDownStreamSerialno.getResultMsg());
            return result;
        }

        // 充值手机号码查询
        Result<Boolean> checkUidResult = checkUid(bizOrder);
        if (!checkUidResult.isSuccess()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_PHONE_AREA);
            result.setResultMsg(checkUidResult.getResultMsg());
            return result;
        }

        // 余额校验
        Result<Boolean> checkBalanceAcct = acctService.checkBalanceAcct(acctInfo, item);
        if (!checkBalanceAcct.isSuccess() || !checkBalanceAcct.getModule()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_BALANCE);
            result.setResultMsg(checkBalanceAcct.getResultMsg());
            return result;
        }

        // 传入如果有itemPrice的时候，直接设置价格，之后会校验,没有则获取商品价格。
        if (bizOrder.getItemPrice() == null) {
            Result<Integer> priceResult = itemService.getSalesPrice(item, acctInfo);
            if (!priceResult.isSuccess()) {
                result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM_PRICE);
                result.setResultMsg(priceResult.getResultMsg());
                return result;
            }
            bizOrder.setItemPrice(priceResult.getModule());
        }

        // 设置bizOrder
        bizOrder.setItemFacePrice(item.getItemFacePrice());
        bizOrder.setItemName(item.getItemName());
        bizOrder.setAmount(Long.parseLong(bizOrder.getItemPrice().toString()) * bizOrder.getAmt());
        if (item.getItemDummyPrice() == null) {
            bizOrder.setAmountDummy(bizOrder.getAmount());
        } else {
            bizOrder.setAmountDummy(Long.parseLong(item.getItemDummyPrice().toString()) * bizOrder.getAmt());
        }

        bizOrder.setStatus(Constants.BizOrder.STATUS_INIT);
        if (traderInfo.isManNotify(item.getCarrierType()) || traderInfo.isAutoNotify(item.getCarrierType())) {
            bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_INIT);
        } else {
            bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_NORMAL);
        }

        // 检查商品以及供货商品
        Result<Boolean> checkItemResult = itemService.checkItem(bizOrder);
        if (!checkItemResult.isSuccess() || !checkItemResult.getModule()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM);
            result.setResultMsg(checkItemResult.getResultMsg());
            return result;
        }

        // 充值用户最大限额校验
        Result<Boolean> checkUidAmountResult = checkUidCount(bizOrder);
        if (!checkUidAmountResult.isSuccess() || !checkUidAmountResult.getModule()) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_UID_AMOUNT);
            result.setResultMsg(checkUidAmountResult.getResultMsg());
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<BizOrder> createBizOrder(BizOrder bizOrder) {
        Result<BizOrder> result = new Result<BizOrder>();
        result.setModule(bizOrder);
        if (bizOrder == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        // 创建订单
        try {
            // 这里设置时间，是给后续的补充判断用。
            bizOrder.setGmtCreate(new Date());
            Long id = bizOrderDAO.insert(bizOrder);
            if (id != null) {
                bizOrder.setId(id);
                result.setSuccess(true);
                result.setModule(bizOrder);
                userAlertService.bizOrderRequest(bizOrder);// 预警
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
            logger.error("createBizOrder error", ex);
        }
        return result;
    }

    private Result<Boolean> checkBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null) {
            result.setResultMsg("bizOrder is null");
            return result;
        }
        if (bizOrder.getUserId() == null) {
            result.setResultMsg("用户编号缺失");
            return result;
        }
        if (bizOrder.getItemId() == null) {
            result.setResultMsg("商品编号缺失");
            return result;
        }
        if (bizOrder.getAmt() == null || bizOrder.getAmt() == 0) {
            result.setResultMsg("购买数量缺失");
            return result;
        }
        if (bizOrder.getChannel() == null) {
            result.setResultMsg("渠道编号缺失");
            return result;
        }
        if (bizOrder.getDownstreamSerialno() == null) {
            result.setResultMsg("商户交易流水号缺失");
            return result;
        }
        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    private Result<Boolean> checkUid(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getItemId() == null || bizOrder.getItemUid() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        Item item = localCachedService.getItem(bizOrder.getItemId());
        if (item == null) {
            result.setResultMsg("不存在该商品");
            return result;
        }

        if (!item.isUidNumber()) {
            // QB、积分兑换为主（商品不是供货给手机）
            if (SwitchConstant.CHECK_AREA_SWITCH && !item.isNationwide()) {
                AreaInfo areaInfo = localCachedService.getProvinceByCode(bizOrder.getAreaCode());
                if (areaInfo != null) {
                    if (item.getSalesAreaList() != null && !item.getSalesAreaList().contains(areaInfo.getProvinceCode())) {
                        logger.warn("市区号和商品省域不匹配 市区号: " + bizOrder.getAreaCode() + " 归属地 ：" + areaInfo.getProvinceName()
                                + " 商品编号 : " + item.getId());
                        result.setResultMsg("市区号和商品省域不匹配");
                        return result;
                    }
                    bizOrder.setProvinceCode(areaInfo.getProvinceCode());
                } else {
                    // 查询不到结果，不影响主流程，只记录一下
                    logger.warn("getAreaInfo by areaCode is null areaCode : " + bizOrder.getAreaCode());
                }
            }
        } else {
            Result<MobileBelong> mobileBelongResult = areaInfoService.queryProvinceCodeByMobile(bizOrder.getItemUid());
            if (!mobileBelongResult.isSuccess()) {
                logger.error("checkCarrierType error " + mobileBelongResult.getResultMsg());
                result.setResultMsg(mobileBelongResult.getResultMsg());
                return result;
            }
            MobileBelong mobileBelong = mobileBelongResult.getModule();

            if (SwitchConstant.CHECK_CARRIER_TYPE_SWITCH) {
                if (mobileBelong != null) {
                    if (!mobileBelong.getCarrierType().equals(item.getCarrierType())) {
                        logger.warn("手机号和商品运营商不匹配 uid: " + bizOrder.getItemUid());
                        result.setResultMsg("手机号和商品运营商不匹配");
                        return result;
                    }
                } else {
                    // 查询不到结果，不影响主流程，只记录一下
                    logger.warn("checkCarrierType mobileBelong is null uid : " + bizOrder.getItemUid());
                }
            }
            if (SwitchConstant.CHECK_AREA_SWITCH && !item.isNationwide()) {
                if (mobileBelong != null) {
                    if (item.getSalesAreaList() != null
                            && !(item.getSalesAreaList().contains(mobileBelong.getProvinceCode()) || item.getSalesAreaList()
                            .contains(mobileBelong.getCityCode()))) {// 市域临时
                        logger.warn("手机号和商品省域不匹配 手机号: " + bizOrder.getItemUid() + " 手机归属地 ：" + mobileBelong.getProvinceName()
                                + " 商品编号 : " + item.getId());
                        result.setResultMsg("手机号和商品省域不匹配");
                        return result;
                    }
                } else {
                    // 查询不到结果，不影响主流程，只记录一下
                    logger.warn("checkArea mobileBelong is null uid : " + bizOrder.getItemUid());
                }
            }

            if (bizOrder.getCarrierType() == null) {
                if (mobileBelong == null) {
                    bizOrder.setCarrierType(item.getCarrierType());
                } else {
                    bizOrder.setCarrierType(mobileBelong.getCarrierType());
                }
            }
            if (StringUtils.isBlank(bizOrder.getProvinceCode())) {
                if (mobileBelong == null) {
                    if (item.getSalesArea() == null) {
                        bizOrder.setProvinceCode("");
                    } else {
                        bizOrder.setProvinceCode(item.getSalesArea().split(Constants.Item.SALES_AREA_SPLIT)[0]);
                    }
                } else {
                    bizOrder.setProvinceCode(mobileBelong.getProvinceCode());
                }
            }
        }

        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    @Override
    public Result<Boolean> updateBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            if (bizOrder.getStatus() != null && bizOrder.getStatus() == Constants.BizOrder.STATUS_SUCCESS) {
                // 记录充值时间。
                Result<BizOrder> queryResult = getBizOrderById(bizOrder.getId());
                if (queryResult.getModule() != null) {
                    bizOrder.setCostTime(bizOrder.computCostTime(queryResult.getModule().getGmtCreate()));
                    // 临时使用itemSize
                    Integer itemSize = queryResult.getModule().getItemSize();
                    if (itemSize == null || itemSize == 0) {
                        bizOrder.setGmtNotify(new Date());
                        bizOrder.setItemSize(bizOrder.getCostTime());
                    }
                }
            }

            int row = bizOrderDAO.updateByPrimaryKeySelective(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);
            } else {
                result.setResultMsg("更新订单失败，没有该订单。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("更新订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("updateBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> updateBizOrder(BizOrder bizOrder, BizOrderExample bizOrderExample) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }
        try {
            int row = bizOrderDAO.updateByExampleSelective(bizOrder, bizOrderExample);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);
            } else {
                result.setResultMsg("更新订单失败 。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("更新订单失败  bizorderId : " + bizOrder.getId());
            logger.error("updateBizOrder error", e);
        }
        return result;
    }

    public Result<Boolean> confirmBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);

        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        // if (bizOrder.isSupplyFromStock() && bizOrder.getStockId() != null) {
        // // 这个逻辑主要是给，手动退款订单用的。
        // Result<Stock> getStockResult =
        // stockService.getStockById(bizOrder.getStockId());
        // // 不影响主流程。
        // if (!getStockResult.isSuccess()) {
        // logger.error("confirmBizOrder getStockById error stockId:" +
        // bizOrder.getStockId()
        // + " msg : " + getStockResult.getResultMsg());
        // } else {
        // Stock stock = getStockResult.getModule();
        // if (stock != null) {
        // stockService.deliveryFromStorage(stock);
        // }
        // }
        // }

        try {
            // 记录充值时间。
            if (bizOrder.getGmtCreate() == null) {
                Result<BizOrder> queryResult = getBizOrderById(bizOrder.getId());
                if (queryResult.getModule() == null) {
                    result.setResultMsg("找不到该订单");
                    return result;
                }
                bizOrder.setCostTime(bizOrder.computCostTime(queryResult.getModule().getGmtCreate()));
                // 临时使用itemSize
                bizOrder.setItemSize(queryResult.getModule().getItemSize());
            } else {
                bizOrder.setCostTime(bizOrder.computCostTime(bizOrder.getGmtCreate()));
            }
            // 临时使用itemSize
            Integer itemSize = bizOrder.getItemSize();
            if (itemSize == null || itemSize == 0) {
                bizOrder.setGmtNotify(new Date());
                bizOrder.setItemSize(bizOrder.getCostTime());
            }

            // 更新订单状态
            bizOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);
            if (bizOrder.isManNotify()) {
                Integer notifyStatus = bizOrder.getNotifyStatus();
                if (notifyStatus == Constants.BizOrder.NOTIFY_INIT || notifyStatus == Constants.BizOrder.NOTIFY_CHARGING
                        || notifyStatus == Constants.BizOrder.NOTIFY_UNKNOWN) {
                    bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_SUCCESS);
                }
            }
            bizOrder.setUpstreamMemo(" ");

            int row = bizOrderDAO.updateByPrimaryKeySelective(bizOrder);
            userAlertService.bizOrderRequest(bizOrder);// 预警
            if (row <= 0) {
                result.setResultMsg("订单确认失败  ，没有该订单。订单id : " + bizOrder.getId());
                return result;
            }
        } catch (SQLException e) {
            result.setResultMsg("订单确认失败  bizorderId : " + bizOrder.getId());
            logger.error("confirmBizOrder error bizOrderId " + bizOrder.getId(), e);
            return result;
        }

        // // 处理中限制 减减
        // if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
        // chargingLimitService.decCount(Long.parseLong(bizOrder.getUpstreamId()));
        // }

        // 用户充值金额计数
        String key = CachedUtils.getUidAmountKey(bizOrder.getUserId(), bizOrder.getItemUid());
        Integer amount = bizOrder.getItemFacePrice();
        memcachedService.inc(key, amount, amount);

        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    public Result<Boolean> cancelBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        // if (bizOrder.isSupplyFromStock() && bizOrder.getStockId() != null) {
        // Result<Stock> getStockResult =
        // stockService.getStockById(bizOrder.getStockId());
        // // 不影响主流程。
        // if (!getStockResult.isSuccess()) {
        // logger.error("cancelBizOrder getStockById error stockId:" +
        // bizOrder.getStockId()
        // + " msg : " + getStockResult.getResultMsg());
        // } else {
        // Stock stock = getStockResult.getModule();
        // if (stock != null && bizOrder.isFlagCardValid()) {
        // stock.setErrorInfo(bizOrder.getUpstreamMemo());
        // stockService.sequestrationStorage(stock);
        // } else if (stock != null && !bizOrder.isFlagCardValid()) {
        // stockService.returnToStorage(stock);
        // }
        //
        // }
        // }

        if (bizOrder.getPayOrderId() == null) {
            Result<BizOrder> getBizOrderByIdResult = getBizOrderById(bizOrder.getId());
            if (!getBizOrderByIdResult.isSuccess()) {
                result.setResultMsg(getBizOrderByIdResult.getResultMsg());
                return result;
            }
            bizOrder.setPayOrderId(getBizOrderByIdResult.getModule().getPayOrderId());
        }

        // 如果订单中有支付单号,则走退款流程。
        if (bizOrder.getPayOrderId() != null) {
            OperationVO operationVO = null;
            if (bizOrder.getDealOperId() != null) {
                operationVO = new OperationVO();
                UserInfo userInfo = new UserInfo();
                userInfo.setId(bizOrder.getDealOperId());
                userInfo.setUserName(bizOrder.getDealOperName());
                operationVO.setUserInfo(userInfo);
                operationVO.setOperationMemo("供货失败");
            }
            Result<RefundOrder> commitRefundResult = payService.commitRefund(bizOrder.getPayOrderId(), operationVO);
            if (!commitRefundResult.isSuccess()) {
                if (commitRefundResult.getResultCode() != Constants.PayOrder.CODE_NO_NEED_REFUND) {
                    // 非正常退款失败。
                    result.setResultMsg(commitRefundResult.getResultMsg());
                    return result;
                }
                // 根本没有支付单，或者支付状态非正常(比如已退款,或者未支付等) ，继续做取消操作
            }
            // 退款成功
        }

        // 记录失败时间。
        if (bizOrder.getGmtCreate() != null) {
            bizOrder.setCostTime(bizOrder.computCostTime(bizOrder.getGmtCreate()));
        }
        // 临时使用itemSize
        Integer itemSize = bizOrder.getItemSize();
        if (itemSize == null || itemSize == 0) {
            bizOrder.setGmtNotify(new Date());
            bizOrder.setItemSize(bizOrder.getCostTime());
        }
        bizOrder.setStatus(Constants.BizOrder.STATUS_FAILED);
        if (bizOrder.isManNotify()) {
            Integer notifyStatus = bizOrder.getNotifyStatus();
            if (notifyStatus == Constants.BizOrder.NOTIFY_INIT || notifyStatus == Constants.BizOrder.NOTIFY_CHARGING
                    || notifyStatus == Constants.BizOrder.NOTIFY_UNKNOWN) {
                bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_FAILED);
            }
        }

        int row = 0;
        try {
            row = bizOrderDAO.updateByPrimaryKeySelective(bizOrder);
            userAlertService.bizOrderRequest(bizOrder);// 预警
        } catch (SQLException e) {
            result.setResultMsg("订单取消失败: bizOrderId : " + bizOrder.getId() + " 数据库异常");
            logger.error("cancelBizOrder error bizOrderId " + bizOrder.getId(), e);
            return result;
        }
        if (row <= 0) {
            result.setResultMsg("取消订单失败  ，没有该订单");
            logger.error("cancelBizOrder error bizOrderId " + bizOrder.getId() + " 更新数 0");
            return result;
        }

        // // 处理中限制 减减
        // if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
        // chargingLimitService.decCount(Long.parseLong(bizOrder.getUpstreamId()));
        // }

        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    public Result<Boolean> unConfirmBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            bizOrder.setStatus(Constants.BizOrder.STATUS_UNCONFIRMED);
            int row = bizOrderDAO.updateByPrimaryKeySelective(bizOrder);
            if (row <= 0) {
                result.setResultMsg("订单至未确认状态失败，没有该订单,订单id : " + bizOrder.getId());
                logger.error("unConfirmBizOrder error bizOrderId " + bizOrder.getId() + " 更新数 0");
                return result;
            }
        } catch (SQLException e) {
            result.setResultMsg("订单至未确认状态失败  bizorderId : " + bizOrder.getId());
            logger.error("unConfirmBizOrder error bizOrderId " + bizOrder.getId(), e);
            return result;
        }

        // 未确认不做计数处理
        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    public Result<Boolean> chargingBizOrderAndCounting(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            bizOrder.setStatus(Constants.BizOrder.STATUS_CHARGING);
            if (bizOrder.isManNotify()) {
                bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_CHARGING);
            }

            int row = bizOrderDAO.updateByPrimaryKeySelective(bizOrder);
            if (row <= 0) {
                result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
                result.setResultMsg("订单至未确认状态失败，没有该订单,订单id : " + bizOrder.getId());
                logger.error("chargingBizOrder error bizOrderId " + bizOrder.getId() + " 更新数 0");
                return result;
            }
        } catch (SQLException e) {
            result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
            result.setResultMsg("订单至未确认状态失败  bizorderId : " + bizOrder.getId());
            logger.error("unConfirmBizOrder error bizOrderId " + bizOrder.getId(), e);
            return result;
        }

        // // 处理中限制 加加
        // if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
        // chargingLimitService.incCount(Long.parseLong(bizOrder.getUpstreamId()));
        // }

        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    @Override
    public Result<BizOrder> getBizOrderById(Long bizOrderId) {
        Result<BizOrder> result = new Result<BizOrder>();
        if (bizOrderId == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            BizOrder bizOrder = bizOrderDAO.selectByPrimaryKey(bizOrderId);
            if (bizOrder != null) {
                result.setSuccess(true);
                result.setModule(bizOrder);
            } else {
                result.setResultMsg("没有该订单。订单id : " + bizOrderId);
            }
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败  id : " + bizOrderId + " msg: " + e.getMessage());
            logger.error("queryBizOrderById error", e);
        }
        return result;
    }

    @Override
    public Result<BizOrder> getBizOrderByPayOrder(Long payOrderId) {
        Result<BizOrder> result = new Result<BizOrder>();
        if (payOrderId == null) {
            result.setResultMsg("入参错误");
            return result;
        }
        try {
            BizOrderExample bizOrderExample = new BizOrderExample();
            bizOrderExample.createCriteria().andPayOrderIdEqualTo(payOrderId);
            @SuppressWarnings("unchecked")
            List<BizOrder> list = (List<BizOrder>) bizOrderDAO.selectByExample(bizOrderExample);
            if (list != null && list.size() > 0) {
                result.setSuccess(true);
                result.setModule(list.get(0));
            } else {
                result.setResultMsg("没有该订单信息  支付单id ：" + payOrderId);
            }
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败  支付单id  : " + payOrderId + " msg: " + e.getMessage());
            logger.error("queryBizOrderByPayOrder error", e);
        }
        return result;
    }

    @Override
    public Result<List<BizOrder>> queryBizOrderPage(BizOrderQuery bizOrderQuery) {
        Result<List<BizOrder>> result = new Result<List<BizOrder>>();
        if (bizOrderQuery == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            List<BizOrder> queryResult = bizOrderDAO.queryByPage(bizOrderQuery);
            result.setSuccess(true);
            result.setModule(queryResult);
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败    msg: " + e.getMessage());
            logger.error("queryBizOrderPage error ", e);
        }
        return result;
    }

    @Override
    public Result<Integer> getCountInExport(BizOrderQuery bizOrderQuery) {
        Result<Integer> result = new Result<Integer>();
        if (bizOrderQuery == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            Integer count = bizOrderDAO.countByExport(bizOrderQuery);
            result.setSuccess(true);
            result.setModule(count);
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败    msg: " + e.getMessage());
            logger.error("getCountInExport error ", e);
        }
        return result;
    }

    @Override
    public Result<List<BizOrder>> queryBizOrderExport(BizOrderQuery bizOrderQuery) {
        Result<List<BizOrder>> result = new Result<List<BizOrder>>();
        if (bizOrderQuery == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            List<BizOrder> queryResult = bizOrderDAO.queryByExport(bizOrderQuery);
            result.setSuccess(true);
            result.setModule(queryResult);
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败    msg: " + e.getMessage());
            logger.error("queryBizOrderPage error ", e);
        }
        return result;
    }

    @Override
    public Result<List<BizOrder>> getBizOrderExport(BizOrderQuery bizOrderQuery) {
        Result<List<BizOrder>> result = new Result<List<BizOrder>>();
        if (bizOrderQuery == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            List<BizOrder> queryResult = bizOrderDAO.selectByExport(bizOrderQuery);
            if (queryResult.size() > 0) {
                result.setSuccess(true);
                result.setModule(queryResult);
            }
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败    msg: " + e.getMessage());
            logger.error("getBizOrderExport error ", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> checkDownstreamSerialno(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null) {
            result.setResultMsg("bizOrder is null");
            return result;
        }

        String serialno = bizOrder.getDownstreamSerialno();
        Long userId = bizOrder.getUserId();
        if (serialno == null || userId == null || bizOrder.getBizId() == null) {
            result.setResultMsg("serialno or userId or bizId is null");
            return result;
        }

        BizOrderExample bizOrderExample = new BizOrderExample();
        bizOrderExample.createCriteria().andDownstreamSerialnoEqualTo(serialno).andUserIdEqualTo(userId);
        try {
            List<BizOrder> list = (List<BizOrder>) bizOrderDAO.selectByExample(bizOrderExample);
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

    @Override
    public Result<BizOrder> getBizOrderDownstreamSerialno(String downstreamSerialno, Long userId) {
        Result<BizOrder> result = new Result<BizOrder>();
        if (downstreamSerialno == null || userId == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            BizOrderExample bizOrderExample = new BizOrderExample();
            bizOrderExample.createCriteria().andDownstreamSerialnoEqualTo(downstreamSerialno).andUserIdEqualTo(userId);
            @SuppressWarnings("unchecked")
            List<BizOrder> list = (List<BizOrder>) bizOrderDAO.selectByExample(bizOrderExample);
            result.setSuccess(true);
            if (list != null && list.size() > 0) {
                result.setModule(list.get(0));
            } else {
                result.setResultMsg("没有该订单信息 下游流水号 ：" + downstreamSerialno);
            }
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败   下游流水号 : " + downstreamSerialno);
            logger.error("getBizOrderDownstreamSerialno error", e);
        }
        return result;
    }

    @Override
    public Result<BizOrder> getBizOrderUpstreamSerialno(String upstreamSerialno, Long userId) {
        Result<BizOrder> result = new Result<BizOrder>();
        if (upstreamSerialno == null || userId == null) {
            result.setResultMsg("入参错误");
            return result;
        }
        try {
            BizOrderExample bizOrderExample = new BizOrderExample();
            bizOrderExample.createCriteria().andUpstreamSerialnoEqualTo(upstreamSerialno).andUpstreamIdEqualTo(userId + "");
            @SuppressWarnings("unchecked")
            List<BizOrder> list = (List<BizOrder>) bizOrderDAO.selectByExample(bizOrderExample);
            if (list != null && list.size() > 0) {
                result.setSuccess(true);
                result.setModule(list.get(0));
            } else {
                result.setResultMsg("没有该订单信息 上游流水号 ：" + upstreamSerialno);
            }
        } catch (SQLException e) {
            result.setResultMsg("订单查询失败   上游流水号 : " + upstreamSerialno);
            logger.error("getBizOrderUpstreamSerialno error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> manualLockBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            int row = bizOrderDAO.lockBizOrder(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);
            } else {
                result.setResultMsg("人工锁定订单失败 ,可能被其他操作员抢先啦。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("人工锁定订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("lockBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> manualUnLockBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            int row = bizOrderDAO.unLockBizOrder(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);
            } else {
                result.setResultMsg("解锁订单失败 ,可能被其他操作员抢先啦。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("解锁订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("unLockBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> manualConfirmBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);

        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            if (bizOrder.getGmtCreate() != null) {
                bizOrder.setCostTime(bizOrder.computCostTime(bizOrder.getGmtCreate()));
            }
            int row = bizOrderDAO.confirmBizOrder(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);

                // // 处理中限制 加加
                // if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
                // chargingLimitService.incCount(Long.parseLong(bizOrder.getUpstreamId()));
                // }

                String key = CachedUtils.getUidAmountKey(bizOrder.getUserId(), bizOrder.getItemUid());
                Integer amount = bizOrder.getItemFacePrice();
                memcachedService.inc(key, amount, amount);
            } else {
                result.setResultMsg("人工确认订单失败,订单已经被确认,或者订单不是你锁定的订单。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("人工确认订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("confirmBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> manualCancelBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);

        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        // 如果订单中有支付单号,则走退款流程。
        if (bizOrder.getPayOrderId() != null) {
            OperationVO operationVO = null;
            if (bizOrder.getDealOperId() != null) {
                operationVO = new OperationVO();
                UserInfo userInfo = new UserInfo();
                userInfo.setId(bizOrder.getDealOperId());
                userInfo.setUserName(bizOrder.getDealOperName());
                operationVO.setUserInfo(userInfo);
                operationVO.setOperationMemo("人工充值失败退款");
            }

            Result<RefundOrder> commitRefundResult = payService.commitRefund(bizOrder.getPayOrderId(), operationVO);
            if (!commitRefundResult.isSuccess()) {
                if (commitRefundResult.getResultCode() != Constants.PayOrder.CODE_NO_NEED_REFUND) {
                    // 非正常退款失败。
                    result.setResultMsg(commitRefundResult.getResultMsg());
                    return result;
                }
                // 根本没有支付单，或者支付状态非正常(比如已退款,或者未支付等) ，继续做取消操作
            }
            // 退款成功
        }

        try {
            int row = bizOrderDAO.cancelBizOrder(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);

                // 处理中限制 加加
                if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
                    chargingLimitService.incLimit(Long.parseLong(bizOrder.getUpstreamId()), bizOrder.getItemFacePrice());
                }
                // // 处理中限制 减减
                // if (StringUtils.isNumeric(bizOrder.getUpstreamId())) {
                // chargingLimitService.decCount(Long.parseLong(bizOrder.getUpstreamId()));
                // }
            } else {
                result.setResultMsg("人工取消订单失败 ,可能订单已经被取消。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("人工取消订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("cancelBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> manualUnConfirmBizOrder(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);

        if (bizOrder == null || bizOrder.getId() == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            int row = bizOrderDAO.unConfirmBizOrder(bizOrder);
            if (row > 0) {
                result.setSuccess(true);
                result.setModule(true);
            } else {
                result.setResultMsg("人工取消订单失败 ,已经被其他操作员锁定。订单id : " + bizOrder.getId());
            }
        } catch (SQLException e) {
            result.setResultMsg("人工取消订单失败  bizorderId : " + bizOrder.getId() + " msg: " + e.getMessage());
            logger.error("cancelBizOrder error", e);
        }
        return result;
    }

    @Override
    public Result<Integer> getCountInCharging(Integer bizId) {
        // 包括充值中 和 处理中，未确认
        Result<Integer> result = new Result<Integer>();
        if (bizId == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            BizOrderExample bizOrderExample = new BizOrderExample();
            List<Integer> values = new ArrayList<Integer>();
            values.add(Constants.BizOrder.STATUS_CHARGING);
            values.add(Constants.BizOrder.STATUS_LOCK);
            values.add(Constants.BizOrder.STATUS_UNCONFIRMED);
            bizOrderExample.createCriteria().andBizIdEqualTo(bizId).andStatusIn(values);
            Integer count = bizOrderDAO.countByExample(bizOrderExample);
            if (count != null) {
                result.setSuccess(true);
                result.setModule(count);
            } else {
                result.setResultMsg("查询充值中的订单数量异常  bizId : " + bizId);
            }
        } catch (SQLException e) {
            result.setResultMsg("查询充值中的订单数量异常  bizId : " + bizId + " msg : " + e.getMessage());
            logger.error("getCountInCharging error biz : " + bizId, e);
        }

        return result;
    }

    @Override
    public Result<Integer> getCountInChargingByTraderId(Long traderId) {
        // 包括充值中 和 处理中和未确认
        Result<Integer> result = new Result<Integer>();
        if (traderId == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        try {
            BizOrderExample bizOrderExample = new BizOrderExample();
            List<Integer> values = new ArrayList<Integer>();
            values.add(Constants.BizOrder.STATUS_CHARGING);
            values.add(Constants.BizOrder.STATUS_LOCK);
            values.add(Constants.BizOrder.STATUS_UNCONFIRMED);
            bizOrderExample.createCriteria().andUpstreamIdEqualTo(traderId + "").andStatusIn(values);
            Integer count = bizOrderDAO.countByExample(bizOrderExample);
            if (count != null) {
                result.setSuccess(true);
                result.setModule(count);
            } else {
                result.setResultMsg("查询充值中的订单数量异常  traderId : " + traderId);
            }
        } catch (SQLException e) {
            result.setResultMsg("查询充值中的订单数量异常  traderId : " + traderId + " msg : " + e.getMessage());
            logger.error("getCountInCharging error traderId : " + traderId, e);
        }

        return result;
    }

    @Override
    public Result<Boolean> checkUidCount(BizOrder bizOrder) {
        Result<Boolean> result = new Result<Boolean>();
        result.setModule(false);
        if (bizOrder == null) {
            result.setResultMsg("bizOrder is null");
            return result;
        }
        if (bizOrder.getUserId() == null || bizOrder.getItemUid() == null || bizOrder.getItemFacePrice() == null) {
            result.setResultMsg("uid ,userId or itemFacePrice  is null ");
            return result;
        }

        Integer amount = bizOrder.getItemFacePrice();
        String key = CachedUtils.getUidAmountKey(bizOrder.getUserId(), bizOrder.getItemUid());
        try {
            Long value = memcachedService.getLongValue(key);
            if (value == null || value == 0L) {
                result.setSuccess(true);
                result.setModule(true);
                return result;
            }

            logger.warn("userId : " + bizOrder.getUserId() + " uid : " + bizOrder.getItemUid() + " amount : " + value);
            if (StringUtils.isNumeric(Utils.getProperty("maxUidAmount"))) {
                if (value + amount >= Utils.getLong("maxUidAmount")) {
                    result.setResultMsg(bizOrder.getItemUid() + "用户今日已经达到最大充值金额");
                    logger.warn("userId : " + bizOrder.getUserId() + " uid : " + bizOrder.getItemUid() + " 今日已经达到最大充值金额");
                    return result;
                }
            }
        } catch (Exception e) {
            // 出错了,就降级了,也返回true, 防止memcached挂了后，导致业务走不下去
            logger.error("check Uid Count异常 ", e);
        }
        result.setSuccess(true);
        result.setModule(true);
        return result;
    }

    @Override
    public Result<BizOrder> robotLockBizOrder(Long upstreamId, String posId, String pcId, Long lockOperId) {
        Result<BizOrder> result = new Result<BizOrder>();
        if (upstreamId == null || lockOperId == null) {
            result.setResultMsg("入参错误");
            return result;
        }

        TraderInfo traderInfo = localCachedService.getTraderInfo(upstreamId);
        if (!traderInfo.isManualSupply()) {
            result.setResultMsg("供货商供货方式不对");
            return result;
        }

        BizOrderQuery bizOrderQuery = new BizOrderQuery();
        bizOrderQuery.setUpstreamId(upstreamId);
        bizOrderQuery.setStatus(Constants.BizOrder.STATUS_CHARGING);

        Long startTime = System.currentTimeMillis();
        BizOrder lockedBizOrder = null;
        result.setSuccess(true);
        while ((System.currentTimeMillis() - 5000) <= startTime) {
            Result<List<BizOrder>> queryResult = this.queryBizOrderPage(bizOrderQuery);
            if (!queryResult.isSuccess()) {
                result.setResultMsg(queryResult.getResultMsg());
                return result;
            }

            List<BizOrder> bizOrderList = queryResult.getModule();
            if (bizOrderList == null || bizOrderList.isEmpty()) {
                return result;
            }
            // 打乱减少竞争
            Collections.shuffle(bizOrderList);
            for (BizOrder bizOrder : bizOrderList) {
                bizOrder.setItemPosId(posId);
                bizOrder.setItemPcId(pcId);
                bizOrder.setLockOperId(lockOperId);
                Result<Boolean> lockResult = this.manualLockBizOrder(bizOrder);
                if (!lockResult.isSuccess()) {
                    continue;
                }
                lockedBizOrder = bizOrder;
                break;
            }
            if (lockedBizOrder == null) {
                continue;
            }
            // 锁定成功
            break;
        }
        result.setModule(lockedBizOrder);
        return result;
    }

    @Override
    public void updataRemaks(BizOrder bizOrder) {
        try {
       bizOrderDAO.remarks(bizOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Result<BizOrder> selectRemaks(Long bizOrderId) {
        Result<BizOrder> result = new Result<BizOrder>();

        try {
            BizOrder bizOrder = bizOrderDAO.selectRemaks(bizOrderId);
            result.setModule(bizOrder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
