package net.sf.ahtutils.mail.content;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.exception.processing.UtilsMailException;
import net.sf.ahtutils.mail.freemarker.FreemarkerEngine;
import net.sf.ahtutils.xml.mail.Attachment;
import net.sf.ahtutils.xml.mail.Mail;

import org.apache.commons.lang.SystemUtils;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import freemarker.template.TemplateException;

public class FreemarkerMimeContentCreator extends AbstractMimeContentCreator
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerMimeContentCreator.class);
	
	private MimeMessage message;
	private FreemarkerEngine fme;
	private static final String encoding = "ISO-8859-1";
	
	public FreemarkerMimeContentCreator(MimeMessage message, FreemarkerEngine fme)
	{
		this.message=message;
		this.fme=fme;
	}
	
	public void createContent(String lang, Document xml, Mail mail) throws MessagingException, UtilsMailException
	{		
		Multipart mpAlternative = new MimeMultipart("alternative");
		
		boolean someContentAvailable = false;
		if(fme.isAvailable(mail.getCode(), lang, "txt"))
		{
			logger.trace("Adding txt body part");
			mpAlternative.addBodyPart(createTxt(lang,xml,mail));
			someContentAvailable=true;
		}
		if(fme.isAvailable(mail.getCode(), lang, "html"))
		{
			logger.trace("Adding html body part");
			mpAlternative.addBodyPart(createHtml(lang,xml,mail));
			someContentAvailable=true;
		}
		if(!someContentAvailable){throw new UtilsMailException("No template available for "+mail.getId()+"/"+lang);}
		
	    if(!mail.isSetAttachment())
	    {
	    	message.setContent(mpAlternative);
	    }
	    else
	    {
	    	Multipart mixed = new MimeMultipart("mixed");
	        MimeBodyPart wrap = new MimeBodyPart();
	        wrap.setContent(mpAlternative);    // HERE'S THE KEY
	        mixed.addBodyPart(wrap);
	       
	        for(Attachment attachment : mail.getAttachment())
	        {
	        	mixed.addBodyPart(createBinary(attachment));
	        }
	        message.setContent(mixed);
	    }
	}
	
	private MimeBodyPart createTxt(String lang, Document xml, Mail mail) throws MessagingException
	{
		fme.createTemplate(mail.getCode(), lang, "txt");
		
		MimeBodyPart txt = new MimeBodyPart();
	
		try
		{
			if(mail.isSetAttachment())
			{
				txt.setContent(fme.process(xml)+SystemUtils.LINE_SEPARATOR, "text/plain; charset=\""+encoding+"\"");
			}
			else{txt.setContent(fme.process(xml), "text/plain; charset=\""+encoding+"\"");}
		}
		catch (SAXException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (ParserConfigurationException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
		
		return txt;
	}
	
	private MimeBodyPart createHtml(String lang,Document xml, Mail mail) throws MessagingException
	{
		fme.createTemplate(mail.getCode(), lang, "html");
		MimeBodyPart html = new MimeBodyPart();
		try
		{
			if(mail.isSetAttachment())
			{
				html.setContent(fme.process(xml)+SystemUtils.LINE_SEPARATOR, "text/html; charset=\""+encoding+"\"");

			}
			else{html.setContent(fme.process(xml), "text/html; charset=\""+encoding+"\"");}
			
		}
		catch (SAXException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (ParserConfigurationException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
		
		return html;
	}
}