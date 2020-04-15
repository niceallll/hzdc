package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizOrderExample {
    protected String orderByClause;
    protected List oredCriteria;

    public BizOrderExample() {
	oredCriteria = new ArrayList();
    }

    protected BizOrderExample(BizOrderExample example) {
	this.orderByClause = example.orderByClause;
	this.oredCriteria = example.oredCriteria;
    }

    public void setOrderByClause(String orderByClause) {
	this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
	return orderByClause;
    }

    public List getOredCriteria() {
	return oredCriteria;
    }

    public void or(Criteria criteria) {
	oredCriteria.add(criteria);
    }

    public Criteria createCriteria() {
	Criteria criteria = createCriteriaInternal();
	if (oredCriteria.size() == 0) {
	    oredCriteria.add(criteria);
	}
	return criteria;
    }

    protected Criteria createCriteriaInternal() {
	Criteria criteria = new Criteria();
	return criteria;
    }

    public void clear() {
	oredCriteria.clear();
    }

    public static class Criteria {
	protected List criteriaWithoutValue;
	protected List criteriaWithSingleValue;
	protected List criteriaWithListValue;
	protected List criteriaWithBetweenValue;

	protected Criteria() {
	    super();
	    criteriaWithoutValue = new ArrayList();
	    criteriaWithSingleValue = new ArrayList();
	    criteriaWithListValue = new ArrayList();
	    criteriaWithBetweenValue = new ArrayList();
	}

	public boolean isValid() {
	    return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0 || criteriaWithListValue.size() > 0
		    || criteriaWithBetweenValue.size() > 0;
	}

	public List getCriteriaWithoutValue() {
	    return criteriaWithoutValue;
	}

	public List getCriteriaWithSingleValue() {
	    return criteriaWithSingleValue;
	}

	public List getCriteriaWithListValue() {
	    return criteriaWithListValue;
	}

	public List getCriteriaWithBetweenValue() {
	    return criteriaWithBetweenValue;
	}

	protected void addCriterion(String condition) {
	    if (condition == null) {
		throw new RuntimeException("Value for condition cannot be null");
	    }
	    criteriaWithoutValue.add(condition);
	}

	protected void addCriterion(String condition, Object value, String property) {
	    if (value == null) {
		throw new RuntimeException("Value for " + property + " cannot be null");
	    }
	    Map map = new HashMap();
	    map.put("condition", condition);
	    map.put("value", value);
	    criteriaWithSingleValue.add(map);
	}

	protected void addCriterion(String condition, List values, String property) {
	    if (values == null || values.size() == 0) {
		throw new RuntimeException("Value list for " + property + " cannot be null or empty");
	    }
	    Map map = new HashMap();
	    map.put("condition", condition);
	    map.put("values", values);
	    criteriaWithListValue.add(map);
	}

	protected void addCriterion(String condition, Object value1, Object value2, String property) {
	    if (value1 == null || value2 == null) {
		throw new RuntimeException("Between values for " + property + " cannot be null");
	    }
	    List list = new ArrayList();
	    list.add(value1);
	    list.add(value2);
	    Map map = new HashMap();
	    map.put("condition", condition);
	    map.put("values", list);
	    criteriaWithBetweenValue.add(map);
	}

	public Criteria andIdIsNull() {
	    addCriterion("id is null");
	    return this;
	}

	public Criteria andIdIsNotNull() {
	    addCriterion("id is not null");
	    return this;
	}

	public Criteria andIdEqualTo(Long value) {
	    addCriterion("id =", value, "id");
	    return this;
	}

	public Criteria andIdNotEqualTo(Long value) {
	    addCriterion("id <>", value, "id");
	    return this;
	}

	public Criteria andIdGreaterThan(Long value) {
	    addCriterion("id >", value, "id");
	    return this;
	}

	public Criteria andIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("id >=", value, "id");
	    return this;
	}

	public Criteria andIdLessThan(Long value) {
	    addCriterion("id <", value, "id");
	    return this;
	}

	public Criteria andIdLessThanOrEqualTo(Long value) {
	    addCriterion("id <=", value, "id");
	    return this;
	}

	public Criteria andIdIn(List values) {
	    addCriterion("id in", values, "id");
	    return this;
	}

	public Criteria andIdNotIn(List values) {
	    addCriterion("id not in", values, "id");
	    return this;
	}

	public Criteria andIdBetween(Long value1, Long value2) {
	    addCriterion("id between", value1, value2, "id");
	    return this;
	}

	public Criteria andIdNotBetween(Long value1, Long value2) {
	    addCriterion("id not between", value1, value2, "id");
	    return this;
	}

	public Criteria andGmtCreateIsNull() {
	    addCriterion("gmt_create is null");
	    return this;
	}

	public Criteria andGmtCreateIsNotNull() {
	    addCriterion("gmt_create is not null");
	    return this;
	}

	public Criteria andGmtCreateEqualTo(Date value) {
	    addCriterion("gmt_create =", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotEqualTo(Date value) {
	    addCriterion("gmt_create <>", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateGreaterThan(Date value) {
	    addCriterion("gmt_create >", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
	    addCriterion("gmt_create >=", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateLessThan(Date value) {
	    addCriterion("gmt_create <", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
	    addCriterion("gmt_create <=", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateIn(List values) {
	    addCriterion("gmt_create in", values, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotIn(List values) {
	    addCriterion("gmt_create not in", values, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateBetween(Date value1, Date value2) {
	    addCriterion("gmt_create between", value1, value2, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
	    addCriterion("gmt_create not between", value1, value2, "gmtCreate");
	    return this;
	}

	public Criteria andGmtModifyIsNull() {
	    addCriterion("gmt_modify is null");
	    return this;
	}

	public Criteria andGmtModifyIsNotNull() {
	    addCriterion("gmt_modify is not null");
	    return this;
	}

	public Criteria andGmtModifyEqualTo(Date value) {
	    addCriterion("gmt_modify =", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotEqualTo(Date value) {
	    addCriterion("gmt_modify <>", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyGreaterThan(Date value) {
	    addCriterion("gmt_modify >", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyGreaterThanOrEqualTo(Date value) {
	    addCriterion("gmt_modify >=", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyLessThan(Date value) {
	    addCriterion("gmt_modify <", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyLessThanOrEqualTo(Date value) {
	    addCriterion("gmt_modify <=", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyIn(List values) {
	    addCriterion("gmt_modify in", values, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotIn(List values) {
	    addCriterion("gmt_modify not in", values, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyBetween(Date value1, Date value2) {
	    addCriterion("gmt_modify between", value1, value2, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotBetween(Date value1, Date value2) {
	    addCriterion("gmt_modify not between", value1, value2, "gmtModify");
	    return this;
	}

	public Criteria andGmtNotifyIsNull() {
	    addCriterion("gmt_notify is null");
	    return this;
	}

	public Criteria andGmtNotifyIsNotNull() {
	    addCriterion("gmt_notify is not null");
	    return this;
	}

	public Criteria andGmtNotifyEqualTo(Date value) {
	    addCriterion("gmt_notify =", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyNotEqualTo(Date value) {
	    addCriterion("gmt_notify <>", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyGreaterThan(Date value) {
	    addCriterion("gmt_notify >", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyGreaterThanOrEqualTo(Date value) {
	    addCriterion("gmt_notify >=", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyLessThan(Date value) {
	    addCriterion("gmt_notify <", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyLessThanOrEqualTo(Date value) {
	    addCriterion("gmt_notify <=", value, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyIn(List values) {
	    addCriterion("gmt_notify in", values, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyNotIn(List values) {
	    addCriterion("gmt_notify not in", values, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyBetween(Date value1, Date value2) {
	    addCriterion("gmt_notify between", value1, value2, "gmtNotify");
	    return this;
	}

	public Criteria andGmtNotifyNotBetween(Date value1, Date value2) {
	    addCriterion("gmt_notify not between", value1, value2, "gmtNotify");
	    return this;
	}

	public Criteria andStatusIsNull() {
	    addCriterion("status is null");
	    return this;
	}

	public Criteria andStatusIsNotNull() {
	    addCriterion("status is not null");
	    return this;
	}

	public Criteria andStatusEqualTo(Integer value) {
	    addCriterion("status =", value, "status");
	    return this;
	}

	public Criteria andStatusNotEqualTo(Integer value) {
	    addCriterion("status <>", value, "status");
	    return this;
	}

	public Criteria andStatusGreaterThan(Integer value) {
	    addCriterion("status >", value, "status");
	    return this;
	}

	public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
	    addCriterion("status >=", value, "status");
	    return this;
	}

	public Criteria andStatusLessThan(Integer value) {
	    addCriterion("status <", value, "status");
	    return this;
	}

	public Criteria andStatusLessThanOrEqualTo(Integer value) {
	    addCriterion("status <=", value, "status");
	    return this;
	}

	public Criteria andStatusIn(List values) {
	    addCriterion("status in", values, "status");
	    return this;
	}

	public Criteria andStatusNotIn(List values) {
	    addCriterion("status not in", values, "status");
	    return this;
	}

	public Criteria andStatusBetween(Integer value1, Integer value2) {
	    addCriterion("status between", value1, value2, "status");
	    return this;
	}

	public Criteria andStatusNotBetween(Integer value1, Integer value2) {
	    addCriterion("status not between", value1, value2, "status");
	    return this;
	}

	public Criteria andNotifyStatusIsNull() {
	    addCriterion("notify_status is null");
	    return this;
	}

	public Criteria andNotifyStatusIsNotNull() {
	    addCriterion("notify_status is not null");
	    return this;
	}

	public Criteria andNotifyStatusEqualTo(Integer value) {
	    addCriterion("notify_status =", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusNotEqualTo(Integer value) {
	    addCriterion("notify_status <>", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusGreaterThan(Integer value) {
	    addCriterion("notify_status >", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusGreaterThanOrEqualTo(Integer value) {
	    addCriterion("notify_status >=", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusLessThan(Integer value) {
	    addCriterion("notify_status <", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusLessThanOrEqualTo(Integer value) {
	    addCriterion("notify_status <=", value, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusIn(List values) {
	    addCriterion("notify_status in", values, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusNotIn(List values) {
	    addCriterion("notify_status not in", values, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusBetween(Integer value1, Integer value2) {
	    addCriterion("notify_status between", value1, value2, "notifyStatus");
	    return this;
	}

	public Criteria andNotifyStatusNotBetween(Integer value1, Integer value2) {
	    addCriterion("notify_status not between", value1, value2, "notifyStatus");
	    return this;
	}

	public Criteria andPayOrderIdIsNull() {
	    addCriterion("pay_order_id is null");
	    return this;
	}

	public Criteria andPayOrderIdIsNotNull() {
	    addCriterion("pay_order_id is not null");
	    return this;
	}

	public Criteria andPayOrderIdEqualTo(Long value) {
	    addCriterion("pay_order_id =", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdNotEqualTo(Long value) {
	    addCriterion("pay_order_id <>", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdGreaterThan(Long value) {
	    addCriterion("pay_order_id >", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("pay_order_id >=", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdLessThan(Long value) {
	    addCriterion("pay_order_id <", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdLessThanOrEqualTo(Long value) {
	    addCriterion("pay_order_id <=", value, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdIn(List values) {
	    addCriterion("pay_order_id in", values, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdNotIn(List values) {
	    addCriterion("pay_order_id not in", values, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdBetween(Long value1, Long value2) {
	    addCriterion("pay_order_id between", value1, value2, "payOrderId");
	    return this;
	}

	public Criteria andPayOrderIdNotBetween(Long value1, Long value2) {
	    addCriterion("pay_order_id not between", value1, value2, "payOrderId");
	    return this;
	}

	public Criteria andUserIdIsNull() {
	    addCriterion("user_id is null");
	    return this;
	}

	public Criteria andUserIdIsNotNull() {
	    addCriterion("user_id is not null");
	    return this;
	}

	public Criteria andUserIdEqualTo(Long value) {
	    addCriterion("user_id =", value, "userId");
	    return this;
	}

	public Criteria andUserIdNotEqualTo(Long value) {
	    addCriterion("user_id <>", value, "userId");
	    return this;
	}

	public Criteria andUserIdGreaterThan(Long value) {
	    addCriterion("user_id >", value, "userId");
	    return this;
	}

	public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("user_id >=", value, "userId");
	    return this;
	}

	public Criteria andUserIdLessThan(Long value) {
	    addCriterion("user_id <", value, "userId");
	    return this;
	}

	public Criteria andUserIdLessThanOrEqualTo(Long value) {
	    addCriterion("user_id <=", value, "userId");
	    return this;
	}

	public Criteria andUserIdIn(List values) {
	    addCriterion("user_id in", values, "userId");
	    return this;
	}

	public Criteria andUserIdNotIn(List values) {
	    addCriterion("user_id not in", values, "userId");
	    return this;
	}

	public Criteria andUserIdBetween(Long value1, Long value2) {
	    addCriterion("user_id between", value1, value2, "userId");
	    return this;
	}

	public Criteria andUserIdNotBetween(Long value1, Long value2) {
	    addCriterion("user_id not between", value1, value2, "userId");
	    return this;
	}

	public Criteria andAmountIsNull() {
	    addCriterion("amount is null");
	    return this;
	}

	public Criteria andAmountIsNotNull() {
	    addCriterion("amount is not null");
	    return this;
	}

	public Criteria andAmountEqualTo(Long value) {
	    addCriterion("amount =", value, "amount");
	    return this;
	}

	public Criteria andAmountNotEqualTo(Long value) {
	    addCriterion("amount <>", value, "amount");
	    return this;
	}

	public Criteria andAmountGreaterThan(Long value) {
	    addCriterion("amount >", value, "amount");
	    return this;
	}

	public Criteria andAmountGreaterThanOrEqualTo(Long value) {
	    addCriterion("amount >=", value, "amount");
	    return this;
	}

	public Criteria andAmountLessThan(Long value) {
	    addCriterion("amount <", value, "amount");
	    return this;
	}

	public Criteria andAmountLessThanOrEqualTo(Long value) {
	    addCriterion("amount <=", value, "amount");
	    return this;
	}

	public Criteria andAmountIn(List values) {
	    addCriterion("amount in", values, "amount");
	    return this;
	}

	public Criteria andAmountNotIn(List values) {
	    addCriterion("amount not in", values, "amount");
	    return this;
	}

	public Criteria andAmountBetween(Long value1, Long value2) {
	    addCriterion("amount between", value1, value2, "amount");
	    return this;
	}

	public Criteria andAmountNotBetween(Long value1, Long value2) {
	    addCriterion("amount not between", value1, value2, "amount");
	    return this;
	}

	public Criteria andAmountDummyIsNull() {
	    addCriterion("amount_dummy is null");
	    return this;
	}

	public Criteria andAmountDummyIsNotNull() {
	    addCriterion("amount_dummy is not null");
	    return this;
	}

	public Criteria andAmountDummyEqualTo(Long value) {
	    addCriterion("amount_dummy =", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyNotEqualTo(Long value) {
	    addCriterion("amount_dummy <>", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyGreaterThan(Long value) {
	    addCriterion("amount_dummy >", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyGreaterThanOrEqualTo(Long value) {
	    addCriterion("amount_dummy >=", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyLessThan(Long value) {
	    addCriterion("amount_dummy <", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyLessThanOrEqualTo(Long value) {
	    addCriterion("amount_dummy <=", value, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyIn(List values) {
	    addCriterion("amount_dummy in", values, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyNotIn(List values) {
	    addCriterion("amount_dummy not in", values, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyBetween(Long value1, Long value2) {
	    addCriterion("amount_dummy between", value1, value2, "amountDummy");
	    return this;
	}

	public Criteria andAmountDummyNotBetween(Long value1, Long value2) {
	    addCriterion("amount_dummy not between", value1, value2, "amountDummy");
	    return this;
	}

	public Criteria andAmtIsNull() {
	    addCriterion("amt is null");
	    return this;
	}

	public Criteria andAmtIsNotNull() {
	    addCriterion("amt is not null");
	    return this;
	}

	public Criteria andAmtEqualTo(Integer value) {
	    addCriterion("amt =", value, "amt");
	    return this;
	}

	public Criteria andAmtNotEqualTo(Integer value) {
	    addCriterion("amt <>", value, "amt");
	    return this;
	}

	public Criteria andAmtGreaterThan(Integer value) {
	    addCriterion("amt >", value, "amt");
	    return this;
	}

	public Criteria andAmtGreaterThanOrEqualTo(Integer value) {
	    addCriterion("amt >=", value, "amt");
	    return this;
	}

	public Criteria andAmtLessThan(Integer value) {
	    addCriterion("amt <", value, "amt");
	    return this;
	}

	public Criteria andAmtLessThanOrEqualTo(Integer value) {
	    addCriterion("amt <=", value, "amt");
	    return this;
	}

	public Criteria andAmtIn(List values) {
	    addCriterion("amt in", values, "amt");
	    return this;
	}

	public Criteria andAmtNotIn(List values) {
	    addCriterion("amt not in", values, "amt");
	    return this;
	}

	public Criteria andAmtBetween(Integer value1, Integer value2) {
	    addCriterion("amt between", value1, value2, "amt");
	    return this;
	}

	public Criteria andAmtNotBetween(Integer value1, Integer value2) {
	    addCriterion("amt not between", value1, value2, "amt");
	    return this;
	}

	public Criteria andBizIdIsNull() {
	    addCriterion("biz_id is null");
	    return this;
	}

	public Criteria andBizIdIsNotNull() {
	    addCriterion("biz_id is not null");
	    return this;
	}

	public Criteria andBizIdEqualTo(Integer value) {
	    addCriterion("biz_id =", value, "bizId");
	    return this;
	}

	public Criteria andBizIdNotEqualTo(Integer value) {
	    addCriterion("biz_id <>", value, "bizId");
	    return this;
	}

	public Criteria andBizIdGreaterThan(Integer value) {
	    addCriterion("biz_id >", value, "bizId");
	    return this;
	}

	public Criteria andBizIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("biz_id >=", value, "bizId");
	    return this;
	}

	public Criteria andBizIdLessThan(Integer value) {
	    addCriterion("biz_id <", value, "bizId");
	    return this;
	}

	public Criteria andBizIdLessThanOrEqualTo(Integer value) {
	    addCriterion("biz_id <=", value, "bizId");
	    return this;
	}

	public Criteria andBizIdIn(List values) {
	    addCriterion("biz_id in", values, "bizId");
	    return this;
	}

	public Criteria andBizIdNotIn(List values) {
	    addCriterion("biz_id not in", values, "bizId");
	    return this;
	}

	public Criteria andBizIdBetween(Integer value1, Integer value2) {
	    addCriterion("biz_id between", value1, value2, "bizId");
	    return this;
	}

	public Criteria andBizIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("biz_id not between", value1, value2, "bizId");
	    return this;
	}

	public Criteria andItemIdIsNull() {
	    addCriterion("item_id is null");
	    return this;
	}

	public Criteria andItemIdIsNotNull() {
	    addCriterion("item_id is not null");
	    return this;
	}

	public Criteria andItemIdEqualTo(Integer value) {
	    addCriterion("item_id =", value, "itemId");
	    return this;
	}

	public Criteria andItemIdNotEqualTo(Integer value) {
	    addCriterion("item_id <>", value, "itemId");
	    return this;
	}

	public Criteria andItemIdGreaterThan(Integer value) {
	    addCriterion("item_id >", value, "itemId");
	    return this;
	}

	public Criteria andItemIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_id >=", value, "itemId");
	    return this;
	}

	public Criteria andItemIdLessThan(Integer value) {
	    addCriterion("item_id <", value, "itemId");
	    return this;
	}

	public Criteria andItemIdLessThanOrEqualTo(Integer value) {
	    addCriterion("item_id <=", value, "itemId");
	    return this;
	}

	public Criteria andItemIdIn(List values) {
	    addCriterion("item_id in", values, "itemId");
	    return this;
	}

	public Criteria andItemIdNotIn(List values) {
	    addCriterion("item_id not in", values, "itemId");
	    return this;
	}

	public Criteria andItemIdBetween(Integer value1, Integer value2) {
	    addCriterion("item_id between", value1, value2, "itemId");
	    return this;
	}

	public Criteria andItemIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_id not between", value1, value2, "itemId");
	    return this;
	}

	public Criteria andItemNameIsNull() {
	    addCriterion("item_name is null");
	    return this;
	}

	public Criteria andItemNameIsNotNull() {
	    addCriterion("item_name is not null");
	    return this;
	}

	public Criteria andItemNameEqualTo(String value) {
	    addCriterion("item_name =", value, "itemName");
	    return this;
	}

	public Criteria andItemNameNotEqualTo(String value) {
	    addCriterion("item_name <>", value, "itemName");
	    return this;
	}

	public Criteria andItemNameGreaterThan(String value) {
	    addCriterion("item_name >", value, "itemName");
	    return this;
	}

	public Criteria andItemNameGreaterThanOrEqualTo(String value) {
	    addCriterion("item_name >=", value, "itemName");
	    return this;
	}

	public Criteria andItemNameLessThan(String value) {
	    addCriterion("item_name <", value, "itemName");
	    return this;
	}

	public Criteria andItemNameLessThanOrEqualTo(String value) {
	    addCriterion("item_name <=", value, "itemName");
	    return this;
	}

	public Criteria andItemNameLike(String value) {
	    addCriterion("item_name like", value, "itemName");
	    return this;
	}

	public Criteria andItemNameNotLike(String value) {
	    addCriterion("item_name not like", value, "itemName");
	    return this;
	}

	public Criteria andItemNameIn(List values) {
	    addCriterion("item_name in", values, "itemName");
	    return this;
	}

	public Criteria andItemNameNotIn(List values) {
	    addCriterion("item_name not in", values, "itemName");
	    return this;
	}

	public Criteria andItemNameBetween(String value1, String value2) {
	    addCriterion("item_name between", value1, value2, "itemName");
	    return this;
	}

	public Criteria andItemNameNotBetween(String value1, String value2) {
	    addCriterion("item_name not between", value1, value2, "itemName");
	    return this;
	}

	public Criteria andItemPriceIsNull() {
	    addCriterion("item_price is null");
	    return this;
	}

	public Criteria andItemPriceIsNotNull() {
	    addCriterion("item_price is not null");
	    return this;
	}

	public Criteria andItemPriceEqualTo(Integer value) {
	    addCriterion("item_price =", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceNotEqualTo(Integer value) {
	    addCriterion("item_price <>", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceGreaterThan(Integer value) {
	    addCriterion("item_price >", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_price >=", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceLessThan(Integer value) {
	    addCriterion("item_price <", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceLessThanOrEqualTo(Integer value) {
	    addCriterion("item_price <=", value, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceIn(List values) {
	    addCriterion("item_price in", values, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceNotIn(List values) {
	    addCriterion("item_price not in", values, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceBetween(Integer value1, Integer value2) {
	    addCriterion("item_price between", value1, value2, "itemPrice");
	    return this;
	}

	public Criteria andItemPriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_price not between", value1, value2, "itemPrice");
	    return this;
	}

	public Criteria andItemFacePriceIsNull() {
	    addCriterion("item_face_price is null");
	    return this;
	}

	public Criteria andItemFacePriceIsNotNull() {
	    addCriterion("item_face_price is not null");
	    return this;
	}

	public Criteria andItemFacePriceEqualTo(Integer value) {
	    addCriterion("item_face_price =", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceNotEqualTo(Integer value) {
	    addCriterion("item_face_price <>", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceGreaterThan(Integer value) {
	    addCriterion("item_face_price >", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_face_price >=", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceLessThan(Integer value) {
	    addCriterion("item_face_price <", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceLessThanOrEqualTo(Integer value) {
	    addCriterion("item_face_price <=", value, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceIn(List values) {
	    addCriterion("item_face_price in", values, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceNotIn(List values) {
	    addCriterion("item_face_price not in", values, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceBetween(Integer value1, Integer value2) {
	    addCriterion("item_face_price between", value1, value2, "itemFacePrice");
	    return this;
	}

	public Criteria andItemFacePriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_face_price not between", value1, value2, "itemFacePrice");
	    return this;
	}

	public Criteria andItemCategoryIdIsNull() {
	    addCriterion("item_category_id is null");
	    return this;
	}

	public Criteria andItemCategoryIdIsNotNull() {
	    addCriterion("item_category_id is not null");
	    return this;
	}

	public Criteria andItemCategoryIdEqualTo(Integer value) {
	    addCriterion("item_category_id =", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdNotEqualTo(Integer value) {
	    addCriterion("item_category_id <>", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdGreaterThan(Integer value) {
	    addCriterion("item_category_id >", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_category_id >=", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdLessThan(Integer value) {
	    addCriterion("item_category_id <", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdLessThanOrEqualTo(Integer value) {
	    addCriterion("item_category_id <=", value, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdIn(List values) {
	    addCriterion("item_category_id in", values, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdNotIn(List values) {
	    addCriterion("item_category_id not in", values, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdBetween(Integer value1, Integer value2) {
	    addCriterion("item_category_id between", value1, value2, "itemCategoryId");
	    return this;
	}

	public Criteria andItemCategoryIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_category_id not between", value1, value2, "itemCategoryId");
	    return this;
	}

	public Criteria andItemUidIsNull() {
	    addCriterion("item_uid is null");
	    return this;
	}

	public Criteria andItemUidIsNotNull() {
	    addCriterion("item_uid is not null");
	    return this;
	}

	public Criteria andItemUidEqualTo(String value) {
	    addCriterion("item_uid =", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidNotEqualTo(String value) {
	    addCriterion("item_uid <>", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidGreaterThan(String value) {
	    addCriterion("item_uid >", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidGreaterThanOrEqualTo(String value) {
	    addCriterion("item_uid >=", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidLessThan(String value) {
	    addCriterion("item_uid <", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidLessThanOrEqualTo(String value) {
	    addCriterion("item_uid <=", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidLike(String value) {
	    addCriterion("item_uid like", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidNotLike(String value) {
	    addCriterion("item_uid not like", value, "itemUid");
	    return this;
	}

	public Criteria andItemUidIn(List values) {
	    addCriterion("item_uid in", values, "itemUid");
	    return this;
	}

	public Criteria andItemUidNotIn(List values) {
	    addCriterion("item_uid not in", values, "itemUid");
	    return this;
	}

	public Criteria andItemUidBetween(String value1, String value2) {
	    addCriterion("item_uid between", value1, value2, "itemUid");
	    return this;
	}

	public Criteria andItemUidNotBetween(String value1, String value2) {
	    addCriterion("item_uid not between", value1, value2, "itemUid");
	    return this;
	}

	public Criteria andItemCardIsNull() {
	    addCriterion("item_card is null");
	    return this;
	}

	public Criteria andItemCardIsNotNull() {
	    addCriterion("item_card is not null");
	    return this;
	}

	public Criteria andItemCardEqualTo(String value) {
	    addCriterion("item_card =", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardNotEqualTo(String value) {
	    addCriterion("item_card <>", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardGreaterThan(String value) {
	    addCriterion("item_card >", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardGreaterThanOrEqualTo(String value) {
	    addCriterion("item_card >=", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardLessThan(String value) {
	    addCriterion("item_card <", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardLessThanOrEqualTo(String value) {
	    addCriterion("item_card <=", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardLike(String value) {
	    addCriterion("item_card like", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardNotLike(String value) {
	    addCriterion("item_card not like", value, "itemCard");
	    return this;
	}

	public Criteria andItemCardIn(List values) {
	    addCriterion("item_card in", values, "itemCard");
	    return this;
	}

	public Criteria andItemCardNotIn(List values) {
	    addCriterion("item_card not in", values, "itemCard");
	    return this;
	}

	public Criteria andItemCardBetween(String value1, String value2) {
	    addCriterion("item_card between", value1, value2, "itemCard");
	    return this;
	}

	public Criteria andItemCardNotBetween(String value1, String value2) {
	    addCriterion("item_card not between", value1, value2, "itemCard");
	    return this;
	}

	public Criteria andItemPosIdIsNull() {
	    addCriterion("item_pos_id is null");
	    return this;
	}

	public Criteria andItemPosIdIsNotNull() {
	    addCriterion("item_pos_id is not null");
	    return this;
	}

	public Criteria andItemPosIdEqualTo(String value) {
	    addCriterion("item_pos_id =", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdNotEqualTo(String value) {
	    addCriterion("item_pos_id <>", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdGreaterThan(String value) {
	    addCriterion("item_pos_id >", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdGreaterThanOrEqualTo(String value) {
	    addCriterion("item_pos_id >=", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdLessThan(String value) {
	    addCriterion("item_pos_id <", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdLessThanOrEqualTo(String value) {
	    addCriterion("item_pos_id <=", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdLike(String value) {
	    addCriterion("item_pos_id like", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdNotLike(String value) {
	    addCriterion("item_pos_id not like", value, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdIn(List values) {
	    addCriterion("item_pos_id in", values, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdNotIn(List values) {
	    addCriterion("item_pos_id not in", values, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdBetween(String value1, String value2) {
	    addCriterion("item_pos_id between", value1, value2, "itemPosId");
	    return this;
	}

	public Criteria andItemPosIdNotBetween(String value1, String value2) {
	    addCriterion("item_pos_id not between", value1, value2, "itemPosId");
	    return this;
	}

	public Criteria andItemPcIdIsNull() {
	    addCriterion("item_pc_id is null");
	    return this;
	}

	public Criteria andItemPcIdIsNotNull() {
	    addCriterion("item_pc_id is not null");
	    return this;
	}

	public Criteria andItemPcIdEqualTo(String value) {
	    addCriterion("item_pc_id =", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdNotEqualTo(String value) {
	    addCriterion("item_pc_id <>", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdGreaterThan(String value) {
	    addCriterion("item_pc_id >", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdGreaterThanOrEqualTo(String value) {
	    addCriterion("item_pc_id >=", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdLessThan(String value) {
	    addCriterion("item_pc_id <", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdLessThanOrEqualTo(String value) {
	    addCriterion("item_pc_id <=", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdLike(String value) {
	    addCriterion("item_pc_id like", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdNotLike(String value) {
	    addCriterion("item_pc_id not like", value, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdIn(List values) {
	    addCriterion("item_pc_id in", values, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdNotIn(List values) {
	    addCriterion("item_pc_id not in", values, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdBetween(String value1, String value2) {
	    addCriterion("item_pc_id between", value1, value2, "itemPcId");
	    return this;
	}

	public Criteria andItemPcIdNotBetween(String value1, String value2) {
	    addCriterion("item_pc_id not between", value1, value2, "itemPcId");
	    return this;
	}

	public Criteria andItemCardPwdIsNull() {
	    addCriterion("item_card_pwd is null");
	    return this;
	}

	public Criteria andItemCardPwdIsNotNull() {
	    addCriterion("item_card_pwd is not null");
	    return this;
	}

	public Criteria andItemCardPwdEqualTo(String value) {
	    addCriterion("item_card_pwd =", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdNotEqualTo(String value) {
	    addCriterion("item_card_pwd <>", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdGreaterThan(String value) {
	    addCriterion("item_card_pwd >", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdGreaterThanOrEqualTo(String value) {
	    addCriterion("item_card_pwd >=", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdLessThan(String value) {
	    addCriterion("item_card_pwd <", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdLessThanOrEqualTo(String value) {
	    addCriterion("item_card_pwd <=", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdLike(String value) {
	    addCriterion("item_card_pwd like", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdNotLike(String value) {
	    addCriterion("item_card_pwd not like", value, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdIn(List values) {
	    addCriterion("item_card_pwd in", values, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdNotIn(List values) {
	    addCriterion("item_card_pwd not in", values, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdBetween(String value1, String value2) {
	    addCriterion("item_card_pwd between", value1, value2, "itemCardPwd");
	    return this;
	}

	public Criteria andItemCardPwdNotBetween(String value1, String value2) {
	    addCriterion("item_card_pwd not between", value1, value2, "itemCardPwd");
	    return this;
	}

	public Criteria andItemSupplyIdIsNull() {
	    addCriterion("item_supply_id is null");
	    return this;
	}

	public Criteria andItemSupplyIdIsNotNull() {
	    addCriterion("item_supply_id is not null");
	    return this;
	}

	public Criteria andItemSupplyIdEqualTo(Long value) {
	    addCriterion("item_supply_id =", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdNotEqualTo(Long value) {
	    addCriterion("item_supply_id <>", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdGreaterThan(Long value) {
	    addCriterion("item_supply_id >", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("item_supply_id >=", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdLessThan(Long value) {
	    addCriterion("item_supply_id <", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdLessThanOrEqualTo(Long value) {
	    addCriterion("item_supply_id <=", value, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdIn(List values) {
	    addCriterion("item_supply_id in", values, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdNotIn(List values) {
	    addCriterion("item_supply_id not in", values, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdBetween(Long value1, Long value2) {
	    addCriterion("item_supply_id between", value1, value2, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSupplyIdNotBetween(Long value1, Long value2) {
	    addCriterion("item_supply_id not between", value1, value2, "itemSupplyId");
	    return this;
	}

	public Criteria andItemSizeIsNull() {
	    addCriterion("item_size is null");
	    return this;
	}

	public Criteria andItemSizeIsNotNull() {
	    addCriterion("item_size is not null");
	    return this;
	}

	public Criteria andItemSizeEqualTo(Integer value) {
	    addCriterion("item_size =", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeNotEqualTo(Integer value) {
	    addCriterion("item_size <>", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeGreaterThan(Integer value) {
	    addCriterion("item_size >", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_size >=", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeLessThan(Integer value) {
	    addCriterion("item_size <", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeLessThanOrEqualTo(Integer value) {
	    addCriterion("item_size <=", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeIn(List values) {
	    addCriterion("item_size in", values, "itemSize");
	    return this;
	}

	public Criteria andItemSizeNotIn(List values) {
	    addCriterion("item_size not in", values, "itemSize");
	    return this;
	}

	public Criteria andItemSizeBetween(Integer value1, Integer value2) {
	    addCriterion("item_size between", value1, value2, "itemSize");
	    return this;
	}

	public Criteria andItemSizeNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_size not between", value1, value2, "itemSize");
	    return this;
	}

	public Criteria andStockIdIsNull() {
	    addCriterion("stock_id is null");
	    return this;
	}

	public Criteria andStockIdIsNotNull() {
	    addCriterion("stock_id is not null");
	    return this;
	}

	public Criteria andStockIdEqualTo(Long value) {
	    addCriterion("stock_id =", value, "stockId");
	    return this;
	}

	public Criteria andStockIdNotEqualTo(Long value) {
	    addCriterion("stock_id <>", value, "stockId");
	    return this;
	}

	public Criteria andStockIdGreaterThan(Long value) {
	    addCriterion("stock_id >", value, "stockId");
	    return this;
	}

	public Criteria andStockIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("stock_id >=", value, "stockId");
	    return this;
	}

	public Criteria andStockIdLessThan(Long value) {
	    addCriterion("stock_id <", value, "stockId");
	    return this;
	}

	public Criteria andStockIdLessThanOrEqualTo(Long value) {
	    addCriterion("stock_id <=", value, "stockId");
	    return this;
	}

	public Criteria andStockIdIn(List values) {
	    addCriterion("stock_id in", values, "stockId");
	    return this;
	}

	public Criteria andStockIdNotIn(List values) {
	    addCriterion("stock_id not in", values, "stockId");
	    return this;
	}

	public Criteria andStockIdBetween(Long value1, Long value2) {
	    addCriterion("stock_id between", value1, value2, "stockId");
	    return this;
	}

	public Criteria andStockIdNotBetween(Long value1, Long value2) {
	    addCriterion("stock_id not between", value1, value2, "stockId");
	    return this;
	}

	public Criteria andLockOperIdIsNull() {
	    addCriterion("lock_oper_id is null");
	    return this;
	}

	public Criteria andLockOperIdIsNotNull() {
	    addCriterion("lock_oper_id is not null");
	    return this;
	}

	public Criteria andLockOperIdEqualTo(Long value) {
	    addCriterion("lock_oper_id =", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdNotEqualTo(Long value) {
	    addCriterion("lock_oper_id <>", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdGreaterThan(Long value) {
	    addCriterion("lock_oper_id >", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("lock_oper_id >=", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdLessThan(Long value) {
	    addCriterion("lock_oper_id <", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdLessThanOrEqualTo(Long value) {
	    addCriterion("lock_oper_id <=", value, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdIn(List values) {
	    addCriterion("lock_oper_id in", values, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdNotIn(List values) {
	    addCriterion("lock_oper_id not in", values, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdBetween(Long value1, Long value2) {
	    addCriterion("lock_oper_id between", value1, value2, "lockOperId");
	    return this;
	}

	public Criteria andLockOperIdNotBetween(Long value1, Long value2) {
	    addCriterion("lock_oper_id not between", value1, value2, "lockOperId");
	    return this;
	}

	public Criteria andDealOperIdIsNull() {
	    addCriterion("deal_oper_id is null");
	    return this;
	}

	public Criteria andDealOperIdIsNotNull() {
	    addCriterion("deal_oper_id is not null");
	    return this;
	}

	public Criteria andDealOperIdEqualTo(Long value) {
	    addCriterion("deal_oper_id =", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdNotEqualTo(Long value) {
	    addCriterion("deal_oper_id <>", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdGreaterThan(Long value) {
	    addCriterion("deal_oper_id >", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("deal_oper_id >=", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdLessThan(Long value) {
	    addCriterion("deal_oper_id <", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdLessThanOrEqualTo(Long value) {
	    addCriterion("deal_oper_id <=", value, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdIn(List values) {
	    addCriterion("deal_oper_id in", values, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdNotIn(List values) {
	    addCriterion("deal_oper_id not in", values, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdBetween(Long value1, Long value2) {
	    addCriterion("deal_oper_id between", value1, value2, "dealOperId");
	    return this;
	}

	public Criteria andDealOperIdNotBetween(Long value1, Long value2) {
	    addCriterion("deal_oper_id not between", value1, value2, "dealOperId");
	    return this;
	}

	public Criteria andChannelIsNull() {
	    addCriterion("channel is null");
	    return this;
	}

	public Criteria andChannelIsNotNull() {
	    addCriterion("channel is not null");
	    return this;
	}

	public Criteria andChannelEqualTo(Integer value) {
	    addCriterion("channel =", value, "channel");
	    return this;
	}

	public Criteria andChannelNotEqualTo(Integer value) {
	    addCriterion("channel <>", value, "channel");
	    return this;
	}

	public Criteria andChannelGreaterThan(Integer value) {
	    addCriterion("channel >", value, "channel");
	    return this;
	}

	public Criteria andChannelGreaterThanOrEqualTo(Integer value) {
	    addCriterion("channel >=", value, "channel");
	    return this;
	}

	public Criteria andChannelLessThan(Integer value) {
	    addCriterion("channel <", value, "channel");
	    return this;
	}

	public Criteria andChannelLessThanOrEqualTo(Integer value) {
	    addCriterion("channel <=", value, "channel");
	    return this;
	}

	public Criteria andChannelIn(List values) {
	    addCriterion("channel in", values, "channel");
	    return this;
	}

	public Criteria andChannelNotIn(List values) {
	    addCriterion("channel not in", values, "channel");
	    return this;
	}

	public Criteria andChannelBetween(Integer value1, Integer value2) {
	    addCriterion("channel between", value1, value2, "channel");
	    return this;
	}

	public Criteria andChannelNotBetween(Integer value1, Integer value2) {
	    addCriterion("channel not between", value1, value2, "channel");
	    return this;
	}

	public Criteria andUpstreamIdIsNull() {
	    addCriterion("upstream_id is null");
	    return this;
	}

	public Criteria andUpstreamIdIsNotNull() {
	    addCriterion("upstream_id is not null");
	    return this;
	}

	public Criteria andUpstreamIdEqualTo(String value) {
	    addCriterion("upstream_id =", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotEqualTo(String value) {
	    addCriterion("upstream_id <>", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdGreaterThan(String value) {
	    addCriterion("upstream_id >", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdGreaterThanOrEqualTo(String value) {
	    addCriterion("upstream_id >=", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLessThan(String value) {
	    addCriterion("upstream_id <", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLessThanOrEqualTo(String value) {
	    addCriterion("upstream_id <=", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLike(String value) {
	    addCriterion("upstream_id like", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotLike(String value) {
	    addCriterion("upstream_id not like", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdIn(List values) {
	    addCriterion("upstream_id in", values, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotIn(List values) {
	    addCriterion("upstream_id not in", values, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdBetween(String value1, String value2) {
	    addCriterion("upstream_id between", value1, value2, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotBetween(String value1, String value2) {
	    addCriterion("upstream_id not between", value1, value2, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamNameIsNull() {
	    addCriterion("upstream_name is null");
	    return this;
	}

	public Criteria andUpstreamNameIsNotNull() {
	    addCriterion("upstream_name is not null");
	    return this;
	}

	public Criteria andUpstreamNameEqualTo(String value) {
	    addCriterion("upstream_name =", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameNotEqualTo(String value) {
	    addCriterion("upstream_name <>", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameGreaterThan(String value) {
	    addCriterion("upstream_name >", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameGreaterThanOrEqualTo(String value) {
	    addCriterion("upstream_name >=", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameLessThan(String value) {
	    addCriterion("upstream_name <", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameLessThanOrEqualTo(String value) {
	    addCriterion("upstream_name <=", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameLike(String value) {
	    addCriterion("upstream_name like", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameNotLike(String value) {
	    addCriterion("upstream_name not like", value, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameIn(List values) {
	    addCriterion("upstream_name in", values, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameNotIn(List values) {
	    addCriterion("upstream_name not in", values, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameBetween(String value1, String value2) {
	    addCriterion("upstream_name between", value1, value2, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamNameNotBetween(String value1, String value2) {
	    addCriterion("upstream_name not between", value1, value2, "upstreamName");
	    return this;
	}

	public Criteria andUpstreamSerialnoIsNull() {
	    addCriterion("upstream_serialno is null");
	    return this;
	}

	public Criteria andUpstreamSerialnoIsNotNull() {
	    addCriterion("upstream_serialno is not null");
	    return this;
	}

	public Criteria andUpstreamSerialnoEqualTo(String value) {
	    addCriterion("upstream_serialno =", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoNotEqualTo(String value) {
	    addCriterion("upstream_serialno <>", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoGreaterThan(String value) {
	    addCriterion("upstream_serialno >", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoGreaterThanOrEqualTo(String value) {
	    addCriterion("upstream_serialno >=", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoLessThan(String value) {
	    addCriterion("upstream_serialno <", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoLessThanOrEqualTo(String value) {
	    addCriterion("upstream_serialno <=", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoLike(String value) {
	    addCriterion("upstream_serialno like", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoNotLike(String value) {
	    addCriterion("upstream_serialno not like", value, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoIn(List values) {
	    addCriterion("upstream_serialno in", values, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoNotIn(List values) {
	    addCriterion("upstream_serialno not in", values, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoBetween(String value1, String value2) {
	    addCriterion("upstream_serialno between", value1, value2, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamSerialnoNotBetween(String value1, String value2) {
	    addCriterion("upstream_serialno not between", value1, value2, "upstreamSerialno");
	    return this;
	}

	public Criteria andUpstreamDateIsNull() {
	    addCriterion("upstream_date is null");
	    return this;
	}

	public Criteria andUpstreamDateIsNotNull() {
	    addCriterion("upstream_date is not null");
	    return this;
	}

	public Criteria andUpstreamDateEqualTo(Date value) {
	    addCriterion("upstream_date =", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateNotEqualTo(Date value) {
	    addCriterion("upstream_date <>", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateGreaterThan(Date value) {
	    addCriterion("upstream_date >", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateGreaterThanOrEqualTo(Date value) {
	    addCriterion("upstream_date >=", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateLessThan(Date value) {
	    addCriterion("upstream_date <", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateLessThanOrEqualTo(Date value) {
	    addCriterion("upstream_date <=", value, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateIn(List values) {
	    addCriterion("upstream_date in", values, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateNotIn(List values) {
	    addCriterion("upstream_date not in", values, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateBetween(Date value1, Date value2) {
	    addCriterion("upstream_date between", value1, value2, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamDateNotBetween(Date value1, Date value2) {
	    addCriterion("upstream_date not between", value1, value2, "upstreamDate");
	    return this;
	}

	public Criteria andUpstreamMemoIsNull() {
	    addCriterion("upstream_memo is null");
	    return this;
	}

	public Criteria andUpstreamMemoIsNotNull() {
	    addCriterion("upstream_memo is not null");
	    return this;
	}

	public Criteria andUpstreamMemoEqualTo(String value) {
	    addCriterion("upstream_memo =", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoNotEqualTo(String value) {
	    addCriterion("upstream_memo <>", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoGreaterThan(String value) {
	    addCriterion("upstream_memo >", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoGreaterThanOrEqualTo(String value) {
	    addCriterion("upstream_memo >=", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoLessThan(String value) {
	    addCriterion("upstream_memo <", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoLessThanOrEqualTo(String value) {
	    addCriterion("upstream_memo <=", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoLike(String value) {
	    addCriterion("upstream_memo like", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoNotLike(String value) {
	    addCriterion("upstream_memo not like", value, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoIn(List values) {
	    addCriterion("upstream_memo in", values, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoNotIn(List values) {
	    addCriterion("upstream_memo not in", values, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoBetween(String value1, String value2) {
	    addCriterion("upstream_memo between", value1, value2, "upstreamMemo");
	    return this;
	}

	public Criteria andUpstreamMemoNotBetween(String value1, String value2) {
	    addCriterion("upstream_memo not between", value1, value2, "upstreamMemo");
	    return this;
	}

	public Criteria andDownstreamIdIsNull() {
	    addCriterion("downstream_id is null");
	    return this;
	}

	public Criteria andDownstreamIdIsNotNull() {
	    addCriterion("downstream_id is not null");
	    return this;
	}

	public Criteria andDownstreamIdEqualTo(String value) {
	    addCriterion("downstream_id =", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdNotEqualTo(String value) {
	    addCriterion("downstream_id <>", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdGreaterThan(String value) {
	    addCriterion("downstream_id >", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdGreaterThanOrEqualTo(String value) {
	    addCriterion("downstream_id >=", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdLessThan(String value) {
	    addCriterion("downstream_id <", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdLessThanOrEqualTo(String value) {
	    addCriterion("downstream_id <=", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdLike(String value) {
	    addCriterion("downstream_id like", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdNotLike(String value) {
	    addCriterion("downstream_id not like", value, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdIn(List values) {
	    addCriterion("downstream_id in", values, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdNotIn(List values) {
	    addCriterion("downstream_id not in", values, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdBetween(String value1, String value2) {
	    addCriterion("downstream_id between", value1, value2, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamIdNotBetween(String value1, String value2) {
	    addCriterion("downstream_id not between", value1, value2, "downstreamId");
	    return this;
	}

	public Criteria andDownstreamNameIsNull() {
	    addCriterion("downstream_name is null");
	    return this;
	}

	public Criteria andDownstreamNameIsNotNull() {
	    addCriterion("downstream_name is not null");
	    return this;
	}

	public Criteria andDownstreamNameEqualTo(String value) {
	    addCriterion("downstream_name =", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameNotEqualTo(String value) {
	    addCriterion("downstream_name <>", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameGreaterThan(String value) {
	    addCriterion("downstream_name >", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameGreaterThanOrEqualTo(String value) {
	    addCriterion("downstream_name >=", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameLessThan(String value) {
	    addCriterion("downstream_name <", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameLessThanOrEqualTo(String value) {
	    addCriterion("downstream_name <=", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameLike(String value) {
	    addCriterion("downstream_name like", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameNotLike(String value) {
	    addCriterion("downstream_name not like", value, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameIn(List values) {
	    addCriterion("downstream_name in", values, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameNotIn(List values) {
	    addCriterion("downstream_name not in", values, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameBetween(String value1, String value2) {
	    addCriterion("downstream_name between", value1, value2, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamNameNotBetween(String value1, String value2) {
	    addCriterion("downstream_name not between", value1, value2, "downstreamName");
	    return this;
	}

	public Criteria andDownstreamDateIsNull() {
	    addCriterion("downstream_date is null");
	    return this;
	}

	public Criteria andDownstreamDateIsNotNull() {
	    addCriterion("downstream_date is not null");
	    return this;
	}

	public Criteria andDownstreamDateEqualTo(Date value) {
	    addCriterion("downstream_date =", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateNotEqualTo(Date value) {
	    addCriterion("downstream_date <>", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateGreaterThan(Date value) {
	    addCriterion("downstream_date >", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateGreaterThanOrEqualTo(Date value) {
	    addCriterion("downstream_date >=", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateLessThan(Date value) {
	    addCriterion("downstream_date <", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateLessThanOrEqualTo(Date value) {
	    addCriterion("downstream_date <=", value, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateIn(List values) {
	    addCriterion("downstream_date in", values, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateNotIn(List values) {
	    addCriterion("downstream_date not in", values, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateBetween(Date value1, Date value2) {
	    addCriterion("downstream_date between", value1, value2, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamDateNotBetween(Date value1, Date value2) {
	    addCriterion("downstream_date not between", value1, value2, "downstreamDate");
	    return this;
	}

	public Criteria andDownstreamSerialnoIsNull() {
	    addCriterion("downstream_serialno is null");
	    return this;
	}

	public Criteria andDownstreamSerialnoIsNotNull() {
	    addCriterion("downstream_serialno is not null");
	    return this;
	}

	public Criteria andDownstreamSerialnoEqualTo(String value) {
	    addCriterion("downstream_serialno =", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoNotEqualTo(String value) {
	    addCriterion("downstream_serialno <>", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoGreaterThan(String value) {
	    addCriterion("downstream_serialno >", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoGreaterThanOrEqualTo(String value) {
	    addCriterion("downstream_serialno >=", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoLessThan(String value) {
	    addCriterion("downstream_serialno <", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoLessThanOrEqualTo(String value) {
	    addCriterion("downstream_serialno <=", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoLike(String value) {
	    addCriterion("downstream_serialno like", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoNotLike(String value) {
	    addCriterion("downstream_serialno not like", value, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoIn(List values) {
	    addCriterion("downstream_serialno in", values, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoNotIn(List values) {
	    addCriterion("downstream_serialno not in", values, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoBetween(String value1, String value2) {
	    addCriterion("downstream_serialno between", value1, value2, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamSerialnoNotBetween(String value1, String value2) {
	    addCriterion("downstream_serialno not between", value1, value2, "downstreamSerialno");
	    return this;
	}

	public Criteria andDownstreamNotesIsNull() {
	    addCriterion("downstream_notes is null");
	    return this;
	}

	public Criteria andDownstreamNotesIsNotNull() {
	    addCriterion("downstream_notes is not null");
	    return this;
	}

	public Criteria andDownstreamNotesEqualTo(String value) {
	    addCriterion("downstream_notes =", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesNotEqualTo(String value) {
	    addCriterion("downstream_notes <>", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesGreaterThan(String value) {
	    addCriterion("downstream_notes >", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesGreaterThanOrEqualTo(String value) {
	    addCriterion("downstream_notes >=", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesLessThan(String value) {
	    addCriterion("downstream_notes <", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesLessThanOrEqualTo(String value) {
	    addCriterion("downstream_notes <=", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesLike(String value) {
	    addCriterion("downstream_notes like", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesNotLike(String value) {
	    addCriterion("downstream_notes not like", value, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesIn(List values) {
	    addCriterion("downstream_notes in", values, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesNotIn(List values) {
	    addCriterion("downstream_notes not in", values, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesBetween(String value1, String value2) {
	    addCriterion("downstream_notes between", value1, value2, "downstreamNotes");
	    return this;
	}

	public Criteria andDownstreamNotesNotBetween(String value1, String value2) {
	    addCriterion("downstream_notes not between", value1, value2, "downstreamNotes");
	    return this;
	}

	public Criteria andMemoIsNull() {
	    addCriterion("memo is null");
	    return this;
	}

	public Criteria andMemoIsNotNull() {
	    addCriterion("memo is not null");
	    return this;
	}

	public Criteria andMemoEqualTo(String value) {
	    addCriterion("memo =", value, "memo");
	    return this;
	}

	public Criteria andMemoNotEqualTo(String value) {
	    addCriterion("memo <>", value, "memo");
	    return this;
	}

	public Criteria andMemoGreaterThan(String value) {
	    addCriterion("memo >", value, "memo");
	    return this;
	}

	public Criteria andMemoGreaterThanOrEqualTo(String value) {
	    addCriterion("memo >=", value, "memo");
	    return this;
	}

	public Criteria andMemoLessThan(String value) {
	    addCriterion("memo <", value, "memo");
	    return this;
	}

	public Criteria andMemoLessThanOrEqualTo(String value) {
	    addCriterion("memo <=", value, "memo");
	    return this;
	}

	public Criteria andMemoLike(String value) {
	    addCriterion("memo like", value, "memo");
	    return this;
	}

	public Criteria andMemoNotLike(String value) {
	    addCriterion("memo not like", value, "memo");
	    return this;
	}

	public Criteria andMemoIn(List values) {
	    addCriterion("memo in", values, "memo");
	    return this;
	}

	public Criteria andMemoNotIn(List values) {
	    addCriterion("memo not in", values, "memo");
	    return this;
	}

	public Criteria andMemoBetween(String value1, String value2) {
	    addCriterion("memo between", value1, value2, "memo");
	    return this;
	}

	public Criteria andMemoNotBetween(String value1, String value2) {
	    addCriterion("memo not between", value1, value2, "memo");
	    return this;
	}

	public Criteria andCostTimeIsNull() {
	    addCriterion("cost_time is null");
	    return this;
	}

	public Criteria andCostTimeIsNotNull() {
	    addCriterion("cost_time is not null");
	    return this;
	}

	public Criteria andCostTimeEqualTo(Integer value) {
	    addCriterion("cost_time =", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeNotEqualTo(Integer value) {
	    addCriterion("cost_time <>", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeGreaterThan(Integer value) {
	    addCriterion("cost_time >", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("cost_time >=", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeLessThan(Integer value) {
	    addCriterion("cost_time <", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeLessThanOrEqualTo(Integer value) {
	    addCriterion("cost_time <=", value, "costTime");
	    return this;
	}

	public Criteria andCostTimeIn(List values) {
	    addCriterion("cost_time in", values, "costTime");
	    return this;
	}

	public Criteria andCostTimeNotIn(List values) {
	    addCriterion("cost_time not in", values, "costTime");
	    return this;
	}

	public Criteria andCostTimeBetween(Integer value1, Integer value2) {
	    addCriterion("cost_time between", value1, value2, "costTime");
	    return this;
	}

	public Criteria andCostTimeNotBetween(Integer value1, Integer value2) {
	    addCriterion("cost_time not between", value1, value2, "costTime");
	    return this;
	}

	public Criteria andSupplyTypeIsNull() {
	    addCriterion("supply_type is null");
	    return this;
	}

	public Criteria andSupplyTypeIsNotNull() {
	    addCriterion("supply_type is not null");
	    return this;
	}

	public Criteria andSupplyTypeEqualTo(Integer value) {
	    addCriterion("supply_type =", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotEqualTo(Integer value) {
	    addCriterion("supply_type <>", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeGreaterThan(Integer value) {
	    addCriterion("supply_type >", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_type >=", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeLessThan(Integer value) {
	    addCriterion("supply_type <", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_type <=", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeIn(List values) {
	    addCriterion("supply_type in", values, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotIn(List values) {
	    addCriterion("supply_type not in", values, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeBetween(Integer value1, Integer value2) {
	    addCriterion("supply_type between", value1, value2, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_type not between", value1, value2, "supplyType");
	    return this;
	}

	public Criteria andRepeatTypeIsNull() {
	    addCriterion("repeat_type is null");
	    return this;
	}

	public Criteria andRepeatTypeIsNotNull() {
	    addCriterion("repeat_type is not null");
	    return this;
	}

	public Criteria andRepeatTypeEqualTo(Integer value) {
	    addCriterion("repeat_type =", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeNotEqualTo(Integer value) {
	    addCriterion("repeat_type <>", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeGreaterThan(Integer value) {
	    addCriterion("repeat_type >", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("repeat_type >=", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeLessThan(Integer value) {
	    addCriterion("repeat_type <", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("repeat_type <=", value, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeIn(List values) {
	    addCriterion("repeat_type in", values, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeNotIn(List values) {
	    addCriterion("repeat_type not in", values, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_type between", value1, value2, "repeatType");
	    return this;
	}

	public Criteria andRepeatTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_type not between", value1, value2, "repeatType");
	    return this;
	}

	public Criteria andManualTypeIsNull() {
	    addCriterion("manual_type is null");
	    return this;
	}

	public Criteria andManualTypeIsNotNull() {
	    addCriterion("manual_type is not null");
	    return this;
	}

	public Criteria andManualTypeEqualTo(Integer value) {
	    addCriterion("manual_type =", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeNotEqualTo(Integer value) {
	    addCriterion("manual_type <>", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeGreaterThan(Integer value) {
	    addCriterion("manual_type >", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("manual_type >=", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeLessThan(Integer value) {
	    addCriterion("manual_type <", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("manual_type <=", value, "manualType");
	    return this;
	}

	public Criteria andManualTypeIn(List values) {
	    addCriterion("manual_type in", values, "manualType");
	    return this;
	}

	public Criteria andManualTypeNotIn(List values) {
	    addCriterion("manual_type not in", values, "manualType");
	    return this;
	}

	public Criteria andManualTypeBetween(Integer value1, Integer value2) {
	    addCriterion("manual_type between", value1, value2, "manualType");
	    return this;
	}

	public Criteria andManualTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("manual_type not between", value1, value2, "manualType");
	    return this;
	}

	public Criteria andDownstreamSupplyWayIsNull() {
	    addCriterion("downstream_supply_way is null");
	    return this;
	}

	public Criteria andDownstreamSupplyWayIsNotNull() {
	    addCriterion("downstream_supply_way is not null");
	    return this;
	}

	public Criteria andDownstreamSupplyWayEqualTo(Integer value) {
	    addCriterion("downstream_supply_way =", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayNotEqualTo(Integer value) {
	    addCriterion("downstream_supply_way <>", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayGreaterThan(Integer value) {
	    addCriterion("downstream_supply_way >", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayGreaterThanOrEqualTo(Integer value) {
	    addCriterion("downstream_supply_way >=", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayLessThan(Integer value) {
	    addCriterion("downstream_supply_way <", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayLessThanOrEqualTo(Integer value) {
	    addCriterion("downstream_supply_way <=", value, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayIn(List values) {
	    addCriterion("downstream_supply_way in", values, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayNotIn(List values) {
	    addCriterion("downstream_supply_way not in", values, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayBetween(Integer value1, Integer value2) {
	    addCriterion("downstream_supply_way between", value1, value2, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andDownstreamSupplyWayNotBetween(Integer value1, Integer value2) {
	    addCriterion("downstream_supply_way not between", value1, value2, "downstreamSupplyWay");
	    return this;
	}

	public Criteria andItemCostPriceIsNull() {
	    addCriterion("item_cost_price is null");
	    return this;
	}

	public Criteria andItemCostPriceIsNotNull() {
	    addCriterion("item_cost_price is not null");
	    return this;
	}

	public Criteria andItemCostPriceEqualTo(Integer value) {
	    addCriterion("item_cost_price =", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceNotEqualTo(Integer value) {
	    addCriterion("item_cost_price <>", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceGreaterThan(Integer value) {
	    addCriterion("item_cost_price >", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_cost_price >=", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceLessThan(Integer value) {
	    addCriterion("item_cost_price <", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceLessThanOrEqualTo(Integer value) {
	    addCriterion("item_cost_price <=", value, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceIn(List values) {
	    addCriterion("item_cost_price in", values, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceNotIn(List values) {
	    addCriterion("item_cost_price not in", values, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceBetween(Integer value1, Integer value2) {
	    addCriterion("item_cost_price between", value1, value2, "itemCostPrice");
	    return this;
	}

	public Criteria andItemCostPriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_cost_price not between", value1, value2, "itemCostPrice");
	    return this;
	}

	public Criteria andCarrierTypeIsNull() {
	    addCriterion("carrier_type is null");
	    return this;
	}

	public Criteria andCarrierTypeIsNotNull() {
	    addCriterion("carrier_type is not null");
	    return this;
	}

	public Criteria andCarrierTypeEqualTo(Integer value) {
	    addCriterion("carrier_type =", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeNotEqualTo(Integer value) {
	    addCriterion("carrier_type <>", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeGreaterThan(Integer value) {
	    addCriterion("carrier_type >", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("carrier_type >=", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeLessThan(Integer value) {
	    addCriterion("carrier_type <", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("carrier_type <=", value, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeIn(List values) {
	    addCriterion("carrier_type in", values, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeNotIn(List values) {
	    addCriterion("carrier_type not in", values, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeBetween(Integer value1, Integer value2) {
	    addCriterion("carrier_type between", value1, value2, "carrierType");
	    return this;
	}

	public Criteria andCarrierTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("carrier_type not between", value1, value2, "carrierType");
	    return this;
	}

	public Criteria andProvinceCodeIsNull() {
	    addCriterion("province_code is null");
	    return this;
	}

	public Criteria andProvinceCodeIsNotNull() {
	    addCriterion("province_code is not null");
	    return this;
	}

	public Criteria andProvinceCodeEqualTo(String value) {
	    addCriterion("province_code =", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeNotEqualTo(String value) {
	    addCriterion("province_code <>", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeGreaterThan(String value) {
	    addCriterion("province_code >", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeGreaterThanOrEqualTo(String value) {
	    addCriterion("province_code >=", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeLessThan(String value) {
	    addCriterion("province_code <", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeLessThanOrEqualTo(String value) {
	    addCriterion("province_code <=", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeLike(String value) {
	    addCriterion("province_code like", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeNotLike(String value) {
	    addCriterion("province_code not like", value, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeIn(List values) {
	    addCriterion("province_code in", values, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeNotIn(List values) {
	    addCriterion("province_code not in", values, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeBetween(String value1, String value2) {
	    addCriterion("province_code between", value1, value2, "provinceCode");
	    return this;
	}

	public Criteria andProvinceCodeNotBetween(String value1, String value2) {
	    addCriterion("province_code not between", value1, value2, "provinceCode");
	    return this;
	}

	public Criteria andActualCostIsNull() {
	    addCriterion("actual_cost is null");
	    return this;
	}

	public Criteria andActualCostIsNotNull() {
	    addCriterion("actual_cost is not null");
	    return this;
	}

	public Criteria andActualCostEqualTo(Integer value) {
	    addCriterion("actual_cost =", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostNotEqualTo(Integer value) {
	    addCriterion("actual_cost <>", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostGreaterThan(Integer value) {
	    addCriterion("actual_cost >", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostGreaterThanOrEqualTo(Integer value) {
	    addCriterion("actual_cost >=", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostLessThan(Integer value) {
	    addCriterion("actual_cost <", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostLessThanOrEqualTo(Integer value) {
	    addCriterion("actual_cost <=", value, "actualCost");
	    return this;
	}

	public Criteria andActualCostIn(List values) {
	    addCriterion("actual_cost in", values, "actualCost");
	    return this;
	}

	public Criteria andActualCostNotIn(List values) {
	    addCriterion("actual_cost not in", values, "actualCost");
	    return this;
	}

	public Criteria andActualCostBetween(Integer value1, Integer value2) {
	    addCriterion("actual_cost between", value1, value2, "actualCost");
	    return this;
	}

	public Criteria andActualCostNotBetween(Integer value1, Integer value2) {
	    addCriterion("actual_cost not between", value1, value2, "actualCost");
	    return this;
	}

	public Criteria andCombineTypeIsNull() {
	    addCriterion("combine_type is null");
	    return this;
	}

	public Criteria andCombineTypeIsNotNull() {
	    addCriterion("combine_type is not null");
	    return this;
	}

	public Criteria andCombineTypeEqualTo(Integer value) {
	    addCriterion("combine_type =", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeNotEqualTo(Integer value) {
	    addCriterion("combine_type <>", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeGreaterThan(Integer value) {
	    addCriterion("combine_type >", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("combine_type >=", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeLessThan(Integer value) {
	    addCriterion("combine_type <", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("combine_type <=", value, "combineType");
	    return this;
	}

	public Criteria andCombineTypeIn(List values) {
	    addCriterion("combine_type in", values, "combineType");
	    return this;
	}

	public Criteria andCombineTypeNotIn(List values) {
	    addCriterion("combine_type not in", values, "combineType");
	    return this;
	}

	public Criteria andCombineTypeBetween(Integer value1, Integer value2) {
	    addCriterion("combine_type between", value1, value2, "combineType");
	    return this;
	}

	public Criteria andCombineTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("combine_type not between", value1, value2, "combineType");
	    return this;
	}

	public Criteria andCombineCountIsNull() {
	    addCriterion("combine_count is null");
	    return this;
	}

	public Criteria andCombineCountIsNotNull() {
	    addCriterion("combine_count is not null");
	    return this;
	}

	public Criteria andCombineCountEqualTo(Integer value) {
	    addCriterion("combine_count =", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountNotEqualTo(Integer value) {
	    addCriterion("combine_count <>", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountGreaterThan(Integer value) {
	    addCriterion("combine_count >", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountGreaterThanOrEqualTo(Integer value) {
	    addCriterion("combine_count >=", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountLessThan(Integer value) {
	    addCriterion("combine_count <", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountLessThanOrEqualTo(Integer value) {
	    addCriterion("combine_count <=", value, "combineCount");
	    return this;
	}

	public Criteria andCombineCountIn(List values) {
	    addCriterion("combine_count in", values, "combineCount");
	    return this;
	}

	public Criteria andCombineCountNotIn(List values) {
	    addCriterion("combine_count not in", values, "combineCount");
	    return this;
	}

	public Criteria andCombineCountBetween(Integer value1, Integer value2) {
	    addCriterion("combine_count between", value1, value2, "combineCount");
	    return this;
	}

	public Criteria andCombineCountNotBetween(Integer value1, Integer value2) {
	    addCriterion("combine_count not between", value1, value2, "combineCount");
	    return this;
	}

	public Criteria andSupplyCountIsNull() {
	    addCriterion("supply_count is null");
	    return this;
	}

	public Criteria andSupplyCountIsNotNull() {
	    addCriterion("supply_count is not null");
	    return this;
	}

	public Criteria andSupplyCountEqualTo(Integer value) {
	    addCriterion("supply_count =", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountNotEqualTo(Integer value) {
	    addCriterion("supply_count <>", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountGreaterThan(Integer value) {
	    addCriterion("supply_count >", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_count >=", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountLessThan(Integer value) {
	    addCriterion("supply_count <", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_count <=", value, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountIn(List values) {
	    addCriterion("supply_count in", values, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountNotIn(List values) {
	    addCriterion("supply_count not in", values, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountBetween(Integer value1, Integer value2) {
	    addCriterion("supply_count between", value1, value2, "supplyCount");
	    return this;
	}

	public Criteria andSupplyCountNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_count not between", value1, value2, "supplyCount");
	    return this;
	}

	public Criteria andSupplyFilterIndexIsNull() {
	    addCriterion("supply_filter_index is null");
	    return this;
	}

	public Criteria andSupplyFilterIndexIsNotNull() {
	    addCriterion("supply_filter_index is not null");
	    return this;
	}

	public Criteria andSupplyFilterIndexEqualTo(Integer value) {
	    addCriterion("supply_filter_index =", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexNotEqualTo(Integer value) {
	    addCriterion("supply_filter_index <>", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexGreaterThan(Integer value) {
	    addCriterion("supply_filter_index >", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_filter_index >=", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexLessThan(Integer value) {
	    addCriterion("supply_filter_index <", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_filter_index <=", value, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexIn(List values) {
	    addCriterion("supply_filter_index in", values, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexNotIn(List values) {
	    addCriterion("supply_filter_index not in", values, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexBetween(Integer value1, Integer value2) {
	    addCriterion("supply_filter_index between", value1, value2, "supplyFilterIndex");
	    return this;
	}

	public Criteria andSupplyFilterIndexNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_filter_index not between", value1, value2, "supplyFilterIndex");
	    return this;
	}

	public Criteria andExtendIsNull() {
	    addCriterion("extend is null");
	    return this;
	}

	public Criteria andExtendIsNotNull() {
	    addCriterion("extend is not null");
	    return this;
	}

	public Criteria andExtendEqualTo(String value) {
	    addCriterion("extend =", value, "extend");
	    return this;
	}

	public Criteria andExtendNotEqualTo(String value) {
	    addCriterion("extend <>", value, "extend");
	    return this;
	}

	public Criteria andExtendGreaterThan(String value) {
	    addCriterion("extend >", value, "extend");
	    return this;
	}

	public Criteria andExtendGreaterThanOrEqualTo(String value) {
	    addCriterion("extend >=", value, "extend");
	    return this;
	}

	public Criteria andExtendLessThan(String value) {
	    addCriterion("extend <", value, "extend");
	    return this;
	}

	public Criteria andExtendLessThanOrEqualTo(String value) {
	    addCriterion("extend <=", value, "extend");
	    return this;
	}

	public Criteria andExtendLike(String value) {
	    addCriterion("extend like", value, "extend");
	    return this;
	}

	public Criteria andExtendNotLike(String value) {
	    addCriterion("extend not like", value, "extend");
	    return this;
	}

	public Criteria andExtendIn(List values) {
	    addCriterion("extend in", values, "extend");
	    return this;
	}

	public Criteria andExtendNotIn(List values) {
	    addCriterion("extend not in", values, "extend");
	    return this;
	}

	public Criteria andExtendBetween(String value1, String value2) {
	    addCriterion("extend between", value1, value2, "extend");
	    return this;
	}

	public Criteria andExtendNotBetween(String value1, String value2) {
	    addCriterion("extend not between", value1, value2, "extend");
	    return this;
	}
    }
}