package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTemplate extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTemplate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"template.xml");
	}
    
    @Test
    public void testTemplate() throws FileNotFoundException
    {
    	Template test = create();
    	Template ref = (Template)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Template.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Template create()
    {
    	Template template = new Template();
    	template.setId("ahtutils-basic-template");
    	template.getElement().add(TestElement.create());
    	template.getField().add(TestField.create());
    	return template;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestTemplate.initJaxb();
		TestTemplate.initFiles();	
		TestTemplate test = new TestTemplate();
		test.save();
    }
}