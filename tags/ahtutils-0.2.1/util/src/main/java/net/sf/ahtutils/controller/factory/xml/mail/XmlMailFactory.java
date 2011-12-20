package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMailFactory
{
	final static Logger logger = LoggerFactory.getLogger(EmailAddressXmlFactory.class);
	
    public static Mail create(String id, String lang, String type)
    {
    	Template template = new Template();
    	template.setLang(lang);
    	template.setType(type);
    	
    	Mail mail = new Mail();
    	mail.setId(id);
    	mail.getTemplate().add(template);
    	
    	return mail;
    }
}