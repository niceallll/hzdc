package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockExample {
    protected String orderByClause;

    protected List oredCriteria;

    public StockExample() {
	oredCriteria = new ArrayList();
    }

    protected StockExample(StockExample example) {
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

	public Criteria andCardIdIsNull() {
	    addCriterion("card_id is null");
	    return this;
	}

	public Criteria andCardIdIsNotNull() {
	    addCriterion("card_id is not null");
	    return this;
	}

	public Criteria andCardIdEqualTo(String value) {
	    addCriterion("card_id =", value, "cardId");
	    return this;
	}

	public Criteria andCardIdNotEqualTo(String value) {
	    addCriterion("card_id <>", value, "cardId");
	    return this;
	}

	public Criteria andCardIdGreaterThan(String value) {
	    addCriterion("card_id >", value, "cardId");
	    return this;
	}

	public Criteria andCardIdGreaterThanOrEqualTo(String value) {
	    addCriterion("card_id >=", value, "cardId");
	    return this;
	}

	public Criteria andCardIdLessThan(String value) {
	    addCriterion("card_id <", value, "cardId");
	    return this;
	}

	public Criteria andCardIdLessThanOrEqualTo(String value) {
	    addCriterion("card_id <=", value, "cardId");
	    return this;
	}

	public Criteria andCardIdLike(String value) {
	    addCriterion("card_id like", value, "cardId");
	    return this;
	}

	public Criteria andCardIdNotLike(String value) {
	    addCriterion("card_id not like", value, "cardId");
	    return this;
	}

	public Criteria andCardIdIn(List values) {
	    addCriterion("card_id in", values, "cardId");
	    return this;
	}

	public Criteria andCardIdNotIn(List values) {
	    addCriterion("card_id not in", values, "cardId");
	    return this;
	}

	public Criteria andCardIdBetween(String value1, String value2) {
	    addCriterion("card_id between", value1, value2, "cardId");
	    return this;
	}

	public Criteria andCardIdNotBetween(String value1, String value2) {
	    addCriterion("card_id not between", value1, value2, "cardId");
	    return this;
	}

	public Criteria andCardPwdIsNull() {
	    addCriterion("card_pwd is null");
	    return this;
	}

	public Criteria andCardPwdIsNotNull() {
	    addCriterion("card_pwd is not null");
	    return this;
	}

	public Criteria andCardPwdEqualTo(String value) {
	    addCriterion("card_pwd =", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdNotEqualTo(String value) {
	    addCriterion("card_pwd <>", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdGreaterThan(String value) {
	    addCriterion("card_pwd >", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdGreaterThanOrEqualTo(String value) {
	    addCriterion("card_pwd >=", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdLessThan(String value) {
	    addCriterion("card_pwd <", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdLessThanOrEqualTo(String value) {
	    addCriterion("card_pwd <=", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdLike(String value) {
	    addCriterion("card_pwd like", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdNotLike(String value) {
	    addCriterion("card_pwd not like", value, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdIn(List values) {
	    addCriterion("card_pwd in", values, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdNotIn(List values) {
	    addCriterion("card_pwd not in", values, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdBetween(String value1, String value2) {
	    addCriterion("card_pwd between", value1, value2, "cardPwd");
	    return this;
	}

	public Criteria andCardPwdNotBetween(String value1, String value2) {
	    addCriterion("card_pwd not between", value1, value2, "cardPwd");
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

	public Criteria andInSerialnoIsNull() {
	    addCriterion("in_serialno is null");
	    return this;
	}

	public Criteria andInSerialnoIsNotNull() {
	    addCriterion("in_serialno is not null");
	    return this;
	}

	public Criteria andInSerialnoEqualTo(Long value) {
	    addCriterion("in_serialno =", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoNotEqualTo(Long value) {
	    addCriterion("in_serialno <>", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoGreaterThan(Long value) {
	    addCriterion("in_serialno >", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoGreaterThanOrEqualTo(Long value) {
	    addCriterion("in_serialno >=", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoLessThan(Long value) {
	    addCriterion("in_serialno <", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoLessThanOrEqualTo(Long value) {
	    addCriterion("in_serialno <=", value, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoIn(List values) {
	    addCriterion("in_serialno in", values, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoNotIn(List values) {
	    addCriterion("in_serialno not in", values, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoBetween(Long value1, Long value2) {
	    addCriterion("in_serialno between", value1, value2, "inSerialno");
	    return this;
	}

	public Criteria andInSerialnoNotBetween(Long value1, Long value2) {
	    addCriterion("in_serialno not between", value1, value2, "inSerialno");
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

	public Criteria andOutTimeIsNull() {
	    addCriterion("out_time is null");
	    return this;
	}

	public Criteria andOutTimeIsNotNull() {
	    addCriterion("out_time is not null");
	    return this;
	}

	public Criteria andOutTimeEqualTo(Date value) {
	    addCriterion("out_time =", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeNotEqualTo(Date value) {
	    addCriterion("out_time <>", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeGreaterThan(Date value) {
	    addCriterion("out_time >", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeGreaterThanOrEqualTo(Date value) {
	    addCriterion("out_time >=", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeLessThan(Date value) {
	    addCriterion("out_time <", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeLessThanOrEqualTo(Date value) {
	    addCriterion("out_time <=", value, "outTime");
	    return this;
	}

	public Criteria andOutTimeIn(List values) {
	    addCriterion("out_time in", values, "outTime");
	    return this;
	}

	public Criteria andOutTimeNotIn(List values) {
	    addCriterion("out_time not in", values, "outTime");
	    return this;
	}

	public Criteria andOutTimeBetween(Date value1, Date value2) {
	    addCriterion("out_time between", value1, value2, "outTime");
	    return this;
	}

	public Criteria andOutTimeNotBetween(Date value1, Date value2) {
	    addCriterion("out_time not between", value1, value2, "outTime");
	    return this;
	}

	public Criteria andErrorInfoIsNull() {
	    addCriterion("error_info is null");
	    return this;
	}

	public Criteria andErrorInfoIsNotNull() {
	    addCriterion("error_info is not null");
	    return this;
	}

	public Criteria andErrorInfoEqualTo(String value) {
	    addCriterion("error_info =", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoNotEqualTo(String value) {
	    addCriterion("error_info <>", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoGreaterThan(String value) {
	    addCriterion("error_info >", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoGreaterThanOrEqualTo(String value) {
	    addCriterion("error_info >=", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoLessThan(String value) {
	    addCriterion("error_info <", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoLessThanOrEqualTo(String value) {
	    addCriterion("error_info <=", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoLike(String value) {
	    addCriterion("error_info like", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoNotLike(String value) {
	    addCriterion("error_info not like", value, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoIn(List values) {
	    addCriterion("error_info in", values, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoNotIn(List values) {
	    addCriterion("error_info not in", values, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoBetween(String value1, String value2) {
	    addCriterion("error_info between", value1, value2, "errorInfo");
	    return this;
	}

	public Criteria andErrorInfoNotBetween(String value1, String value2) {
	    addCriterion("error_info not between", value1, value2, "errorInfo");
	    return this;
	}

	public Criteria andCardSerialNoIsNull() {
	    addCriterion("card_serial_no is null");
	    return this;
	}

	public Criteria andCardSerialNoIsNotNull() {
	    addCriterion("card_serial_no is not null");
	    return this;
	}

	public Criteria andCardSerialNoEqualTo(String value) {
	    addCriterion("card_serial_no =", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoNotEqualTo(String value) {
	    addCriterion("card_serial_no <>", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoGreaterThan(String value) {
	    addCriterion("card_serial_no >", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoGreaterThanOrEqualTo(String value) {
	    addCriterion("card_serial_no >=", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoLessThan(String value) {
	    addCriterion("card_serial_no <", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoLessThanOrEqualTo(String value) {
	    addCriterion("card_serial_no <=", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoLike(String value) {
	    addCriterion("card_serial_no like", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoNotLike(String value) {
	    addCriterion("card_serial_no not like", value, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoIn(List values) {
	    addCriterion("card_serial_no in", values, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoNotIn(List values) {
	    addCriterion("card_serial_no not in", values, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoBetween(String value1, String value2) {
	    addCriterion("card_serial_no between", value1, value2, "cardSerialNo");
	    return this;
	}

	public Criteria andCardSerialNoNotBetween(String value1, String value2) {
	    addCriterion("card_serial_no not between", value1, value2, "cardSerialNo");
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

	public Criteria andSupplyTraderIdIsNull() {
	    addCriterion("supply_trader_id is null");
	    return this;
	}

	public Criteria andSupplyTraderIdIsNotNull() {
	    addCriterion("supply_trader_id is not null");
	    return this;
	}

	public Criteria andSupplyTraderIdEqualTo(Long value) {
	    addCriterion("supply_trader_id =", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdNotEqualTo(Long value) {
	    addCriterion("supply_trader_id <>", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdGreaterThan(Long value) {
	    addCriterion("supply_trader_id >", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdGreaterThanOrEqualTo(Long value) {
	    addCriterion("supply_trader_id >=", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdLessThan(Long value) {
	    addCriterion("supply_trader_id <", value, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdLessThanOrEqualTo(Long value) {
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

	public Criteria andSupplyTraderIdBetween(Long value1, Long value2) {
	    addCriterion("supply_trader_id between", value1, value2, "supplyTraderId");
	    return this;
	}

	public Criteria andSupplyTraderIdNotBetween(Long value1, Long value2) {
	    addCriterion("supply_trader_id not between", value1, value2, "supplyTraderId");
	    return this;
	}

	public Criteria andOutSerialNoIsNull() {
	    addCriterion("out_serial_no is null");
	    return this;
	}

	public Criteria andOutSerialNoIsNotNull() {
	    addCriterion("out_serial_no is not null");
	    return this;
	}

	public Criteria andOutSerialNoEqualTo(Long value) {
	    addCriterion("out_serial_no =", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoNotEqualTo(Long value) {
	    addCriterion("out_serial_no <>", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoGreaterThan(Long value) {
	    addCriterion("out_serial_no >", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoGreaterThanOrEqualTo(Long value) {
	    addCriterion("out_serial_no >=", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoLessThan(Long value) {
	    addCriterion("out_serial_no <", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoLessThanOrEqualTo(Long value) {
	    addCriterion("out_serial_no <=", value, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoIn(List values) {
	    addCriterion("out_serial_no in", values, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoNotIn(List values) {
	    addCriterion("out_serial_no not in", values, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoBetween(Long value1, Long value2) {
	    addCriterion("out_serial_no between", value1, value2, "outSerialNo");
	    return this;
	}

	public Criteria andOutSerialNoNotBetween(Long value1, Long value2) {
	    addCriterion("out_serial_no not between", value1, value2, "outSerialNo");
	    return this;
	}
    }
}