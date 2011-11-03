package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJr extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestJr.class);
		
	private static File fXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"jr.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Jr test = createJr();
    	Jr ref = (Jr)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Jr.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Jr jr = createJr();
    	JaxbUtil.debug2(this.getClass(),jr,nsPrefixMapper);
    	JaxbUtil.save(fXml, jr,nsPrefixMapper, true);
    }
    
    public static Jr createJr()
    {
    	Jr jr = new Jr();
    	jr.setName("testReportName");
    	jr.setType("mr");
    	return jr;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestJr.initPrefixMapper();
		TestJr.initFiles();	
		TestJr test = new TestJr();
		test.save();
    }
}