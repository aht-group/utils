package org.jeesl.factory.xml.system.io.ssi.docker;

import org.jeesl.model.xml.system.io.ssi.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlContainerFactory.class);
	
	public static Container build() {return new Container();}
	
	public static Container build(String code, String status)
	{
		Container xml = build();
		xml.setCode(code);
		xml.setStatus(status);
		return xml;
	}
}