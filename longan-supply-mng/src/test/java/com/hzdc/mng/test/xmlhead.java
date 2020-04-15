package com.hzdc.mng.test;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "commandId", "userId", "name", "desc" })
public class xmlhead {
    private String commandId = null;
    private String userId = null;
    private String name = null;
    private String desc = null;

    public xmlhead() {
	super();
    }

    public xmlhead(String commandId, String userId, String name, String desc) {
	this.commandId = commandId;
	this.userId = userId;
	this.name = name;
	this.desc = desc;
    }

    @XmlElement(name = "CommandId")
    public void setCommandId(String commandId) {
	this.commandId = commandId;
    }

    @XmlElement(name = "UserId")
    public void setUserId(String userId) {
	this.userId = userId;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public String getCommandId() {
	return commandId;
    }

    public String getUserId() {
	return userId;
    }

    public String getName() {
	return name;
    }

    public String getDesc() {
	return desc;
    }
}
