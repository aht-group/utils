package net.sf.ahtutils.xml.navigation;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBreadcrumb extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBreadcrumb.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Breadcrumb.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Breadcrumb actual = create();
    	Breadcrumb expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Breadcrumb.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Breadcrumb create() {return create(true);}
    public static Breadcrumb create(boolean withChilds)
    {
    	Breadcrumb xml = new Breadcrumb();
    	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlBreadcrumb.initJaxb();
		TestXmlBreadcrumb.initFiles();	
		TestXmlBreadcrumb test = new TestXmlBreadcrumb();
		test.save();
    }
}