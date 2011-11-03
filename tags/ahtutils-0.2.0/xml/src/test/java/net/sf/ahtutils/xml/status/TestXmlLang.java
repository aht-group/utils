package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlLang extends AbstractXmlStatusTest
{
	static Log logger = LogFactory.getLog(TestXmlLang.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"lang.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Lang actual = create();
    	Lang expected = (Lang)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Lang.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Lang create(){return create(true);}
    public static Lang create(boolean withChilds)
    {
    	Lang xml = new Lang();
    	xml.setKey("myKey");
    	xml.setTranslation("myTranslation");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlLang.initFiles();	
		TestXmlLang test = new TestXmlLang();
		test.save();
    }
}