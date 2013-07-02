package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTemplates extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTemplates.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"templates.xml");
	}
    
    @Test
    public void testTemplates() throws FileNotFoundException
    {
    	Templates test = create();
    	Templates ref = (Templates)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Templates.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Templates create()
    {
    	Templates templates = new Templates();
    	templates.getTemplate().add(TestTemplate.create());
    	return templates;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestTemplates.initJaxb();
		TestTemplates.initFiles();	
		TestTemplates test = new TestTemplates();
		test.save();
    }
}