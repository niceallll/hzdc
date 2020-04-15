package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockLogExample {
    protected String orderByClause;

    protected List oredCriteria;

    public StockLogExample() {
	oredCriteria = new ArrayList();
    }

    protected StockLogExample(StockLogExample example) {
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

	public Criteria andStockCountIsNull() {
	    addCriterion("stock_count is null");
	    return this;
	}

	public Criteria andStockCountIsNotNull() {
	    addCriterion("stock_count is not null");
	    return this;
	}

	public Criteria andStockCountEqualTo(Integer value) {
	    addCriterion("stock_count =", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountNotEqualTo(Integer value) {
	    addCriterion("stock_count <>", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountGreaterThan(Integer value) {
	    addCriterion("stock_count >", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountGreaterThanOrEqualTo(Integer value) {
	    addCriterion("stock_count >=", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountLessThan(Integer value) {
	    addCriterion("stock_count <", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountLessThanOrEqualTo(Integer value) {
	    addCriterion("stock_count <=", value, "stockCount");
	    return this;
	}

	public Criteria andStockCountIn(List values) {
	    addCriterion("stock_count in", values, "stockCount");
	    return this;
	}

	public Criteria andStockCountNotIn(List values) {
	    addCriterion("stock_count not in", values, "stockCount");
	    return this;
	}

	public Criteria andStockCountBetween(Integer value1, Integer value2) {
	    addCriterion("stock_count between", value1, value2, "stockCount");
	    return this;
	}

	public Criteria andStockCountNotBetween(Integer value1, Integer value2) {
	    addCriterion("stock_count not between", value1, value2, "stockCount");
	    return this;
	}

	public Criteria andTypeIsNull() {
	    addCriterion("type is null");
	    return this;
	}

	public Criteria andTypeIsNotNull() {
	    addCriterion("type is not null");
	    return this;
	}

	public Criteria andTypeEqualTo(Integer value) {
	    addCriterion("type =", value, "type");
	    return this;
	}

	public Criteria andTypeNotEqualTo(Integer value) {
	    addCriterion("type <>", value, "type");
	    return this;
	}

	public Criteria andTypeGreaterThan(Integer value) {
	    addCriterion("type >", value, "type");
	    return this;
	}

	public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("type >=", value, "type");
	    return this;
	}

	public Criteria andTypeLessThan(Integer value) {
	    addCriterion("type <", value, "type");
	    return this;
	}

	public Criteria andTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("type <=", value, "type");
	    return this;
	}

	public Criteria andTypeIn(List values) {
	    addCriterion("type in", values, "type");
	    return this;
	}

	public Criteria andTypeNotIn(List values) {
	    addCriterion("type not in", values, "type");
	    return this;
	}

	public Criteria andTypeBetween(Integer value1, Integer value2) {
	    addCriterion("type between", value1, value2, "type");
	    return this;
	}

	public Criteria andTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("type not between", value1, value2, "type");
	    return this;
	}

	public Criteria andStockMemoIsNull() {
	    addCriterion("stock_memo is null");
	    return this;
	}

	public Criteria andStockMemoIsNotNull() {
	    addCriterion("stock_memo is not null");
	    return this;
	}

	public Criteria andStockMemoEqualTo(String value) {
	    addCriterion("stock_memo =", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoNotEqualTo(String value) {
	    addCriterion("stock_memo <>", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoGreaterThan(String value) {
	    addCriterion("stock_memo >", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoGreaterThanOrEqualTo(String value) {
	    addCriterion("stock_memo >=", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoLessThan(String value) {
	    addCriterion("stock_memo <", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoLessThanOrEqualTo(String value) {
	    addCriterion("stock_memo <=", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoLike(String value) {
	    addCriterion("stock_memo like", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoNotLike(String value) {
	    addCriterion("stock_memo not like", value, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoIn(List values) {
	    addCriterion("stock_memo in", values, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoNotIn(List values) {
	    addCriterion("stock_memo not in", values, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoBetween(String value1, String value2) {
	    addCriterion("stock_memo between", value1, value2, "stockMemo");
	    return this;
	}

	public Criteria andStockMemoNotBetween(String value1, String value2) {
	    addCriterion("stock_memo not between", value1, value2, "stockMemo");
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
    }
}