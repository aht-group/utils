package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Resources;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestResources extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestResources.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"resources.xml");
	}
    
    @Test
    public void testResources() throws FileNotFoundException
    {
    	Resources test = createResources();
    	Resources ref = (Resources)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Resources.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Resources resources = createResources();
    	JaxbUtil.debug2(this.getClass(),resources, nsPrefixMapper);
    	JaxbUtil.save(fXml, resources, nsPrefixMapper, true);
    }
    
    public static Resources createResources()
    {
    	Resources resources = new Resources();
    	resources.setDir("src/main/resources/report/resources");
    	resources.getResource().add(TestResource.createResource());
    	return resources;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resourcess/config");
			loggerInit.init();		
			
		TestResources.initPrefixMapper();
		TestResources.initFiles();	
		TestResources test = new TestResources();
		test.save();
    }
}