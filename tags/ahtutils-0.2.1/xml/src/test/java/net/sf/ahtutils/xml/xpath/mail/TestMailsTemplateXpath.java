package net.sf.ahtutils.xml.xpath.mail;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Template;
import net.sf.ahtutils.xml.mail.TestXmlTemplate;
import net.sf.ahtutils.xml.xpath.MailXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMailsTemplateXpath extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMailsTemplateXpath.class);
    
	private Template xml1,xml2,xml3,xml4;
	private Mail mail;
	
	@Before
	public void initXml()
	{
		mail = new Mail();
		
		xml1 = TestXmlTemplate.create("l1","t1",false);mail.getTemplate().add(xml1);
		xml2 = TestXmlTemplate.create("l2","t2",false);mail.getTemplate().add(xml2);
		xml3 = TestXmlTemplate.create("l3","t3",false);mail.getTemplate().add(xml3);
		xml4 = TestXmlTemplate.create("l3","t3",false);mail.getTemplate().add(xml4);
	}
	
	@Test
	public void testId1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Template actual = MailXpath.getTemplate(mail, xml1.getLang(),xml1.getType());
		assertJaxbEquals(xml1, actual);
	}
	    
	@Test
	public void testId2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Template actual = MailXpath.getTemplate(mail, xml2.getLang(),xml2.getType());
		assertJaxbEquals(xml2, actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		MailXpath.getTemplate(mail, "nullLang", "nullType");
	}
	    
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		MailXpath.getTemplate(mail, xml3.getLang(),xml3.getType());
	}
}