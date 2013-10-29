package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlClassFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlClassFactory.class);
		
	public static net.sf.ahtutils.xml.status.Class create(String code)
	{
		net.sf.ahtutils.xml.status.Class xml = new net.sf.ahtutils.xml.status.Class();
		xml.setCode(code);
		return xml;
	}
}