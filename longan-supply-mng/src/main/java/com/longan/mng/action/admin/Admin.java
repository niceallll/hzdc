package com.longan.mng.action.admin;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.AreaInfoService;
import com.longan.biz.core.ChargingLimitService;
import com.longan.mng.action.common.BaseController;

@Controller
public class Admin extends BaseController {
    @Resource
    private AreaInfoService areaInfoService;

    @Resource
    private ChargingLimitService chargingLimitService;

    @Resource
    private LocalCachedService localCachedService;

    @RequestMapping(value = "admin/initMobileBelong", method = RequestMethod.GET)
    public void initMobielBelong() {
	File file = new File("/root/kane/11.csv");
	areaInfoService.importDb(file);
    }

    @RequestMapping(value = "admin/test", method = RequestMethod.GET)
    public void test() {
	System.out.println(localCachedService.getProvinceByCode("110000").getProvinceName());
    }

    @RequestMapping(value = "admin/initCharingLimit", method = RequestMethod.GET)
    public void chargingLimitService() {
	chargingLimitService.initChargingCount();
    }
}
