package org.jeesl.factory.xml.module.ts;

import org.jeesl.model.xml.module.ts.Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBridgeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlBridgeFactory.class);
	
	public static <E extends Enum<E>> Bridge build(E code) {return build(code.toString());}
	public static Bridge build(String code)
	{
		Bridge xml = new Bridge();
		if(code!=null){xml.setCode(code);}
		return xml;
	}
}