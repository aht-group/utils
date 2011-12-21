package net.sf.ahtutils.xml.xpath.status;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.status.TestXmlTranslation;
import net.sf.ahtutils.xml.status.TestXmlTranslations;
import net.sf.ahtutils.xml.status.Translation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStatusXPathTranslation extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestStatusXPathTranslation.class);
    
	private Translations translations;
	private Translation l1,l2,l3;
	
	@Before
	public void iniDbseed()
	{
		translations = TestXmlTranslations.create(false);

		l1 = TestXmlTranslation.create(false);l1.setKey("ok");translations.getTranslation().add(l1);
		l2 = TestXmlTranslation.create(false);l2.setKey("multi");translations.getTranslation().add(l2);
		l3 = TestXmlTranslation.create(false);l3.setKey("multi");translations.getTranslation().add(l3);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Translation actual = StatusXpath.getTranslation(translations, l1.getKey());
	    Assert.assertEquals(l1,actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		StatusXpath.getTranslation(translations, "-1");
	}
	
	 @Test(expected=ExlpXpathNotUniqueException.class)
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
		 StatusXpath.getTranslation(translations, l2.getKey());
	 }
}