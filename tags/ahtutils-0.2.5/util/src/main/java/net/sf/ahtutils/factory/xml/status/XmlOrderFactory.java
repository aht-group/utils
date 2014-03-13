package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOrderFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOrderFactory.class);
		
	public static Order create(String code)
	{
		Order xml = new Order();
		xml.setCode(code);
		return xml;
	}
}