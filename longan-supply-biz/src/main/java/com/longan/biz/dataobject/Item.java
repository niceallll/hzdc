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

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(Item.class);

    private Integer id;
    private Date gmtCreate;
    private Date gmtModify;
    private String itemName;
    private Integer itemFacePrice;
    private String itemUnit;
    private Integer itemSize;
    private Integer itemRange;
    private Integer itemSalesPrice;
    private Integer itemSalesPrice2;
    private Integer itemSalesPrice3;
    private Integer itemSalesPrice4;
    private Integer itemCostPrice;
    private Integer itemDummyPrice;
    private Integer bizId;
    private Integer itemCategoryId;
    private Integer itemType;
    private String itemExt1;
    private String itemExt2;
    private Long userId;
    private Integer status;
    private Integer carrierType;
    private String salesArea;
    private List<String> salesAreaList;
    private Integer supplyFilterType;
    private String itemSalesAreaDesc;
    private Boolean associated;
    private Boolean canUp;
    private Boolean canSync;
    private Integer faceType;
    private String numberList;
    private Integer minNumber;
    private Integer maxNumber;
    private Integer exchargeRatio;
    private Integer repeatType;
    private Integer repeatCount;
    private Integer repeatTime;
    private Integer combineType;

    private Integer pddStatus;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
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

    public String getItemUnit() {
	return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
	this.itemUnit = itemUnit;
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

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getItemCategoryId() {
	return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
	this.itemCategoryId = itemCategoryId;
    }

    public Integer getItemType() {
	return itemType;
    }

    public void setItemType(Integer itemType) {
	this.itemType = itemType;
    }

    public String getItemExt1() {
	return itemExt1;
    }

    public void setItemExt1(String itemExt1) {
	this.itemExt1 = itemExt1;
    }

    public String getItemExt2() {
	return itemExt2;
    }

    public void setItemExt2(String itemExt2) {
	this.itemExt2 = itemExt2;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Integer getItemSize() {
	return itemSize;
    }

    public void setItemSize(Integer itemSize) {
	this.itemSize = itemSize;
    }

    public Integer getItemRange() {
	return itemRange;
    }

    public void setItemRange(Integer itemRange) {
	this.itemRange = itemRange;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }

    public String getSalesArea() {
	return salesArea;
    }

    public void setSalesArea(String salesArea) {
	this.salesArea = salesArea;
    }

    public Boolean getCanSync() {
	return canSync;
    }

    public void setCanSync(Boolean canSync) {
	this.canSync = canSync;
    }

    public Integer getFaceType() {
	return faceType;
    }

    public void setFaceType(Integer faceType) {
	this.faceType = faceType;
    }

    public String getNumberList() {
	return numberList;
    }

    public void setNumberList(String numberList) {
	this.numberList = numberList;
    }

    public Integer getMinNumber() {
	return minNumber;
    }

    public void setMinNumber(Integer minNumber) {
	this.minNumber = minNumber;
    }

    public Integer getMaxNumber() {
	return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
	this.maxNumber = maxNumber;
    }

    public Integer getExchargeRatio() {
	return exchargeRatio;
    }

    public void setExchargeRatio(Integer exchargeRatio) {
	this.exchargeRatio = exchargeRatio;
    }

    public Integer getRepeatType() {
	return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
	this.repeatType = repeatType;
    }

    public Integer getRepeatCount() {
	return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
	this.repeatCount = repeatCount;
    }

    public Integer getRepeatTime() {
	return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
	this.repeatTime = repeatTime;
    }

    public Integer getCombineType() {
	return combineType;
    }

    public void setCombineType(Integer combineType) {
	this.combineType = combineType;
    }

    public Integer getPddStatus() {
	return pddStatus;
    }

    public void setPddStatus(Integer pddStatus) {
	this.pddStatus = pddStatus;
    }

    public void setSalesAreaList(List<String> salesAreaList) {
	this.salesAreaList = salesAreaList;
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

    public Integer getSupplyFilterType() {
	return supplyFilterType;
    }

    public void setSupplyFilterType(Integer supplyFilterType) {
	this.supplyFilterType = supplyFilterType;
    }

    public Boolean getAssociated() {
	return associated;
    }

    public void setAssociated(Boolean associated) {
	this.associated = associated;
    }

    public Boolean getCanUp() {
	if (isCombine()) {
	    if (status == null) {
		return false;
	    }

	    return status != Constants.Item.STATUS_UP;
	} else
	    return canUp;
    }

    public void setCanUp(Boolean canUp) {
	this.canUp = canUp;
    }

    public Integer getSalesAreaType() {
	if (isNationwide()) {
	    return Constants.Item.SALE_TYPE_NATIONWIDE;
	}
	return Constants.Item.SALE_TYPE_AREA;
    }

    public String getItemSalesAreaDesc() {
	return itemSalesAreaDesc;
    }

    public void setItemSalesAreaDesc(String itemSalesAreaDesc) {
	this.itemSalesAreaDesc = itemSalesAreaDesc;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (status == Constants.Item.STATUS_DEL) {
	    result = Constants.Item.STATUS_DEL_DESC;
	} else if (status == Constants.Item.STATUS_DOWN) {
	    result = Constants.Item.STATUS_DOWN_DESC;
	} else if (status == Constants.Item.STATUS_INIT) {
	    result = Constants.Item.STATUS_INIT_DESC;
	} else if (status == Constants.Item.STATUS_UP) {
	    result = Constants.Item.STATUS_UP_DESC;
	}
	return result;
    }

    public String getPddStatusDesc() {
	String result = null;
	if (pddStatus == null) {
	    result = "未知";
	} else if (pddStatus == 0) {
	    result = "已下架";
	} else if (pddStatus == 1) {
	    result = "已上架";
	} else if (pddStatus == 2) {
	    result = "下架失败";
	} else if (pddStatus == 3) {
	    result = "上架失败";
	} else {
	    result = "未知";
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

    public boolean canSync() {
	if (canSync == null) {
	    return false;
	}
	return canSync;
    }

    public boolean canUpOperation() {
	if (status == null) {
	    return false;
	}
	return status != Constants.Item.STATUS_UP;
    }

    public boolean canDownOperation() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Item.STATUS_UP;
    }

    public boolean canUpCombine() {
	if (itemCategoryId == null || itemCategoryId == 0) {
	    return false;
	}
	return true;
    }

    public String getPriceDesc(Integer price) {
	if (price == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(price + "");
	return result.toString();
    }

    public String getBizDesc() {
	return Constants.BIZ_MAP.get(bizId);
    }

    public String getItemTypeDesc() {
	String result = null;
	if (itemType == null) {
	    return null;
	}
	if (Constants.Item.TYPE_ITEM_CARD == itemType) {
	    result = Constants.Item.TYPE_ITEM_CARD_DESC;
	} else if (Constants.Item.TYPE_ITEM_CHARGE == itemType) {
	    result = Constants.Item.TYPE_ITEM_CHARGE_DESC;
	} else if (Constants.Item.TYPE_ITEM_COMBINE == itemType) {
	    result = Constants.Item.TYPE_ITEM_COMBINE_DESC;
	}
	return result;
    }

    public boolean isCombine() {
	return Constants.Item.TYPE_ITEM_COMBINE == itemType;
    }

    public boolean isNationwide() {
	return StringUtils.isBlank(salesArea);
    }

    /**
     * 商品是否供货给手机
     * 
     * @return
     */
    public boolean isUidNumber() {
	if (carrierType == null) {
	    return false;
	}
	return carrierType != Constants.Item.CARRIER_TYPE_OTHER;
    }

    public String getItemSizeDesc() {
	if (itemSize == null || itemSize == 0) {
	    return null;
	}
	return Constants.ITEM_SIZE_MAP.get(itemSize + "");
    }

    public String getItemRangeDesc() {
	if (itemRange == null || itemRange == 99) {
	    return null;
	}
	return Constants.ITEM_RANGE_MAP.get(itemRange + "");
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", itemName='" + itemName + '\'' +
                ", itemFacePrice=" + itemFacePrice +
                ", itemUnit='" + itemUnit + '\'' +
                ", itemSize=" + itemSize +
                ", itemRange=" + itemRange +
                ", itemSalesPrice=" + itemSalesPrice +
                ", itemSalesPrice2=" + itemSalesPrice2 +
                ", itemSalesPrice3=" + itemSalesPrice3 +
                ", itemSalesPrice4=" + itemSalesPrice4 +
                ", itemCostPrice=" + itemCostPrice +
                ", itemDummyPrice=" + itemDummyPrice +
                ", bizId=" + bizId +
                ", itemCategoryId=" + itemCategoryId +
                ", itemType=" + itemType +
                ", itemExt1='" + itemExt1 + '\'' +
                ", itemExt2='" + itemExt2 + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", carrierType=" + carrierType +
                ", salesArea='" + salesArea + '\'' +
                ", salesAreaList=" + salesAreaList +
                ", supplyFilterType=" + supplyFilterType +
                ", itemSalesAreaDesc='" + itemSalesAreaDesc + '\'' +
                ", associated=" + associated +
                ", canUp=" + canUp +
                ", canSync=" + canSync +
                ", faceType=" + faceType +
                ", numberList='" + numberList + '\'' +
                ", minNumber=" + minNumber +
                ", maxNumber=" + maxNumber +
                ", exchargeRatio=" + exchargeRatio +
                ", repeatType=" + repeatType +
                ", repeatCount=" + repeatCount +
                ", repeatTime=" + repeatTime +
                ", combineType=" + combineType +
                ", pddStatus=" + pddStatus +
                '}';
    }
}