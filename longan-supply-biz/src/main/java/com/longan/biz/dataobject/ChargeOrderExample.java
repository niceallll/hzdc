package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeOrderExample {
    protected String orderByClause;

    protected List oredCriteria;

    public ChargeOrderExample() {
	oredCriteria = new ArrayList();
    }

    protected ChargeOrderExample(ChargeOrderExample example) {
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

	public Criteria andOperIdIsNull() {
	    addCriterion("oper_id is null");
	    return this;
	}

	public Criteria andOperIdIsNotNull() {
	    addCriterion("oper_id is not null");
	    return this;
	}

	public Criteria andOperIdEqualTo(Integer value) {
	    addCriterion("oper_id =", value, "operId");
	    return this;
	}

	public Criteria andOperIdNotEqualTo(Integer value) {
	    addCriterion("oper_id <>", value, "operId");
	    return this;
	}

	public Criteria andOperIdGreaterThan(Integer value) {
	    addCriterion("oper_id >", value, "operId");
	    return this;
	}

	public Criteria andOperIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("oper_id >=", value, "operId");
	    return this;
	}

	public Criteria andOperIdLessThan(Integer value) {
	    addCriterion("oper_id <", value, "operId");
	    return this;
	}

	public Criteria andOperIdLessThanOrEqualTo(Integer value) {
	    addCriterion("oper_id <=", value, "operId");
	    return this;
	}

	public Criteria andOperIdIn(List values) {
	    addCriterion("oper_id in", values, "operId");
	    return this;
	}

	public Criteria andOperIdNotIn(List values) {
	    addCriterion("oper_id not in", values, "operId");
	    return this;
	}

	public Criteria andOperIdBetween(Integer value1, Integer value2) {
	    addCriterion("oper_id between", value1, value2, "operId");
	    return this;
	}

	public Criteria andOperIdNotBetween(Integer value1, Integer value2) {
	    addCriterion("oper_id not between", value1, value2, "operId");
	    return this;
	}

	public Criteria andOperNameIsNull() {
	    addCriterion("oper_name is null");
	    return this;
	}

	public Criteria andOperNameIsNotNull() {
	    addCriterion("oper_name is not null");
	    return this;
	}

	public Criteria andOperNameEqualTo(String value) {
	    addCriterion("oper_name =", value, "operName");
	    return this;
	}

	public Criteria andOperNameNotEqualTo(String value) {
	    addCriterion("oper_name <>", value, "operName");
	    return this;
	}

	public Criteria andOperNameGreaterThan(String value) {
	    addCriterion("oper_name >", value, "operName");
	    return this;
	}

	public Criteria andOperNameGreaterThanOrEqualTo(String value) {
	    addCriterion("oper_name >=", value, "operName");
	    return this;
	}

	public Criteria andOperNameLessThan(String value) {
	    addCriterion("oper_name <", value, "operName");
	    return this;
	}

	public Criteria andOperNameLessThanOrEqualTo(String value) {
	    addCriterion("oper_name <=", value, "operName");
	    return this;
	}

	public Criteria andOperNameLike(String value) {
	    addCriterion("oper_name like", value, "operName");
	    return this;
	}

	public Criteria andOperNameNotLike(String value) {
	    addCriterion("oper_name not like", value, "operName");
	    return this;
	}

	public Criteria andOperNameIn(List values) {
	    addCriterion("oper_name in", values, "operName");
	    return this;
	}

	public Criteria andOperNameNotIn(List values) {
	    addCriterion("oper_name not in", values, "operName");
	    return this;
	}

	public Criteria andOperNameBetween(String value1, String value2) {
	    addCriterion("oper_name between", value1, value2, "operName");
	    return this;
	}

	public Criteria andOperNameNotBetween(String value1, String value2) {
	    addCriterion("oper_name not between", value1, value2, "operName");
	    return this;
	}

	public Criteria andVerifyOperNameIsNull() {
	    addCriterion("verify_oper_name is null");
	    return this;
	}

	public Criteria andVerifyOperNameIsNotNull() {
	    addCriterion("verify_oper_name is not null");
	    return this;
	}

	public Criteria andVerifyOperNameEqualTo(String value) {
	    addCriterion("verify_oper_name =", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameNotEqualTo(String value) {
	    addCriterion("verify_oper_name <>", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameGreaterThan(String value) {
	    addCriterion("verify_oper_name >", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameGreaterThanOrEqualTo(String value) {
	    addCriterion("verify_oper_name >=", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameLessThan(String value) {
	    addCriterion("verify_oper_name <", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameLessThanOrEqualTo(String value) {
	    addCriterion("verify_oper_name <=", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameLike(String value) {
	    addCriterion("verify_oper_name like", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameNotLike(String value) {
	    addCriterion("verify_oper_name not like", value, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameIn(List values) {
	    addCriterion("verify_oper_name in", values, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameNotIn(List values) {
	    addCriterion("verify_oper_name not in", values, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameBetween(String value1, String value2) {
	    addCriterion("verify_oper_name between", value1, value2, "verifyOperName");
	    return this;
	}

	public Criteria andVerifyOperNameNotBetween(String value1, String value2) {
	    addCriterion("verify_oper_name not between", value1, value2, "verifyOperName");
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

	public Criteria andBankSerialnoIsNull() {
	    addCriterion("bank_serialno is null");
	    return this;
	}

	public Criteria andBankSerialnoIsNotNull() {
	    addCriterion("bank_serialno is not null");
	    return this;
	}

	public Criteria andBankSerialnoEqualTo(String value) {
	    addCriterion("bank_serialno =", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoNotEqualTo(String value) {
	    addCriterion("bank_serialno <>", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoGreaterThan(String value) {
	    addCriterion("bank_serialno >", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoGreaterThanOrEqualTo(String value) {
	    addCriterion("bank_serialno >=", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoLessThan(String value) {
	    addCriterion("bank_serialno <", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoLessThanOrEqualTo(String value) {
	    addCriterion("bank_serialno <=", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoLike(String value) {
	    addCriterion("bank_serialno like", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoNotLike(String value) {
	    addCriterion("bank_serialno not like", value, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoIn(List values) {
	    addCriterion("bank_serialno in", values, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoNotIn(List values) {
	    addCriterion("bank_serialno not in", values, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoBetween(String value1, String value2) {
	    addCriterion("bank_serialno between", value1, value2, "bankSerialno");
	    return this;
	}

	public Criteria andBankSerialnoNotBetween(String value1, String value2) {
	    addCriterion("bank_serialno not between", value1, value2, "bankSerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoIsNull() {
	    addCriterion("alipay_serialno is null");
	    return this;
	}

	public Criteria andAlipaySerialnoIsNotNull() {
	    addCriterion("alipay_serialno is not null");
	    return this;
	}

	public Criteria andAlipaySerialnoEqualTo(String value) {
	    addCriterion("alipay_serialno =", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoNotEqualTo(String value) {
	    addCriterion("alipay_serialno <>", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoGreaterThan(String value) {
	    addCriterion("alipay_serialno >", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoGreaterThanOrEqualTo(String value) {
	    addCriterion("alipay_serialno >=", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoLessThan(String value) {
	    addCriterion("alipay_serialno <", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoLessThanOrEqualTo(String value) {
	    addCriterion("alipay_serialno <=", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoLike(String value) {
	    addCriterion("alipay_serialno like", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoNotLike(String value) {
	    addCriterion("alipay_serialno not like", value, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoIn(List values) {
	    addCriterion("alipay_serialno in", values, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoNotIn(List values) {
	    addCriterion("alipay_serialno not in", values, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoBetween(String value1, String value2) {
	    addCriterion("alipay_serialno between", value1, value2, "alipaySerialno");
	    return this;
	}

	public Criteria andAlipaySerialnoNotBetween(String value1, String value2) {
	    addCriterion("alipay_serialno not between", value1, value2, "alipaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoIsNull() {
	    addCriterion("tenpay_serialno is null");
	    return this;
	}

	public Criteria andTenpaySerialnoIsNotNull() {
	    addCriterion("tenpay_serialno is not null");
	    return this;
	}

	public Criteria andTenpaySerialnoEqualTo(String value) {
	    addCriterion("tenpay_serialno =", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoNotEqualTo(String value) {
	    addCriterion("tenpay_serialno <>", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoGreaterThan(String value) {
	    addCriterion("tenpay_serialno >", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoGreaterThanOrEqualTo(String value) {
	    addCriterion("tenpay_serialno >=", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoLessThan(String value) {
	    addCriterion("tenpay_serialno <", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoLessThanOrEqualTo(String value) {
	    addCriterion("tenpay_serialno <=", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoLike(String value) {
	    addCriterion("tenpay_serialno like", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoNotLike(String value) {
	    addCriterion("tenpay_serialno not like", value, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoIn(List values) {
	    addCriterion("tenpay_serialno in", values, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoNotIn(List values) {
	    addCriterion("tenpay_serialno not in", values, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoBetween(String value1, String value2) {
	    addCriterion("tenpay_serialno between", value1, value2, "tenpaySerialno");
	    return this;
	}

	public Criteria andTenpaySerialnoNotBetween(String value1, String value2) {
	    addCriterion("tenpay_serialno not between", value1, value2, "tenpaySerialno");
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
    }
}