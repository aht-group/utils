package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;
import net.sf.ahtutils.test.AbstractTgXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJr extends AbstractTgXmlTest
{
	static Log logger = LogFactory.getLog(TestJr.class);
	
	private static final String rootDir = "src/test/resources/data/xml/report/jr";
	
	private static File fJr;
	
	@BeforeClass
	public static void initFiles()
	{
		fJr = new File(rootDir,"jr.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Jr test = createJr();
    	Jr ref = (Jr)JaxbUtil.loadJAXB(fJr.getAbsolutePath(), Jr.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Jr jr = createJr();
    	JaxbUtil.debug2(this.getClass(),jr, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fJr, jr, new AhtUtilsNsPrefixMapper(), true);
    	
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
			
		TestJr.initFiles();	
		TestJr test = new TestJr();
		test.save();
    }
}