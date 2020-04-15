package com.longan.mng.action.biz;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.longan.biz.dataobject.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.ItemService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.FuncUtils;
import com.longan.biz.utils.OperLogUtils;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.domain.MobileCheck;
import com.longan.client.remote.service.CallBackService;
import com.longan.client.remote.service.CallMessageService;
import com.longan.client.remote.service.SupplyForRemoteService;
import com.longan.client.remote.service.SupplyQueryService;
import com.longan.mng.action.common.BaseController;


@Controller
@RequestMapping("biz/bizOrderDeal")
public class BizOrderDeal extends BaseController {
    private final static Long mcheck_unicom = Utils.getLong("mcheck.unicom");
    private final static Long mcheck_cmcc = Utils.getLong("mcheck.cmcc");
    private final static Long mcheck_telecom = Utils.getLong("mcheck.telecom");

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private CallBackService callBackService;

    @Resource
    private CallMessageService callMessageService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private SupplyForRemoteService supplyForRemoteService;

    @Resource
    private SupplyQueryService supplyQueryService;

    @Resource
    private ItemService itemService;

    // 预成功订单：预失败
    @RequestMapping(params = "type=refund")
    public String onRequestRefund(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model,
                                  HttpServletRequest request) {
        String returnUrl = "biz/queryBizOrder";
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行退款操作 订单号 : " + bizOrderId);
        if (!checkSupplyOrderFailed(bizOrderId)) {
            alertError(model, "包含未失败的供货单、不允许做退款操作");
            return returnUrl;
        }

        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
            alertError(model, "没有该订单");
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (!bizOrder.canDeal()) {
            alertError(model, "该订单不允许做退款操作");
            return returnUrl;
        }

        String successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
        if (flag) {
            successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
        }

        bizOrder.setDealOperId(userInfo.getId());
        bizOrder.setDealOperName(userInfo.getUserName());
        Integer oNotifyStatus = bizOrder.getNotifyStatus();
        Result<Boolean> cancelBizOrderResult = bizOrderService.cancelBizOrder(bizOrder);
        if (!cancelBizOrderResult.isSuccess()) {
            alertError(model, cancelBizOrderResult.getResultMsg());
            return returnUrl;
        }

        try {
            if (FuncUtils.noneNotified(oNotifyStatus)) {
                callBackService.callBackAsync(bizOrder);
            }
        } catch (Exception ex) {
            logger.error("callBackAsync error", ex);
        }

        @SuppressWarnings("unchecked")
        Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
        OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                .getModule(), userInfo, map.get("moduleName") + "(退款)", bizOrder.getBizId(), map.get("loginIp"));
        operationLogService.createOperationLog(operationLog);

        alertSuccess(model, successUrl);
        return returnUrl;
    }
    // 预成功订单：预批量失败退款
    @RequestMapping(params = "type=refundBatch")
    public String onRequestRefundRBatch(@RequestParam("bizOrderIds") String bizOrderIds, HttpSession session, Model model,
                                  HttpServletRequest request) {
        System.out.println("预批量失败退款"+bizOrderIds);
        String successUrl = null;
        String returnUrl = "biz/queryBizOrder";
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行退款操作 订单号 : " + bizOrderIds);//日志记录操作
        String[] split = bizOrderIds.split(",");
        for (int i = 0; i < split.length; i++) {
            Long bizOrderId = Long.valueOf(split[i]);
            if (!checkSupplyOrderFailed(bizOrderId)) {
                alertError(model, bizOrderId+"包含未失败的供货单、不允许做退款操作");
                return returnUrl;
            }

            Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
            if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
                alertError(model, "没有该订单");
                return returnUrl;
            }
            BizOrder bizOrder = queryReuslt.getModule();
            if (!bizOrder.canDeal()) {
                alertError(model, bizOrderId+"该订单不允许做退款操作");
                return returnUrl;
            }

             successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
            if (flag) {
                successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
            }

            bizOrder.setDealOperId(userInfo.getId());
            bizOrder.setDealOperName(userInfo.getUserName());
            Integer oNotifyStatus = bizOrder.getNotifyStatus();
            Result<Boolean> cancelBizOrderResult = bizOrderService.cancelBizOrder(bizOrder);
            if (!cancelBizOrderResult.isSuccess()) {
                alertError(model, cancelBizOrderResult.getResultMsg());
                return returnUrl;
            }

            try {
                if (FuncUtils.noneNotified(oNotifyStatus)) {
                    callBackService.callBackAsync(bizOrder);
                }
            } catch (Exception ex) {
                logger.error("callBackAsync error", ex);
            }
            @SuppressWarnings("unchecked")
            Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
            OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                    .getModule(), userInfo, map.get("moduleName") + "(退款)", bizOrder.getBizId(), map.get("loginIp"));
            operationLogService.createOperationLog(operationLog);
        }

        alertSuccess(model, successUrl);
        return returnUrl;
    }
