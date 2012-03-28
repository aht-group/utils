package net.sf.ahtutils.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class AhtUtilsMimeMessage extends MimeMessage
{
	private String msgId;

	public AhtUtilsMimeMessage(Session session, String msgId)
	{
		super(session);
		this.msgId=msgId;
	}

	@Override
	protected void updateMessageID() throws MessagingException
	{
		this.
		setHeader("Message-ID", msgId);
	}
}

