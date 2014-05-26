package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSummary extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Summary.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Summary actual = create();
    	Summary expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Summary.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Summary create()
    {
    	Summary xml = new Summary();
    	
    	xml.setValue("myReference");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlSummary.initJaxb();
		TestXmlSummary.initFiles();	
		TestXmlSummary test = new TestXmlSummary();
		test.save();
    }
}