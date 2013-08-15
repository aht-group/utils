package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlObserver extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlObserver.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Observer.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Observer actual = create(false);
    	Observer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Observer.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Observer create(boolean withChilds)
    {
    	Observer xml = new Observer();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(false),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlObserver.initJaxb();
		TestXmlObserver.initFiles();	
		TestXmlObserver test = new TestXmlObserver();
		test.save();
    }
}