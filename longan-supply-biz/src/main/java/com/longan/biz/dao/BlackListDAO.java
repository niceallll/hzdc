package com.longan.biz.dao;

import java.sql.SQLException;

public interface BlackListDAO {
    int countByItemUid(String itemUid) throws SQLException;
}
