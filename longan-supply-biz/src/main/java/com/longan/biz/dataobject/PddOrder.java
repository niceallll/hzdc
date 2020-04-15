package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Arrays;

public class PddOrder implements Serializable {

    private Object refund_increment_get_response;
    private Object[] refund_list;
    private Long id;
    private String speed_refund_status;
    private String after_sale_reason;
    private Integer after_sales_status;
    private Integer after_sales_type;
    private String confirm_time;
    private String createdTime;
    private String discount_amount;
    private String good_image;
    private Long goods_id;
    private String goods_name;
    private String goods_number;
    private String goods_price;
    private String order_amount;
    private String outer_goods_id;
    private String order_sn;
    private String outer_id;
    private String refund_amount;
    private String sku_id;
    private String tracking_number;
    private String updated_time;
    private Integer speed_refund_flag;
    private Long uid;

    public Object getRefund_increment_get_response() {
        return refund_increment_get_response;
    }

    public void setRefund_increment_get_response(Object refund_increment_get_response) {
        this.refund_increment_get_response = refund_increment_get_response;
    }

    public Object[] getRefund_list() {
        return refund_list;
    }

    public void setRefund_list(Object[] refund_list) {
        this.refund_list = refund_list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeed_refund_status() {
        return speed_refund_status;
    }

    public void setSpeed_refund_status(String speed_refund_status) {
        this.speed_refund_status = speed_refund_status;
    }

    public String getAfter_sale_reason() {
        return after_sale_reason;
    }

    public void setAfter_sale_reason(String after_sale_reason) {
        this.after_sale_reason = after_sale_reason;
    }

    public Integer getAfter_sales_status() {
        return after_sales_status;
    }

    public void setAfter_sales_status(Integer after_sales_status) {
        this.after_sales_status = after_sales_status;
    }

    public Integer getAfter_sales_type() {
        return after_sales_type;
    }

    public void setAfter_sales_type(Integer after_sales_type) {
        this.after_sales_type = after_sales_type;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getGood_image() {
        return good_image;
    }

    public void setGood_image(String good_image) {
        this.good_image = good_image;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOuter_goods_id() {
        return outer_goods_id;
    }

    public void setOuter_goods_id(String outer_goods_id) {
        this.outer_goods_id = outer_goods_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public Integer getSpeed_refund_flag() {
        return speed_refund_flag;
    }

    public void setSpeed_refund_flag(Integer speed_refund_flag) {
        this.speed_refund_flag = speed_refund_flag;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "PddOrder{" +
                "refund_increment_get_response=" + refund_increment_get_response +
                ", refund_list=" + Arrays.toString(refund_list) +
                ", id=" + id +
                ", speed_refund_status='" + speed_refund_status + '\'' +
                ", after_sale_reason='" + after_sale_reason + '\'' +
                ", after_sales_status=" + after_sales_status +
                ", after_sales_type=" + after_sales_type +
                ", confirm_time='" + confirm_time + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", discount_amount='" + discount_amount + '\'' +
                ", good_image='" + good_image + '\'' +
                ", goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_number='" + goods_number + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", order_amount='" + order_amount + '\'' +
                ", outer_goods_id='" + outer_goods_id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", outer_id='" + outer_id + '\'' +
                ", refund_amount='" + refund_amount + '\'' +
                ", sku_id='" + sku_id + '\'' +
                ", tracking_number='" + tracking_number + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", speed_refund_flag=" + speed_refund_flag +
                ", uid=" + uid +
                '}';
    }
}
