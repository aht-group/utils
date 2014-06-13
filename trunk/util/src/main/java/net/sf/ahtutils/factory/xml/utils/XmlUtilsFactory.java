package net.sf.ahtutils.factory.xml.utils;

import net.sf.ahtutils.xml.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtilsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUtilsFactory.class);
		
	public static Utils build()
	{
		Utils xml = new Utils();
		return xml;
	}
}