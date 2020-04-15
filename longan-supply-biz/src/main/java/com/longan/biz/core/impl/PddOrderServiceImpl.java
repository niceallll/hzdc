package com.longan.biz.core.impl;

import com.longan.biz.core.PddOrderService;
import com.longan.biz.dao.BizOrderDAO;
import com.longan.biz.dao.PddOrderDao;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.PddKey;
import com.longan.biz.dataobject.PddOrder;
import com.longan.biz.domain.Result;
import net.sf.json.JSONObject;
import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PddOrderServiceImpl implements PddOrderService {
    @Resource
    private PddOrderDao pddOrderDao;
    @Resource
    private BizOrderDAO bizOrderDAO;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Integer after_sales_status = 1;
    private static final Integer after_sales_type = 1;

    @Override
    public Result<PddOrder> selectOrder(Long uid) throws Exception {
        Result<PddOrder> result = new Result<PddOrder>();
        PddOrder pddOrder = pddOrderDao.selectByPrimaryKey(uid);
        if (pddOrder == null) {
            PddKey pddKey = new PddKey();
            BizOrder bizOrder = bizOrderDAO.selectByPrimaryKey(uid);
            String time = dateFormat.format(bizOrder.getGmtModify());
            //accessToken是从memcached中获取的
            String url = pddKey.sign(after_sales_status, after_sales_type, time, time);

            /*List<PddOrder> pddOrderList = new ArrayList<PddOrder>();
            pddOrderList.add(pddOrders);
            for (PddOrder order : pddOrderList) {
                if (order.getOrderSn().equals(bizOrder.getDownstreamSerialno())) {
                    order.setUid(bizOrder.getId());
                    pddOrderDao.insert(order);
                } else if (!order.getOrderSn().equals(bizOrder.getDownstreamSerialno())) {
                    break;
                }*/
           /* }
            pddOrderDao.insert(pddOrders);
            PddOrder pddOrder1=pddOrderDao.selectByPrimaryKey(uid);
            result.setModule(pddOrder1);
            return result;
        } else if (pddOrder != null) {
            result.setModule(pddOrder);
            return result;
        }*/
            return null;
        }


   /* private static String httpPost(String urls) {
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
*/

    return null;
    }
}
