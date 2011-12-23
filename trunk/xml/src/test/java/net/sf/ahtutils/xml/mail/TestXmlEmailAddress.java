package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEmailAddress extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEmailAddress.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"emailAddress.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	EmailAddress actual = createEmailAddress();
    	EmailAddress expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), EmailAddress.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static EmailAddress createEmailAddress() {return createEmailAddress(true);}
    public static EmailAddress createEmailAddress(boolean withChilds)
    {
    	EmailAddress xml = new EmailAddress();
    	xml.setName("myName");
    	xml.setEmail("my@e.mail");
    	    	
    	return xml;
    }
    
    public void save() {save(createEmailAddress(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlEmailAddress.initPrefixMapper();
		TestXmlEmailAddress.initFiles();	
		TestXmlEmailAddress test = new TestXmlEmailAddress();
		test.save();
    }
}