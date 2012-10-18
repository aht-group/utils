package net.sf.ahtutils.mail.smtp;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.ahtutils.test.UtilsMailTestBootstrap;
import net.sf.exlp.util.config.ConfigKey;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliSmtpMailSender
{
	final static Logger logger = LoggerFactory.getLogger(CliSmtpMailSender.class);
	
	private String smtpHost,smtpUser,smtpPassword;
	private int smtpPort;
	
	public CliSmtpMailSender(String smtpHost,int smtpPort)
	{
		this.smtpHost=smtpHost;
		this.smtpPort=smtpPort;
	}
	
	public void authenticate(String smtpUser,String smtpPassword)
	{
		this.smtpUser=smtpUser;
		this.smtpPassword=smtpPassword;
	}
	
	public void send(String from, String to) throws MessagingException, UnsupportedEncodingException
	{
		Properties props = System.getProperties();
         
         props.put("mail.smtp.host", smtpHost);
         props.put("mail.smtp.port", smtpPort);
         
         props.put("mail.transport.protocol","smtp");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.tls", "true");
         props.put("mail.smtp.user", smtpUser);
         props.put("mail.password", smtpPassword);
         
         props.put("mail.debug","true");
 
         Authenticator auth = new Authenticator()
         {
             @Override public PasswordAuthentication getPasswordAuthentication()
             {
            	 return new PasswordAuthentication(smtpUser,smtpPassword);
             }
         };
             
        Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(true);
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(RecipientType.TO, new InternetAddress(to));
		
		msg.setSubject("It works!");
        msg.setText("body");
        msg.saveChanges();
		
		Transport.send(msg);
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = UtilsMailTestBootstrap.init();

		CliSmtpMailSender cli = new CliSmtpMailSender(config.getString(ConfigKey.netSmtpHost),config.getInt(ConfigKey.netSmtpPort));
		cli.authenticate(config.getString(ConfigKey.netSmtpUser), config.getString(ConfigKey.netSmtpPwd));
		cli.send(config.getString("net.smtp.test.from"),config.getString("net.smtp.test.to"));
	}
}