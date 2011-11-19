package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Template;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlMailFactory
{
	static Log logger = LogFactory.getLog(XmlMailFactory.class);
	
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