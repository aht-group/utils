package net.sf.ahtutils.mail;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.mail.EmailAddress;
import net.sf.ahtutils.xml.mail.From;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.To;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUtilsMailCreator
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsMailCreator.class);
		
	protected String lang;
	protected Header header;
	protected String mailId;
	
	protected boolean doneInjection,doneWork;
	
	public AbstractUtilsMailCreator(String mailId)
	{
		lang = "de";
		this.mailId=mailId;
		doneInjection=false;
		doneWork=false;
	}
	
	protected void setInjections(){doneInjection=true;}
	protected void setWork(){doneWork=true;}
	
	protected void checkPreconditions() throws UtilsConfigurationException
	{
		if(doneInjection==false){throw new UtilsConfigurationException("No Injections done");}
		if(doneWork==false){throw new UtilsConfigurationException("No Work defined");}
	}
	
	protected void createHeader(String subject, EmailAddress fromAddress)
	{
		header = new Header();
		header.setSubject(subject);
				
		From from = new From();
		from.setEmailAddress(fromAddress);
		header.setFrom(from);		
		
		header.setTo(new To());
	}
}