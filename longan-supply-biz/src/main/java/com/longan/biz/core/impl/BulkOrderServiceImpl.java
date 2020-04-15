package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.BulkOrderService;
import com.longan.biz.dao.BulkOrderDAO;
import com.longan.biz.dataobject.BulkOrder;
import com.longan.biz.dataobject.BulkOrderQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class BulkOrderServiceImpl extends BaseService implements BulkOrderService {
    @Resource
    private BulkOrderDAO bulkOrderDAO;

    @Override
    public Result<BulkOrder> createBulkOrder(BulkOrder bulkOrder) {
	Result<BulkOrder> result = new Result<BulkOrder>();
	result.setModule(bulkOrder);
	if (bulkOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	try {
	    Long id = bulkOrderDAO.insert(bulkOrder);
	    if (id != null) {
		bulkOrder.setId(id);
		result.setSuccess(true);
		result.setModule(bulkOrder);
	    } else {
		result.setResultMsg("创建多笔单失败");
	    }
	} catch (SQLException ex) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建多笔单失败,数据库异常");
	    logger.error("createBulkOrder error", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateBulkOrder(BulkOrder bulkOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (bulkOrder == null || bulkOrder.getId() == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	try {
	    int id = bulkOrderDAO.updateBulkOrder(bulkOrder);
	    if (id > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("更新多笔供货单失败");
	    }
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("更新多笔供货单失败 ,数据库异常");
	    logger.error("updateBulkOrder error", e);
	}

	return result;
    }

    @Override
    public Result<BulkOrder> getBulkOrderByIdAndTraderId(Long id, Long traderId) {
	Result<BulkOrder> result = new Result<BulkOrder>();
	if (traderId == null || id == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	BulkOrderQuery bulkOrderQuery = new BulkOrderQuery();
	bulkOrderQuery.setSupplyTraderId(traderId);
	bulkOrderQuery.setId(id);

	try {
	    List<BulkOrder> list = bulkOrderDAO.queryByPage(bulkOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("getBulkOrderByIdAndTraderId error 查询数据库异常");
	    logger.error("getBulkOrderByIdAndTraderId error", ex);
	    return result;
	}
	return result;
    }

    @Override
    public Result<BulkOrder> getBulkOrderByUpstreamSerialno(String upstreamSerialno, Long traderId) {
	Result<BulkOrder> result = new Result<BulkOrder>();
	if (traderId == null || upstreamSerialno == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	BulkOrderQuery bulkOrderQuery = new BulkOrderQuery();
	bulkOrderQuery.setSupplyTraderId(traderId);
	bulkOrderQuery.setUpstreamSerialno(upstreamSerialno);

	try {
	    List<BulkOrder> list = bulkOrderDAO.queryByPage(bulkOrderQuery);
	    result.setSuccess(true);
	    if (list != null && !list.isEmpty()) {
		result.setModule(list.get(0));
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("getBulkOrderByUpstreamSerialno error 查询数据库异常");
	    logger.error("getBulkOrderByUpstreamSerialno error", ex);
	    return result;
	}
	return result;
    }
}
