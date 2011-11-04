package net.sf.ahtutils.xml.xpath.mail;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.ahtutils.xml.mail.TestXmlMail;
import net.sf.ahtutils.xml.xpath.MailXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TestMailsMailXpath extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestMailsMailXpath.class);
    
	private Mail xml1,xml2,xml3,xml4;
	private Mails mails;
	
	@Before
	public void iniMedia()
	{
		mails = new Mails();
		
		xml1 = TestXmlMail.create("t1",false);mails.getMail().add(xml1);
		xml2 = TestXmlMail.create("t2",false);mails.getMail().add(xml2);
		xml3 = TestXmlMail.create("t3",false);mails.getMail().add(xml3);
		xml4 = TestXmlMail.create("t3",false);mails.getMail().add(xml4);
	}
	
	@Test
	public void testId1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Mail actual = MailXpath.getMail(mails, xml1.getId());
		assertJaxbEquals(xml1, actual);
	}
	    
	@Test
	public void testId2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Mail actual = MailXpath.getMail(mails, xml2.getId());
		assertJaxbEquals(xml2, actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		MailXpath.getMail(mails, "nullCode");
	}
	    
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		MailXpath.getMail(mails, xml3.getId());
	}
}