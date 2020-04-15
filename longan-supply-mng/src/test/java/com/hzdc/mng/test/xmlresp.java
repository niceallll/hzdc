package com.hzdc.mng.test;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class xmlresp {
    private String p1 = null;
    private String p2 = null;

    public xmlresp() {
	super();
    }

    public void addPart(Object obj) {
    }

    public void setP1(String p1) {
	this.p1 = p1;
    }

    public void setP2(String p2) {
	this.p2 = p2;
    }

    public String getP1() {
	return p1;
    }

    public String getP2() {
	return p2;
    }
}
