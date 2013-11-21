package net.sf.ahtutils.factory.xml.project;

import net.sf.ahtutils.xml.project.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUserFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserFactory.class);
		
	public static User create(String firstName, String lastName)
	{
		User xml = new User();
		xml.setFirstName(firstName);
		xml.setLastName(lastName);
		return xml;
		
	}
}