package net.sf.ahtutils.xml.navigation;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlNavigation extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlNavigation.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "navigation");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Navigation actual = create();
    	Navigation expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Navigation.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Navigation create() {return create(true);}
    public static Navigation create(boolean withChilds)
    {
    	Navigation xml = new Navigation();
    	xml.setCode("myCode");
    	xml.setPackage("myPackage");
    	
    	if(withChilds)
    	{
    		xml.setViewPattern(TestXmlViewPattern.create(false));
        	xml.setUrlMapping(TestXmlUrlMapping.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlNavigation.initJaxb();
		TestXmlNavigation.initFiles();	
		TestXmlNavigation test = new TestXmlNavigation();
		test.save();
    }
}