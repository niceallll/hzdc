package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.longan.biz.dataobject.BizOrderExample.Criteria;

public class SmsOrderExample {
    protected String orderByClause;
    protected List oredCriteria;

    public SmsOrderExample() {
	oredCriteria = new ArrayList();
    }

    public SmsOrderExample(SmsOrderExample example) {
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
