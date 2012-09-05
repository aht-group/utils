package net.sf.ahtutils.controller.factory.xml.security;

import net.sf.ahtutils.xml.security.Rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleFactory.class);
		
	public static Rule build(boolean valid, String type, String code, int min, Integer max, int acutal)
	{
		Rule xml = new Rule();
		xml.setValid(valid);
		xml.setType(type);
		xml.setCode(code);
		xml.setMin(min);
		xml.setMax(max);
		xml.setActual(acutal);
		return xml;
	}
}