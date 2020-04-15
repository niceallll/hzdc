package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.TaskDAO;
import com.longan.biz.dataobject.Task;
import com.longan.biz.dataobject.TaskQuery;

public class TaskDAOImpl implements TaskDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(Task task) throws SQLException {
	return (Long) sqlMapClient.insert("task.insert", task);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> queryListUnDone(Long inTime) throws SQLException {
	return (List<Task>) sqlMapClient.queryForList("task.queryListUnDone", inTime);
    }

    @Override
    public Integer updateStatusByid(Long id, Integer status) throws SQLException {
	Map<String, Object> params = new HashMap<String, Object>(2);
	params.put("id", id);
	params.put("status", status);
	return (Integer) sqlMapClient.update("task.updateStatusByid", params);
    }

    @Override
    public Integer cancelTask(Long id) throws SQLException {
	return (Integer) sqlMapClient.update("task.cancelTask", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> queryByPage(TaskQuery taskQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("task.queryByPageCount", taskQuery);
	taskQuery.setTotalItem(count);
	return (List<Task>) sqlMapClient.queryForList("task.queryByPage", taskQuery);
    }

    @Override
    public Integer updateTaskById(Task task) throws SQLException {
	if (task == null) {
	    return 0;
	}
	return sqlMapClient.update("task.updateTaskById", task);
    }

    @Override
    public Task getTaskById(Long id) throws SQLException {
	if (id == null) {
	    return null;
	}
	return (Task) sqlMapClient.queryForObject("task.getTaskById", id);
    }

}
