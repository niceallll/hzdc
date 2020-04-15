package com.hzdc.mng.test;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
public class xmlroot {
    private String str;
    private int i;
    private Long l;

    @XmlElement(name = "Header")
    private xmlhead head = null;

    @XmlElement(name = "Content")
    private xmlcnt cnt = new xmlcnt();

    // private xmlresp cnt = new xmlresp();

    public xmlroot() {
	super();
    }

    public xmlroot(String id, String user, String name, String desc) {
	head = new xmlhead(id, user, name, desc);
    }

    public void addPart(Object obj) {
	cnt.addPart(obj);
    }

    public xmlhead getHead() {
	return head;
    }

    public xmlcnt getCnt() {
	return cnt;
    }
}
