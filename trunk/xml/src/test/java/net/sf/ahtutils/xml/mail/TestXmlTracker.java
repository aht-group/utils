package net.sf.ahtutils.xml.mail;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
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
        setXmlFile(dirSuffix,"tracker");
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
    	xml.setCreated(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setSent(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setRetryCounter(1);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		TestXmlTracker.initFiles();	
		TestXmlTracker test = new TestXmlTracker();
		test.save();
    }
}