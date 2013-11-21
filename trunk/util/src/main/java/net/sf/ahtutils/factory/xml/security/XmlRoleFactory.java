package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.xml.security.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	public static Role create(String code, String label)
	{
		Role xml = new Role();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
		
	}
}