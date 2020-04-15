package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplyBatchExample {
    protected String orderByClause;
    protected List oredCriteria;

    public SupplyBatchExample() {
	oredCriteria = new ArrayList();
    }

    public SupplyBatchExample(SupplyBatchExample example) {
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

	public Criteria andUpstreamIdIsNull() {
	    addCriterion("upstream_id is null");
	    return this;
	}

	public Criteria andUpstreamIdIsNotNull() {
	    addCriterion("upstream_id is not null");
	    return this;
	}

	public Criteria andUpstreamIdEqualTo(Long value) {
	    addCriterion("upstream_id =", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotEqualTo(Long value) {
	    addCriterion("upstream_id <>", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdGreaterThan(Long value) {
	    addCriterion("upstream_id >", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("upstream_id >=", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLessThan(Long value) {
	    addCriterion("upstream_id <", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLessThanOrEqualTo(Long value) {
	    addCriterion("upstream_id <=", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdLike(Long value) {
	    addCriterion("upstream_id like", value, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotLike(Long value) {
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

	public Criteria andUpstreamIdBetween(Long value1, Long value2) {
	    addCriterion("upstream_id between", value1, value2, "upstreamId");
	    return this;
	}

	public Criteria andUpstreamIdNotBetween(Long value1, Long value2) {
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

	public Criteria andOperIdIsNull() {
	    addCriterion("oper_id is null");
	    return this;
	}

	public Criteria andOperIdIsNotNull() {
	    addCriterion("oper_id is not null");
	    return this;
	}

	public Criteria andOperIdEqualTo(Long value) {
	    addCriterion("oper_id =", value, "operId");
	    return this;
	}

	public Criteria andOperIdNotEqualTo(Long value) {
	    addCriterion("oper_id <>", value, "operId");
	    return this;
	}

	public Criteria andOperIdGreaterThan(Long value) {
	    addCriterion("oper_id >", value, "operId");
	    return this;
	}

	public Criteria andOperIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("oper_id >=", value, "operId");
	    return this;
	}

	public Criteria andOperIdLessThan(Long value) {
	    addCriterion("oper_id <", value, "operId");
	    return this;
	}

	public Criteria andOperIdLessThanOrEqualTo(Long value) {
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

	public Criteria andOperIdBetween(Long value1, Long value2) {
	    addCriterion("oper_id between", value1, value2, "operId");
	    return this;
	}

	public Criteria andOperIdNotBetween(Long value1, Long value2) {
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

	public Criteria andVerifyOperIdIsNull() {
	    addCriterion("verify_oper_id is null");
	    return this;
	}

	public Criteria andVerifyOperIdIsNotNull() {
	    addCriterion("verify_oper_id is not null");
	    return this;
	}

	public Criteria andVerifyOperIdEqualTo(Long value) {
	    addCriterion("verify_oper_id =", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdNotEqualTo(Long value) {
	    addCriterion("verify_oper_id <>", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdGreaterThan(Long value) {
	    addCriterion("verify_oper_id >", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("verify_oper_id >=", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdLessThan(Long value) {
	    addCriterion("verify_oper_id <", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdLessThanOrEqualTo(Long value) {
	    addCriterion("verify_oper_id <=", value, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdIn(List values) {
	    addCriterion("verify_oper_id in", values, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdNotIn(List values) {
	    addCriterion("verify_oper_id not in", values, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdBetween(Long value1, Long value2) {
	    addCriterion("verify_oper_id between", value1, value2, "verifyOperId");
	    return this;
	}

	public Criteria andVerifyOperIdNotBetween(Long value1, Long value2) {
	    addCriterion("verify_oper_id not between", value1, value2, "verifyOperId");
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

	public Criteria andFileNameIsNull() {
	    addCriterion("file_name is null");
	    return this;
	}

	public Criteria andFileNameIsNotNull() {
	    addCriterion("file_name is not null");
	    return this;
	}

	public Criteria andFileNameEqualTo(String value) {
	    addCriterion("file_name =", value, "fileName");
	    return this;
	}

	public Criteria andFileNameNotEqualTo(String value) {
	    addCriterion("file_name <>", value, "fileName");
	    return this;
	}

	public Criteria andFileNameGreaterThan(String value) {
	    addCriterion("file_name >", value, "fileName");
	    return this;
	}

	public Criteria andFileNameGreaterThanOrEqualTo(String value) {
	    addCriterion("file_name >=", value, "fileName");
	    return this;
	}

	public Criteria andFileNameLessThan(String value) {
	    addCriterion("file_name <", value, "fileName");
	    return this;
	}

	public Criteria andFileNameLessThanOrEqualTo(String value) {
	    addCriterion("file_name <=", value, "fileName");
	    return this;
	}

	public Criteria andFileNameLike(String value) {
	    addCriterion("file_name like", value, "fileName");
	    return this;
	}

	public Criteria andFileNameNotLike(String value) {
	    addCriterion("file_name not like", value, "fileName");
	    return this;
	}

	public Criteria andFileNameIn(List values) {
	    addCriterion("file_name in", values, "fileName");
	    return this;
	}

	public Criteria andFileNameNotIn(List values) {
	    addCriterion("file_name not in", values, "fileName");
	    return this;
	}

	public Criteria andFileNameBetween(String value1, String value2) {
	    addCriterion("file_name between", value1, value2, "fileName");
	    return this;
	}

	public Criteria andFileNameNotBetween(String value1, String value2) {
	    addCriterion("file_name not between", value1, value2, "fileName");
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

	public Criteria andResultIsNull() {
	    addCriterion("result is null");
	    return this;
	}

	public Criteria andResultIsNotNull() {
	    addCriterion("result is not null");
	    return this;
	}

	public Criteria andResultEqualTo(Integer value) {
	    addCriterion("result =", value, "result");
	    return this;
	}

	public Criteria andResultNotEqualTo(Integer value) {
	    addCriterion("result <>", value, "result");
	    return this;
	}

	public Criteria andResultGreaterThan(Integer value) {
	    addCriterion("result >", value, "result");
	    return this;
	}

	public Criteria andResultGreaterThanOrEqualTo(Integer value) {
	    addCriterion("result >=", value, "result");
	    return this;
	}

	public Criteria andResultLessThan(Integer value) {
	    addCriterion("result <", value, "result");
	    return this;
	}

	public Criteria andResultLessThanOrEqualTo(Integer value) {
	    addCriterion("result <=", value, "result");
	    return this;
	}

	public Criteria andResultIn(List values) {
	    addCriterion("result in", values, "result");
	    return this;
	}

	public Criteria andResultNotIn(List values) {
	    addCriterion("result not in", values, "result");
	    return this;
	}

	public Criteria andResultBetween(Integer value1, Integer value2) {
	    addCriterion("result between", value1, value2, "result");
	    return this;
	}

	public Criteria andResultNotBetween(Integer value1, Integer value2) {
	    addCriterion("result not between", value1, value2, "result");
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
