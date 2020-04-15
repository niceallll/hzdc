package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.SupplyBatchService;
import com.longan.biz.dao.SupplyBatchDAO;
import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyBatchQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class SupplyBatchServiceImpl extends BaseService implements SupplyBatchService {
    @Resource
    private SupplyBatchDAO supplyBatchDAO;

    @Override
    public Result<Boolean> createSupplyBatch(SupplyBatch supplyBatch) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    supplyBatch.setStatus(Constants.SupplyBatch.STATUS_INIT);
	    supplyBatch.setResult(Constants.SupplyBatch.RESULT_INIT);
	    Long id = supplyBatchDAO.insert(supplyBatch);
	    if (id == null) {
		result.setResultMsg("新增批量失败");
		return result;
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception ex) {
	    result.setResultMsg("新增批量失败  msg:" + ex.getMessage());
	    logger.error("createSupplyBatch error ", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateSupplyBatch(SupplyBatch supplyBatch) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    int row = supplyBatchDAO.updateByPrimaryKeySelective(supplyBatch);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException ex) {
	    result.setResultMsg("更新批量失败   msg: " + ex.getMessage());
	    logger.error("updateSupplyBatch error ", ex);
	}
	return result;
    }

    @Override
    public Result<SupplyBatch> getSupplyBatchById(Long id) {
	Result<SupplyBatch> result = new Result<SupplyBatch>();
	try {
	    SupplyBatch supplyBatch = supplyBatchDAO.selectByPrimaryKey(id);
	    if (supplyBatch != null) {
		result.setSuccess(true);
		result.setModule(supplyBatch);
	    } else {
		result.setResultMsg("不存在该批量申请 ");
	    }
	} catch (Exception ex) {
	    result.setResultMsg("批量申请查询异常  msg : " + ex.getMessage());
	    logger.error("getSupplyBatchById error id = " + id, ex);
	}
	return result;
    }

    @Override
    public Result<List<SupplyBatch>> querySupplyBatch(SupplyBatchQuery supplyBatchQuery) {
	Result<List<SupplyBatch>> result = new Result<List<SupplyBatch>>();
	if (supplyBatchQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<SupplyBatch> queryResult = supplyBatchDAO.queryByPage(supplyBatchQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("人工列表查询失败  msg: " + ex.getMessage());
	    logger.error("querySupplyBatch error ", ex);
	}
	return result;
    }
}
