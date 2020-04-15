package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class ItemSales implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer itemId;
    private String itemName;
    private Integer itemFacePrice;
    private Integer itemSalesPrice;
    private Integer status;
    private Long userId;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
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

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getFacePriceDesc() {
	if (itemFacePrice == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
	return result.toString();
    }

    public String getSalesPriceDesc() {
	if (itemSalesPrice == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(itemSalesPrice + "");
	return result.toString();
    }

    public String getStatusDesc() {
	if (status == null) {
	    return "未开通";
	}

	String result = null;
	if (Constants.ItemSales.STATUS_OPEN == status) {
	    result = Constants.ItemSales.STATUS_OPEN_DESC;
	} else if (Constants.ItemSales.STATUS_CLOSE == status) {
	    result = Constants.ItemSales.STATUS_CLOSE_DESC;
	} else if (Constants.ItemSales.STATUS_DEL == status) {
	    result = Constants.ItemSales.STATUS_DEL_DESC;
	}
	return result;
    }

    public String getStatusColor() {
	if (status == null) {
	    return "red";
	}

	String result = null;
	if (Constants.ItemSales.STATUS_OPEN == status) {
	    result = "green";
	} else if (Constants.ItemSales.STATUS_CLOSE == status) {
	    result = "blue";
	} else if (Constants.ItemSales.STATUS_DEL == status) {
	    result = "grey";
	}
	return result;
    }

    public boolean canOpen() {
	if (id == null) {
	    return true;
	}
	return false;
    }

    public boolean canClose() {
	if (id == null) {
	    return false;
	}

	if (Constants.ItemSales.STATUS_OPEN == status) {
	    return true;
	}
	return false;
    }

    public boolean canDel() {
	if (id == null) {
	    return false;
	}

	if (Constants.ItemSales.STATUS_CLOSE == status) {
	    return true;
	}
	return false;
    }

    public boolean canEdit() {
	if (id == null) {
	    return false;
	}
	return true;
    }
}
