package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBcc extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBcc.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"bcc.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Bcc actual = create();
    	Bcc expected = (Bcc)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Bcc.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Bcc create() {return create(true);}
    public static Bcc create(boolean withChilds)
    {
    	Bcc xml = new Bcc();

    	if(withChilds)
    	{
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlBcc.initPrefixMapper();
		TestXmlBcc.initFiles();	
		TestXmlBcc test = new TestXmlBcc();
		test.save();
    }
}