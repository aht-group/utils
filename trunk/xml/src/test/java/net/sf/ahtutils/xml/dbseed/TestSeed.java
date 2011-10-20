package net.sf.ahtutils.xml.dbseed;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSeed extends AbstractXmlDbseedTest
{
	static Log logger = LogFactory.getLog(TestSeed.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"seed.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Seed actual = createSeed();
    	Seed expected = (Seed)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Seed.class);
    	assertJaxbEquals(expected, actual);
    }
 
    public static Seed createSeed(){return createSeed(true);}
    public static Seed createSeed(boolean withChilds)
    {
    	Seed xml = new Seed();
    	xml.setCode("myCode");
    	xml.setTemplate("myTemplate");
    	xml.setContent("myContent");
    	return xml;
    }
    
    public void save() {save(createSeed(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestSeed.initPrefixMapper();
		TestSeed.initFiles();	
		TestSeed test = new TestSeed();
		test.save();
    }
}