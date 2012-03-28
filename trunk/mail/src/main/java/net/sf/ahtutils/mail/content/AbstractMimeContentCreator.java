package net.sf.ahtutils.mail.content;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import net.sf.ahtutils.xml.mail.Attachment;

public class AbstractMimeContentCreator
{
	protected MimeBodyPart createBinary(Attachment attachment) throws MessagingException
	{
		MimeBodyPart binary = new MimeBodyPart();
		DataSource source = new ByteArrayDataSource(attachment.getData(), attachment.getFile().getMime());
		binary.setDataHandler(new DataHandler(source));
		binary.setFileName(attachment.getFile().getName());
		return binary;
	}
}
