package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AcctInfoExample {
    protected String orderByClause;
    protected List oredCriteria;

    public AcctInfoExample() {
	oredCriteria = new ArrayList();
    }

    protected AcctInfoExample(AcctInfoExample example) {
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

	public Criteria andLoginIdIsNull() {
	    addCriterion("login_id is null");
	    return this;
	}

	public Criteria andLoginIdIsNotNull() {
	    addCriterion("login_id is not null");
	    return this;
	}

	public Criteria andLoginIdEqualTo(String value) {
	    addCriterion("login_id =", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdNotEqualTo(String value) {
	    addCriterion("login_id <>", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdGreaterThan(String value) {
	    addCriterion("login_id >", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdGreaterThanOrEqualTo(String value) {
	    addCriterion("login_id >=", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdLessThan(String value) {
	    addCriterion("login_id <", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdLessThanOrEqualTo(String value) {
	    addCriterion("login_id <=", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdLike(String value) {
	    addCriterion("login_id like", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdNotLike(String value) {
	    addCriterion("login_id not like", value, "loginId");
	    return this;
	}

	public Criteria andLoginIdIn(List values) {
	    addCriterion("login_id in", values, "loginId");
	    return this;
	}

	public Criteria andLoginIdNotIn(List values) {
	    addCriterion("login_id not in", values, "loginId");
	    return this;
	}

	public Criteria andLoginIdBetween(String value1, String value2) {
	    addCriterion("login_id between", value1, value2, "loginId");
	    return this;
	}

	public Criteria andLoginIdNotBetween(String value1, String value2) {
	    addCriterion("login_id not between", value1, value2, "loginId");
	    return this;
	}

	public Criteria andBalanceIsNull() {
	    addCriterion("balance is null");
	    return this;
	}

	public Criteria andBalanceIsNotNull() {
	    addCriterion("balance is not null");
	    return this;
	}

	public Criteria andBalanceEqualTo(Long value) {
	    addCriterion("balance =", value, "balance");
	    return this;
	}

	public Criteria andBalanceNotEqualTo(Long value) {
	    addCriterion("balance <>", value, "balance");
	    return this;
	}

	public Criteria andBalanceGreaterThan(Long value) {
	    addCriterion("balance >", value, "balance");
	    return this;
	}

	public Criteria andBalanceGreaterThanOrEqualTo(Long value) {
	    addCriterion("balance >=", value, "balance");
	    return this;
	}

	public Criteria andBalanceLessThan(Long value) {
	    addCriterion("balance <", value, "balance");
	    return this;
	}

	public Criteria andBalanceLessThanOrEqualTo(Long value) {
	    addCriterion("balance <=", value, "balance");
	    return this;
	}

	public Criteria andBalanceIn(List values) {
	    addCriterion("balance in", values, "balance");
	    return this;
	}

	public Criteria andBalanceNotIn(List values) {
	    addCriterion("balance not in", values, "balance");
	    return this;
	}

	public Criteria andBalanceBetween(Long value1, Long value2) {
	    addCriterion("balance between", value1, value2, "balance");
	    return this;
	}

	public Criteria andBalanceNotBetween(Long value1, Long value2) {
	    addCriterion("balance not between", value1, value2, "balance");
	    return this;
	}

	public Criteria andLastTradeBalanceIsNull() {
	    addCriterion("last_trade_balance is null");
	    return this;
	}

	public Criteria andLastTradeBalanceIsNotNull() {
	    addCriterion("last_trade_balance is not null");
	    return this;
	}

	public Criteria andLastTradeBalanceEqualTo(Long value) {
	    addCriterion("last_trade_balance =", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceNotEqualTo(Long value) {
	    addCriterion("last_trade_balance <>", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceGreaterThan(Long value) {
	    addCriterion("last_trade_balance >", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceGreaterThanOrEqualTo(Long value) {
	    addCriterion("last_trade_balance >=", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceLessThan(Long value) {
	    addCriterion("last_trade_balance <", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceLessThanOrEqualTo(Long value) {
	    addCriterion("last_trade_balance <=", value, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceIn(List values) {
	    addCriterion("last_trade_balance in", values, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceNotIn(List values) {
	    addCriterion("last_trade_balance not in", values, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceBetween(Long value1, Long value2) {
	    addCriterion("last_trade_balance between", value1, value2, "lastTradeBalance");
	    return this;
	}

	public Criteria andLastTradeBalanceNotBetween(Long value1, Long value2) {
	    addCriterion("last_trade_balance not between", value1, value2, "lastTradeBalance");
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

	public Criteria andLastTradeDateIsNull() {
	    addCriterion("last_trade_date is null");
	    return this;
	}

	public Criteria andLastTradeDateIsNotNull() {
	    addCriterion("last_trade_date is not null");
	    return this;
	}

	public Criteria andLastTradeDateEqualTo(Date value) {
	    addCriterionForJDBCDate("last_trade_date =", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateNotEqualTo(Date value) {
	    addCriterionForJDBCDate("last_trade_date <>", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateGreaterThan(Date value) {
	    addCriterionForJDBCDate("last_trade_date >", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateGreaterThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("last_trade_date >=", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateLessThan(Date value) {
	    addCriterionForJDBCDate("last_trade_date <", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateLessThanOrEqualTo(Date value) {
	    addCriterionForJDBCDate("last_trade_date <=", value, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateIn(List values) {
	    addCriterionForJDBCDate("last_trade_date in", values, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateNotIn(List values) {
	    addCriterionForJDBCDate("last_trade_date not in", values, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("last_trade_date between", value1, value2, "lastTradeDate");
	    return this;
	}

	public Criteria andLastTradeDateNotBetween(Date value1, Date value2) {
	    addCriterionForJDBCDate("last_trade_date not between", value1, value2, "lastTradeDate");
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

	public Criteria andVerificationCodeIsNull() {
	    addCriterion("verification_code is null");
	    return this;
	}

	public Criteria andVerificationCodeIsNotNull() {
	    addCriterion("verification_code is not null");
	    return this;
	}

	public Criteria andVerificationCodeEqualTo(String value) {
	    addCriterion("verification_code =", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeNotEqualTo(String value) {
	    addCriterion("verification_code <>", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeGreaterThan(String value) {
	    addCriterion("verification_code >", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeGreaterThanOrEqualTo(String value) {
	    addCriterion("verification_code >=", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeLessThan(String value) {
	    addCriterion("verification_code <", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeLessThanOrEqualTo(String value) {
	    addCriterion("verification_code <=", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeLike(String value) {
	    addCriterion("verification_code like", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeNotLike(String value) {
	    addCriterion("verification_code not like", value, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeIn(List values) {
	    addCriterion("verification_code in", values, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeNotIn(List values) {
	    addCriterion("verification_code not in", values, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeBetween(String value1, String value2) {
	    addCriterion("verification_code between", value1, value2, "verificationCode");
	    return this;
	}

	public Criteria andVerificationCodeNotBetween(String value1, String value2) {
	    addCriterion("verification_code not between", value1, value2, "verificationCode");
	    return this;
	}

	public Criteria andSalesPriceIsNull() {
	    addCriterion("sales_price is null");
	    return this;
	}

	public Criteria andSalesPriceIsNotNull() {
	    addCriterion("sales_price is not null");
	    return this;
	}

	public Criteria andSalesPriceEqualTo(Integer value) {
	    addCriterion("sales_price =", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceNotEqualTo(Integer value) {
	    addCriterion("sales_price <>", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceGreaterThan(Integer value) {
	    addCriterion("sales_price >", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("sales_price >=", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceLessThan(Integer value) {
	    addCriterion("sales_price <", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceLessThanOrEqualTo(Integer value) {
	    addCriterion("sales_price <=", value, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceIn(List values) {
	    addCriterion("sales_price in", values, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceNotIn(List values) {
	    addCriterion("sales_price not in", values, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceBetween(Integer value1, Integer value2) {
	    addCriterion("sales_price between", value1, value2, "salesPrice");
	    return this;
	}

	public Criteria andSalesPriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("sales_price not between", value1, value2, "salesPrice");
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
    }
}