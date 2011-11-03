package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlTranslations extends AbstractXmlStatusTest
{
	static Log logger = LogFactory.getLog(TestXmlTranslations.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"translations.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Translations actual = create();
    	Translations expected = (Translations)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Translations.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Translations create(){return create(true);}
    public static Translations create(boolean withChilds)
    {
    	Translations xml = new Translations();
    	
    	if(withChilds)
    	{
    		xml.getTranslation().add(TestXmlTranslation.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlTranslations.initFiles();	
		TestXmlTranslations test = new TestXmlTranslations();
		test.save();
    }
}