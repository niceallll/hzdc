package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.Task;
import com.longan.biz.dataobject.TaskQuery;
import com.longan.biz.domain.Result;

public interface TaskService {
    Result<Long> submitTask(Task task);

    Result<List<Task>> queryTaskListUnDone();

    Result<Boolean> setTaskDone(Long id);

    Result<Boolean> setTaskException(Long id);

    Result<Boolean> cancelTask(Long id);

    Result<Boolean> commitTask(final Task task);

    Result<Boolean> updateTask(Task task);

    Result<List<Task>> queryTaskByPage(TaskQuery taskQuery);

    Result<Task> getTaskById(Long id);

}
