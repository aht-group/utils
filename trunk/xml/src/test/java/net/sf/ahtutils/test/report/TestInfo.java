package net.sf.ahtutils.test.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractTgXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.Info;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestInfo extends AbstractTgXmlTest
{
	static Log logger = LogFactory.getLog(TestInfo.class);
	
	private static final String rootDir = "src/test/resources/data/xml/info/info";
	
	private static File fInfo;
	
	@BeforeClass
	public static void initFiles()
	{
		fInfo = new File(rootDir,"info.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Info test = createInfo();
    	Info ref = (Info)JaxbUtil.loadJAXB(fInfo.getAbsolutePath(), Info.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Info info = createInfo();
    	JaxbUtil.debug2(this.getClass(),info, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fInfo, info, new AhtUtilsNsPrefixMapper(), true);
    	
    }
    
    public static Info createInfo()
    {
    	Info info = new Info();
    	info.setCode("myCode");
    	return info;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestInfo.initFiles();	
		TestInfo test = new TestInfo();
		test.save();
    }
}