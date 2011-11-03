package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlTranslation extends AbstractXmlStatusTest
{
static Log logger = LogFactory.getLog(TestXmlLang.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"translation.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Translation actual = create();
    	Translation expected = (Translation)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Translation.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Translation create(){return create(true);}
    public static Translation create(boolean withChilds)
    {
    	Translation xml = new Translation();
    	xml.setKey("myKey");

    	if(withChilds)
    	{
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlTranslation.initFiles();	
		TestXmlTranslation test = new TestXmlTranslation();
		test.save();
    }
}