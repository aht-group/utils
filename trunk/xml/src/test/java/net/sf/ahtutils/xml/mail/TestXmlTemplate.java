package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlTemplate extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlTemplate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"template.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Template actual = create();
    	Template expected = (Template)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Template.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Template create() {return create(true);}
    public static Template create(boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setFile("myFile");
    	xml.setLang("myLang");
    	xml.setType("myType");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlTemplate.initPrefixMapper();
		TestXmlTemplate.initFiles();	
		TestXmlTemplate test = new TestXmlTemplate();
		test.save();
    }
}