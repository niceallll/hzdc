package com.longan.biz.dataobject;


import com.longan.biz.utils.Md5Encrypt;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PddKey {
    private static final String url = "https://gw-api.pinduoduo.com/api/router?";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String type = "pdd.refund.list.increment.get";
    private static final String clientId = "1bc3c6b0116f4aa286fe34d2b9849646";
    private static final String clientSecret = "9c97861554dac57bdef79c4508be468d896b69d7";
    private static final String accessToken = "ef1ffd328d984998bc1a8e782f217ac5956d4517";
    private String gmtNotify;
    private String endNotify;

    public String getEndNotify() {
        return endNotify;
    }

    public void setEndNotify(String endNotify) {
        Long epoch = null;
        try {
            epoch = dateFormat.parse(endNotify).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(epoch);
        String timesTamp = String.valueOf(epoch);
        this.endNotify = timesTamp;
    }

    public String getGmtNotify() {
        return gmtNotify;
    }


    public void setGmtNotify(String gmtNotify) {
        Long epoch = null;
        try {
            epoch = dateFormat.parse(gmtNotify).getTime() / 1000 - 600;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(epoch);
        String timesTamp = String.valueOf(epoch);
        this.gmtNotify = timesTamp;
    }

    public String sign(Integer afterSalesStatus, Integer afterSalesType, String gmtNotify, String endNotify) {
        PddKey pddKey = new PddKey();
        pddKey.setGmtNotify(gmtNotify);
        pddKey.setEndNotify(endNotify);
        String rs = String.valueOf(new StringBuilder().append(String.valueOf("client_id")).append(String.valueOf(clientId)).append(",")
                .append(String.valueOf("access_token")).append(String.valueOf(accessToken)).append(",")
                .append(String.valueOf("after_sales_status")).append(String.valueOf(afterSalesStatus)).append(",")
                .append(String.valueOf("after_sales_type")).append(String.valueOf(afterSalesType)).append(",")
                .append(String.valueOf("end_updated_at")).append(String.valueOf(pddKey.getEndNotify())).append(",")
                .append(String.valueOf("timestamp")).append(String.valueOf(pddKey.getGmtNotify())).append(",")
                .append(String.valueOf("type")).append(String.valueOf(type)).append(",")
                .append(String.valueOf("start_updated_at")).append(String.valueOf(pddKey.getGmtNotify())).append(","));
        System.out.println(rs);
        String[] arr = rs.split("\\,");
        String returnString = sortLetter(arr);
        System.out.println(returnString);
        String rl = String.valueOf(new StringBuilder().append(String.valueOf(clientSecret)).append(String.valueOf(returnString)).append(String.valueOf(clientSecret)));
        String s = Md5Encrypt.md5(rl);
        rl = s.toUpperCase();
        rs = String.valueOf(new StringBuilder().append(String.valueOf(url)).append(String.valueOf("type=")).append(String.valueOf(type))
                .append(String.valueOf("&sign=")).append(rl).append("&client_id=")
                .append(String.valueOf(clientId)).append(String.valueOf("&timestamp="))
                .append(String.valueOf(pddKey.getGmtNotify()))
                /*.append(String.valueOf("&order_status="))
                .append(String.valueOf(downstreamSerialno))*/
                .append(String.valueOf("&after_sales_status=")).append(String.valueOf(afterSalesStatus))
                .append(String.valueOf("&after_sales_type=")).append(String.valueOf(afterSalesType))
                .append(String.valueOf("&end_updated_at=")).append(String.valueOf(pddKey.getEndNotify()))
                .append(String.valueOf("&start_updated_at=")).append(String.valueOf(pddKey.getGmtNotify()))
                .append("&access_token=").append(accessToken));
        return rs;
    }

    public String sortLetter(String[] input) {
        Arrays.sort(input, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            sb.append(input[i]);
        }
        return sb.toString().replaceAll("=", "");
    }

    public static void main(String[] args) {
        PddKey pddKey = new PddKey();
        String ul = pddKey.sign(1, 1, "2020-03-25 10:50:00", "2020-03-25 11:00:00");
        String rl = httpPost(ul);
        JSONObject jsonObject = JSONObject.fromObject(rl);
        PddOrder pddOrders = (PddOrder) JSONObject.toBean(jsonObject, PddOrder.class);
        List<PddOrder> orders = new ArrayList<PddOrder>();
        orders.add(pddOrders);
        for (PddOrder order : orders) {
            System.out.println(order);
        }

    }

    private static String httpPost(String urls) {
        HttpURLConnection connection = null;
        OutputStream dataout = null;
        BufferedReader reader = null;
        String line = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
            connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
            connection.setRequestMethod("POST");// 设置请求方式为post,默认GET请求
            connection.setUseCaches(false);// post请求不能使用缓存设为false
            connection.setConnectTimeout(3000);// 连接主机的超时时间
            connection.setReadTimeout(3000);// 从主机读取数据的超时时间
            connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
            connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("Authorization", "Bearer 66cb225f1c3ff0ddfdae31rae2b57488aadfb8b5e7");
            connection.connect();// 建立TCP连接,getOutputStream会隐含的进行connect,所以此处可以不要

            dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
            dataout.flush();
            dataout.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));// 发送http请求

                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));//
                }
                connection.disconnect();  //关闭http连接
                System.out.println(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return result.toString();
    }

}
