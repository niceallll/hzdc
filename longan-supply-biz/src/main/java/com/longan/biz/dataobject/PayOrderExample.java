package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PayOrderExample {
    protected String orderByClause;
    protected List oredCriteria;

    public PayOrderExample() {
	oredCriteria = new ArrayList();
    }

    protected PayOrderExample(PayOrderExample example) {
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

	public Criteria andAcctDateIsNull() {
	    addCriterion("acct_date is null");
	    return this;
	}

	public Criteria andAcctDateIsNotNull() {
	    addCriterion("acct_date is not null");
	    return this;
	}

	public Criteria andAcctDateEqualTo(String value) {
	    addCriterion("acct_date =", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateNotEqualTo(String value) {
	    addCriterion("acct_date <>", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateGreaterThan(String value) {
	    addCriterion("acct_date >", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateGreaterThanOrEqualTo(String value) {
	    addCriterion("acct_date >=", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateLessThan(String value) {
	    addCriterion("acct_date <", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateLessThanOrEqualTo(String value) {
	    addCriterion("acct_date <=", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateLike(String value) {
	    addCriterion("acct_date like", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateNotLike(String value) {
	    addCriterion("acct_date not like", value, "acctDate");
	    return this;
	}

	public Criteria andAcctDateIn(List values) {
	    addCriterion("acct_date in", values, "acctDate");
	    return this;
	}

	public Criteria andAcctDateNotIn(List values) {
	    addCriterion("acct_date not in", values, "acctDate");
	    return this;
	}

	public Criteria andAcctDateBetween(String value1, String value2) {
	    addCriterion("acct_date between", value1, value2, "acctDate");
	    return this;
	}

	public Criteria andAcctDateNotBetween(String value1, String value2) {
	    addCriterion("acct_date not between", value1, value2, "acctDate");
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

	public Criteria andPayModeIsNull() {
	    addCriterion("pay_mode is null");
	    return this;
	}

	public Criteria andPayModeIsNotNull() {
	    addCriterion("pay_mode is not null");
	    return this;
	}

	public Criteria andPayModeEqualTo(Integer value) {
	    addCriterion("pay_mode =", value, "payMode");
	    return this;
	}

	public Criteria andPayModeNotEqualTo(Integer value) {
	    addCriterion("pay_mode <>", value, "payMode");
	    return this;
	}

	public Criteria andPayModeGreaterThan(Integer value) {
	    addCriterion("pay_mode >", value, "payMode");
	    return this;
	}

	public Criteria andPayModeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("pay_mode >=", value, "payMode");
	    return this;
	}

	public Criteria andPayModeLessThan(Integer value) {
	    addCriterion("pay_mode <", value, "payMode");
	    return this;
	}

	public Criteria andPayModeLessThanOrEqualTo(Integer value) {
	    addCriterion("pay_mode <=", value, "payMode");
	    return this;
	}

	public Criteria andPayModeIn(List values) {
	    addCriterion("pay_mode in", values, "payMode");
	    return this;
	}

	public Criteria andPayModeNotIn(List values) {
	    addCriterion("pay_mode not in", values, "payMode");
	    return this;
	}

	public Criteria andPayModeBetween(Integer value1, Integer value2) {
	    addCriterion("pay_mode between", value1, value2, "payMode");
	    return this;
	}

	public Criteria andPayModeNotBetween(Integer value1, Integer value2) {
	    addCriterion("pay_mode not between", value1, value2, "payMode");
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

	public Criteria andErrorMsgIsNull() {
	    addCriterion("error_msg is null");
	    return this;
	}

	public Criteria andErrorMsgIsNotNull() {
	    addCriterion("error_msg is not null");
	    return this;
	}

	public Criteria andErrorMsgEqualTo(String value) {
	    addCriterion("error_msg =", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgNotEqualTo(String value) {
	    addCriterion("error_msg <>", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgGreaterThan(String value) {
	    addCriterion("error_msg >", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
	    addCriterion("error_msg >=", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgLessThan(String value) {
	    addCriterion("error_msg <", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgLessThanOrEqualTo(String value) {
	    addCriterion("error_msg <=", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgLike(String value) {
	    addCriterion("error_msg like", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgNotLike(String value) {
	    addCriterion("error_msg not like", value, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgIn(List values) {
	    addCriterion("error_msg in", values, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgNotIn(List values) {
	    addCriterion("error_msg not in", values, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgBetween(String value1, String value2) {
	    addCriterion("error_msg between", value1, value2, "errorMsg");
	    return this;
	}

	public Criteria andErrorMsgNotBetween(String value1, String value2) {
	    addCriterion("error_msg not between", value1, value2, "errorMsg");
	    return this;
	}

	public Criteria andPayTypeIsNull() {
	    addCriterion("pay_type is null");
	    return this;
	}

	public Criteria andPayTypeIsNotNull() {
	    addCriterion("pay_type is not null");
	    return this;
	}

	public Criteria andPayTypeEqualTo(Integer value) {
	    addCriterion("pay_type =", value, "payType");
	    return this;
	}

	public Criteria andPayTypeNotEqualTo(Integer value) {
	    addCriterion("pay_type <>", value, "payType");
	    return this;
	}

	public Criteria andPayTypeGreaterThan(Integer value) {
	    addCriterion("pay_type >", value, "payType");
	    return this;
	}

	public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("pay_type >=", value, "payType");
	    return this;
	}

	public Criteria andPayTypeLessThan(Integer value) {
	    addCriterion("pay_type <", value, "payType");
	    return this;
	}

	public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("pay_type <=", value, "payType");
	    return this;
	}

	public Criteria andPayTypeIn(List values) {
	    addCriterion("pay_type in", values, "payType");
	    return this;
	}

	public Criteria andPayTypeNotIn(List values) {
	    addCriterion("pay_type not in", values, "payType");
	    return this;
	}

	public Criteria andPayTypeBetween(Integer value1, Integer value2) {
	    addCriterion("pay_type between", value1, value2, "payType");
	    return this;
	}

	public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("pay_type not between", value1, value2, "payType");
	    return this;
	}

	public Criteria andChannleIsNull() {
	    addCriterion("channle is null");
	    return this;
	}

	public Criteria andChannleIsNotNull() {
	    addCriterion("channle is not null");
	    return this;
	}

	public Criteria andChannleEqualTo(Integer value) {
	    addCriterion("channle =", value, "channle");
	    return this;
	}

	public Criteria andChannleNotEqualTo(Integer value) {
	    addCriterion("channle <>", value, "channle");
	    return this;
	}

	public Criteria andChannleGreaterThan(Integer value) {
	    addCriterion("channle >", value, "channle");
	    return this;
	}

	public Criteria andChannleGreaterThanOrEqualTo(Integer value) {
	    addCriterion("channle >=", value, "channle");
	    return this;
	}

	public Criteria andChannleLessThan(Integer value) {
	    addCriterion("channle <", value, "channle");
	    return this;
	}

	public Criteria andChannleLessThanOrEqualTo(Integer value) {
	    addCriterion("channle <=", value, "channle");
	    return this;
	}

	public Criteria andChannleIn(List values) {
	    addCriterion("channle in", values, "channle");
	    return this;
	}

	public Criteria andChannleNotIn(List values) {
	    addCriterion("channle not in", values, "channle");
	    return this;
	}

	public Criteria andChannleBetween(Integer value1, Integer value2) {
	    addCriterion("channle between", value1, value2, "channle");
	    return this;
	}

	public Criteria andChannleNotBetween(Integer value1, Integer value2) {
	    addCriterion("channle not between", value1, value2, "channle");
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

	public Criteria andItemIdIsNull() {
	    addCriterion("item_id is null");
	    return this;
	}

	public Criteria andItemIdIsNotNull() {
	    addCriterion("item_id is not null");
	    return this;
	}

	public Criteria andItemIdEqualTo(Long value) {
	    addCriterion("item_id =", value, "itemId");
	    return this;
	}

	public Criteria andItemIdNotEqualTo(Long value) {
	    addCriterion("item_id <>", value, "itemId");
	    return this;
	}

	public Criteria andItemIdGreaterThan(Long value) {
	    addCriterion("item_id >", value, "itemId");
	    return this;
	}

	public Criteria andItemIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("item_id >=", value, "itemId");
	    return this;
	}

	public Criteria andItemIdLessThan(Long value) {
	    addCriterion("item_id <", value, "itemId");
	    return this;
	}

	public Criteria andItemIdLessThanOrEqualTo(Long value) {
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

	public Criteria andItemIdBetween(Long value1, Long value2) {
	    addCriterion("item_id between", value1, value2, "itemId");
	    return this;
	}

	public Criteria andItemIdNotBetween(Long value1, Long value2) {
	    addCriterion("item_id not between", value1, value2, "itemId");
	    return this;
	}

	public Criteria andSupplyItemIdIsNull() {
	    addCriterion("supply_item_id is null");
	    return this;
	}

	public Criteria andSupplyItemIdIsNotNull() {
	    addCriterion("supply_item_id is not null");
	    return this;
	}

	public Criteria andSupplyItemIdEqualTo(Integer value) {
	    addCriterion("supply_item_id =", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdNotEqualTo(Integer value) {
	    addCriterion("supply_item_id <>", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdGreaterThan(Integer value) {
	    addCriterion("supply_item_id >", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_item_id >=", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdLessThan(Integer value) {
	    addCriterion("supply_item_id <", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_item_id <=", value, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdIn(List values) {
	    addCriterion("supply_item_id in", values, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdNotIn(List values) {
	    addCriterion("supply_item_id not in", values, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdBetween(Integer value1, Integer value2) {
	    addCriterion("supply_item_id between", value1, value2, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyItemIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_item_id not between", value1, value2, "supplyItemId");
	    return this;
	}

	public Criteria andSupplyTraderIdIsNull() {
	    addCriterion("supply_trader_id is null");
	    return this;
	}

	public Criteria andSupplyTraderIdIsNotNull() {
	    addCriterion("supply_trader_id is not null");
	    return this;
	}

	public Criteria andSupplyTraderIdEqualTo(Integer value) {
	    addCriterion("supply_trader_id =", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdNotEqualTo(Integer value) {
	    addCriterion("supply_trader_id <>", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdGreaterThan(Integer value) {
	    addCriterion("supply_trader_id >", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_trader_id >=", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdLessThan(Integer value) {
	    addCriterion("supply_trader_id <", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_trader_id <=", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdIn(List values) {
	    addCriterion("supply_trader_id in", values, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdNotIn(List values) {
	    addCriterion("supply_trader_id not in", values, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdBetween(Integer value1, Integer value2) {
	    addCriterion("supply_trader_id between", value1, value2, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_trader_id not between", value1, value2, "supplyTraderId");
	    return this;
	}

	public Criteria andAcctLogIdIsNull() {
	    addCriterion("acct_log_id is null");
	    return this;
	}

	public Criteria andAcctLogIdIsNotNull() {
	    addCriterion("acct_log_id is not null");
	    return this;
	}

	public Criteria andAcctLogIdEqualTo(Long value) {
	    addCriterion("acct_log_id =", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdNotEqualTo(Long value) {
	    addCriterion("acct_log_id <>", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdGreaterThan(Long value) {
	    addCriterion("acct_log_id >", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("acct_log_id >=", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdLessThan(Long value) {
	    addCriterion("acct_log_id <", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdLessThanOrEqualTo(Long value) {
	    addCriterion("acct_log_id <=", value, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdIn(List values) {
	    addCriterion("acct_log_id in", values, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdNotIn(List values) {
	    addCriterion("acct_log_id not in", values, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdBetween(Long value1, Long value2) {
	    addCriterion("acct_log_id between", value1, value2, "acctLogId");
	    return this;
	}

	public Criteria andAcctLogIdNotBetween(Long value1, Long value2) {
	    addCriterion("acct_log_id not between", value1, value2, "acctLogId");
	    return this;
	}

	public Criteria andBankNoIsNull() {
	    addCriterion("bank_no is null");
	    return this;
	}

	public Criteria andBankNoIsNotNull() {
	    addCriterion("bank_no is not null");
	    return this;
	}

	public Criteria andBankNoEqualTo(String value) {
	    addCriterion("bank_no =", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoNotEqualTo(String value) {
	    addCriterion("bank_no <>", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoGreaterThan(String value) {
	    addCriterion("bank_no >", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoGreaterThanOrEqualTo(String value) {
	    addCriterion("bank_no >=", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoLessThan(String value) {
	    addCriterion("bank_no <", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoLessThanOrEqualTo(String value) {
	    addCriterion("bank_no <=", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoLike(String value) {
	    addCriterion("bank_no like", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoNotLike(String value) {
	    addCriterion("bank_no not like", value, "bankNo");
	    return this;
	}

	public Criteria andBankNoIn(List values) {
	    addCriterion("bank_no in", values, "bankNo");
	    return this;
	}

	public Criteria andBankNoNotIn(List values) {
	    addCriterion("bank_no not in", values, "bankNo");
	    return this;
	}

	public Criteria andBankNoBetween(String value1, String value2) {
	    addCriterion("bank_no between", value1, value2, "bankNo");
	    return this;
	}

	public Criteria andBankNoNotBetween(String value1, String value2) {
	    addCriterion("bank_no not between", value1, value2, "bankNo");
	    return this;
	}
    }
}