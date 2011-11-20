package net.sf.ahtutils.mail.freemarker;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.controller.exception.AhtUtilsDeveloperException;
import net.sf.ahtutils.controller.factory.xml.cloud.facebook.TestSignedRequestFactory;
import net.sf.ahtutils.test.AbstractFileProcessingTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import freemarker.template.TemplateException;

public class TestFreemarkerEngine extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestSignedRequestFactory.class);
	
	private FreemarkerEngine fme;
	
	
	@Before
	public void init()
	{	
		fme = new FreemarkerEngine(null);
	}
	
	@After
	public void close()
	{
		fme = null;
	}
    
    @Test(expected=AhtUtilsDeveloperException.class)
    public void devException() throws SAXException, IOException, ParserConfigurationException, TemplateException
    {
    	fme.process("test");
    }
	
}