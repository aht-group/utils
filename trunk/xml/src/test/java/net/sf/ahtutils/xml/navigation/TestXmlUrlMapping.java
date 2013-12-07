package net.sf.ahtutils.xml.navigation;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUrlMapping extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUrlMapping.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"urlMapping.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	UrlMapping actual = create();
    	UrlMapping expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), UrlMapping.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static UrlMapping create() {return create(true);}
    public static UrlMapping create(boolean withChilds)
    {
    	UrlMapping xml = new UrlMapping();
    	xml.setValue("myUrlMapping");
    	xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUrlMapping.initJaxb();
		TestXmlUrlMapping.initFiles();	
		TestXmlUrlMapping test = new TestXmlUrlMapping();
		test.save();
    }
}