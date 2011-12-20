package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAttachment extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAttachment.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"attachment.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Attachment actual = create();
    	Attachment expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Attachment.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Attachment create() {return create(true);}
    public static Attachment create(boolean withChilds)
    {
    	Attachment xml = new Attachment();
    	
    	if(withChilds)
    	{
    		xml.setData("myBinaryData".getBytes());
    		xml.setFile(new net.sf.exlp.xml.io.File());
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlAttachment.initPrefixMapper();
		TestXmlAttachment.initFiles();	
		TestXmlAttachment test = new TestXmlAttachment();
		test.save();
    }
}