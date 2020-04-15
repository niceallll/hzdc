package com.hzdc.mng.test;

import com.longan.biz.utils.DateTool;

import java.util.Date;

public class DemoTest {
    public static void main(String[] args) {
        Date date = DateTool.beforeDay(new Date(), -30);
        System.out.println(date);

    }

}
