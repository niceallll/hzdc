package com.longan.biz.domain;

import java.util.List;

import com.longan.biz.dataobject.OperationInfo;

public class MainOperation {
    private OperationInfo mainOperationInfo;
    private List<OperationInfo> subOperationInfoList;

    public OperationInfo getMainOperationInfo() {
	return mainOperationInfo;
    }

    public void setMainOperationInfo(OperationInfo mainOperationInfo) {
	this.mainOperationInfo = mainOperationInfo;
    }

    public List<OperationInfo> getSubOperationInfoList() {
	return subOperationInfoList;
    }

    public void setSubOperationInfoList(List<OperationInfo> subOperationInfoList) {
	this.subOperationInfoList = subOperationInfoList;
    }
}
