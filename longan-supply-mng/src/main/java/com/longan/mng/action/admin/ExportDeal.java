package com.longan.mng.action.admin;

import java.io.File;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.ExportService;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.ExportTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class ExportDeal extends BaseController {
    @Resource
    private ExportService exportService;

    @RequestMapping("admin/downloadExportFile")
    public void doQueryExport(Long id, HttpServletResponse response, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	Result<Export> result = exportService.getExportById(id);
	if (!result.isSuccess()) {
	    logger.error("无该记录 exportId：", id);
	    return;
	}

	Export export = result.getModule();
	if (!userInfo.getId().equals(export.getUserId())) {
	    logger.error("无权下载此文档 exportId：", id);
	    return;
	}

	try {
	    String fileName = export.getFileName();
	    response.setContentType("APPLICATION/OCTET-STREAM");
	    response.setHeader("Content-Disposition", "attachment; filename=" + ExportTool.getFileName(fileName));

	    File file = new File(fileName);
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

    @RequestMapping("admin/deleteExportFile")
    public String doQueryExport(Long id, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	Result<Export> getResult = exportService.getExportById(id);
	if (!getResult.isSuccess()) {
	    alertError(model, getResult.getResultMsg());
	    return "error/error";
	}

	Export export = getResult.getModule();
	if (!userInfo.getId().equals(export.getUserId())) {
	    alertError(model, "无权下载此文档 exportId：" + id);
	    return "error/autherror";
	}

	String fileName = export.getFileName();
	File file = new File(fileName);
	if (file.exists()) {
	    file.delete();
	}

	Result<Boolean> result = exportService.deleteExportById(id);
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return "error/error";
	}
	return "success/succ";
    }
}
