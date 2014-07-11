package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCategory extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategory.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Category.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Category actual = create(true);
    	Category expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Category.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Category create(boolean withChilds)
    {
    	Category xml = new Category();
    	xml.setId(123);
    	xml.setName("myName");
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setQa(TestXmlQa.create(false));
    		xml.getTest().add(TestXmlTest.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlCategory.initJaxb();
		TestXmlCategory.initFiles();	
		TestXmlCategory test = new TestXmlCategory();
		test.save();
    }
}