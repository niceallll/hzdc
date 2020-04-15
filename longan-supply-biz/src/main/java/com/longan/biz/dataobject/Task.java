package com.longan.biz.dataobject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longan.biz.utils.Constants;

public class Task {
    Logger logger = LoggerFactory.getLogger(Task.class);

    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private Long userId;

    private String userName;

    private String objectName;

    private String serviceName;

    private String methodName;

    private Date commitTime;

    private Integer status;

    private byte[] instance;

    private Integer bizId;

    private String moduleName;

    private Serializable nativeObject;

    private String instanceDesc;

    private Integer commitType;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
	return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
	this.gmtModify = gmtModify;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getObjectName() {
	return objectName;
    }

    public void setObjectName(String objectName) {
	this.objectName = objectName;
    }

    public String getServiceName() {
	return serviceName;
    }

    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    public String getMethodName() {
	return methodName;
    }

    public void setMethodName(String methodName) {
	this.methodName = methodName;
    }

    public Date getCommitTime() {
	return commitTime;
    }

    public void setCommitTime(Date commitTime) {
	this.commitTime = commitTime;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public byte[] getInstance() {
	return instance;
    }

    public void setInstance(byte[] instance) {
	this.instance = instance;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public String getModuleName() {
	return moduleName;
    }

    public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
    }

    public Serializable getNativeObject() {
	if (nativeObject == null && instance != null) {
	    Object object = byteToObject(instance);
	    if (object != null && instance instanceof Serializable) {
		nativeObject = (Serializable) object;
	    }
	}
	return nativeObject;
    }

    public void setNativeObject(Serializable nativeObject) {
	this.nativeObject = nativeObject;
	if (nativeObject == null) {
	    return;
	}
	byte[] bytes = getBytesFromObject(nativeObject);
	if (bytes != null) {
	    this.setInstance(bytes);
	}
    }

    public String getInstanceDesc() {
	return instanceDesc;
    }

    public void setInstanceDesc(String instanceDesc) {
	this.instanceDesc = instanceDesc;
    }

    private Object byteToObject(byte[] bytes) {
	Object result = null;
	if (bytes == null || bytes.length == 0) {
	    return null;
	}
	ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
	ObjectInputStream oi;
	try {
	    oi = new ObjectInputStream(bi);
	} catch (IOException e) {
	    logger.error("byteToObject error", e);
	    return result;
	}
	try {
	    return oi.readObject();
	} catch (IOException e) {
	    logger.error("byteToObject error", e);
	    return result;
	} catch (ClassNotFoundException e) {
	    logger.error("byteToObject error", e);
	    return result;
	}
    }

    private byte[] getBytesFromObject(Serializable obj) {
	if (obj == null) {
	    return null;
	}
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	ObjectOutputStream oo = null;
	try {
	    oo = new ObjectOutputStream(bo);
	} catch (IOException e) {
	    logger.error("getBytesFromObject error", e);
	    return null;
	}
	try {
	    oo.writeObject(obj);
	} catch (IOException e) {
	    logger.error("getBytesFromObject error", e);
	    return null;
	}
	return bo.toByteArray();
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (status == Constants.Task.STATUS_CANCEL) {
	    result = Constants.Task.STATUS_CANCEL_DESC;
	} else if (status == Constants.Task.STATUS_DONE) {
	    result = Constants.Task.STATUS_DONE_DESC;
	} else if (status == Constants.Task.STATUS_EXCEPTION) {
	    result = Constants.Task.STATUS_EXCEPTION_DESC;
	} else if (status == Constants.Task.STATUS_UNDONE) {
	    result = Constants.Task.STATUS_UNDONE_DESC;
	}
	return result;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_EXCEPTION;
    }

    public boolean showBlue() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_UNDONE;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_DONE;
    }

    public boolean canCancel() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_UNDONE;
    }

    public boolean canEdit() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_UNDONE;
    }

    public boolean isDone() {
	if (status == null) {
	    return false;
	}
	return status == Constants.Task.STATUS_DONE;
    }

    public Integer getCommitType() {

	return commitType;
    }

    public void setCommitType(Integer commitType) {
	this.commitType = commitType;
    }

    public boolean isRealTimeCommit() {
	if (commitType == null) {
	    return false;
	}
	return Constants.Task.COMMIT_TYPE_REAL_TIME == commitType;
    }

}