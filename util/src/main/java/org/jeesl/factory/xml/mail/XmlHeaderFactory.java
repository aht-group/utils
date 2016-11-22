package org.jeesl.factory.xml.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.EmailAddress;
import net.sf.ahtutils.xml.mail.From;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.To;

public class XmlHeaderFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
	
	public XmlHeaderFactory()
	{
		
	}
	
    public static Header create(String subject, String from, String to)
    {
    	return build(subject,XmlEmailAddressFactory.create(from),XmlEmailAddressFactory.create(to));
    }
    
    public static Header build(String subject, EmailAddress from, EmailAddress to)
    {
    	Header xml = new Header();
    	xml.setSubject(subject);
    	
    	From xmlFrom = new From();
    	xmlFrom.setEmailAddress(from);
    	xml.setFrom(xmlFrom);
    	
    	To xmlTo = new To();
    	xmlTo.getEmailAddress().add(to);
    	xml.setTo(xmlTo);
    	
    	return xml;
    }
    
    public Header build(Message message) throws MessagingException
    {
    	Header xml = new Header();
    	xml.setSubject(message.getSubject());
    	
    	XmlFromFactory f = new XmlFromFactory();
    	xml.setFrom(f.build(message));
    	
    	return xml;
    }
}