package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlMails extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlMails.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"mails.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Mails actual = create();
    	Mails expected = (Mails)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Mails.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Mails create() {return create(true);}
    public static Mails create(boolean withChilds)
    {
    	Mails xml = new Mails();
    	xml.setDir("myDir");
    	
    	if(withChilds)
    	{
    		xml.getMail().add(TestXmlMail.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlMails.initPrefixMapper();
		TestXmlMails.initFiles();	
		TestXmlMails test = new TestXmlMails();
		test.save();
    }
}