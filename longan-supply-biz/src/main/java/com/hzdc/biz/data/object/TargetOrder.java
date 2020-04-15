package com.hzdc.biz.data.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TargetOrder {
    private Long userId;
    private Long acctId;
    private Date sdate;
    private Integer term;
    private Integer inc = 1;
    private List<Items> itemList = new ArrayList<Items>();

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getAcctId() {
	return acctId;
    }

    public void setAcctId(Long acctId) {
	this.acctId = acctId;
    }

    public Date getSdate() {
	return sdate;
    }

    public void setSdate(Date sdate) {
	this.sdate = sdate;
    }

    public Integer getTerm() {
	return term;
    }

    public void setTerm(Integer term) {
	this.term = term;
    }

    public Integer getInc() {
	return inc;
    }

    public void setInc(Integer inc) {
	this.inc = inc;
    }

    public List<Items> getItemList() {
	return itemList;
    }

    public void setItem10(Integer max) {
	itemList.add(new Items(26, "全国移动10元话费", max, 1l, 1l, 1l, 3l));
    }

    public void setItem20(Integer max) {
	itemList.add(new Items(27, "全国移动20元话费", max, 2l, 2l, 2l, 4l));
    }

    public void setItem30(Integer max) {
	itemList.add(new Items(28, "全国移动30元话费", max, 3l, 3l, 3l, 5l));
    }

    public void setItem50(Integer max) {
	itemList.add(new Items(29, "全国移动50元话费", max, 4l, 4l, 4l, 6l));
    }

    public void setItem100(Integer max) {
	itemList.add(new Items(30, "全国移动100元话费", max, 5l, 5l, 5l, 7l));
    }

    public void setItem200(Integer max) {
	itemList.add(new Items(31, "全国移动200元话费", max, 6l, 6l, 6l, 8l));
    }

    public void setItem300(Integer max) {
	itemList.add(new Items(32, "全国移动300元话费", max, 7l, 7l, 7l, 9l));
    }

    public void setItem500(Integer max) {
	itemList.add(new Items(33, "全国移动500元话费", max, 8l, 8l, 8l, 10l));
    }

    public class Items {
	private Integer itemId;
	private String itemName;
	private Integer max;
	private Integer pos = 0;

	private Long bizOrderId;
	private Long supplyOrderId;
	private Long payOrderId;
	private Long acctLogId;

	public Items(Integer itemId, String itemName, Integer max, Long bizOrderId, Long supplyOrderId, Long payOrderId,
		Long acctLogId) {
	    this.itemId = itemId;
	    this.itemName = itemName;
	    this.max = max;
	    this.bizOrderId = bizOrderId;
	    this.supplyOrderId = supplyOrderId;
	    this.payOrderId = payOrderId;
	    this.acctLogId = acctLogId;
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

	public Integer getMax() {
	    return max;
	}

	public void setMax(Integer max) {
	    this.max = max;
	}

	public Integer getPos() {
	    return pos;
	}

	public void setPos(Integer pos) {
	    this.pos = pos;
	}

	public Long getBizOrderId() {
	    return bizOrderId;
	}

	public void setBizOrderId(Long bizOrderId) {
	    this.bizOrderId = bizOrderId;
	}

	public Long getSupplyOrderId() {
	    return supplyOrderId;
	}

	public void setSupplyOrderId(Long supplyOrderId) {
	    this.supplyOrderId = supplyOrderId;
	}

	public Long getPayOrderId() {
	    return payOrderId;
	}

	public void setPayOrderId(Long payOrderId) {
	    this.payOrderId = payOrderId;
	}

	public Long getAcctLogId() {
	    return acctLogId;
	}

	public void setAcctLogId(Long acctLogId) {
	    this.acctLogId = acctLogId;
	}
    }
}
