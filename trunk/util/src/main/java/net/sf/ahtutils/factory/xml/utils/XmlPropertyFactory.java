package net.sf.ahtutils.factory.xml.utils;

import net.sf.ahtutils.xml.utils.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyFactory.class);
		
	public static Property build(String code, String  value, boolean frozen)
	{
		Property xml = new Property();
		xml.setKey(code);
		xml.setValue(value);
		xml.setFrozen(frozen);
		return xml;
	}
}