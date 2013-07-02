package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlValue extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlValue.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Value.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Value actual = create();
    	Value expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Value.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Value create()
    {
    	Value xml = new Value();
    	xml.setValue("myValue");
    	xml.setType("myType");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlValue.initPrefixMapper();
		TestXmlValue.initFiles();	
		TestXmlValue test = new TestXmlValue();
		test.save();
    }
}