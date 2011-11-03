package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlCc extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlCc.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cc.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Cc actual = create();
    	Cc expected = (Cc)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Cc.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Cc create() {return create(true);}
    public static Cc create(boolean withChilds)
    {
    	Cc xml = new Cc();

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
			
		TestXmlCc.initPrefixMapper();
		TestXmlCc.initFiles();	
		TestXmlCc test = new TestXmlCc();
		test.save();
    }
}