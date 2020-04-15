package com.longan.biz.dao;


import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.longan.biz.dataobject.PddOrder;

import java.sql.SQLException;
import java.util.List;

public interface PddOrderDao {

    PddOrder selectByPrimaryKey(Long id) throws SQLException;

    Long insert(PddOrder pddOrder) throws SQLException;
    List<PddOrder> select (PddOrder pddOrder)throws SQLException;

}
