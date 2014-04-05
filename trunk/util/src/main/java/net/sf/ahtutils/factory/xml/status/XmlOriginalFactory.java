package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOriginalFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOriginalFactory.class);
		
	public static net.sf.ahtutils.xml.status.Class create(String code)
	{
		net.sf.ahtutils.xml.status.Class xml = new net.sf.ahtutils.xml.status.Class();
		xml.setCode(code);
		return xml;
	}
}