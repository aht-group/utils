package net.sf.ahtutils.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlActor extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlActor.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix,Actor.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Actor actual = create(false);
    	Actor expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Actor.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Actor create(boolean withChilds)
    {
    	Actor xml = new Actor();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setUri("myUri");
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(false),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlActor.initJaxb();
		TestXmlActor.initFiles();	
		TestXmlActor test = new TestXmlActor();
		test.save();
    }
}