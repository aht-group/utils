package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPreCondition extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,PreCondition.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	PreCondition actual = create();
    	PreCondition expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), PreCondition.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static PreCondition create()
    {
    	PreCondition xml = new PreCondition();
    	
    	xml.setValue("myPreCondition");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlPreCondition.initJaxb();
		TestXmlPreCondition.initFiles();	
		TestXmlPreCondition test = new TestXmlPreCondition();
		test.save();
    }
}