package com.hzdc.mng.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class xmlcnt {
    @XmlElementWrapper(name = "Members")
    @XmlElements({ @XmlElement(name = "sub", type = xmlsub.class), @XmlElement(name = "Member", type = xmlpart.class) })
    private List<Object> members = new ArrayList<Object>();

    public xmlcnt() {
	super();
    }

    public void addPart(Object obj) {
	members.add(obj);
    }
}
