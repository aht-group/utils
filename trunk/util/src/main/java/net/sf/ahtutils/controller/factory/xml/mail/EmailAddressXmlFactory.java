package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.xml.mail.EmailAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailAddressXmlFactory
{
	static Log logger = LogFactory.getLog(EmailAddressXmlFactory.class);
		
    public static EmailAddress create(String name, String email)
    {
    	EmailAddress xml = new EmailAddress();
    	xml.setName(name);
    	xml.setEmail(email);
    	return xml;
    }
}