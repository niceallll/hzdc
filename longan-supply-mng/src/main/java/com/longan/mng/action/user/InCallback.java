package com.longan.mng.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.client.remote.service.CallBackService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.InCallbackForm;
import com.longan.mng.utils.Constants;

@Controller
@RequestMapping("user/inCallback")
public class InCallback extends BaseController {
    @Resource
    private Validator validator;

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private CallBackService callBackService;

    @Resource
    private CommonsMultipartResolver multipartResolver;

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute("inCallbackForm") InCallbackForm inCallbackForm) {
	UserInfo userInfo = localCachedService.getUserInfo(Long.parseLong(inCallbackForm.getUserId()));
	inCallbackForm.setUserName(userInfo.getUserName());
	inCallbackForm.setCompayInfo(userInfo.getCompayInfo());
	return "user/inCallback";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void onPost(HttpServletRequest request, HttpSession session,
	    @ModelAttribute("inCallbackForm") InCallbackForm inCallbackForm, BindingResult bindingResult, Model model) {
	validator.validate(inCallbackForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	if (!checkFile(request, multipartResolver)) {
	    alertError(model, "上传文件不能是空");
	    return;
	}

	final Long userId = Long.parseLong(inCallbackForm.getUserId());
	final String fileName = uploadFile(request, userId);
	if (fileName == null) {
	    alertError(model, "上传文件不能是空");
	    return;
	}

	UserInfo operUser = getUserInfo(session);
	logger.warn(operUser.getUserName() + " 开始执行下游定单重新回调 下游 id : " + userId);
	es.execute(new Runnable() {
	    @Override
	    public void run() {
		try {
		    InputStream is = new FileInputStream(fileName);
		    HSSFWorkbook workbook = new HSSFWorkbook(is);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    int iRowLen = sheet.getLastRowNum();
		    for (int iRow = 1; iRow <= iRowLen; iRow++) {
			Row row = sheet.getRow(iRow);
			// String phoneNo = row.getCell(0).getStringCellValue();
			String serialNo = row.getCell(1).getStringCellValue();

			Result<BizOrder> queryResult = bizOrderService.getBizOrderDownstreamSerialno(serialNo, userId);
			if (!queryResult.isSuccess()) {
			    logger.error("定单查询返回: " + queryResult.getResultMsg() + " 下游编号: " + serialNo);
			    continue;
			}

			BizOrder bizOrder = queryResult.getModule();
			if (bizOrder == null) {
			    logger.warn("没有该订单 下游编号: " + serialNo);
			    continue;
			}

			Result<Boolean> callBackResult = callBackService.callBackAsync(bizOrder);
			if (!callBackResult.isSuccess() || !callBackResult.getModule()) {
			    logger.error("callback failed bizOrderId : " + bizOrder.getId(), callBackResult.getResultMsg());
			}

			try {
			    // 等候1秒再发送通知
			    Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
		    }
		    logger.warn("执行下游定单重新回调完成 下游 id : " + userId);
		    is.close();
		} catch (Exception ex) {
		    logger.error("执行下游定单重新回调失败 下游  id : " + userId, ex);
		}
	    }
	});
	alertSuccess(model, "queryDownStreamInfo.do?type=2");
    }

    private String uploadFile(HttpServletRequest request, Long userId) {
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	String fileName = null;
	if (multipartResolver.isMultipart(multipartRequest)) {// 判断request是否有文件上传
	    // srcfname是指文件上传标签的name=值
	    MultiValueMap<String, MultipartFile> multfiles = multipartRequest.getMultiFileMap();
	    for (String srcfname : multfiles.keySet()) {
		MultipartFile mfile = multfiles.getFirst(srcfname);
		try {
		    if (!mfile.isEmpty()) {
			fileName = Constants.UPLOAD_PATH + userId + ".xls";
			mfile.transferTo(new File(fileName));
		    }
		} catch (Exception e) {
		    logger.error("uploadFile error userId:" + userId, e);
		}
	    }
	} else {
	    logger.error("uploadFile error file is null");
	}
	return fileName;
    }
}
