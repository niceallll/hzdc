package com.longan.biz.core.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.BlackListService;
import com.longan.biz.dao.BlackListDAO;
import com.longan.biz.domain.Result;

public class BlackListServiceImpl extends BaseService implements BlackListService {
    @Resource
    private BlackListDAO blackListDAO;

    @Override
    public Result<Integer> getCountByUid(String itemUid) {
	Result<Integer> result = new Result<Integer>();
	try {
	    Integer count = blackListDAO.countByItemUid(itemUid);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败 msg: " + e.getMessage());
	    logger.error("getCountByUid error ", e);
	}
	return result;
    }
}
