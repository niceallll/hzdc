package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

import com.longan.biz.utils.Constants;

public class TaskForm extends BaseTaskForm {
    @NotBlank(message = "必须设置生效时间")
    private String commitType = Constants.Task.COMMIT_TYPE_TASK + "";

    @NotBlank(message = "任务编号不能为空")
    private String id;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }
}
