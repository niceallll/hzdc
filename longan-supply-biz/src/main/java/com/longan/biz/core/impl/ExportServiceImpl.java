package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.ExportService;
import com.longan.biz.dao.ExportDAO;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class ExportServiceImpl extends BaseService implements ExportService {
    @Resource
    private ExportDAO exportDAO;

    @Resource
    protected LocalCachedService localCachedService;

    @Override
    public Result<Export> createExport(Export export, BizOrderQuery bizOrderQuery) {
	export.setDateStart(bizOrderQuery.getStartGmtCreate());
	export.setDateEnd(bizOrderQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (bizOrderQuery.getItemId() != null) {
	    export.setItemId(bizOrderQuery.getItemId());
	    export.setItemName(getItemName(bizOrderQuery.getItemId()));
	}
	if (bizOrderQuery.getUserId() != null) {
	    export.setDownstreamId(bizOrderQuery.getUserId());
	    export.setDownstreamName(getUserName(bizOrderQuery.getUserId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    @Override
    public Result<Export> createExport(Export export, SupplyOrderQuery supplyOrderQuery) {
	export.setDateStart(supplyOrderQuery.getStartGmtCreate());
	export.setDateEnd(supplyOrderQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (supplyOrderQuery.getItemId() != null) {
	    export.setItemId(supplyOrderQuery.getItemId());
	    export.setItemName(getItemName(supplyOrderQuery.getItemId()));
	}
	if (supplyOrderQuery.getUserId() != null) {
	    export.setDownstreamId(supplyOrderQuery.getUserId());
	    export.setDownstreamName(getUserName(supplyOrderQuery.getUserId()));
	}
	if (supplyOrderQuery.getSupplyTraderId() != null) {
	    export.setUpstreamId(supplyOrderQuery.getSupplyTraderId());
	    export.setUpstreamName(getUserName(supplyOrderQuery.getSupplyTraderId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    @Override
    public Result<Export> createExport(Export export, RefundOrderQuery refundOrderQuery) {
	export.setDateStart(refundOrderQuery.getStartGmtCreate());
	export.setDateEnd(refundOrderQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (refundOrderQuery.getUserId() != null) {
	    export.setDownstreamId(refundOrderQuery.getUserId());
	    export.setDownstreamName(getUserName(refundOrderQuery.getUserId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    @Override
    public Result<Export> createExport(Export export, AcctLogQuery acctLogQuery) {
	export.setDateStart(acctLogQuery.getStartGmtCreate());
	export.setDateEnd(acctLogQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (acctLogQuery.getItemId() != null) {
	    export.setItemId(acctLogQuery.getItemId());
	    export.setItemName(getItemName(acctLogQuery.getItemId()));
	}
	if (acctLogQuery.getUserId() != null) {
	    export.setDownstreamId(acctLogQuery.getUserId());
	    export.setDownstreamName(getUserName(acctLogQuery.getUserId()));
	}
	if (acctLogQuery.getUpStreamId() != null) {
	    export.setUpstreamId(acctLogQuery.getUpStreamId());
	    export.setUpstreamName(getUserName(acctLogQuery.getUpStreamId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    @Override
    public Result<Export> createExport(Export export, SmsOrderQuery smsOrderQuery) {
	export.setDateStart(smsOrderQuery.getStartGmtCreate());
	export.setDateEnd(smsOrderQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (smsOrderQuery.getItemId() != null) {
	    export.setItemId(smsOrderQuery.getItemId());
	    export.setItemName(getItemName(smsOrderQuery.getItemId()));
	}
	if (smsOrderQuery.getUserId() != null) {
	    export.setDownstreamId(smsOrderQuery.getUserId());
	    export.setDownstreamName(getUserName(smsOrderQuery.getUserId()));
	}
	if (smsOrderQuery.getUpstreamId() != null) {
	    export.setUpstreamId(smsOrderQuery.getUpstreamId());
	    export.setUpstreamName(getUserName(smsOrderQuery.getUpstreamId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    @Override
    public Result<Export> createExport(Export export, SmsSupplyQuery smsSupplyQuery) {
	export.setDateStart(smsSupplyQuery.getStartGmtCreate());
	export.setDateEnd(smsSupplyQuery.getEndGmtCreate());
	export.setStatus(Constants.Export.STATUS_EXPORT);
	if (smsSupplyQuery.getSupplyTraderId() != null) {
	    export.setUpstreamId(smsSupplyQuery.getSupplyTraderId());
	    export.setUpstreamName(getUserName(smsSupplyQuery.getSupplyTraderId()));
	}
	export.setGmtCreate(new Date());
	return createExport(export);
    }

    private Result<Export> createExport(Export export) {
	Result<Export> result = new Result<Export>();
	try {
	    Long id = exportDAO.insert(export);
	    if (id != null) {
		export.setId(id);
		result.setSuccess(true);
		result.setModule(export);
	    } else {
		result.setResultMsg("创建导出单失败");
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("创建导出单失败  msg: " + ex.getMessage());
	    logger.error("createExport error", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateExport(Export export, Boolean isSucc) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    Export updateExport = new Export();
	    updateExport.setId(export.getId());
	    updateExport.setGmtModify(new Date());
	    if (isSucc) {
		updateExport.setStatus(Constants.Export.STATUS_SUCCESS);
	    } else {
		updateExport.setStatus(Constants.Export.STATUS_FAILED);
	    }
	    updateExport.setCostTime(updateExport.computCostTime(export.getGmtCreate()));

	    int row = exportDAO.updateByPrimaryKeySelective(updateExport);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新导出单失败。导出单id : " + updateExport.getId());
	    }
	} catch (SQLException e) {
	    result.setResultMsg("导出单更新失败  exportId : " + export.getId());
	    logger.error("updateExport error exportId " + export.getId(), e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<Export> getExportById(Long id) {
	Result<Export> result = new Result<Export>();
	if (id == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    Export export = exportDAO.selectByPrimaryKey(id);
	    if (export != null) {
		result.setSuccess(true);
		result.setModule(export);
	    } else {
		result.setResultMsg("没有该导出单。导出单id : " + id);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("导出单查询失败  id : " + id + " msg: " + e.getMessage());
	    logger.error("getExportById error", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> deleteExportById(Long id) {
	Result<Boolean> result = new Result<Boolean>();
	if (id == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    int count = exportDAO.deleteByPrimaryKey(id);
	    if (count > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("导出单删除失败 id : " + id);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("导出单删除失败  id : " + id + " msg: " + e.getMessage());
	    logger.error("deleteExportById error", e);
	}
	return result;
    }

    private String getItemName(Integer itemId) {
	Item item = localCachedService.getItem(itemId);
	if (item == null) {
	    return "";
	} else {
	    return item.getItemName();
	}
    }

    private String getUserName(Long userId) {
	UserInfo userInfo = localCachedService.getUserInfo(userId);
	if (userInfo == null) {
	    return "";
	} else {
	    return userInfo.getUserName();
	}
    }
}
