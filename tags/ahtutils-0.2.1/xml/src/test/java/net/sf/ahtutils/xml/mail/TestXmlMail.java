package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMail extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMail.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"mail.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Mail actual = create();
    	Mail expected = (Mail)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Mail.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Mail create() {return create(true);}
    public static Mail create(boolean withChilds){return create("myId", withChilds);}
    public static Mail create(String id, boolean withChilds)
    {
    	Mail xml = new Mail();
    	xml.setId(id);
    	xml.setMsgId("myMsgID");
    	xml.setDir("myDir");
    	xml.setExample("myExample");
    	xml.setTest(true);
    	
    	if(withChilds)
    	{
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    		xml.getAttachment().add(TestXmlAttachment.create(false));
    		xml.getAttachment().add(TestXmlAttachment.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlMail.initPrefixMapper();
		TestXmlMail.initFiles();	
		TestXmlMail test = new TestXmlMail();
		test.save();
    }
}