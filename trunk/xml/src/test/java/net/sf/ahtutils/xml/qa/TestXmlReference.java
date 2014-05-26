package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlReference extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Reference.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Reference actual = create();
    	Reference expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Reference.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Reference create()
    {
    	Reference xml = new Reference();
    	
    	xml.setValue("myReference");

    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlReference.initJaxb();
		TestXmlReference.initFiles();	
		TestXmlReference test = new TestXmlReference();
		test.save();
    }
}