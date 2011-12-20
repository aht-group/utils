package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.xml.mail.EmailAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailAddressXmlFactory
{
	final static Logger logger = LoggerFactory.getLogger(EmailAddressXmlFactory.class);
		
	public static EmailAddress create(String email){return create(null, email);}
    public static EmailAddress create(String name, String email)
    {
    	EmailAddress xml = new EmailAddress();
    	xml.setName(name);
    	xml.setEmail(email);
    	return xml;
    }
}