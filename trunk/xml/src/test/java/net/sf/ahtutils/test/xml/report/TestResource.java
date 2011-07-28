package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Resource;
import net.sf.ahtutils.xml.report.Resource.Value;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestResource extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestResource.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"resource.xml");
	}
    
    @Test
    public void testResource() throws FileNotFoundException
    {
    	Resource test = createResource();
    	Resource ref = (Resource)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Resource.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Resource resource = createResource();
    	JaxbUtil.debug2(this.getClass(),resource, nsPrefixMapper);
    	JaxbUtil.save(fXml, resource, nsPrefixMapper, true);
    }
    
    public static Resource createResource()
    {
    	Resource resource = new Resource();
    	resource.setName("logo");
    	resource.setType("image");
    	Value myValue = new Resource.Value();
    	myValue.setValue("logo.png");
    	resource.setValue(myValue);
    	return resource;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestResource.initPrefixMapper();
		TestResource.initFiles();	
		TestResource test = new TestResource();
		test.save();
    }
}