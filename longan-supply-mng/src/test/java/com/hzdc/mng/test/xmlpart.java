package com.hzdc.mng.test;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "p1", "p2", "p3" })
public class xmlpart {
    private String p1 = null;
    private String p2 = null;
    private int p3 = 0;

    public xmlpart() {
	super();
    }

    public xmlpart(String p1, String p2, int p3) {
	this.p1 = p1;
	this.p2 = p2;
	this.p3 = p3;
    }

    public void setP1(String p1) {
	this.p1 = p1;
    }

    public void setP2(String p2) {
	this.p2 = p2;
    }

    public void setP3(int p3) {
	this.p3 = p3;
    }

    public String getP1() {
	return p1;
    }

    public String getP2() {
	return p2;
    }

    public int getP3() {
	return p3;
    }
}
