package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AcctLogExample {
    protected String orderByClause;
    protected List oredCriteria;

    public AcctLogExample() {
	oredCriteria = new ArrayList();
    }

    protected AcctLogExample(AcctLogExample example) {
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

	protected void addCriterionForJDBCDate(String condition, Date value, String property) {
	    addCriterion(condition, new java.sql.Date(value.getTime()), property);
	}

	protected void addCriterionForJDBCDate(String condition, List values, String property) {
	    if (values == null || values.size() == 0) {
		throw new RuntimeException("Value list for " + property + " cannot be null or empty");
	    }
	    List dateList = new ArrayList();
	    Iterator iter = values.iterator();
	    while (iter.hasNext()) {
		dateList.add(new java.sql.Date(((Date) iter.next()).getTime()));
	    }
	    addCriterion(condition, dateList, property);
	}

	protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
	    if (value1 == null || value2 == null) {
		throw new RuntimeException("Between values for " + property + " cannot be null");
	    }
	    addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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
	    addCriterionForJDBCDate("gmt_create =", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_create <>", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateGreaterThan(Date value) {
	    addCriterionForJDBCDate("gmt_create >", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_create >=", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateLessThan(Date value) {
	    addCriterionForJDBCDate("gmt_create <", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_create <=", value, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateIn(List values) {
	    addCriterionForJDBCDate("gmt_create in", values, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotIn(List values) {
	    addCriterionForJDBCDate("gmt_create not in", values, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("gmt_create between", value1, value2, "gmtCreate");
	    return this;
	}

	public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("gmt_create not between", value1, value2, "gmtCreate");
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
	    addCriterionForJDBCDate("gmt_modify =", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_modify <>", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyGreaterThan(Date value) {
	    addCriterionForJDBCDate("gmt_modify >", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyGreaterThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_modify >=", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyLessThan(Date value) {
	    addCriterionForJDBCDate("gmt_modify <", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyLessThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("gmt_modify <=", value, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyIn(List values) {
	    addCriterionForJDBCDate("gmt_modify in", values, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotIn(List values) {
	    addCriterionForJDBCDate("gmt_modify not in", values, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("gmt_modify between", value1, value2, "gmtModify");
	    return this;
	}

	public Criteria andGmtModifyNotBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("gmt_modify not between", value1, value2, "gmtModify");
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

	public Criteria andAcctIdIsNull() {
	    addCriterion("acct_id is null");
	    return this;
	}

	public Criteria andAcctIdIsNotNull() {
	    addCriterion("acct_id is not null");
	    return this;
	}

	public Criteria andAcctIdEqualTo(Long value) {
	    addCriterion("acct_id =", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdNotEqualTo(Long value) {
	    addCriterion("acct_id <>", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdGreaterThan(Long value) {
	    addCriterion("acct_id >", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("acct_id >=", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdLessThan(Long value) {
	    addCriterion("acct_id <", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdLessThanOrEqualTo(Long value) {
	    addCriterion("acct_id <=", value, "acctId");
	    return this;
	}

	public Criteria andAcctIdIn(List values) {
	    addCriterion("acct_id in", values, "acctId");
	    return this;
	}

	public Criteria andAcctIdNotIn(List values) {
	    addCriterion("acct_id not in", values, "acctId");
	    return this;
	}

	public Criteria andAcctIdBetween(Long value1, Long value2) {
	    addCriterion("acct_id between", value1, value2, "acctId");
	    return this;
	}

	public Criteria andAcctIdNotBetween(Long value1, Long value2) {
	    addCriterion("acct_id not between", value1, value2, "acctId");
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

	public Criteria andRefundOrderIdIsNull() {
	    addCriterion("refund_order_id is null");
	    return this;
	}

	public Criteria andRefundOrderIdIsNotNull() {
	    addCriterion("refund_order_id is not null");
	    return this;
	}

	public Criteria andRefundOrderIdEqualTo(Long value) {
	    addCriterion("refund_order_id =", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdNotEqualTo(Long value) {
	    addCriterion("refund_order_id <>", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdGreaterThan(Long value) {
	    addCriterion("refund_order_id >", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("refund_order_id >=", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdLessThan(Long value) {
	    addCriterion("refund_order_id <", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdLessThanOrEqualTo(Long value) {
	    addCriterion("refund_order_id <=", value, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdIn(List values) {
	    addCriterion("refund_order_id in", values, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdNotIn(List values) {
	    addCriterion("refund_order_id not in", values, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdBetween(Long value1, Long value2) {
	    addCriterion("refund_order_id between", value1, value2, "refundOrderId");
	    return this;
	}

	public Criteria andRefundOrderIdNotBetween(Long value1, Long value2) {
	    addCriterion("refund_order_id not between", value1, value2, "refundOrderId");
	    return this;
	}

	public Criteria andBizOrderIdIsNull() {
	    addCriterion("biz_order_id is null");
	    return this;
	}

	public Criteria andBizOrderIdIsNotNull() {
	    addCriterion("biz_order_id is not null");
	    return this;
	}

	public Criteria andBizOrderIdEqualTo(Long value) {
	    addCriterion("biz_order_id =", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdNotEqualTo(Long value) {
	    addCriterion("biz_order_id <>", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdGreaterThan(Long value) {
	    addCriterion("biz_order_id >", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("biz_order_id >=", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdLessThan(Long value) {
	    addCriterion("biz_order_id <", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdLessThanOrEqualTo(Long value) {
	    addCriterion("biz_order_id <=", value, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdIn(List values) {
	    addCriterion("biz_order_id in", values, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdNotIn(List values) {
	    addCriterion("biz_order_id not in", values, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdBetween(Long value1, Long value2) {
	    addCriterion("biz_order_id between", value1, value2, "bizOrderId");
	    return this;
	}

	public Criteria andBizOrderIdNotBetween(Long value1, Long value2) {
	    addCriterion("biz_order_id not between", value1, value2, "bizOrderId");
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

	public Criteria andTradeTypeIsNull() {
	    addCriterion("trade_type is null");
	    return this;
	}

	public Criteria andTradeTypeIsNotNull() {
	    addCriterion("trade_type is not null");
	    return this;
	}

	public Criteria andTradeTypeEqualTo(Integer value) {
	    addCriterion("trade_type =", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeNotEqualTo(Integer value) {
	    addCriterion("trade_type <>", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeGreaterThan(Integer value) {
	    addCriterion("trade_type >", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("trade_type >=", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeLessThan(Integer value) {
	    addCriterion("trade_type <", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("trade_type <=", value, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeIn(List values) {
	    addCriterion("trade_type in", values, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeNotIn(List values) {
	    addCriterion("trade_type not in", values, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeBetween(Integer value1, Integer value2) {
	    addCriterion("trade_type between", value1, value2, "tradeType");
	    return this;
	}

	public Criteria andTradeTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("trade_type not between", value1, value2, "tradeType");
	    return this;
	}

	public Criteria andAmtInIsNull() {
	    addCriterion("amt_in is null");
	    return this;
	}

	public Criteria andAmtInIsNotNull() {
	    addCriterion("amt_in is not null");
	    return this;
	}

	public Criteria andAmtInEqualTo(Long value) {
	    addCriterion("amt_in =", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInNotEqualTo(Long value) {
	    addCriterion("amt_in <>", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInGreaterThan(Long value) {
	    addCriterion("amt_in >", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInGreaterThanOrEqualTo(Long value) {
	    addCriterion("amt_in >=", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInLessThan(Long value) {
	    addCriterion("amt_in <", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInLessThanOrEqualTo(Long value) {
	    addCriterion("amt_in <=", value, "amtIn");
	    return this;
	}

	public Criteria andAmtInIn(List values) {
	    addCriterion("amt_in in", values, "amtIn");
	    return this;
	}

	public Criteria andAmtInNotIn(List values) {
	    addCriterion("amt_in not in", values, "amtIn");
	    return this;
	}

	public Criteria andAmtInBetween(Long value1, Long value2) {
	    addCriterion("amt_in between", value1, value2, "amtIn");
	    return this;
	}

	public Criteria andAmtInNotBetween(Long value1, Long value2) {
	    addCriterion("amt_in not between", value1, value2, "amtIn");
	    return this;
	}

	public Criteria andAmtOutIsNull() {
	    addCriterion("amt_out is null");
	    return this;
	}

	public Criteria andAmtOutIsNotNull() {
	    addCriterion("amt_out is not null");
	    return this;
	}

	public Criteria andAmtOutEqualTo(Long value) {
	    addCriterion("amt_out =", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutNotEqualTo(Long value) {
	    addCriterion("amt_out <>", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutGreaterThan(Long value) {
	    addCriterion("amt_out >", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutGreaterThanOrEqualTo(Long value) {
	    addCriterion("amt_out >=", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutLessThan(Long value) {
	    addCriterion("amt_out <", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutLessThanOrEqualTo(Long value) {
	    addCriterion("amt_out <=", value, "amtOut");
	    return this;
	}

	public Criteria andAmtOutIn(List values) {
	    addCriterion("amt_out in", values, "amtOut");
	    return this;
	}

	public Criteria andAmtOutNotIn(List values) {
	    addCriterion("amt_out not in", values, "amtOut");
	    return this;
	}

	public Criteria andAmtOutBetween(Long value1, Long value2) {
	    addCriterion("amt_out between", value1, value2, "amtOut");
	    return this;
	}

	public Criteria andAmtOutNotBetween(Long value1, Long value2) {
	    addCriterion("amt_out not between", value1, value2, "amtOut");
	    return this;
	}

	public Criteria andAmtInExIsNull() {
	    addCriterion("amt_in_ex is null");
	    return this;
	}

	public Criteria andAmtInExIsNotNull() {
	    addCriterion("amt_in_ex is not null");
	    return this;
	}

	public Criteria andAmtInExEqualTo(Long value) {
	    addCriterion("amt_in_ex =", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExNotEqualTo(Long value) {
	    addCriterion("amt_in_ex <>", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExGreaterThan(Long value) {
	    addCriterion("amt_in_ex >", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExGreaterThanOrEqualTo(Long value) {
	    addCriterion("amt_in_ex >=", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExLessThan(Long value) {
	    addCriterion("amt_in_ex <", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExLessThanOrEqualTo(Long value) {
	    addCriterion("amt_in_ex <=", value, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExIn(List values) {
	    addCriterion("amt_in_ex in", values, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExNotIn(List values) {
	    addCriterion("amt_in_ex not in", values, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExBetween(Long value1, Long value2) {
	    addCriterion("amt_in_ex between", value1, value2, "amtInEx");
	    return this;
	}

	public Criteria andAmtInExNotBetween(Long value1, Long value2) {
	    addCriterion("amt_in_ex not between", value1, value2, "amtInEx");
	    return this;
	}

	public Criteria andAmtOutExIsNull() {
	    addCriterion("amt_out_ex is null");
	    return this;
	}

	public Criteria andAmtOutExIsNotNull() {
	    addCriterion("amt_out_ex is not null");
	    return this;
	}

	public Criteria andAmtOutExEqualTo(Long value) {
	    addCriterion("amt_out_ex =", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExNotEqualTo(Long value) {
	    addCriterion("amt_out_ex <>", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExGreaterThan(Long value) {
	    addCriterion("amt_out_ex >", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExGreaterThanOrEqualTo(Long value) {
	    addCriterion("amt_out_ex >=", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExLessThan(Long value) {
	    addCriterion("amt_out_ex <", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExLessThanOrEqualTo(Long value) {
	    addCriterion("amt_out_ex <=", value, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExIn(List values) {
	    addCriterion("amt_out_ex in", values, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExNotIn(List values) {
	    addCriterion("amt_out_ex not in", values, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExBetween(Long value1, Long value2) {
	    addCriterion("amt_out_ex between", value1, value2, "amtOutEx");
	    return this;
	}

	public Criteria andAmtOutExNotBetween(Long value1, Long value2) {
	    addCriterion("amt_out_ex not between", value1, value2, "amtOutEx");
	    return this;
	}

	public Criteria andAmtBalanceIsNull() {
	    addCriterion("amt_balance is null");
	    return this;
	}

	public Criteria andAmtBalanceIsNotNull() {
	    addCriterion("amt_balance is not null");
	    return this;
	}

	public Criteria andAmtBalanceEqualTo(Long value) {
	    addCriterion("amt_balance =", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceNotEqualTo(Long value) {
	    addCriterion("amt_balance <>", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceGreaterThan(Long value) {
	    addCriterion("amt_balance >", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceGreaterThanOrEqualTo(Long value) {
	    addCriterion("amt_balance >=", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceLessThan(Long value) {
	    addCriterion("amt_balance <", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceLessThanOrEqualTo(Long value) {
	    addCriterion("amt_balance <=", value, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceIn(List values) {
	    addCriterion("amt_balance in", values, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceNotIn(List values) {
	    addCriterion("amt_balance not in", values, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceBetween(Long value1, Long value2) {
	    addCriterion("amt_balance between", value1, value2, "amtBalance");
	    return this;
	}

	public Criteria andAmtBalanceNotBetween(Long value1, Long value2) {
	    addCriterion("amt_balance not between", value1, value2, "amtBalance");
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

	public Criteria andBillIdIsNull() {
	    addCriterion("bill_id is null");
	    return this;
	}

	public Criteria andBillIdIsNotNull() {
	    addCriterion("bill_id is not null");
	    return this;
	}

	public Criteria andBillIdEqualTo(Long value) {
	    addCriterion("bill_id =", value, "billId");
	    return this;
	}

	public Criteria andBillIdNotEqualTo(Long value) {
	    addCriterion("bill_id <>", value, "billId");
	    return this;
	}

	public Criteria andBillIdGreaterThan(Long value) {
	    addCriterion("bill_id >", value, "billId");
	    return this;
	}

	public Criteria andBillIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("bill_id >=", value, "billId");
	    return this;
	}

	public Criteria andBillIdLessThan(Long value) {
	    addCriterion("bill_id <", value, "billId");
	    return this;
	}

	public Criteria andBillIdLessThanOrEqualTo(Long value) {
	    addCriterion("bill_id <=", value, "billId");
	    return this;
	}

	public Criteria andBillIdIn(List values) {
	    addCriterion("bill_id in", values, "billId");
	    return this;
	}

	public Criteria andBillTypeIsNull() {
	    addCriterion("bill_type is null");
	    return this;
	}

	public Criteria andBillTypeIsNotNull() {
	    addCriterion("bill_type is not null");
	    return this;
	}

	public Criteria andBillTypeEqualTo(Integer value) {
	    addCriterion("bill_type =", value, "billType");
	    return this;
	}

	public Criteria andBillTypeNotEqualTo(Integer value) {
	    addCriterion("bill_type <>", value, "billType");
	    return this;
	}

	public Criteria andBillTypeGreaterThan(Integer value) {
	    addCriterion("bill_type >", value, "billType");
	    return this;
	}

	public Criteria andBillTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("bill_type >=", value, "billType");
	    return this;
	}

	public Criteria andBillTypeLessThan(Integer value) {
	    addCriterion("bill_type <", value, "billType");
	    return this;
	}

	public Criteria andBillTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("bill_type <=", value, "billType");
	    return this;
	}

	public Criteria andBillTypeIn(List values) {
	    addCriterion("bill_type in", values, "billType");
	    return this;
	}

	public Criteria andBillTypeNotIn(List values) {
	    addCriterion("bill_type not in", values, "billType");
	    return this;
	}

	public Criteria andBillTypeBetween(Integer value1, Integer value2) {
	    addCriterion("bill_type between", value1, value2, "billType");
	    return this;
	}

	public Criteria andBillTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("bill_type not between", value1, value2, "billType");
	    return this;
	}

    }
}