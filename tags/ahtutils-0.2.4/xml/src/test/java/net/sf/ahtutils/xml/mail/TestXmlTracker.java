package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTracker extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTracker.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"tracker.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Tracker actual = create();
    	Tracker expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tracker.class);
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
		UtilsXmlTstBootstrap.init();
			
		TestXmlTracker.initPrefixMapper();
		TestXmlTracker.initFiles();	
		TestXmlTracker test = new TestXmlTracker();
		test.save();
    }
}