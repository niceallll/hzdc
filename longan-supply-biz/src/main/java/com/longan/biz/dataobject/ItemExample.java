package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemExample {
    protected String orderByClause;

    protected List oredCriteria;

    public ItemExample() {
	oredCriteria = new ArrayList();
    }

    protected ItemExample(ItemExample example) {
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

	public Criteria andIdEqualTo(Integer value) {
	    addCriterion("id =", value, "id");
	    return this;
	}

	public Criteria andIdNotEqualTo(Integer value) {
	    addCriterion("id <>", value, "id");
	    return this;
	}

	public Criteria andIdGreaterThan(Integer value) {
	    addCriterion("id >", value, "id");
	    return this;
	}

	public Criteria andIdGreaterThanOrEqualTo(Integer value) {
	    addCriterion("id >=", value, "id");
	    return this;
	}

	public Criteria andIdLessThan(Integer value) {
	    addCriterion("id <", value, "id");
	    return this;
	}

	public Criteria andIdLessThanOrEqualTo(Integer value) {
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

	public Criteria andIdBetween(Integer value1, Integer value2) {
	    addCriterion("id between", value1, value2, "id");
	    return this;
	}

	public Criteria andIdNotBetween(Integer value1, Integer value2) {
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

	public Criteria andItemUnitIsNull() {
	    addCriterion("item_unit is null");
	    return this;
	}

	public Criteria andItemUnitIsNotNull() {
	    addCriterion("item_unit is not null");
	    return this;
	}

	public Criteria andItemUnitEqualTo(String value) {
	    addCriterion("item_unit =", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitNotEqualTo(String value) {
	    addCriterion("item_unit <>", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitGreaterThan(String value) {
	    addCriterion("item_unit >", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitGreaterThanOrEqualTo(String value) {
	    addCriterion("item_unit >=", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitLessThan(String value) {
	    addCriterion("item_unit <", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitLessThanOrEqualTo(String value) {
	    addCriterion("item_unit <=", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitLike(String value) {
	    addCriterion("item_unit like", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitNotLike(String value) {
	    addCriterion("item_unit not like", value, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitIn(List values) {
	    addCriterion("item_unit in", values, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitNotIn(List values) {
	    addCriterion("item_unit not in", values, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitBetween(String value1, String value2) {
	    addCriterion("item_unit between", value1, value2, "itemUnit");
	    return this;
	}

	public Criteria andItemUnitNotBetween(String value1, String value2) {
	    addCriterion("item_unit not between", value1, value2, "itemUnit");
	    return this;
	}

	public Criteria andItemSalesPriceIsNull() {
	    addCriterion("item_sales_price is null");
	    return this;
	}

	public Criteria andItemSalesPriceIsNotNull() {
	    addCriterion("item_sales_price is not null");
	    return this;
	}

	public Criteria andItemSalesPriceEqualTo(Integer value) {
	    addCriterion("item_sales_price =", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceNotEqualTo(Integer value) {
	    addCriterion("item_sales_price <>", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceGreaterThan(Integer value) {
	    addCriterion("item_sales_price >", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price >=", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceLessThan(Integer value) {
	    addCriterion("item_sales_price <", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceLessThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price <=", value, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceIn(List values) {
	    addCriterion("item_sales_price in", values, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceNotIn(List values) {
	    addCriterion("item_sales_price not in", values, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceBetween(Integer value1, Integer value2) {
	    addCriterion("item_sales_price between", value1, value2, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_sales_price not between", value1, value2, "itemSalesPrice");
	    return this;
	}

	public Criteria andItemSalesPrice2IsNull() {
	    addCriterion("item_sales_price2 is null");
	    return this;
	}

	public Criteria andItemSalesPrice2IsNotNull() {
	    addCriterion("item_sales_price2 is not null");
	    return this;
	}

	public Criteria andItemSalesPrice2EqualTo(Integer value) {
	    addCriterion("item_sales_price2 =", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2NotEqualTo(Integer value) {
	    addCriterion("item_sales_price2 <>", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2GreaterThan(Integer value) {
	    addCriterion("item_sales_price2 >", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2GreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price2 >=", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2LessThan(Integer value) {
	    addCriterion("item_sales_price2 <", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2LessThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price2 <=", value, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2In(List values) {
	    addCriterion("item_sales_price2 in", values, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2NotIn(List values) {
	    addCriterion("item_sales_price2 not in", values, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2Between(Integer value1, Integer value2) {
	    addCriterion("item_sales_price2 between", value1, value2, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice2NotBetween(Integer value1, Integer value2) {
	    addCriterion("item_sales_price2 not between", value1, value2, "itemSalesPrice2");
	    return this;
	}

	public Criteria andItemSalesPrice3IsNull() {
	    addCriterion("item_sales_price3 is null");
	    return this;
	}

	public Criteria andItemSalesPrice3IsNotNull() {
	    addCriterion("item_sales_price3 is not null");
	    return this;
	}

	public Criteria andItemSalesPrice3EqualTo(Integer value) {
	    addCriterion("item_sales_price3 =", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3NotEqualTo(Integer value) {
	    addCriterion("item_sales_price3 <>", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3GreaterThan(Integer value) {
	    addCriterion("item_sales_price3 >", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3GreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price3 >=", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3LessThan(Integer value) {
	    addCriterion("item_sales_price3 <", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3LessThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price3 <=", value, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3In(List values) {
	    addCriterion("item_sales_price3 in", values, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3NotIn(List values) {
	    addCriterion("item_sales_price3 not in", values, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3Between(Integer value1, Integer value2) {
	    addCriterion("item_sales_price3 between", value1, value2, "itemSalesPrice3");
	    return this;
	}

	public Criteria andItemSalesPrice3NotBetween(Integer value1, Integer value2) {
	    addCriterion("item_sales_price3 not between", value1, value2, "itemSalesPrice3");
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

	public Criteria andItemDummyPriceIsNull() {
	    addCriterion("item_dummy_price is null");
	    return this;
	}

	public Criteria andItemDummyPriceIsNotNull() {
	    addCriterion("item_dummy_price is not null");
	    return this;
	}

	public Criteria andItemDummyPriceEqualTo(Integer value) {
	    addCriterion("item_dummy_price =", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceNotEqualTo(Integer value) {
	    addCriterion("item_dummy_price <>", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceGreaterThan(Integer value) {
	    addCriterion("item_dummy_price >", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_dummy_price >=", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceLessThan(Integer value) {
	    addCriterion("item_dummy_price <", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceLessThanOrEqualTo(Integer value) {
	    addCriterion("item_dummy_price <=", value, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceIn(List values) {
	    addCriterion("item_dummy_price in", values, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceNotIn(List values) {
	    addCriterion("item_dummy_price not in", values, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceBetween(Integer value1, Integer value2) {
	    addCriterion("item_dummy_price between", value1, value2, "itemDummyPrice");
	    return this;
	}

	public Criteria andItemDummyPriceNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_dummy_price not between", value1, value2, "itemDummyPrice");
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

	public Criteria andItemTypeIsNull() {
	    addCriterion("item_type is null");
	    return this;
	}

	public Criteria andItemTypeIsNotNull() {
	    addCriterion("item_type is not null");
	    return this;
	}

	public Criteria andItemTypeEqualTo(Integer value) {
	    addCriterion("item_type =", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeNotEqualTo(Integer value) {
	    addCriterion("item_type <>", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeGreaterThan(Integer value) {
	    addCriterion("item_type >", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_type >=", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeLessThan(Integer value) {
	    addCriterion("item_type <", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("item_type <=", value, "itemType");
	    return this;
	}

	public Criteria andItemTypeIn(List values) {
	    addCriterion("item_type in", values, "itemType");
	    return this;
	}

	public Criteria andItemTypeNotIn(List values) {
	    addCriterion("item_type not in", values, "itemType");
	    return this;
	}

	public Criteria andItemTypeBetween(Integer value1, Integer value2) {
	    addCriterion("item_type between", value1, value2, "itemType");
	    return this;
	}

	public Criteria andItemTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_type not between", value1, value2, "itemType");
	    return this;
	}

	public Criteria andItemExt1IsNull() {
	    addCriterion("item_ext1 is null");
	    return this;
	}

	public Criteria andItemExt1IsNotNull() {
	    addCriterion("item_ext1 is not null");
	    return this;
	}

	public Criteria andItemExt1EqualTo(String value) {
	    addCriterion("item_ext1 =", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1NotEqualTo(String value) {
	    addCriterion("item_ext1 <>", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1GreaterThan(String value) {
	    addCriterion("item_ext1 >", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1GreaterThanOrEqualTo(String value) {
	    addCriterion("item_ext1 >=", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1LessThan(String value) {
	    addCriterion("item_ext1 <", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1LessThanOrEqualTo(String value) {
	    addCriterion("item_ext1 <=", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1Like(String value) {
	    addCriterion("item_ext1 like", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1NotLike(String value) {
	    addCriterion("item_ext1 not like", value, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1In(List values) {
	    addCriterion("item_ext1 in", values, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1NotIn(List values) {
	    addCriterion("item_ext1 not in", values, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1Between(String value1, String value2) {
	    addCriterion("item_ext1 between", value1, value2, "itemExt1");
	    return this;
	}

	public Criteria andItemExt1NotBetween(String value1, String value2) {
	    addCriterion("item_ext1 not between", value1, value2, "itemExt1");
	    return this;
	}

	public Criteria andItemExt2IsNull() {
	    addCriterion("item_ext2 is null");
	    return this;
	}

	public Criteria andItemExt2IsNotNull() {
	    addCriterion("item_ext2 is not null");
	    return this;
	}

	public Criteria andItemExt2EqualTo(String value) {
	    addCriterion("item_ext2 =", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2NotEqualTo(String value) {
	    addCriterion("item_ext2 <>", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2GreaterThan(String value) {
	    addCriterion("item_ext2 >", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2GreaterThanOrEqualTo(String value) {
	    addCriterion("item_ext2 >=", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2LessThan(String value) {
	    addCriterion("item_ext2 <", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2LessThanOrEqualTo(String value) {
	    addCriterion("item_ext2 <=", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2Like(String value) {
	    addCriterion("item_ext2 like", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2NotLike(String value) {
	    addCriterion("item_ext2 not like", value, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2In(List values) {
	    addCriterion("item_ext2 in", values, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2NotIn(List values) {
	    addCriterion("item_ext2 not in", values, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2Between(String value1, String value2) {
	    addCriterion("item_ext2 between", value1, value2, "itemExt2");
	    return this;
	}

	public Criteria andItemExt2NotBetween(String value1, String value2) {
	    addCriterion("item_ext2 not between", value1, value2, "itemExt2");
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

	public Criteria andItemSizeLike(Integer value) {
	    addCriterion("item_size like", value, "itemSize");
	    return this;
	}

	public Criteria andItemSizeNotLike(Integer value) {
	    addCriterion("item_size not like", value, "itemSize");
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

	public Criteria andItemRangeIsNull() {
	    addCriterion("item_range is null");
	    return this;
	}

	public Criteria andItemRangeIsNotNull() {
	    addCriterion("item_range is not null");
	    return this;
	}

	public Criteria andItemRangeEqualTo(Integer value) {
	    addCriterion("item_range =", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeNotEqualTo(Integer value) {
	    addCriterion("item_range <>", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeGreaterThan(Integer value) {
	    addCriterion("item_range >", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_range >=", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeLessThan(Integer value) {
	    addCriterion("item_range <", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeLessThanOrEqualTo(Integer value) {
	    addCriterion("item_range <=", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeLike(String value) {
	    addCriterion("item_range like", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeNotLike(String value) {
	    addCriterion("item_range not like", value, "itemRange");
	    return this;
	}

	public Criteria andItemRangeIn(List values) {
	    addCriterion("item_range in", values, "itemRange");
	    return this;
	}

	public Criteria andItemRangeNotIn(List values) {
	    addCriterion("item_range not in", values, "itemRange");
	    return this;
	}

	public Criteria andItemRangeBetween(Integer value1, Integer value2) {
	    addCriterion("item_range between", value1, value2, "itemRange");
	    return this;
	}

	public Criteria andItemRangeNotBetween(Integer value1, Integer value2) {
	    addCriterion("item_range not between", value1, value2, "itemRange");
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

	public Criteria andItemSalesPrice4IsNull() {
	    addCriterion("item_sales_price4 is null");
	    return this;
	}

	public Criteria andItemSalesPrice4IsNotNull() {
	    addCriterion("item_sales_price4 is not null");
	    return this;
	}

	public Criteria andItemSalesPrice4EqualTo(Integer value) {
	    addCriterion("item_sales_price4 =", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4NotEqualTo(Integer value) {
	    addCriterion("item_sales_price4 <>", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4GreaterThan(Integer value) {
	    addCriterion("item_sales_price4 >", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4GreaterThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price4 >=", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4LessThan(Integer value) {
	    addCriterion("item_sales_price4 <", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4LessThanOrEqualTo(Integer value) {
	    addCriterion("item_sales_price4 <=", value, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4In(List values) {
	    addCriterion("item_sales_price4 in", values, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4NotIn(List values) {
	    addCriterion("item_sales_price4 not in", values, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4Between(Integer value1, Integer value2) {
	    addCriterion("item_sales_price4 between", value1, value2, "itemSalesPrice4");
	    return this;
	}

	public Criteria andItemSalesPrice4NotBetween(Integer value1, Integer value2) {
	    addCriterion("item_sales_price4 not between", value1, value2, "itemSalesPrice4");
	    return this;
	}

	public Criteria andSalesAreaIsNull() {
	    addCriterion("sales_area is null");
	    return this;
	}

	public Criteria andSalesAreaIsNotNull() {
	    addCriterion("sales_area is not null");
	    return this;
	}

	public Criteria andSalesAreaEqualTo(String value) {
	    addCriterion("sales_area =", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaNotEqualTo(String value) {
	    addCriterion("sales_area <>", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaGreaterThan(String value) {
	    addCriterion("sales_area >", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaGreaterThanOrEqualTo(String value) {
	    addCriterion("sales_area >=", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaLessThan(String value) {
	    addCriterion("sales_area <", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaLessThanOrEqualTo(String value) {
	    addCriterion("sales_area <=", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaLike(String value) {
	    addCriterion("sales_area like", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaNotLike(String value) {
	    addCriterion("sales_area not like", value, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaIn(List values) {
	    addCriterion("sales_area in", values, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaNotIn(List values) {
	    addCriterion("sales_area not in", values, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaBetween(String value1, String value2) {
	    addCriterion("sales_area between", value1, value2, "salesArea");
	    return this;
	}

	public Criteria andSalesAreaNotBetween(String value1, String value2) {
	    addCriterion("sales_area not between", value1, value2, "salesArea");
	    return this;
	}

	public Criteria andSupplyFilterTypeIsNull() {
	    addCriterion("supply_filter_type is null");
	    return this;
	}

	public Criteria andSupplyFilterTypeIsNotNull() {
	    addCriterion("supply_filter_type is not null");
	    return this;
	}

	public Criteria andSupplyFilterTypeEqualTo(Integer value) {
	    addCriterion("supply_filter_type =", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeNotEqualTo(Integer value) {
	    addCriterion("supply_filter_type <>", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeGreaterThan(Integer value) {
	    addCriterion("supply_filter_type >", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_filter_type >=", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeLessThan(Integer value) {
	    addCriterion("supply_filter_type <", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_filter_type <=", value, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeIn(List values) {
	    addCriterion("supply_filter_type in", values, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeNotIn(List values) {
	    addCriterion("supply_filter_type not in", values, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeBetween(Integer value1, Integer value2) {
	    addCriterion("supply_filter_type between", value1, value2, "supplyFilterType");
	    return this;
	}

	public Criteria andSupplyFilterTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_filter_type not between", value1, value2, "supplyFilterType");
	    return this;
	}

	public Criteria andCanSyncIsNull() {
	    addCriterion("can_sync is null");
	    return this;
	}

	public Criteria andCanSyncIsNotNull() {
	    addCriterion("can_sync is not null");
	    return this;
	}

	public Criteria andCanSyncEqualTo(Boolean value) {
	    addCriterion("can_sync =", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncNotEqualTo(Boolean value) {
	    addCriterion("can_sync <>", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncGreaterThan(Boolean value) {
	    addCriterion("can_sync >", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncGreaterThanOrEqualTo(Boolean value) {
	    addCriterion("can_sync >=", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncLessThan(Boolean value) {
	    addCriterion("can_sync <", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncLessThanOrEqualTo(Boolean value) {
	    addCriterion("can_sync <=", value, "canSync");
	    return this;
	}

	public Criteria andCanSyncIn(List values) {
	    addCriterion("can_sync in", values, "canSync");
	    return this;
	}

	public Criteria andCanSyncNotIn(List values) {
	    addCriterion("can_sync not in", values, "canSync");
	    return this;
	}

	public Criteria andCanSyncBetween(Boolean value1, Boolean value2) {
	    addCriterion("can_sync between", value1, value2, "canSync");
	    return this;
	}

	public Criteria andCanSyncNotBetween(Boolean value1, Boolean value2) {
	    addCriterion("can_sync not between", value1, value2, "canSync");
	    return this;
	}

	public Criteria andFaceTypeIsNull() {
	    addCriterion("face_type is null");
	    return this;
	}

	public Criteria andFaceTypeIsNotNull() {
	    addCriterion("face_type is not null");
	    return this;
	}

	public Criteria andFaceTypeEqualTo(Integer value) {
	    addCriterion("face_type =", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeNotEqualTo(Integer value) {
	    addCriterion("face_type <>", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeGreaterThan(Integer value) {
	    addCriterion("face_type >", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("face_type >=", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeLessThan(Integer value) {
	    addCriterion("face_type <", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("face_type <=", value, "faceType");
	    return this;
	}

	public Criteria andFaceTypeIn(List values) {
	    addCriterion("face_type in", values, "faceType");
	    return this;
	}

	public Criteria andFaceTypeNotIn(List values) {
	    addCriterion("face_type not in", values, "faceType");
	    return this;
	}

	public Criteria andFaceTypeBetween(Integer value1, Integer value2) {
	    addCriterion("face_type between", value1, value2, "faceType");
	    return this;
	}

	public Criteria andFaceTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("face_type not between", value1, value2, "faceType");
	    return this;
	}

	public Criteria andNumberListIsNull() {
	    addCriterion("number_list is null");
	    return this;
	}

	public Criteria andNumberListIsNotNull() {
	    addCriterion("number_list is not null");
	    return this;
	}

	public Criteria andNumberListEqualTo(String value) {
	    addCriterion("number_list =", value, "numberList");
	    return this;
	}

	public Criteria andNumberListNotEqualTo(String value) {
	    addCriterion("number_list <>", value, "numberList");
	    return this;
	}

	public Criteria andNumberListGreaterThan(String value) {
	    addCriterion("number_list >", value, "numberList");
	    return this;
	}

	public Criteria andNumberListGreaterThanOrEqualTo(String value) {
	    addCriterion("number_list >=", value, "numberList");
	    return this;
	}

	public Criteria andNumberListLessThan(String value) {
	    addCriterion("number_list <", value, "numberList");
	    return this;
	}

	public Criteria andNumberListLessThanOrEqualTo(String value) {
	    addCriterion("number_list <=", value, "numberList");
	    return this;
	}

	public Criteria andNumberListLike(String value) {
	    addCriterion("number_list like", value, "numberList");
	    return this;
	}

	public Criteria andNumberListNotLike(String value) {
	    addCriterion("number_list not like", value, "numberList");
	    return this;
	}

	public Criteria andNumberListIn(List values) {
	    addCriterion("number_list in", values, "numberList");
	    return this;
	}

	public Criteria andNumberListNotIn(List values) {
	    addCriterion("number_list not in", values, "numberList");
	    return this;
	}

	public Criteria andNumberListBetween(String value1, String value2) {
	    addCriterion("number_list between", value1, value2, "numberList");
	    return this;
	}

	public Criteria andNumberListNotBetween(String value1, String value2) {
	    addCriterion("number_list not between", value1, value2, "numberList");
	    return this;
	}

	public Criteria andMinNumberIsNull() {
	    addCriterion("min_number is null");
	    return this;
	}

	public Criteria andMinNumberIsNotNull() {
	    addCriterion("min_number is not null");
	    return this;
	}

	public Criteria andMinNumberEqualTo(Integer value) {
	    addCriterion("min_number =", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberNotEqualTo(Integer value) {
	    addCriterion("min_number <>", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberGreaterThan(Integer value) {
	    addCriterion("min_number >", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberGreaterThanOrEqualTo(Integer value) {
	    addCriterion("min_number >=", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberLessThan(Integer value) {
	    addCriterion("min_number <", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberLessThanOrEqualTo(Integer value) {
	    addCriterion("min_number <=", value, "minNumber");
	    return this;
	}

	public Criteria andMinNumberIn(List values) {
	    addCriterion("min_number in", values, "minNumber");
	    return this;
	}

	public Criteria andMinNumberNotIn(List values) {
	    addCriterion("min_number not in", values, "minNumber");
	    return this;
	}

	public Criteria andMinNumberBetween(Integer value1, Integer value2) {
	    addCriterion("min_number between", value1, value2, "minNumber");
	    return this;
	}

	public Criteria andMinNumberNotBetween(Integer value1, Integer value2) {
	    addCriterion("min_number not between", value1, value2, "minNumber");
	    return this;
	}

	public Criteria andMaxNumberIsNull() {
	    addCriterion("max_number is null");
	    return this;
	}

	public Criteria andMaxNumberIsNotNull() {
	    addCriterion("max_number is not null");
	    return this;
	}

	public Criteria andMaxNumberEqualTo(Integer value) {
	    addCriterion("max_number =", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberNotEqualTo(Integer value) {
	    addCriterion("max_number <>", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberGreaterThan(Integer value) {
	    addCriterion("max_number >", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberGreaterThanOrEqualTo(Integer value) {
	    addCriterion("max_number >=", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberLessThan(Integer value) {
	    addCriterion("max_number <", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberLessThanOrEqualTo(Integer value) {
	    addCriterion("max_number <=", value, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberIn(List values) {
	    addCriterion("max_number in", values, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberNotIn(List values) {
	    addCriterion("max_number not in", values, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberBetween(Integer value1, Integer value2) {
	    addCriterion("max_number between", value1, value2, "maxNumber");
	    return this;
	}

	public Criteria andMaxNumberNotBetween(Integer value1, Integer value2) {
	    addCriterion("max_number not between", value1, value2, "maxNumber");
	    return this;
	}

	public Criteria andExchargeRatioIsNull() {
	    addCriterion("excharge_ratio is null");
	    return this;
	}

	public Criteria andExchargeRatioIsNotNull() {
	    addCriterion("excharge_ratio is not null");
	    return this;
	}

	public Criteria andExchargeRatioEqualTo(Integer value) {
	    addCriterion("excharge_ratio =", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioNotEqualTo(Integer value) {
	    addCriterion("excharge_ratio <>", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioGreaterThan(Integer value) {
	    addCriterion("excharge_ratio >", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioGreaterThanOrEqualTo(Integer value) {
	    addCriterion("excharge_ratio >=", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioLessThan(Integer value) {
	    addCriterion("excharge_ratio <", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioLessThanOrEqualTo(Integer value) {
	    addCriterion("excharge_ratio <=", value, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioIn(List values) {
	    addCriterion("excharge_ratio in", values, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioNotIn(List values) {
	    addCriterion("excharge_ratio not in", values, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioBetween(Integer value1, Integer value2) {
	    addCriterion("excharge_ratio between", value1, value2, "exchargeRatio");
	    return this;
	}

	public Criteria andExchargeRatioNotBetween(Integer value1, Integer value2) {
	    addCriterion("excharge_ratio not between", value1, value2, "exchargeRatio");
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

	public Criteria andRepeatCountIsNull() {
	    addCriterion("repeat_count is null");
	    return this;
	}

	public Criteria andRepeatCountIsNotNull() {
	    addCriterion("repeat_count is not null");
	    return this;
	}

	public Criteria andRepeatCountEqualTo(Integer value) {
	    addCriterion("repeat_count =", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountNotEqualTo(Integer value) {
	    addCriterion("repeat_count <>", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountGreaterThan(Integer value) {
	    addCriterion("repeat_count >", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountGreaterThanOrEqualTo(Integer value) {
	    addCriterion("repeat_count >=", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountLessThan(Integer value) {
	    addCriterion("repeat_count <", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountLessThanOrEqualTo(Integer value) {
	    addCriterion("repeat_count <=", value, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountIn(List values) {
	    addCriterion("repeat_count in", values, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountNotIn(List values) {
	    addCriterion("repeat_count not in", values, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_count between", value1, value2, "repeatCount");
	    return this;
	}

	public Criteria andRepeatCountNotBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_count not between", value1, value2, "repeatCount");
	    return this;
	}

	public Criteria andRepeatTimeIsNull() {
	    addCriterion("repeat_time is null");
	    return this;
	}

	public Criteria andRepeatTimeIsNotNull() {
	    addCriterion("repeat_time is not null");
	    return this;
	}

	public Criteria andRepeatTimeEqualTo(Integer value) {
	    addCriterion("repeat_time =", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeNotEqualTo(Integer value) {
	    addCriterion("repeat_time <>", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeGreaterThan(Integer value) {
	    addCriterion("repeat_time >", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("repeat_time >=", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeLessThan(Integer value) {
	    addCriterion("repeat_time <", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeLessThanOrEqualTo(Integer value) {
	    addCriterion("repeat_time <=", value, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeIn(List values) {
	    addCriterion("repeat_time in", values, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeNotIn(List values) {
	    addCriterion("repeat_time not in", values, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_time between", value1, value2, "repeatTime");
	    return this;
	}

	public Criteria andRepeatTimeNotBetween(Integer value1, Integer value2) {
	    addCriterion("repeat_time not between", value1, value2, "repeatTime");
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
    }
}