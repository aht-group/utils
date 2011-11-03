package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlLangs extends AbstractXmlStatusTest
{
	static Log logger = LogFactory.getLog(TestXmlLangs.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"langs.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Langs actual = create();
    	Langs expected = (Langs)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Langs.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Langs create(){return create(true);}
    public static Langs create(boolean withChilds)
    {
    	Langs xml = new Langs();
    	
    	if(withChilds)
    	{
    		xml.getLang().add(TestXmlLang.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlLangs.initFiles();	
		TestXmlLangs test = new TestXmlLangs();
		test.save();
    }
}