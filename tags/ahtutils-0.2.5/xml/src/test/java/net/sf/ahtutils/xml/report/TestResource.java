package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.report.Resource.Value;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResource extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestResource.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"resource.xml");
	}
    
    @Test
    public void testResource() throws FileNotFoundException
    {
    	Resource test = create();
    	Resource ref = (Resource)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Resource.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Resource create()
    {
    	Resource resource = new Resource();
    	resource.setName("logo");
    	resource.setType("image");
    	Value myValue = new Resource.Value();
    	myValue.setValue("logo.png");
    	resource.setValue(myValue);
    	return resource;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestResource.initJaxb();
		TestResource.initFiles();	
		TestResource test = new TestResource();
		test.save();
    }
}