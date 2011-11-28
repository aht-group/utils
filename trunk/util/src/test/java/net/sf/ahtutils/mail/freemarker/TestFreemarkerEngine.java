package net.sf.ahtutils.mail.freemarker;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.controller.exception.AhtUtilsDeveloperException;
import net.sf.ahtutils.test.AbstractFileProcessingTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import freemarker.template.TemplateException;

public class TestFreemarkerEngine extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFreemarkerEngine.class);
	
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