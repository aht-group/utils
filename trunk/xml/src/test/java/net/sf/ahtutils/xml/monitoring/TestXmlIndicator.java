package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlIndicator extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlIndicator.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Indicator.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Indicator actual = create(true);
    	Indicator expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Indicator.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Indicator create(boolean withChilds)
    {
    	Indicator xml = new Indicator();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.getDataSet().add(TestXmlDataSet.create(false));xml.getDataSet().add(TestXmlDataSet.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlIndicator.initJaxb();
		TestXmlIndicator.initFiles();	
		TestXmlIndicator test = new TestXmlIndicator();
		test.save();
    }
}