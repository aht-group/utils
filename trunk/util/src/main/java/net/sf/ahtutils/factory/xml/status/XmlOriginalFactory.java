package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Original;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOriginalFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOriginalFactory.class);
		
	public static Original build(String code)
	{
		Original xml = new Original();
		xml.setCode(code);
		return xml;
	}
}