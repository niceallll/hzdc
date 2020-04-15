package com.longan.biz.dataobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderInfoExample {
    protected String orderByClause;

    protected List oredCriteria;

    public TraderInfoExample() {
	oredCriteria = new ArrayList();
    }

    protected TraderInfoExample(TraderInfoExample example) {
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

	public Criteria andSupplyTypeIsNull() {
	    addCriterion("supply_type is null");
	    return this;
	}

	public Criteria andSupplyTypeIsNotNull() {
	    addCriterion("supply_type is not null");
	    return this;
	}

	public Criteria andSupplyTypeEqualTo(Integer value) {
	    addCriterion("supply_type =", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotEqualTo(Integer value) {
	    addCriterion("supply_type <>", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeGreaterThan(Integer value) {
	    addCriterion("supply_type >", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("supply_type >=", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeLessThan(Integer value) {
	    addCriterion("supply_type <", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("supply_type <=", value, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeIn(List values) {
	    addCriterion("supply_type in", values, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotIn(List values) {
	    addCriterion("supply_type not in", values, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeBetween(Integer value1, Integer value2) {
	    addCriterion("supply_type between", value1, value2, "supplyType");
	    return this;
	}

	public Criteria andSupplyTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("supply_type not between", value1, value2, "supplyType");
	    return this;
	}

	public Criteria andIsAsyncSupplyIsNull() {
	    addCriterion("is_async_supply is null");
	    return this;
	}

	public Criteria andIsAsyncSupplyIsNotNull() {
	    addCriterion("is_async_supply is not null");
	    return this;
	}

	public Criteria andIsAsyncSupplyEqualTo(Boolean value) {
	    addCriterion("is_async_supply =", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyNotEqualTo(Boolean value) {
	    addCriterion("is_async_supply <>", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyGreaterThan(Boolean value) {
	    addCriterion("is_async_supply >", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyGreaterThanOrEqualTo(Boolean value) {
	    addCriterion("is_async_supply >=", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyLessThan(Boolean value) {
	    addCriterion("is_async_supply <", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyLessThanOrEqualTo(Boolean value) {
	    addCriterion("is_async_supply <=", value, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyIn(List values) {
	    addCriterion("is_async_supply in", values, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyNotIn(List values) {
	    addCriterion("is_async_supply not in", values, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyBetween(Boolean value1, Boolean value2) {
	    addCriterion("is_async_supply between", value1, value2, "isAsyncSupply");
	    return this;
	}

	public Criteria andIsAsyncSupplyNotBetween(Boolean value1, Boolean value2) {
	    addCriterion("is_async_supply not between", value1, value2, "isAsyncSupply");
	    return this;
	}

	public Criteria andCallbackUrlIsNull() {
	    addCriterion("callback_url is null");
	    return this;
	}

	public Criteria andCallbackUrlIsNotNull() {
	    addCriterion("callback_url is not null");
	    return this;
	}

	public Criteria andCallbackUrlEqualTo(String value) {
	    addCriterion("callback_url =", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlNotEqualTo(String value) {
	    addCriterion("callback_url <>", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlGreaterThan(String value) {
	    addCriterion("callback_url >", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlGreaterThanOrEqualTo(String value) {
	    addCriterion("callback_url >=", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlLessThan(String value) {
	    addCriterion("callback_url <", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlLessThanOrEqualTo(String value) {
	    addCriterion("callback_url <=", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlLike(String value) {
	    addCriterion("callback_url like", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlNotLike(String value) {
	    addCriterion("callback_url not like", value, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlIn(List values) {
	    addCriterion("callback_url in", values, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlNotIn(List values) {
	    addCriterion("callback_url not in", values, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlBetween(String value1, String value2) {
	    addCriterion("callback_url between", value1, value2, "callbackUrl");
	    return this;
	}

	public Criteria andCallbackUrlNotBetween(String value1, String value2) {
	    addCriterion("callback_url not between", value1, value2, "callbackUrl");
	    return this;
	}

	public Criteria andDownstreamKeyIsNull() {
	    addCriterion("downstream_key is null");
	    return this;
	}

	public Criteria andDownstreamKeyIsNotNull() {
	    addCriterion("downstream_key is not null");
	    return this;
	}

	public Criteria andDownstreamKeyEqualTo(String value) {
	    addCriterion("downstream_key =", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyNotEqualTo(String value) {
	    addCriterion("downstream_key <>", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyGreaterThan(String value) {
	    addCriterion("downstream_key >", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyGreaterThanOrEqualTo(String value) {
	    addCriterion("downstream_key >=", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyLessThan(String value) {
	    addCriterion("downstream_key <", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyLessThanOrEqualTo(String value) {
	    addCriterion("downstream_key <=", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyLike(String value) {
	    addCriterion("downstream_key like", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyNotLike(String value) {
	    addCriterion("downstream_key not like", value, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyIn(List values) {
	    addCriterion("downstream_key in", values, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyNotIn(List values) {
	    addCriterion("downstream_key not in", values, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyBetween(String value1, String value2) {
	    addCriterion("downstream_key between", value1, value2, "downstreamKey");
	    return this;
	}

	public Criteria andDownstreamKeyNotBetween(String value1, String value2) {
	    addCriterion("downstream_key not between", value1, value2, "downstreamKey");
	    return this;
	}

	public Criteria andTraderTypeIsNull() {
	    addCriterion("trader_type is null");
	    return this;
	}

	public Criteria andTraderTypeIsNotNull() {
	    addCriterion("trader_type is not null");
	    return this;
	}

	public Criteria andTraderTypeEqualTo(Integer value) {
	    addCriterion("trader_type =", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeNotEqualTo(Integer value) {
	    addCriterion("trader_type <>", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeGreaterThan(Integer value) {
	    addCriterion("trader_type >", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeGreaterThanOrEqualTo(Integer value) {
	    addCriterion("trader_type >=", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeLessThan(Integer value) {
	    addCriterion("trader_type <", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeLessThanOrEqualTo(Integer value) {
	    addCriterion("trader_type <=", value, "traderType");
	    return this;
	}

	public Criteria andTraderTypeIn(List values) {
	    addCriterion("trader_type in", values, "traderType");
	    return this;
	}

	public Criteria andTraderTypeNotIn(List values) {
	    addCriterion("trader_type not in", values, "traderType");
	    return this;
	}

	public Criteria andTraderTypeBetween(Integer value1, Integer value2) {
	    addCriterion("trader_type between", value1, value2, "traderType");
	    return this;
	}

	public Criteria andTraderTypeNotBetween(Integer value1, Integer value2) {
	    addCriterion("trader_type not between", value1, value2, "traderType");
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

	public Criteria andChargingLimitIsNull() {
	    addCriterion("charging_limit is null");
	    return this;
	}

	public Criteria andChargingLimitIsNotNull() {
	    addCriterion("charging_limit is not null");
	    return this;
	}

	public Criteria andChargingLimitEqualTo(Long value) {
	    addCriterion("charging_limit =", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitNotEqualTo(Long value) {
	    addCriterion("charging_limit <>", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitGreaterThan(Long value) {
	    addCriterion("charging_limit >", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitGreaterThanOrEqualTo(Long value) {
	    addCriterion("charging_limit >=", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitLessThan(Long value) {
	    addCriterion("charging_limit <", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitLessThanOrEqualTo(Long value) {
	    addCriterion("charging_limit <=", value, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitIn(List values) {
	    addCriterion("charging_limit in", values, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitNotIn(List values) {
	    addCriterion("charging_limit not in", values, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitBetween(Long value1, Long value2) {
	    addCriterion("charging_limit between", value1, value2, "chargingLimit");
	    return this;
	}

	public Criteria andChargingLimitNotBetween(Long value1, Long value2) {
	    addCriterion("charging_limit not between", value1, value2, "chargingLimit");
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

	public Criteria andSmsExtendIsNull() {
	    addCriterion("sms_extend is null");
	    return this;
	}

	public Criteria andSmsExtendIsNotNull() {
	    addCriterion("sms_extend is not null");
	    return this;
	}

	public Criteria andSmsExtendEqualTo(String value) {
	    addCriterion("sms_extend =", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendNotEqualTo(String value) {
	    addCriterion("sms_extend <>", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendGreaterThan(String value) {
	    addCriterion("sms_extend >", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendGreaterThanOrEqualTo(String value) {
	    addCriterion("sms_extend >=", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendLessThan(String value) {
	    addCriterion("sms_extend <", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendLessThanOrEqualTo(String value) {
	    addCriterion("sms_extend <=", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendLike(String value) {
	    addCriterion("sms_extend like", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendNotLike(String value) {
	    addCriterion("sms_extend not like", value, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendIn(List values) {
	    addCriterion("sms_extend in", values, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendNotIn(List values) {
	    addCriterion("sms_extend not in", values, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendBetween(String value1, String value2) {
	    addCriterion("sms_extend between", value1, value2, "smsExtend");
	    return this;
	}

	public Criteria andSmsExtendNotBetween(String value1, String value2) {
	    addCriterion("sms_extend not between", value1, value2, "smsExtend");
	    return this;
	}

	public Criteria andNotifyUrlIsNull() {
	    addCriterion("notify_url is null");
	    return this;
	}

	public Criteria andNotifyUrlIsNotNull() {
	    addCriterion("notify_url is not null");
	    return this;
	}

	public Criteria andNotifyUrlEqualTo(String value) {
	    addCriterion("notify_url =", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlNotEqualTo(String value) {
	    addCriterion("notify_url <>", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlGreaterThan(String value) {
	    addCriterion("notify_url >", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlGreaterThanOrEqualTo(String value) {
	    addCriterion("notify_url >=", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlLessThan(String value) {
	    addCriterion("notify_url <", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlLessThanOrEqualTo(String value) {
	    addCriterion("notify_url <=", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlLike(String value) {
	    addCriterion("notify_url like", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlNotLike(String value) {
	    addCriterion("notify_url not like", value, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlIn(List values) {
	    addCriterion("notify_url in", values, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlNotIn(List values) {
	    addCriterion("notify_url not in", values, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlBetween(String value1, String value2) {
	    addCriterion("notify_url between", value1, value2, "notifyUrl");
	    return this;
	}

	public Criteria andNotifyUrlNotBetween(String value1, String value2) {
	    addCriterion("notify_url not between", value1, value2, "notifyUrl");
	    return this;
	}
    }
}