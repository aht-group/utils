package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlDescriptions extends AbstractXmlStatusTest
{
	static Log logger = LogFactory.getLog(TestXmlDescriptions.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"descriptions.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Descriptions actual = create();
    	Descriptions expected = (Descriptions)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Descriptions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Descriptions create(){return create(true);}
    public static Descriptions create(boolean withChilds)
    {
    	Descriptions xml = new Descriptions();
    	
    	if(withChilds)
    	{
    		xml.getDescription().add(TestXmlDescription.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlDescriptions.initFiles();	
		TestXmlDescriptions test = new TestXmlDescriptions();
		test.save();
    }
}