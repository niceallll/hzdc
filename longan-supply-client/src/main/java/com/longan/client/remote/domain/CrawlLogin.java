package com.longan.client.remote.domain;

import java.io.Serializable;

public class CrawlLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pos;
    private String rsakey;
    private String user;
    private String pwd;
    private String rand;
    private String msg;
    private String file;

    public Integer getPos() {
	return pos;
    }

    public void setPos(Integer pos) {
	this.pos = pos;
    }

    public String getRsakey() {
	return rsakey;
    }

    public void setRsakey(String rsakey) {
	this.rsakey = rsakey;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPwd() {
	return pwd;
    }

    public void setPwd(String pwd) {
	this.pwd = pwd;
    }

    public String getRand() {
	return rand;
    }

    public void setRand(String rand) {
	this.rand = rand;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public String getFile() {
	return file;
    }

    public void setFile(String file) {
	this.file = file;
    }
}
