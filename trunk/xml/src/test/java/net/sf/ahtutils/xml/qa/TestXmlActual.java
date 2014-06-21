package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlActual extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Actual.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Actual actual = create();
    	Actual expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Actual.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Actual create()
    {
    	Actual xml = new Actual();
    	xml.setValue("myActual");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlActual.initJaxb();
		TestXmlActual.initFiles();	
		TestXmlActual test = new TestXmlActual();
		test.save();
    }
}