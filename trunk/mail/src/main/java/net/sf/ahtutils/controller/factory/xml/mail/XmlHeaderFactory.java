package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.xml.mail.From;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.To;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHeaderFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
	
    public static Header create(String subject, String from, String to)
    {
    	Header xml = new Header();
    	xml.setSubject(subject);
    	
    	From xmlFrom = new From();
    	xmlFrom.setEmailAddress(XmlEmailAddressFactory.create(from));
    	xml.setFrom(xmlFrom);
    	
    	To xmlTo = new To();
    	xmlTo.getEmailAddress().add(XmlEmailAddressFactory.create(to));
    	xml.setTo(xmlTo);
    	
    	return xml;
    }
}