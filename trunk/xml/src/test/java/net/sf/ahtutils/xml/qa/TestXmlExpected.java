package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlExpected extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Expected.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Expected actual = create();
    	Expected expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Expected.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Expected create()
    {
    	Expected xml = new Expected();
    	
    	xml.setValue("myExpected");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlExpected.initJaxb();
		TestXmlExpected.initFiles();	
		TestXmlExpected test = new TestXmlExpected();
		test.save();
    }
}