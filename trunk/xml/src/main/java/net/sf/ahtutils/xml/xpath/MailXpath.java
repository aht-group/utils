package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.ahtutils.xml.mail.Template;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailXpath
{
	final static Logger logger = LoggerFactory.getLogger(MailXpath.class);

	public static synchronized Mail getMail(Mails mails, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(mails);
		
		@SuppressWarnings("unchecked")
		List<Mail> listResult = (List<Mail>)context.selectNodes("mail[@id='"+id+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Mail.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Mail.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	public static synchronized Template getTemplate(Mail mail, String lang, String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(mail);
		
		@SuppressWarnings("unchecked")
		List<Template> listResult = (List<Template>)context.selectNodes("template[@lang='"+lang+"' and @type='"+type+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Template.class.getSimpleName()+" for lang="+lang+" and type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Template.class.getSimpleName()+" for lang="+lang+" and type="+type);}
		return listResult.get(0);
	}
}