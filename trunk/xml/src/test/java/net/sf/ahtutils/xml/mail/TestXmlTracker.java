package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlTracker extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlTracker.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"tracker.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Tracker actual = create();
    	Tracker expected = (Tracker)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tracker.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Tracker create() {return create(true);}
    public static Tracker create(boolean withChilds)
    {
    	Tracker xml = new Tracker();
    	xml.setId(123);
    	xml.setRefId(345);
    	xml.setType("myType");
    	xml.setCreated(getXmlDefaultDate());
    	xml.setSent(getXmlDefaultDate());
    	xml.setRetryCounter(1);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlTracker.initPrefixMapper();
		TestXmlTracker.initFiles();	
		TestXmlTracker test = new TestXmlTracker();
		test.save();
    }
}