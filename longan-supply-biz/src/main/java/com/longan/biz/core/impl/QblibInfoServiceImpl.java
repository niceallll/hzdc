package com.longan.biz.core.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.QblibInfoService;
import com.longan.biz.dao.MobileQblibDAO;
import com.longan.biz.dao.PhoneQblibDAO;
import com.longan.biz.dataobject.PhoneQblibExample;
import com.longan.biz.domain.Result;

public class QblibInfoServiceImpl extends BaseService implements QblibInfoService {
    @Resource
    private MobileQblibDAO mobileQblibDAO;

    @Resource
    private PhoneQblibDAO phoneQblibDAO;

    @Override
    public Result<Boolean> deletePhoneByUid(String itemUid) {
	Result<Boolean> result = new Result<Boolean>();
	PhoneQblibExample example = new PhoneQblibExample();
	example.createCriteria().andItemUidEqualTo(itemUid);
	try {
	    int num = phoneQblibDAO.deleteByExample(example);
	    if (num > 0) {
		result.setSuccess(true);
	    }
	} catch (SQLException e) {
	    logger.error("deletePhoneByUid error ", e);
	    result.setResultMsg("删除固话号失败");
	}
	return result;
    }

    @Override
    public Result<String> queryMobileByAreaCode(String areacode) {
	Result<String> result = new Result<String>();
	try {
	    String mobile = mobileQblibDAO.selectMobileByAreaCode(areacode);
	    if (StringUtils.isNotBlank(mobile)) {
		result.setModule(mobile);
		result.setSuccess(true);
	    }
	} catch (SQLException e) {
	    logger.error("queryMobileByAreaCode error ", e);
	    result.setResultMsg("随机手机号失败");
	}
	return result;
    }

    @Override
    public Result<String> queryPhoneByAreaCode(String areacode) {
	Result<String> result = new Result<String>();
	try {
	    String phone = phoneQblibDAO.selectPhoneByAreaCode(areacode);
	    if (StringUtils.isNotBlank(phone)) {
		result.setModule(phone);
		result.setSuccess(true);
	    }
	} catch (SQLException e) {
	    logger.error("queryPhoneByAreaCode error ", e);
	    result.setResultMsg("随机固话号失败");
	}
	return result;
    }
}
