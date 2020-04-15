package com.hzdc.mng.test;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class testxml {
    public static void main(String[] args) throws Exception {
	// obj2xml();
    }

    private static void obj2xml() {
	try {
	    JAXBContext jc = JAXBContext.newInstance(xmlroot.class);
	    Marshaller ms = jc.createMarshaller();
	    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    xmlroot st = new xmlroot("id", "user", "name", "desc");
	    st.addPart(new xmlpart("p1", "p2", 1));
	    st.addPart(new xmlpart("p11", "p22", 2));
	    StringWriter sw = new StringWriter();
	    ms.marshal(st, sw);
	    System.out.println(sw.toString().replace(" standalone=\"yes\"", ""));
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }

    private static void xml2obj() {
	try {
	    JAXBContext jc = JAXBContext.newInstance(xmlroot.class);
	    Unmarshaller ums = jc.createUnmarshaller();
	    xmlroot st = (xmlroot) ums.unmarshal(new File("c:/temp/testresp.xml"));
	    System.out.println(st.getHead().getCommandId());
	    System.out.println(st.getHead().getUserId());
	    System.out.println(st.getHead().getName());
	    System.out.println(st.getHead().getDesc());
	    // System.out.println(st.getCnt().getP1());
	    // System.out.println(st.getCnt().getP2());
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }
}
