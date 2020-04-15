package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import com.alibaba.druid.util.StringUtils;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.TaskService;
import com.longan.biz.dao.TaskDAO;
import com.longan.biz.dataobject.Task;
import com.longan.biz.dataobject.TaskQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.SpringUtils;

public class TaskServiceImpl extends BaseService implements TaskService {
    @Resource
    private TaskDAO taskDAO;

    private final static long inTime = 30; // 单位秒

    private final static ExecutorService taskEs = Executors.newFixedThreadPool(2);

    @Override
    public Result<Long> submitTask(Task task) {
	Result<Long> result = new Result<Long>();
	if (task == null || task.getNativeObject() == null) {
	    result.setResultMsg("输入参数异常");
	    return result;
	}
	if (StringUtils.isEmpty(task.getObjectName())) {
	    task.setObjectName(task.getNativeObject().getClass().getName());
	}

	Long id = null;
	if (task.isRealTimeCommit()) {
	    Result<Boolean> realTimeSubmitTaskResult = realTimeSubmitTask(task);
	    if (!realTimeSubmitTaskResult.isSuccess()) {
		result.setResultMsg(realTimeSubmitTaskResult.getResultMsg());
		return result;
	    }
	} else {
	    if (task.getCommitTime() == null) {
		result.setResultMsg("定时任务必须设置生效时间");
		return result;
	    }

	    if (task.getCommitTime().getTime() <= System.currentTimeMillis()) {
		result.setResultMsg("生效时间必须大于当前时间");
		return result;
	    }
	    try {
		id = taskDAO.insert(task);
	    } catch (SQLException e) {
		logger.error("createTask error", e);
		result.setResultMsg("创建任务失败，数据库异常");
		return result;
	    }
	}

	result.setSuccess(true);
	result.setModule(id);
	return result;

    }

    @Override
    public Result<List<Task>> queryTaskListUnDone() {
	Result<List<Task>> result = new Result<List<Task>>();
	List<Task> taskList = new ArrayList<Task>();

	try {
	    taskList = taskDAO.queryListUnDone(inTime);
	} catch (SQLException e) {
	    logger.error("queryTaskListUnDone error", e);
	    result.setResultMsg("获取未执行任务数据库异常");
	    return result;
	}

	result.setSuccess(true);
	result.setModule(taskList);
	return result;
    }

    @Override
    public Result<Boolean> setTaskDone(Long id) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Integer count = 0;
	try {
	    count = taskDAO.updateStatusByid(id, Constants.Task.STATUS_DONE);
	} catch (SQLException e) {
	    logger.error("setTaskDone error", e);
	    result.setResultMsg("设置已完成任务失败，数据库异常");
	    return result;
	}
	result.setSuccess(true);
	result.setModule(count > 0);
	return result;
    }

    @Override
    public Result<Boolean> setTaskException(Long id) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Integer count = 0;
	try {
	    count = taskDAO.updateStatusByid(id, Constants.Task.STATUS_EXCEPTION);
	} catch (SQLException e) {
	    logger.error("setTaskException error", e);
	    result.setResultMsg("设置异常任务失败，数据库异常");
	    return result;
	}
	result.setSuccess(true);
	result.setModule(count > 0);
	return result;
    }

    @Override
    public Result<Boolean> cancelTask(Long id) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Integer count = 0;
	try {
	    count = taskDAO.cancelTask(id);
	} catch (SQLException e) {
	    logger.error("cancelTask error", e);
	    result.setResultMsg("取消任务失败，数据库异常");
	    return result;
	}
	boolean flag = count > 0;
	result.setSuccess(flag);
	result.setModule(flag);

	if (!flag) {
	    result.setResultMsg("只能对未执行的任务做取消操作");
	}

	return result;
    }

    @Override
    public Result<Boolean> commitTask(final Task task) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (StringUtils.isEmpty(task.getObjectName()) || StringUtils.isEmpty(task.getMethodName())
		|| StringUtils.isEmpty(task.getServiceName()) || task.getNativeObject() == null || task.getCommitTime() == null) {
	    logger.error("submitTask error params exception");
	    result.setResultMsg("submitTask error params exception");
	    return result;
	}

	Runnable runable = new Runnable() {
	    @Override
	    public void run() {
		long now = System.currentTimeMillis();
		if (task.getCommitTime().getTime() - now > 0) {
		    // 延迟到执行时间再执行， 过期的任务立即执行。
		    try {
			Thread.sleep(task.getCommitTime().getTime() - now);
		    } catch (InterruptedException e1) {
			logger.error("InterruptedException", e1);
		    }
		}

		Result<Boolean> taskResult = realTimeSubmitTask(task);
		if (!taskResult.isSuccess()) {
		    setTaskException(task.getId());
		}
		setTaskDone(task.getId());
	    }
	};

	// 线程池，线程数量这里设置不多， 所以有些任务可能会延迟 1，到2秒，无伤大雅。
	taskEs.submit(runable);
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<Boolean> updateTask(Task task) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    taskDAO.updateTaskById(task);
	} catch (SQLException e) {
	    logger.error("updateTask error", e);
	    result.setResultMsg("修改任务异常，数据库异常");
	    return result;
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<List<Task>> queryTaskByPage(TaskQuery taskQuery) {
	Result<List<Task>> result = new Result<List<Task>>();
	try {
	    List<Task> queryResult = taskDAO.queryByPage(taskQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("任务列表失败,数据库查询异常");
	    logger.error("queryTaskByPage error ", e);
	}
	return result;
    }

    @Override
    public Result<Task> getTaskById(Long id) {
	Result<Task> result = new Result<Task>();
	try {
	    Task taskResult = taskDAO.getTaskById(id);
	    result.setSuccess(true);
	    result.setModule(taskResult);
	} catch (SQLException e) {
	    result.setResultMsg("任务查询失败,数据库查询异常");
	    logger.error("getTaskById error id = " + id, e);
	}
	return result;
    }

    private Result<Boolean> realTimeSubmitTask(Task task) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (StringUtils.isEmpty(task.getObjectName()) || StringUtils.isEmpty(task.getMethodName())
		|| StringUtils.isEmpty(task.getServiceName()) || task.getNativeObject() == null) {
	    logger.error("realTimeSubmitTask error params exception");
	    result.setResultMsg("realTimeSubmitTask error params exception");
	    return result;
	}

	Object object = task.getNativeObject();
	Object service = SpringUtils.getBean(task.getServiceName());
	if (service == null) {
	    logger.error("realTimeSubmitTask error service is null, serviceName : " + task.getServiceName() + "  methodName: "
		    + task.getMethodName() + " description: " + task.getInstanceDesc());
	    result.setResultMsg("即使生效任务操作失败, 找不到该服务，服务名称" + task.getServiceName());
	    return result;
	}
	try {
	    java.lang.reflect.Method method = service.getClass().getMethod(task.getMethodName(),
		    Class.forName(task.getObjectName()));
	    Result<?> invokeResult = (Result<?>) method.invoke(service, object);
	    if (!invokeResult.isSuccess()) {
		logger.error("submitTask error result is failed : " + invokeResult.getResultMsg());
		result.setResultMsg(invokeResult.getResultMsg());
		return result;
	    }
	} catch (Exception e) {
	    logger.error("method invoke error serviceName : " + task.getServiceName() + "  methodName: " + task.getMethodName()
		    + " description: " + task.getInstanceDesc(), e);
	    result.setResultMsg("即使生效任务操作失败，具体原因请咨询系统管理员");
	    return result;
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

}
