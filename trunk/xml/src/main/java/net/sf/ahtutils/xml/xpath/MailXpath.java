package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailXpath
{
	static Log logger = LogFactory.getLog(MailXpath.class);

	public static synchronized Mail getMail(Mails mails, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(mails);
		
		@SuppressWarnings("unchecked")
		List<Mail> listResult = (List<Mail>)context.selectNodes("mail[@id='"+id+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Mail.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Mail.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
}