//    // 预成功订单：预批量失败
//    @RequestMapping(params = "type=refundBatch")
//    public String onRequestRefundBatch(@RequestParam("bizOrderIds") Long bizOrderIds, HttpSession session, Model model,
//                                  HttpServletRequest request) {
//
//        return  null;
//    }
    //预成功订单：批量确认成功订单
    @RequestMapping(params = "type=makeUpBatch")
    public String onRequestmakeUpBatch(@RequestParam("bizOrderIds") String bizOrderIds, HttpSession session, Model model,
                              HttpServletRequest request) {
        System.out.println("批量确认成功订单"+bizOrderIds);
        String returnUrl = "biz/queryBizOrder";
        String successUrl = null;
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行确认操作 订单号 : " + bizOrderIds);
        String[] split = bizOrderIds.split(",");
        for (int i = 0; i < split.length; i++) {
            Long bizOrderId = Long.valueOf(split[i]);
            if (!checkSupplyOrder(bizOrderId)) {
                alertError(model, bizOrderId+"包含未完成的供货单、不允许做确认操作");
                return returnUrl;
            }

            Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
            if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
                alertError(model, bizOrderId+"没有该订单");
                return returnUrl;
            }
            BizOrder bizOrder = queryReuslt.getModule();
            if (!bizOrder.canDeal()) {
                alertError(model, bizOrderId+"该订单不允许做确认操作");
                return returnUrl;
            }
                successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
            if (flag) {
                successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
            }

            bizOrder.setDealOperId(userInfo.getId());
            bizOrder.setDealOperName(userInfo.getUserName());
            Integer oNotifyStatus = bizOrder.getNotifyStatus();
            Result<Boolean> confirmBizOrderResult = bizOrderService.confirmBizOrder(bizOrder);
            if (!confirmBizOrderResult.isSuccess()) {
                alertError(model, confirmBizOrderResult.getResultMsg());
                return returnUrl;
            }

            try {
                if (FuncUtils.noneNotified(oNotifyStatus)) {
                    callBackService.callBackAsync(bizOrder);
                }
            } catch (Exception ex) {
                logger.error("callBackAsync error", ex);
            }
            callMessageService.sendSmsNote(bizOrder);

            @SuppressWarnings("unchecked")
            Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
            OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                    .getModule(), userInfo, map.get("moduleName") + "(确认)", bizOrder.getBizId(), map.get("loginIp"));
            operationLogService.createOperationLog(operationLog);
        }
        alertSuccess(model, successUrl);
        return returnUrl;
     }
    // 预成功订单：预失败
    @RequestMapping(params = "type=makeUp")
    public String onRequestMakeUp(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model,
                                  HttpServletRequest request) {
        String returnUrl = "biz/queryBizOrder";
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行确认操作 订单号 : " + bizOrderId);
        if (!checkSupplyOrder(bizOrderId)) {
            alertError(model, "包含未完成的供货单、不允许做确认操作");
            return returnUrl;
        }

        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
            alertError(model, "没有该订单");
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (!bizOrder.canDeal()) {
            alertError(model, "该订单不允许做确认操作");
            return returnUrl;
        }

        String successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
        if (flag) {
            successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
        }

        bizOrder.setDealOperId(userInfo.getId());
        bizOrder.setDealOperName(userInfo.getUserName());
        Integer oNotifyStatus = bizOrder.getNotifyStatus();
        Result<Boolean> confirmBizOrderResult = bizOrderService.confirmBizOrder(bizOrder);
        if (!confirmBizOrderResult.isSuccess()) {
            alertError(model, confirmBizOrderResult.getResultMsg());
            return returnUrl;
        }

        try {
            if (FuncUtils.noneNotified(oNotifyStatus)) {
                callBackService.callBackAsync(bizOrder);
            }
        } catch (Exception ex) {
            logger.error("callBackAsync error", ex);
        }
        callMessageService.sendSmsNote(bizOrder);

        @SuppressWarnings("unchecked")
        Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
        OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                .getModule(), userInfo, map.get("moduleName") + "(确认)", bizOrder.getBizId(), map.get("loginIp"));
        operationLogService.createOperationLog(operationLog);

        alertSuccess(model, successUrl);
        return returnUrl;
    }

    // 预成功订单：预失败
    @RequestMapping(params = "type=repeatCharge")
    public String onRequestRepeatCharge(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model,
                                        HttpServletRequest request) {
        return repeatCharge(false, bizOrderId, session, model, request);
    }
    //预成功订单：预批量失败补充操作
    @RequestMapping(params = "type=repeatChargeBatch")
    public String onRequestRepeatChargeBatch(@RequestParam("bizOrderIds") String bizOrderIds, HttpSession session, Model model,
                                        HttpServletRequest request) {
        System.out.println("预批量失败补充操作"+bizOrderIds);
        return repeatChargeBatch(false, bizOrderIds, session, model, request);
    }

    // 预成功订单：预失败
    @RequestMapping(params = "type=fastestCharge")
    public String onRequestFastestCharge(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model,
                                         HttpServletRequest request) {
        return repeatCharge(true, bizOrderId, session, model, request);
    }

    // 预成功订单：处理中|未确认|预失败、失败+预成功回调定单
    @RequestMapping(params = "type=notify")
    public String onRequestNotify(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model) {
        String returnUrl = "biz/queryBizOrder";
        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行回调操作 订单号 : " + bizOrderId);
        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
            alertError(model, "没有该订单");
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (!bizOrder.canNotify()) {
            alertError(model, "该订单不允许做回调操作");
            return returnUrl;
        }

        if (bizOrder.isManNotify() && bizOrder.getNotifyStatus() != Constants.BizOrder.NOTIFY_SUCCESS) {
            BizOrder updateBizOrder = new BizOrder();
            updateBizOrder.setId(bizOrderId);
            updateBizOrder.setGmtNotify(new Date());
            updateBizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_SUCCESS);
            // 临时使用itemSize
            updateBizOrder.setItemSize(updateBizOrder.computCostTime(bizOrder.getGmtCreate()));
            updateBizOrder.setDealOperId(userInfo.getId());
            updateBizOrder.setDealOperName(userInfo.getUserName());
            Result<Boolean> notifyResult = bizOrderService.updateBizOrder(updateBizOrder);
            if (!notifyResult.isSuccess()) {
                alertError(model, "更新通知失败");
                return returnUrl;
            }
            bizOrder.setNotifyStatus(Constants.BizOrder.NOTIFY_SUCCESS);
        }
        if (bizOrder.getNotifyStatus() != Constants.BizOrder.NOTIFY_SUCCESS) {
            alertError(model, "只允许预成功回调");
            return returnUrl;
        }

        bizOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);
        try {
            callBackService.callBackAsync(bizOrder);
        } catch (Exception ex) {
            logger.error("callBackAsync error", ex);
        }

        String successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
        alertSuccess(model, successUrl);
        return returnUrl;
    }

    // 正常回调
    @RequestMapping(params = "type=callback")
    public String onRequestCallback(@RequestParam("bizOrderId") Long bizOrderId, HttpSession session, Model model) {
        String returnUrl = "biz/queryBizOrder";
        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行回调操作 订单号 : " + bizOrderId);
        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
            alertError(model, "没有该订单");
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (!bizOrder.canCallback()) {
            alertError(model, "该订单不允许做回调操作");
            return returnUrl;
        }

        if (bizOrder.isCombine() && bizOrder.getStatus() == Constants.BizOrder.STATUS_SUCCESS) {
            bizOrder.setExtend(supplyOrderService.getCombineOrderExtends(bizOrder.getId()));
        }

        try {
            callBackService.callBackAsync(bizOrder);
        } catch (Exception ex) {
            logger.error("callBackAsync error", ex);
        }

        String successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
        alertSuccess(model, successUrl);
        return returnUrl;
    }

    // 预成功订单
    @RequestMapping(params = "type=mobileCheck")
    public String onRequestMobileCheck(@RequestParam("bizOrderId") Long bizOrderId, Model model) {
        String returnUrl = "/biz/mobileCheck";
        Result<MobileCheck> result = new Result<MobileCheck>();
        model.addAttribute("result", result);

        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess()) {
            result.setResultMsg(queryReuslt.getResultMsg());
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (bizOrder == null) {
            result.setResultMsg("没有该订单");
            return returnUrl;
        }

        try {
            Long upstreamId = Long.parseLong(bizOrder.getUpstreamId());
            Integer carrierType = bizOrder.getCarrierType();
            if (carrierType == Constants.Item.CARRIER_TYPE_UNICOM) {
                if (mcheck_unicom != 0) {
                    upstreamId = mcheck_unicom;
                }
            } else if (carrierType == Constants.Item.CARRIER_TYPE_MOBILE) {
                if (mcheck_cmcc != 0) {
                    upstreamId = mcheck_cmcc;
                }
            } else if (carrierType == Constants.Item.CARRIER_TYPE_TELECOM) {
                if (mcheck_telecom != 0) {
                    upstreamId = mcheck_telecom;
                }
            }

            result = supplyQueryService.mobileCheck(upstreamId, bizOrderId, bizOrder.getItemUid(), bizOrder.getItemFacePrice());
        } catch (Exception ex) {
            logger.error("mobileCheck 查询失败", ex);
            return returnUrl;
        }
        model.addAttribute("result", result);
        return returnUrl;
    }
    //预失败补充操作
    private String repeatCharge(Boolean fastest, Long bizOrderId, HttpSession session, Model model, HttpServletRequest request) {
        String returnUrl = "biz/queryBizOrder";
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行补充操作 订单号 : " + bizOrderId);
        if (!checkSupplyOrderFailed(bizOrderId)) {
            alertError(model, "包含未失败的供货单、不允许做补充操作");
            return returnUrl;
        }

        Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
        if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
            alertError(model, "没有该订单");
            return returnUrl;
        }
        BizOrder bizOrder = queryReuslt.getModule();
        if (!bizOrder.canDeal()) {
            alertError(model, "该订单不允许做补充操作");
            return returnUrl;
        }

        String successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
        if (flag) {
            successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
        }

        bizOrder.setDealOperId(userInfo.getId());
        bizOrder.setDealOperName(userInfo.getUserName());
        bizOrder.setRepeatType(Constants.BizOrder.REPEAT_TYPE_YES);
        // 手动补充，不作最大补充次数、时间限制
        bizOrder.setManualType(Constants.BizOrder.MANUAL_TYPE_YES);
        if (fastest) {
            // 快速通道补充
            bizOrder.setSupplyFilterIndex(getSupplyFastestIndex(bizOrder.getItemId()) - 1);
        } else {
            // 手动补充，从最初通道开始补充
            bizOrder.setSupplyFilterIndex(-1);
        }
        bizOrder.setStatus(Constants.BizOrder.STATUS_CHARGING);

        Result<SupplyOrder> supplyResult = null;
        try {
            supplyResult = supplyForRemoteService.supply(bizOrder);
        } catch (Exception ex) {
            logger.error("repeatCharge error ", ex);
            alertError(model, "补充供货异常");
            return returnUrl;
        }

        if (!supplyResult.isSuccess()) {
            if (Constants.ErrorCode.CODE_ERROR_SUPPLY_UNCONFIRM.equals(supplyResult.getResultCode())) {
                // 不处理
            } else {
                alertError(model, supplyResult.getResultMsg());
                if (supplyResult.getModule() != null) {
                    supplyResult.getModule().setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
                    supplyOrderService.cancelSupplyOrder(supplyResult.getModule());
                }

                if (bizOrder.isManNotify()) {
                    // 预失败
                    bizOrder.setStatus(Constants.BizOrder.STATUS_PENDING);
                    bizOrderService.updateBizOrder(bizOrder);
                    alertError(model, "预成功订单、补充供货失败");
                } else {
                    bizOrderService.cancelBizOrder(bizOrder);
                    alertError(model, "补充供货失败");
                }
                return returnUrl;
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
        OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                .getModule(), userInfo, map.get("moduleName") + "(补充)", bizOrder.getBizId(), map.get("loginIp"));
        operationLogService.createOperationLog(operationLog);

        alertSuccess(model, successUrl);
        return returnUrl;
    }
    //预批量失败快速补充操作
    private String repeatChargeBatch(Boolean fastest, String bizOrderIds, HttpSession session, Model model, HttpServletRequest request) {
        String returnUrl = "biz/queryBizOrder";
        boolean flag = request.getHeader("Referer").indexOf("queryAllBizOrder") >= 0;
        String successUrl = null;
        if (flag) {
            returnUrl = "statistic/queryAllBizOrder";
        }

        UserInfo userInfo = getUserInfo(session);
        logger.warn(userInfo.getUserName() + "执行补充操作 订单号 : " + bizOrderIds);//记录日志
        String[] split = bizOrderIds.split(",");
        for (int i = 0;  i < split.length; i++) {
            Long bizOrderId = Long.valueOf(split[i]);
            if (!checkSupplyOrderFailed(bizOrderId)) {
                alertError(model, bizOrderId+"包含未失败的供货单、不允许做补充操作");
                return returnUrl;
            }
            //查询是否有此订单id
            Result<BizOrder> queryReuslt = bizOrderService.getBizOrderById(bizOrderId);
            if (!queryReuslt.isSuccess() || queryReuslt.getModule() == null) {
                alertError(model, bizOrderId+"没有该订单");
                return returnUrl;
            }
            //此订单能否操作
            BizOrder bizOrder = queryReuslt.getModule();
            if (!bizOrder.canDeal()) {
                alertError(model, bizOrderId+"该订单不允许做补充操作");
                return returnUrl;
            }

            successUrl = "queryBizOrder.do?bizId=" + bizOrder.getBizId() + "&id=" + bizOrderId;
            if (flag) {
                successUrl = "../statistic/queryAllBizOrder.do?id=" + bizOrderId;
            }

            bizOrder.setDealOperId(userInfo.getId());
            bizOrder.setDealOperName(userInfo.getUserName());
            bizOrder.setRepeatType(Constants.BizOrder.REPEAT_TYPE_YES);
            // 手动补充，不作最大补充次数、时间限制
            bizOrder.setManualType(Constants.BizOrder.MANUAL_TYPE_YES);
            if (fastest) {
                // 快速通道补充
                bizOrder.setSupplyFilterIndex(getSupplyFastestIndex(bizOrder.getItemId()) - 1);
            } else {
                // 手动补充，从最初通道开始补充
                bizOrder.setSupplyFilterIndex(-1);
            }
            bizOrder.setStatus(Constants.BizOrder.STATUS_CHARGING);

            Result<SupplyOrder> supplyResult = null;
            try {
                supplyResult = supplyForRemoteService.supply(bizOrder);
            } catch (Exception ex) {
                logger.error("repeatCharge error ", ex);
                alertError(model, "补充供货异常");
                return returnUrl;
            }

            if (!supplyResult.isSuccess()) {
                if (Constants.ErrorCode.CODE_ERROR_SUPPLY_UNCONFIRM.equals(supplyResult.getResultCode())) {
                    // 不处理
                } else {
                    alertError(model, supplyResult.getResultMsg());
                    if (supplyResult.getModule() != null) {
                        supplyResult.getModule().setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
                        supplyOrderService.cancelSupplyOrder(supplyResult.getModule());
                    }

                    if (bizOrder.isManNotify()) {
                        // 预失败
                        bizOrder.setStatus(Constants.BizOrder.STATUS_PENDING);
                        bizOrderService.updateBizOrder(bizOrder);
                        alertError(model, bizOrderId+"预成功订单、补充供货失败");
                    } else {
                        bizOrderService.cancelBizOrder(bizOrder);
                        alertError(model, bizOrderId+"补充供货失败");
                    }
                    return returnUrl;
                }
            }

            @SuppressWarnings("unchecked")
            Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
            OperationLog operationLog = OperLogUtils.operationLogDeal(bizOrder, bizOrderService.getBizOrderById(bizOrderId)
                    .getModule(), userInfo, map.get("moduleName") + "(补充)", bizOrder.getBizId(), map.get("loginIp"));
            operationLogService.createOperationLog(operationLog);
        }
        alertSuccess(model, successUrl);
        return returnUrl;
    }

    private Boolean checkSupplyOrderFailed(Long bizOrderId) {
        Result<List<SupplyOrder>> queryReuslt = supplyOrderService.querySupplyOrderByBizOrder(bizOrderId);
        if (queryReuslt.isSuccess() && queryReuslt.getModule() != null) {
            for (SupplyOrder supplyOrder : queryReuslt.getModule()) {
                if (supplyOrder.getSupplyStatus() != Constants.SupplyOrder.STATUS_FAILED) {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean checkSupplyOrder(Long bizOrderId) {
        Result<List<SupplyOrder>> queryReuslt = supplyOrderService.querySupplyOrderByBizOrder(bizOrderId);
        if (queryReuslt.isSuccess() && queryReuslt.getModule() != null) {
            for (SupplyOrder supplyOrder : queryReuslt.getModule()) {
                if (supplyOrder.canDeal()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Integer getSupplyFastestIndex(Integer itemId) {
        Result<List<ItemSupply>> result = itemService.queryItemSupplyOnSale(itemId);
        if (result.isSuccess() && result.getModule() != null) {
            try {
                List<ItemSupply> list = result.getModule();
                for (int pos = 0; pos < list.size(); pos++) {
                    if (list.get(pos).isFastestWay()) {
                        return pos;
                    }
                }
            } catch (Exception ex) {
            }
        }
        return 0;
    }

    @RequestMapping(params = "type=remaks")
    public String updataRemakss(@RequestParam("bizOrderId") Long bizOrderId,@RequestParam("bizId")int bizId ,Model model) {

        Result<BizOrder> selectBizOrder = bizOrderService.selectRemaks(bizOrderId);
        BizOrder bizOrder = selectBizOrder.getModule();
        if (bizOrder.getLockOperId() != null) {
            UserInfo oper = localCachedService.getUserInfo(bizOrder.getLockOperId());
            bizOrder.setLockOperName(oper == null ? "" : oper.getUserName());
        }
        if (bizOrder.getDealOperId() != null) {
            UserInfo oper = localCachedService.getUserInfo(bizOrder.getDealOperId());
            bizOrder.setDealOperName(oper == null ? "" : oper.getUserName());
        }
        if (bizOrder.getUserId() != null) {
            UserInfo oper = localCachedService.getUserInfo(bizOrder.getUserId());
            bizOrder.setDownstreamName(oper == null ? "" : oper.getUserName());
        }
        model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
        model.addAttribute("bizOrder", bizOrder);
        return "biz/lyly";
    }

    @ResponseBody
    @RequestMapping(params = "type=rem",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String uodataRemak(BizOrder bizOrder, @RequestParam("bizOrderId") Long bizOrderId) {

        bizOrder.setId(bizOrderId);
        Result<BizOrder> selectBizOder = bizOrderService.selectRemaks(bizOrderId);
        BizOrder bizOrders = selectBizOder.getModule();
        if (bizOrders.getId().equals(bizOrder.getId())) {
            if (bizOrders.getMemo()!=null){
                if (bizOrders.getMemo().equals(bizOrder.getMemo())) {
                    return "没有修改";
                } else if (!bizOrders.getMemo().equals(bizOrder.getMemo())) {
                    bizOrderService.updataRemaks(bizOrder);
                    return "修改成功";
                }
                }else {
                if (bizOrder.getMemo()==null||bizOrder.getMemo()==""){
                    return "没有修改";
                }else {
                    bizOrderService.updataRemaks(bizOrder);
                    return "修改成功";
                }

            }
        }else {
            return "数据格式错误";
        }
        return null;
    }
}
