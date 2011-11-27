package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFrom extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFrom.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"from.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	From actual = createFrom();
    	From expected = (From)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), From.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static From createFrom() {return createFrom(true);}
    public static From createFrom(boolean withChilds)
    {
    	From xml = new From();

    	if(withChilds)
    	{
    		xml.setEmailAddress(TestXmlEmailAddress.createEmailAddress(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(createFrom(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlFrom.initPrefixMapper();
		TestXmlFrom.initFiles();	
		TestXmlFrom test = new TestXmlFrom();
		test.save();
    }
}