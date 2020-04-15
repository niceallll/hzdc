package com.longan.mng.form;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

import com.longan.biz.utils.Constants;

public class BaseTaskForm {
    @NotBlank(message = "必须设置生效时间")
    private String commitType = Constants.Task.COMMIT_TYPE_REAL_TIME + "";
    private Date commitTime;

    public String getCommitType() {
	return commitType;
    }

    public void setCommitType(String commitType) {
	this.commitType = commitType;
    }

    public Date getCommitTime() {
	return commitTime;
    }

    public void setCommitTime(Date commitTime) {
	this.commitTime = commitTime;
    }

    public boolean isRealTimeCommit() {
	if (StringUtils.isBlank(commitType)) {
	    // 默认true
	    return true;
	}
	return String.valueOf(Constants.Task.COMMIT_TYPE_REAL_TIME).equals(commitType);
    }

}
