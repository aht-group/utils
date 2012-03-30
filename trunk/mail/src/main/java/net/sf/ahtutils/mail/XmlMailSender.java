package net.sf.ahtutils.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.mail.content.FreemarkerMimeContentCreator;
import net.sf.ahtutils.mail.content.XmlMimeContentCreator;
import net.sf.ahtutils.mail.freemarker.FreemarkerEngine;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMailSender
{
	final static Logger logger = LoggerFactory.getLogger(XmlMailSender.class);
	
	private String smtpHost;

	public XmlMailSender(String smtpHost)
	{
		this.smtpHost=smtpHost;
	}
	
	public void send(Mail mail) throws MessagingException, UnsupportedEncodingException
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mail.getHeader().getFrom().getEmailAddress().getEmail()));

		MimeMessageCreator mmc = new MimeMessageCreator(msg);
		mmc.createHeader(mail.getHeader());
		
		XmlMimeContentCreator mcc = new XmlMimeContentCreator(msg);
		mcc.createContent(mail);
		
		Transport.send(msg);
	}
	
	public void send(FreemarkerEngine fme, String lang, Document doc) throws UnsupportedEncodingException, MessagingException, UtilsProcessingException
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
//		props.put("mail.smtp.port", "25");
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);
		MimeMessage message = new MimeMessage(session);
		MimeMessageCreator mmc = new MimeMessageCreator(message);
		
		mmc.createHeader(getHeader(doc.getRootElement()));

		Mail mail = getMailAndDetachAtt(doc.getRootElement());
				
		FreemarkerMimeContentCreator mcc = new FreemarkerMimeContentCreator(message, fme);
		mcc.createContent("de",doc,mail);
		
		Transport transport = session.getTransport("smtp");
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
	}
	
	private Header getHeader(Element root) throws UtilsProcessingException
	{
		logger.trace("Parsing header");
		for(Object o: root.getContent())
		{
			Element e = (Element)o;
			if(e.getName().equals("header"))
			{
				return JDomUtil.toJaxb(e, Header.class);
			}
		}
		throw new UtilsProcessingException("No <header> Element found");
	}
	
	private Mail getMailAndDetachAtt(Element root) throws UtilsProcessingException
	{
		logger.trace("Parsing Mail");
		for(Object o: root.getContent())
		{
			Element e = (Element)o;
			if(e.getName().equals("mail"))
			{
				Mail mail = JDomUtil.toJaxb(e, Mail.class);
				logger.warn("Detach att NYI");
//				e.detach();
				return mail;
			}
		}
		throw new UtilsProcessingException("No <mail> Element found");
	}
}