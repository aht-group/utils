package net.sf.ahtutils.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.ahtutils.mail.content.XmlMimeContentCreator;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.exlp.util.xml.JaxbUtil;

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
}