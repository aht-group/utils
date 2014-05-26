package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSteps extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Steps.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Steps actual = create();
    	Steps expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Steps.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Steps create()
    {
    	Steps xml = new Steps();
    	
    	xml.setValue("mySteps");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlSteps.initJaxb();
		TestXmlSteps.initFiles();	
		TestXmlSteps test = new TestXmlSteps();
		test.save();
    }
}