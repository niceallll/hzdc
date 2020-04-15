package com.longan.biz.core.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.AreaInfoService;
import com.longan.biz.core.BaseService;
import com.longan.biz.dao.AreaInfoDAO;
import com.longan.biz.dao.MobileBelongDAO;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.AreaInfoExample;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.MobileBelongExample;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class AreaInfoServiceImpl extends BaseService implements AreaInfoService {
    @Resource
    private MobileBelongDAO mobileBelongDAO;

    @Resource
    private AreaInfoDAO areaInfoDAO;

    @SuppressWarnings("unchecked")
    @Override
    public Result<MobileBelong> queryProvinceCodeByMobile(String mobile) {
	Result<MobileBelong> result = new Result<MobileBelong>();

	Pattern pattern = Pattern.compile("\\d{11}");
	Matcher matcher = pattern.matcher(mobile);
	if (!matcher.matches()) {
	    result.setResultMsg("手机号格式不对");
	    return result;
	}
	String part = mobile.substring(0, 7);

	MobileBelongExample example = new MobileBelongExample();
	example.createCriteria().andMobilePartEqualTo(part);
	List<MobileBelong> list = null;
	try {
	    list = (List<MobileBelong>) mobileBelongDAO.selectByExample(example);
	} catch (SQLException e) {
	    logger.error("queryProvinceCodeByMobile error ", e);
	    result.setResultMsg("查询手机号失败");
	    return result;
	}

	result.setSuccess(true);
	if (list != null && list.size() > 0) {
	    result.setModule(list.get(0));
	}
	return result;
    }

    @Override
    public Result<Boolean> importDb(File file) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	if (file == null) {
	    result.setResultMsg("file is null");
	    return result;
	}

	BufferedReader br = null;
	try {
	    br = new BufferedReader(new FileReader(file));
	    String str = null;
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] strArray = str.split(",");
		MobileBelong mobileBelong = new MobileBelong();
		mobileBelong.setMobilePart(StringUtils.replace(strArray[0], "\"", ""));
		mobileBelong.setProvinceName(StringUtils.replace(strArray[1], "\"", ""));
		mobileBelong.setCityName(StringUtils.replace(strArray[2], "\"", ""));
		mobileBelong.setMobileTypeName(StringUtils.replace(strArray[3], "\"", ""));
		mobileBelong.setAreaCode(StringUtils.replace(strArray[4], "\"", ""));
		mobileBelong.setCarrierName(StringUtils.replace(strArray[5], "\"", ""));
		String carrier = StringUtils.replace(strArray[6], "\"", "");
		if ("LT".equals(carrier)) {
		    mobileBelong.setCarrierType(Constants.MobileBelong.CARRIER_TYPE_UNICOM);
		} else if ("DX".equals(carrier)) {
		    mobileBelong.setCarrierType(Constants.MobileBelong.CARRIER_TYPE_TELECOM);
		} else if ("YD".equals(carrier)) {
		    mobileBelong.setCarrierType(Constants.MobileBelong.CARRIER_TYPE_MOBILE);
		}
		mobileBelong.setProvinceCode(StringUtils.replace(strArray[7], "\"", ""));
		mobileBelong.setCityCode(StringUtils.replace(strArray[8], "\"", ""));
		mobileBelongDAO.insert(mobileBelong);
		logger.warn("insert one row success");
	    }
	} catch (FileNotFoundException e) {
	    logger.error("importDb error ", e);
	} catch (IOException e) {
	    logger.error("importDb error ", e);
	} catch (SQLException e) {
	    logger.error("importDb insert error", e);
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    logger.error("importDb io close error", e);
		}
	    }
	}

	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<AreaInfo>> queryAllProvince() {
	Result<List<AreaInfo>> result = new Result<List<AreaInfo>>();
	AreaInfoExample example = new AreaInfoExample();
	example.createCriteria().andTypeEqualTo(Constants.AreaInfo.AREA_TYPE_PROVINCE);
	List<AreaInfo> list = null;
	try {
	    list = (List<AreaInfo>) areaInfoDAO.selectByExample(example);
	} catch (SQLException e) {
	    logger.error("queryAllProvince error ", e);
	    result.setResultMsg("查询省域编号失败  msg: " + e.getMessage());
	    return result;
	}

	result.setSuccess(true);
	result.setModule(list);
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<AreaInfo>> queryAllCity() {
	Result<List<AreaInfo>> result = new Result<List<AreaInfo>>();
	AreaInfoExample example = new AreaInfoExample();
	example.createCriteria().andTypeEqualTo(Constants.AreaInfo.AREA_TYPE_CITY);
	List<AreaInfo> list = null;
	try {
	    list = (List<AreaInfo>) areaInfoDAO.selectByExample(example);
	} catch (SQLException e) {
	    logger.error("queryAllCity error ", e);
	    result.setResultMsg("查询市域编号失败  msg: " + e.getMessage());
	    return result;
	}

	result.setSuccess(true);
	result.setModule(list);
	return result;
    }
}
