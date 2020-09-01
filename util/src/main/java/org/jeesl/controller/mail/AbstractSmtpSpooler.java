package org.jeesl.controller.mail;

import javax.mail.MessagingException;

import org.apache.commons.cli.Option;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRest;
import org.jeesl.factory.txt.system.io.mail.core.TxtMailFactory;
import org.jeesl.mail.smtp.TextMailSender;
import org.jeesl.model.xml.system.io.mail.Attachment;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractSmtpSpooler
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSmtpSpooler.class);
	
	protected UtilsCliOption jco;
	protected JeeslIoMailRest rest;
	
	protected TextMailSender smtp;
	
	protected Option oUrl,oSmtp;
	protected String cfgUrl,cfgSmtp;
	
	public AbstractSmtpSpooler()
	{
		
	}
	
	protected void buildSmtp(String smtpHost)
	{
		smtp = new TextMailSender(smtpHost);
		smtp.setSmtpDebug(false);
//		smtp.tlsPasswordAuthentication(config.getString(ConfigKey.netSmtpUser),config.getString(ConfigKey.netSmtpPwd));
//		templateMailer.plainPasswordAuthentication(config.getString(ConfigKey.netSmtpUser),config.getString(ConfigKey.netSmtpPwd));
		smtp.debugSettings();
	}
	
	protected void createOptions()
	{
		jco.buildHelp();
		jco.buildDebug();
        
        oUrl = Option.builder("url").required(true).hasArg(true).argName("URL").desc("URL Endpoint").build(); jco.getOptions().addOption(oUrl);
        oSmtp = Option.builder("smtp").required(true).hasArg(true).argName("HOST").desc("SMTP HOST (at the moment, without auth").build(); jco.getOptions().addOption(oSmtp);
	}
	
	protected void debugConfig()
	{
		logger.info("URL: "+cfgUrl);
		logger.info("SMTP: "+cfgSmtp);
	} 
	
	protected void debug(boolean txt, boolean jaxb, boolean confirm)
	{
		Mails mails = rest.spool();
		for(Mail mail : mails.getMail())
		{
			sanitize(mail);
			if(!mail.getAttachment().isEmpty())
			{
				for(Attachment att : mail.getAttachment()) {att.setData(null);}
			}
			if(jaxb) {JaxbUtil.info(mail);}
			if(txt) {logger.info(TxtMailFactory.debug(mail));}
			if(confirm) {rest.confirm(mail.getId());}
		}
	}
	
	protected Integer spool() throws Exception
	{
		Mails mails = rest.spool();
		for(Mail mail : mails.getMail())
		{
			sanitize(mail);
			try
			{
				if(!hasVeto(mail))
				{
					logger.info(TxtMailFactory.debug(mail));
					smtp.send(mail);
				}
				else
				{
					logger.warn("Veto: "+TxtMailFactory.debug(mail));
				}
				rest.confirm(mail.getId());
			}
			catch (MessagingException e) {e.printStackTrace();}
		}
		if(mails.isSetQueue()){return mails.getQueue();}
		else {return Integer.MAX_VALUE;}
	}
	
	public void sanitize(Mail mail) {}
	public boolean hasVeto(Mail mail) {return false;}
	
	public void spooler()
	{
		Integer queue;
		while(true)
		{
			int sleepMs = 1;
			try
			{
				queue = spool();
				if(queue==0){sleepMs = 60*1000;}
				logger.info("Queue:"+queue+" Sleeping:"+sleepMs);
			}
			catch (Exception e1) {sleepMs = 60*1000;}
			try{Thread.sleep(sleepMs);}catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}