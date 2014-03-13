package net.sf.ahtutils.xml.navigation;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViewPattern extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViewPattern.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "viewPattern");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	ViewPattern actual = create();
    	ViewPattern expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ViewPattern.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static ViewPattern create() {return create(true);}
    public static ViewPattern create(boolean withChilds)
    {
    	ViewPattern xml = new ViewPattern();
    	xml.setValue("myViewPattern");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlViewPattern.initJaxb();
		TestXmlViewPattern.initFiles();	
		TestXmlViewPattern test = new TestXmlViewPattern();
		test.save();
    }
}