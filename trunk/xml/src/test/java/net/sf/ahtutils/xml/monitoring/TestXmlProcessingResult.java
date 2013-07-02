package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlProcessingResult extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlProcessingResult.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,ProcessingResult.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	ProcessingResult actual = create();
    	ProcessingResult expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ProcessingResult.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static ProcessingResult create()
    {
    	ProcessingResult xml = new ProcessingResult();
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlProcessingResult.initJaxb();
		TestXmlProcessingResult.initFiles();	
		TestXmlProcessingResult test = new TestXmlProcessingResult();
		test.save();
    }
}