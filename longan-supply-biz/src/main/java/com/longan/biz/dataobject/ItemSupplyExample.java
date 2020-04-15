package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSupplyExample {
    protected String orderByClause;

    protected List oredCriteria;

    public ItemSupplyExample() {
	oredCriteria = new ArrayList();
    }

    protected ItemSupplyExample(ItemSupplyExample example) {
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

	public Criteria andPriorityIsNull() {
	    addCriterion("priority is null");
	    return this;
	}

	public Criteria andPriorityIsNotNull() {
	    addCriterion("priority is not null");
	    return this;
	}

	public Criteria andPriorityEqualTo(Integer value) {
	    addCriterion("priority =", value, "priority");
	    return this;
	}

	public Criteria andPriorityNotEqualTo(Integer value) {
	    addCriterion("priority <>", value, "priority");
	    return this;
	}

	public Criteria andPriorityGreaterThan(Integer value) {
	    addCriterion("priority >", value, "priority");
	    return this;
	}

	public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
	    addCriterion("priority >=", value, "priority");
	    return this;
	}

	public Criteria andPriorityLessThan(Integer value) {
	    addCriterion("priority <", value, "priority");
	    return this;
	}

	public Criteria andPriorityLessThanOrEqualTo(Integer value) {
	    addCriterion("priority <=", value, "priority");
	    return this;
	}

	public Criteria andPriorityIn(List values) {
	    addCriterion("priority in", values, "priority");
	    return this;
	}

	public Criteria andPriorityNotIn(List values) {
	    addCriterion("priority not in", values, "priority");
	    return this;
	}

	public Criteria andPriorityBetween(Integer value1, Integer value2) {
	    addCriterion("priority between", value1, value2, "priority");
	    return this;
	}

	public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
	    addCriterion("priority not between", value1, value2, "priority");
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

	public Criteria andQuantityIsNull() {
	    addCriterion("quantity is null");
	    return this;
	}

	public Criteria andQuantityIsNotNull() {
	    addCriterion("quantity is not null");
	    return this;
	}

	public Criteria andQuantityEqualTo(Integer value) {
	    addCriterion("quantity =", value, "quantity");
	    return this;
	}

	public Criteria andQuantityNotEqualTo(Integer value) {
	    addCriterion("quantity <>", value, "quantity");
	    return this;
	}

	public Criteria andQuantityGreaterThan(Integer value) {
	    addCriterion("quantity >", value, "quantity");
	    return this;
	}

	public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
	    addCriterion("quantity >=", value, "quantity");
	    return this;
	}

	public Criteria andQuantityLessThan(Integer value) {
	    addCriterion("quantity <", value, "quantity");
	    return this;
	}

	public Criteria andQuantityLessThanOrEqualTo(Integer value) {
	    addCriterion("quantity <=", value, "quantity");
	    return this;
	}

	public Criteria andQuantityIn(List values) {
	    addCriterion("quantity in", values, "quantity");
	    return this;
	}

	public Criteria andQuantityNotIn(List values) {
	    addCriterion("quantity not in", values, "quantity");
	    return this;
	}

	public Criteria andQuantityBetween(Integer value1, Integer value2) {
	    addCriterion("quantity between", value1, value2, "quantity");
	    return this;
	}

	public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
	    addCriterion("quantity not between", value1, value2, "quantity");
	    return this;
	}

	public Criteria andWarnQuantityIsNull() {
	    addCriterion("warn_quantity is null");
	    return this;
	}

	public Criteria andWarnQuantityIsNotNull() {
	    addCriterion("warn_quantity is not null");
	    return this;
	}

	public Criteria andWarnQuantityEqualTo(Integer value) {
	    addCriterion("warn_quantity =", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityNotEqualTo(Integer value) {
	    addCriterion("warn_quantity <>", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityGreaterThan(Integer value) {
	    addCriterion("warn_quantity >", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityGreaterThanOrEqualTo(Integer value) {
	    addCriterion("warn_quantity >=", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityLessThan(Integer value) {
	    addCriterion("warn_quantity <", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityLessThanOrEqualTo(Integer value) {
	    addCriterion("warn_quantity <=", value, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityIn(List values) {
	    addCriterion("warn_quantity in", values, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityNotIn(List values) {
	    addCriterion("warn_quantity not in", values, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityBetween(Integer value1, Integer value2) {
	    addCriterion("warn_quantity between", value1, value2, "warnQuantity");
	    return this;
	}

	public Criteria andWarnQuantityNotBetween(Integer value1, Integer value2) {
	    addCriterion("warn_quantity not between", value1, value2, "warnQuantity");
	    return this;
	}

	public Criteria andMaxDayIsNull() {
	    addCriterion("max_day is null");
	    return this;
	}

	public Criteria andMaxDayIsNotNull() {
	    addCriterion("max_day is not null");
	    return this;
	}

	public Criteria andMaxDayEqualTo(Integer value) {
	    addCriterion("max_day =", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayNotEqualTo(Integer value) {
	    addCriterion("max_day <>", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayGreaterThan(Integer value) {
	    addCriterion("max_day >", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayGreaterThanOrEqualTo(Integer value) {
	    addCriterion("max_day >=", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayLessThan(Integer value) {
	    addCriterion("max_day <", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayLessThanOrEqualTo(Integer value) {
	    addCriterion("max_day <=", value, "maxDay");
	    return this;
	}

	public Criteria andMaxDayIn(List values) {
	    addCriterion("max_day in", values, "maxDay");
	    return this;
	}

	public Criteria andMaxDayNotIn(List values) {
	    addCriterion("max_day not in", values, "maxDay");
	    return this;
	}

	public Criteria andMaxDayBetween(Integer value1, Integer value2) {
	    addCriterion("max_day between", value1, value2, "maxDay");
	    return this;
	}

	public Criteria andMaxDayNotBetween(Integer value1, Integer value2) {
	    addCriterion("max_day not between", value1, value2, "maxDay");
	    return this;
	}

	public Criteria andNumDayIsNull() {
	    addCriterion("num_day is null");
	    return this;
	}

	public Criteria andNumDayIsNotNull() {
	    addCriterion("num_day is not null");
	    return this;
	}

	public Criteria andNumDayEqualTo(Integer value) {
	    addCriterion("num_day =", value, "numDay");
	    return this;
	}

	public Criteria andNumDayNotEqualTo(Integer value) {
	    addCriterion("num_day <>", value, "numDay");
	    return this;
	}

	public Criteria andNumDayGreaterThan(Integer value) {
	    addCriterion("num_day >", value, "numDay");
	    return this;
	}

	public Criteria andNumDayGreaterThanOrEqualTo(Integer value) {
	    addCriterion("num_day >=", value, "numDay");
	    return this;
	}

	public Criteria andNumDayLessThan(Integer value) {
	    addCriterion("num_day <", value, "numDay");
	    return this;
	}

	public Criteria andNumDayLessThanOrEqualTo(Integer value) {
	    addCriterion("num_day <=", value, "numDay");
	    return this;
	}

	public Criteria andNumDayIn(List values) {
	    addCriterion("num_day in", values, "numDay");
	    return this;
	}

	public Criteria andNumDayNotIn(List values) {
	    addCriterion("num_day not in", values, "numDay");
	    return this;
	}

	public Criteria andNumDayBetween(Integer value1, Integer value2) {
	    addCriterion("num_day between", value1, value2, "numDay");
	    return this;
	}

	public Criteria andNumDayNotBetween(Integer value1, Integer value2) {
	    addCriterion("num_day not between", value1, value2, "numDay");
	    return this;
	}

	public Criteria andMaxMounthIsNull() {
	    addCriterion("max_mounth is null");
	    return this;
	}

	public Criteria andMaxMounthIsNotNull() {
	    addCriterion("max_mounth is not null");
	    return this;
	}

	public Criteria andMaxMounthEqualTo(Integer value) {
	    addCriterion("max_mounth =", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthNotEqualTo(Integer value) {
	    addCriterion("max_mounth <>", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthGreaterThan(Integer value) {
	    addCriterion("max_mounth >", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthGreaterThanOrEqualTo(Integer value) {
	    addCriterion("max_mounth >=", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthLessThan(Integer value) {
	    addCriterion("max_mounth <", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthLessThanOrEqualTo(Integer value) {
	    addCriterion("max_mounth <=", value, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthIn(List values) {
	    addCriterion("max_mounth in", values, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthNotIn(List values) {
	    addCriterion("max_mounth not in", values, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthBetween(Integer value1, Integer value2) {
	    addCriterion("max_mounth between", value1, value2, "maxMounth");
	    return this;
	}

	public Criteria andMaxMounthNotBetween(Integer value1, Integer value2) {
	    addCriterion("max_mounth not between", value1, value2, "maxMounth");
	    return this;
	}

	public Criteria andNumMounthIsNull() {
	    addCriterion("num_mounth is null");
	    return this;
	}

	public Criteria andNumMounthIsNotNull() {
	    addCriterion("num_mounth is not null");
	    return this;
	}

	public Criteria andNumMounthEqualTo(Integer value) {
	    addCriterion("num_mounth =", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthNotEqualTo(Integer value) {
	    addCriterion("num_mounth <>", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthGreaterThan(Integer value) {
	    addCriterion("num_mounth >", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthGreaterThanOrEqualTo(Integer value) {
	    addCriterion("num_mounth >=", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthLessThan(Integer value) {
	    addCriterion("num_mounth <", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthLessThanOrEqualTo(Integer value) {
	    addCriterion("num_mounth <=", value, "numMounth");
	    return this;
	}

	public Criteria andNumMounthIn(List values) {
	    addCriterion("num_mounth in", values, "numMounth");
	    return this;
	}

	public Criteria andNumMounthNotIn(List values) {
	    addCriterion("num_mounth not in", values, "numMounth");
	    return this;
	}

	public Criteria andNumMounthBetween(Integer value1, Integer value2) {
	    addCriterion("num_mounth between", value1, value2, "numMounth");
	    return this;
	}

	public Criteria andNumMounthNotBetween(Integer value1, Integer value2) {
	    addCriterion("num_mounth not between", value1, value2, "numMounth");
	    return this;
	}

	public Criteria andSupplyProductCodeIsNull() {
	    addCriterion("supply_product_code is null");
	    return this;
	}

	public Criteria andSupplyProductCodeIsNotNull() {
	    addCriterion("supply_product_code is not null");
	    return this;
	}

	public Criteria andSupplyProductCodeEqualTo(String value) {
	    addCriterion("supply_product_code =", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeNotEqualTo(String value) {
	    addCriterion("supply_product_code <>", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeGreaterThan(String value) {
	    addCriterion("supply_product_code >", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeGreaterThanOrEqualTo(String value) {
	    addCriterion("supply_product_code >=", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeLessThan(String value) {
	    addCriterion("supply_product_code <", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeLessThanOrEqualTo(String value) {
	    addCriterion("supply_product_code <=", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeLike(String value) {
	    addCriterion("supply_product_code like", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeNotLike(String value) {
	    addCriterion("supply_product_code not like", value, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeIn(List values) {
	    addCriterion("supply_product_code in", values, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeNotIn(List values) {
	    addCriterion("supply_product_code not in", values, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeBetween(String value1, String value2) {
	    addCriterion("supply_product_code between", value1, value2, "supplyProductCode");
	    return this;
	}

	public Criteria andSupplyProductCodeNotBetween(String value1, String value2) {
	    addCriterion("supply_product_code not between", value1, value2, "supplyProductCode");
	    return this;
	}

	public Criteria andLossTypeIsNull() {
	    addCriterion("loss_type is null");
	    return this;
	}

	public Criteria andLossTypeIsNotNull() {
	    addCriterion("loss_type is not null");
	    return this;
	}

	public Criteria andLossTypeEqualTo(Integer value) {
	    addCriterion("loss_type =", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeNotEqualTo(Integer value) {
	    addCriterion("loss_type <>", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeGreaterThan(Integer value) {
	    addCriterion("loss_type >", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("loss_type >=", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeLessThan(Integer value) {
	    addCriterion("loss_type <", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("loss_type <=", value, "lossType");
	    return this;
	}

	public Criteria andLossTypeIn(List values) {
	    addCriterion("loss_type in", values, "lossType");
	    return this;
	}

	public Criteria andLossTypeNotIn(List values) {
	    addCriterion("loss_type not in", values, "lossType");
	    return this;
	}

	public Criteria andLossTypeBetween(Integer value1, Integer value2) {
	    addCriterion("loss_type between", value1, value2, "lossType");
	    return this;
	}

	public Criteria andLossTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("loss_type not between", value1, value2, "lossType");
	    return this;
	}

	public Criteria andLossTimeIsNull() {
	    addCriterion("loss_time is null");
	    return this;
	}

	public Criteria andLossTimeIsNotNull() {
	    addCriterion("loss_time is not null");
	    return this;
	}

	public Criteria andLossTimeEqualTo(Integer value) {
	    addCriterion("loss_time =", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeNotEqualTo(Integer value) {
	    addCriterion("loss_time <>", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeGreaterThan(Integer value) {
	    addCriterion("loss_time >", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("loss_time >=", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeLessThan(Integer value) {
	    addCriterion("loss_time <", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeLessThanOrEqualTo(Integer value) {
	    addCriterion("loss_time <=", value, "lossTime");
	    return this;
	}

	public Criteria andLossTimeIn(List values) {
	    addCriterion("loss_time in", values, "lossTime");
	    return this;
	}

	public Criteria andLossTimeNotIn(List values) {
	    addCriterion("loss_time not in", values, "lossTime");
	    return this;
	}

	public Criteria andLossTimeBetween(Integer value1, Integer value2) {
	    addCriterion("loss_time between", value1, value2, "lossTime");
	    return this;
	}

	public Criteria andLossTimeNotBetween(Integer value1, Integer value2) {
	    addCriterion("loss_time not between", value1, value2, "lossTime");
	    return this;
	}
    }
}