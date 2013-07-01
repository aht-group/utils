package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.mail.AbstractXmlMailTest;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMonitoring extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMonitoring.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Monitoring.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Monitoring actual = create(false);
    	Monitoring expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Monitoring.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Monitoring create(boolean withChilds)
    {
    	Monitoring xml = new Monitoring();
    	xml.setId(123);
    	
    	if(withChilds)
    	{

    	}
    	
    	return xml;
    }
    
    public void save() {save(create(false),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlMonitoring.initPrefixMapper();
		TestXmlMonitoring.initFiles();	
		TestXmlMonitoring test = new TestXmlMonitoring();
		test.save();
    }
}