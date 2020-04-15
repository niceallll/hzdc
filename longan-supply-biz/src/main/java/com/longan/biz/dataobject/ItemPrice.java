package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class ItemPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(ItemPrice.class);

    private Long id; // itemSupplyId
    private Integer bizId;
    private Date gmtModify;
    private Integer itemId;
    private String itemName;
    private String supplyTraderName;
    private Long supplyTraderId;
    private String salesArea;
    private List<String> salesAreaList;
    private Integer itemFacePrice;
    private Integer itemSalesPrice;
    private Integer itemSalesPrice2;
    private Integer itemSalesPrice3;
    private Integer itemSalesPrice4;
    private Integer itemCostPrice;
    private Integer itemDummyPrice;
    private String itemSalesPriceDiscount;
    private String itemSalesPrice2Discount;
    private String itemSalesPrice3Discount;
    private String itemSalesPrice4Discount;
    private String itemCostPriceDiscount;
    private String itemDummyPriceDiscount;
    private Integer priority;
    private Integer status;
    private String itemSalesAreaDesc;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Date getGmtModify() {
	return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
	this.gmtModify = gmtModify;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public String getSupplyTraderName() {
	return supplyTraderName;
    }

    public void setSupplyTraderName(String supplyTraderName) {
	this.supplyTraderName = supplyTraderName;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getSalesArea() {
	return salesArea;
    }

    public void setSalesArea(String salesArea) {
	this.salesArea = salesArea;
    }

    public void setSalesAreaList(List<String> salesAreaList) {
	this.salesAreaList = salesAreaList;
    }

    public String getItemCostPriceDiscount() {
	if (itemCostPriceDiscount == null) {
	    setItemCostPriceDiscount(getDiscountDesc(itemCostPrice));
	}
	return itemCostPriceDiscount;
    }

    public void setItemCostPriceDiscount(String itemCostPriceDiscount) {
	this.itemCostPriceDiscount = itemCostPriceDiscount;
    }

    public String getItemDummyPriceDiscount() {
	if (itemDummyPriceDiscount == null) {
	    setItemDummyPriceDiscount(getDiscountDesc(itemDummyPrice));
	}
	return itemDummyPriceDiscount;
    }

    public void setItemDummyPriceDiscount(String itemDummyPriceDiscount) {
	this.itemDummyPriceDiscount = itemDummyPriceDiscount;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public Integer getItemSalesPrice() {
	return itemSalesPrice;
    }

    public void setItemSalesPrice(Integer itemSalesPrice) {
	this.itemSalesPrice = itemSalesPrice;
    }

    public Integer getItemSalesPrice2() {
	return itemSalesPrice2;
    }

    public void setItemSalesPrice2(Integer itemSalesPrice2) {
	this.itemSalesPrice2 = itemSalesPrice2;
    }

    public Integer getItemSalesPrice3() {
	return itemSalesPrice3;
    }

    public void setItemSalesPrice3(Integer itemSalesPrice3) {
	this.itemSalesPrice3 = itemSalesPrice3;
    }

    public Integer getItemSalesPrice4() {
	return itemSalesPrice4;
    }

    public void setItemSalesPrice4(Integer itemSalesPrice4) {
	this.itemSalesPrice4 = itemSalesPrice4;
    }

    public Integer getItemCostPrice() {
	return itemCostPrice;
    }

    public void setItemCostPrice(Integer itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
    }

    public Integer getItemDummyPrice() {
	return itemDummyPrice;
    }

    public void setItemDummyPrice(Integer itemDummyPrice) {
	this.itemDummyPrice = itemDummyPrice;
    }

    public String getItemSalesPriceDiscount() {
	if (itemSalesPriceDiscount == null) {
	    setItemSalesPriceDiscount(getDiscountDesc(itemSalesPrice));
	}
	return itemSalesPriceDiscount;
    }

    public void setItemSalesPriceDiscount(String itemSalesPriceDiscount) {
	this.itemSalesPriceDiscount = itemSalesPriceDiscount;
    }

    public String getItemSalesPrice2Discount() {
	if (itemSalesPrice2Discount == null) {
	    setItemSalesPrice2Discount(getDiscountDesc(itemSalesPrice2));
	}
	return itemSalesPrice2Discount;
    }

    public void setItemSalesPrice2Discount(String itemSalesPrice2Discount) {
	this.itemSalesPrice2Discount = itemSalesPrice2Discount;
    }

    public String getItemSalesPrice3Discount() {
	if (itemSalesPrice3Discount == null) {
	    setItemSalesPrice3Discount(getDiscountDesc(itemSalesPrice3));
	}
	return itemSalesPrice3Discount;
    }

    public void setItemSalesPrice3Discount(String itemSalesPrice3Discount) {
	this.itemSalesPrice3Discount = itemSalesPrice3Discount;
    }

    public String getItemSalesPrice4Discount() {
	if (itemSalesPrice4Discount == null) {
	    setItemSalesPrice4Discount(getDiscountDesc(itemSalesPrice4));
	}
	return itemSalesPrice4Discount;
    }

    public void setItemSalesPrice4Discount(String itemSalesPrice4Discount) {
	this.itemSalesPrice4Discount = itemSalesPrice4Discount;
    }

    public Integer getPriority() {
	return priority;
    }

    public void setPriority(Integer priority) {
	this.priority = priority;
    }

    public String getItemSalesAreaDesc() {
	return itemSalesAreaDesc;
    }

    public void setItemSalesAreaDesc(String itemSalesAreaDesc) {
	this.itemSalesAreaDesc = itemSalesAreaDesc;
    }

    public List<String> getSalesAreaList() {
	if (salesAreaList == null) {
	    setSalesAreaList();
	}
	return salesAreaList;
    }

    public void setSalesAreaList() {
	salesAreaList = new ArrayList<String>();
	if (isNationwide()) {
	    return;
	}
	try {
	    String[] strs = salesArea.split(Constants.Item.SALES_AREA_SPLIT);
	    for (String str : strs) {
		salesAreaList.add(str);
	    }
	} catch (Exception e) {
	    logger.error("setSalesAreaList error", e);
	}
    }

    public boolean isNationwide() {
	return StringUtils.isBlank(salesArea);
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (status == Constants.ItemSupply.STATUS_DEL) {
	    result = Constants.ItemSupply.STATUS_DEL_DESC;
	} else if (status == Constants.ItemSupply.STATUS_DOWN) {
	    result = Constants.ItemSupply.STATUS_DOWN_DESC;
	} else if (status == Constants.ItemSupply.STATUS_INIT) {
	    result = Constants.Item.STATUS_INIT_DESC;
	} else if (status == Constants.ItemSupply.STATUS_UP) {
	    result = Constants.ItemSupply.STATUS_UP_DESC;
	}
	return result;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Item.STATUS_DOWN;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Item.STATUS_UP;
    }

    public String getPriceDesc(Integer price) {
	if (price == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(price + "");
	return result.toString();
    }

    public String getDiscountDesc(Integer price) {
	if (price == null || itemFacePrice == null) {
	    return null;
	}
	if (price == 0) {
	    return "0";
	}
	Double result = BigDecimalUtils.doubleDiveidPercent(price + "", itemFacePrice + "");
	return result.toString();
    }
}
