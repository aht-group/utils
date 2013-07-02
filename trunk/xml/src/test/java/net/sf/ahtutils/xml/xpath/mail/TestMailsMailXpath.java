package net.sf.ahtutils.xml.xpath.mail;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.ahtutils.xml.mail.TestXmlMail;
import net.sf.ahtutils.xml.xpath.MailXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMailsMailXpath extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMailsMailXpath.class);
    
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
		JaxbUtil.trace(mails);
	}
	
	@Test
	public void testId1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Mail actual = MailXpath.getMail(mails, xml1.getCode());
		assertJaxbEquals(xml1, actual);
	}
	    
	@Test
	public void testId2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Mail actual = MailXpath.getMail(mails, xml2.getCode());
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
		MailXpath.getMail(mails, xml3.getCode());
	}
}