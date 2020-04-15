package com.hzdc.biz.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class date {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //Date d = sdf.parse("2018-1-12");
        String format = sdf.format(date);
        Date parse = sdf.parse("2018-1-12 1:12:12");
        System.out.println(parse);
        System.out.println("我就是中文测试");
        System.out.println(format);


    }
}
