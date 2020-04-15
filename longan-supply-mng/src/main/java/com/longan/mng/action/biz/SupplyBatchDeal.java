package com.longan.mng.action.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.SupplyBatchService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.service.CallBackService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.SupplyBatchForm;

@Controller
@RequestMapping("biz/supplyBatchDeal")
public class SupplyBatchDeal extends BaseController {
    private final static String BATCH_PATH = Utils.getProperty("batch.url");
    private final static String queryListPage = "biz/querySupplyBatch";

    @Resource
    private Validator validator;

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private SupplyBatchService supplyBatchService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private CallBackService callBackService;

    @Resource
    private CommonsMultipartResolver multipartResolver;

    @RequestMapping(params = "type=page")
    public String onRequestPage(@ModelAttribute("supplyBatchForm") SupplyBatchForm supplyBatchForm,
	    @RequestParam("bizId") Integer bizId, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	setSelectValue(model, bizId);
	supplyBatchForm.setBizId(bizId.toString());
	return "biz/inSupplyBatch";
    }

    @RequestMapping(params = "type=add")
    public String onRequestAdd(HttpServletRequest request, HttpSession session,
	    @ModelAttribute("supplyBatchForm") SupplyBatchForm supplyBatchForm, BindingResult bindingResult, Model model) {
	UserInfo userInfo = getUserInfo(session);
	if (!checkBizAuth(Integer.parseInt(supplyBatchForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	Integer bizId = Integer.parseInt(supplyBatchForm.getBizId());
	setSelectValue(model, bizId);
	validator.validate(supplyBatchForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return "biz/inSupplyBatch";
	}
	if (!checkFile(request, multipartResolver)) {
	    alertError(model, "上传文件不能是空");
	    return "biz/inSupplyBatch";
	}

	String fileName = uploadFile(request, bizId, Long.parseLong(supplyBatchForm.getUpstreamId()));
	if (fileName == null) {
	    alertError(model, "上传文件不能是空");
	    return "biz/inSupplyBatch";
	}

	SupplyBatch supplyBatch = formToSupplyBatch(userInfo, fileName, supplyBatchForm);
	Result<Boolean> result = supplyBatchService.createSupplyBatch(supplyBatch);
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	} else {
	    alertSuccess(model, "querySupplyBatch.do?bizId=" + supplyBatchForm.getBizId());
	}
	return "biz/inSupplyBatch";
    }

    @RequestMapping(params = "type=down")
    public void onRequestDown(Long id, HttpServletResponse response) {
	Result<SupplyBatch> result = supplyBatchService.getSupplyBatchById(id);
	if (!result.isSuccess()) {
	    logger.error("无该记录 supplyBatchId：", id);
	    return;
	}

	try {
	    SupplyBatch supplyBatch = result.getModule();
	    String fileName = supplyBatch.getFileName();
	    response.setContentType("APPLICATION/OCTET-STREAM");

	    File file = new File(fileName);
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    if (file.exists()) {
		OutputStream out = response.getOutputStream();
		out.write(FileUtils.readFileToByteArray(file));
		out.flush();
		out.close();
	    }
	} catch (Exception ex) {
	    logger.error("下载文档失败：", ex);
	}
    }

    @RequestMapping(params = "type=pass")
    public String onRequestPass(Long id, Integer bizId, HttpSession session, Model model) {
	Result<SupplyBatch> supplyBatchResult = supplyBatchService.getSupplyBatchById(id);
	if (!supplyBatchResult.isSuccess()) {
	    alertError(model, supplyBatchResult.getResultMsg());
	    return queryListPage;
	}

	final SupplyBatch supplyBatch = supplyBatchResult.getModule();
	if (supplyBatch.getStatus() != Constants.SupplyBatch.STATUS_INIT) {
	    alertError(model, "审核状态非正常");
	    return queryListPage;
	}

	UserInfo userInfo = getUserInfo(session);
	final Long operId = userInfo.getId();
	final String operName = userInfo.getUserName();
	SupplyBatch updateSupplyBatch = new SupplyBatch();
	updateSupplyBatch.setId(id);
	updateSupplyBatch.setStatus(Constants.SupplyBatch.STATUS_SUCCESS);
	updateSupplyBatch.setResult(Constants.SupplyBatch.RESULT_NORMAL);
	updateSupplyBatch.setVerifyOperId(operId);
	updateSupplyBatch.setVerifyOperName(operName);
	Result<Boolean> result = supplyBatchService.updateSupplyBatch(updateSupplyBatch);
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	} else {
	    logger.warn("开始批量慢充处理 id : " + id);
	    es.execute(new Runnable() {
		@Override
		public void run() {
		    SupplyBatch resultSupplyBatch = new SupplyBatch();
		    resultSupplyBatch.setId(supplyBatch.getId());
		    int failTimes = 0;
		    int succTimes = 0;
		    try {
			InputStream is = new FileInputStream(supplyBatch.getFileName());
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int iRowLen = sheet.getLastRowNum();
			for (int iRow = 1; iRow <= iRowLen; iRow++) {
			    Row row = sheet.getRow(iRow);
			    String phoneNo = "";
			    if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				phoneNo = new Double(row.getCell(0).getNumericCellValue()).longValue() + "";
			    } else {
				phoneNo = row.getCell(0).getStringCellValue().trim();
			    }
			    Long supplyOrderId = 0l;
			    if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				supplyOrderId = new Double(row.getCell(1).getNumericCellValue()).longValue();
			    } else {
				supplyOrderId = Long.parseLong(row.getCell(1).getStringCellValue().trim());
			    }
			    String statusDesc = row.getCell(2).getStringCellValue().trim();
			    boolean result = singleResult(operId, operName, supplyOrderId, phoneNo, statusDesc);
			    if (result) {
				succTimes++;
			    } else {
				failTimes++;
			    }
			}

			logger.warn("批量慢充处理完成 id : " + supplyBatch.getId());
			is.close();
		    } catch (Exception ex) {
			resultSupplyBatch.setErrorMsg("处理异常");
			logger.error("批量慢充处理异常 id : " + supplyBatch.getId(), ex);
		    }

		    resultSupplyBatch.setCostTime(supplyBatch.computCostTime(supplyBatch.getGmtCreate()));
		    if (failTimes == 0 && succTimes > 0) {
			resultSupplyBatch.setResult(Constants.SupplyBatch.RESULT_SUCCESS);
		    } else if (succTimes == 0) {
			resultSupplyBatch.setResult(Constants.SupplyBatch.RESULT_FAILED);
		    } else {
			resultSupplyBatch.setResult(Constants.SupplyBatch.RESULT_PARTS);
		    }
		    supplyBatchService.updateSupplyBatch(resultSupplyBatch);
		}
	    });
	    alertSuccess(model, "querySupplyBatch.do?bizId=" + bizId);
	}
	return queryListPage;
    }

    @RequestMapping(params = "type=cancel")
    public String onRequestCancel(Long id, Integer bizId, HttpSession session, Model model) {
	Result<SupplyBatch> supplyBatchResult = supplyBatchService.getSupplyBatchById(id);
	if (!supplyBatchResult.isSuccess()) {
	    alertError(model, supplyBatchResult.getResultMsg());
	    return queryListPage;
	}

	SupplyBatch supplyBatch = supplyBatchResult.getModule();
	if (supplyBatch.getStatus() != Constants.SupplyBatch.STATUS_INIT) {
	    alertError(model, "审核状态非正常");
	    return queryListPage;
	}

	UserInfo userInfo = getUserInfo(session);
	SupplyBatch updateSupplyBatch = new SupplyBatch();
	updateSupplyBatch.setId(id);
	updateSupplyBatch.setStatus(Constants.SupplyBatch.STATUS_FAILED);
	updateSupplyBatch.setResult(Constants.SupplyBatch.RESULT_CLOSE);
	updateSupplyBatch.setVerifyOperId(userInfo.getId());
	updateSupplyBatch.setVerifyOperName(userInfo.getUserName());
	Result<Boolean> result = supplyBatchService.updateSupplyBatch(updateSupplyBatch);
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	} else {
	    alertSuccess(model, "querySupplyBatch.do?bizId=" + bizId);
	}
	return queryListPage;
    }

    private SupplyBatch formToSupplyBatch(UserInfo userInfo, String fileName, SupplyBatchForm supplyBatchForm) {
	SupplyBatch result = new SupplyBatch();
	result.setBizId(Integer.parseInt(supplyBatchForm.getBizId()));
	Long upstreamId = Long.parseLong(supplyBatchForm.getUpstreamId());
	result.setUpstreamId(upstreamId);
	result.setUpstreamName(localCachedService.getUserInfo(upstreamId).getUserName());
	result.setOperId(userInfo.getId());
	result.setOperName(userInfo.getUserName());
	result.setFileName(fileName);
	result.setMemo(supplyBatchForm.getMemo());
	return result;
    }

    private void setSelectValue(Model model, Integer bizId) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(bizId));
	model.addAttribute("bizId", bizId);

	// Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	// if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	// Map<Long, String> map = new HashMap<Long, String>();
	// for (UserInfo user : usUserResult.getModule()) {
	// map.put(user.getId(), user.getUserName());
	// }
	// model.addAttribute("upStreamList", map);
	// }
	model.addAttribute("upStreamList", localCachedService.getUpStreamUser());
    }

    private String uploadFile(HttpServletRequest request, Integer bizId, Long upstreamId) {
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	String fileName = null;
	if (multipartResolver.isMultipart(multipartRequest)) {// 判断request是否有文件上传
	    // srcfname是指文件上传标签的name=值
	    MultiValueMap<String, MultipartFile> multfiles = multipartRequest.getMultiFileMap();
	    for (String srcfname : multfiles.keySet()) {
		MultipartFile mfile = multfiles.getFirst(srcfname);
		try {
		    if (!mfile.isEmpty()) {
			fileName = getFilePath(bizId, upstreamId) + UUID.randomUUID() + ".xls";
			mfile.transferTo(new File(fileName));
		    }
		} catch (Exception ex) {
		    logger.error("uploadFile error upstreamId:" + upstreamId, ex);
		}
	    }
	} else {
	    logger.error("uploadFile error file is null");
	}
	return fileName;
    }

    private String getFilePath(Integer bizId, Long upstreamId) {
	String path = new StringBuilder(BATCH_PATH).append(upstreamId).append("/").append(bizId).append("/").toString();
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}
	return path;
    }

    private boolean singleResult(Long operId, String operName, Long supplyOrderId, String phoneNo, String statusDesc) {
	Result<SupplyOrder> queryResult = supplyOrderService.getSupplyOrderById(supplyOrderId);
	if (!queryResult.isSuccess() || queryResult.getModule() == null) {
	    logger.warn("没有该供货单 supplyOrderId " + supplyOrderId);
	    return false;
	}

	SupplyOrder supplyOrder = queryResult.getModule();
	if (!supplyOrder.canDeal()) {
	    logger.warn("该供货单不允许做操作 supplyOrderId " + supplyOrderId);
	    return true;
	}
	if (!supplyOrder.getItemUid().equals(phoneNo)) {
	    logger.warn("该供货单操作、电话号码不匹配 supplyOrderId " + supplyOrderId);
	    return false;
	}

	supplyOrder.setDealOperId(operId);
	supplyOrder.setDealOperName(operName);
	supplyOrder.setSaleStatus(Constants.SupplyOrder.SALE_SUCCESS);
	supplyOrder.setFinalType(Constants.SupplyOrder.FINAL_TYPE_YES);
	if ("成功".equals(statusDesc)) {
	    return singleMakeUp(supplyOrder);
	} else if ("失败".equals(statusDesc)) {
	    return singleRefund(supplyOrder);
	} else {
	    logger.error("批量操作未知状态 : " + statusDesc);
	}
	return false;
    }

    private boolean singleMakeUp(SupplyOrder supplyOrder) {
	logger.warn("执行确认操作 supplyOrderId :" + supplyOrder.getId());
	Result<Boolean> confirmSupplyOrderResult = supplyOrderService.confirmSupplyOrder(supplyOrder);
	if (!confirmSupplyOrderResult.isSuccess()) {
	    logger.warn("执行确认操作错误 supplyOrderId : " + supplyOrder.getId() + " , " + confirmSupplyOrderResult.getResultMsg());
	    return false;
	}

	Result<BizOrder> bizOrderQueryReuslt = bizOrderService.getBizOrderById(supplyOrder.getBizOrderId());
	if (!bizOrderQueryReuslt.isSuccess()) {
	    logger.warn("没有该订单 bizOrderId : " + supplyOrder.getBizOrderId() + " , " + bizOrderQueryReuslt.getResultMsg());
	    return false;
	}

	BizOrder bizOrder = bizOrderQueryReuslt.getModule();
	if (bizOrder == null) {
	    logger.warn("没有该订单 bizOrderId : " + supplyOrder.getBizOrderId());
	    return false;
	}

	bizOrder.setDealOperId(supplyOrder.getDealOperId());
	bizOrder.setDealOperName(supplyOrder.getDealOperName());
	Result<Boolean> confirmBizOrderResult = bizOrderService.confirmBizOrder(bizOrder);
	if (!confirmBizOrderResult.isSuccess()) {
	    logger.warn("执行确认操作错误 bizOrderId : " + bizOrder.getId() + " , " + confirmBizOrderResult.getResultMsg());
	    return false;
	}

	try {
	    callBackService.callBackAsync(bizOrder);
	} catch (Exception e) {
	    logger.error("callBackAsync error", e);
	}
	return true;
    }

    private boolean singleRefund(SupplyOrder supplyOrder) {
	logger.warn("执行退款操作 supplyOrderId :" + supplyOrder.getId());
	Result<Boolean> cancelSupplyOrderResult = supplyOrderService.cancelSupplyOrder(supplyOrder);
	if (!cancelSupplyOrderResult.isSuccess()) {
	    logger.warn("执行退款操作错误 supplyOrderId : " + supplyOrder.getId() + " , " + cancelSupplyOrderResult.getResultMsg());
	    return false;
	}

	Result<BizOrder> bizOrderQueryReuslt = bizOrderService.getBizOrderById(supplyOrder.getBizOrderId());
	if (!bizOrderQueryReuslt.isSuccess()) {
	    logger.warn("没有该订单 bizOrderId : " + supplyOrder.getBizOrderId() + " , " + bizOrderQueryReuslt.getResultMsg());
	    return false;
	}

	BizOrder bizOrder = bizOrderQueryReuslt.getModule();
	if (bizOrder == null) {
	    logger.warn("没有该订单 bizOrderId : " + supplyOrder.getBizOrderId());
	    return false;
	}

	bizOrder.setDealOperId(supplyOrder.getDealOperId());
	bizOrder.setDealOperName(supplyOrder.getDealOperName());
	Result<Boolean> cancelBizOrderResult = bizOrderService.cancelBizOrder(bizOrder);
	if (!cancelBizOrderResult.isSuccess()) {
	    logger.warn("执行退款操作错误 bizOrderId : " + bizOrder.getId() + " , " + cancelBizOrderResult.getResultMsg());
	    return false;
	}

	try {
	    callBackService.callBackAsync(bizOrder);
	} catch (Exception e) {
	    logger.error("callBackAsync error", e);
	}
	return true;
    }
}
