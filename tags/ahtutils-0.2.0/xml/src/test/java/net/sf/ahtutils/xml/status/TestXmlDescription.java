package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlDescription extends AbstractXmlStatusTest
{
	static Log logger = LogFactory.getLog(TestXmlDescription.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"description.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Description actual = create();
    	Description expected = (Description)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Description.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Description create(){return create(true);}
    public static Description create(boolean withChilds)
    {
    	Description xml = new Description();
    	xml.setKey("myKey");
    	xml.setValue("myValue");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlDescription.initFiles();	
		TestXmlDescription test = new TestXmlDescription();
		test.save();
    }
}