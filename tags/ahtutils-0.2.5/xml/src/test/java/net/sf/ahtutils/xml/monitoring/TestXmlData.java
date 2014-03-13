package net.sf.ahtutils.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlData extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Data.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Data actual = create(true);
    	Data expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Data.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Data create(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123);
    	xml.setRange(1000);
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setIndicator(TestXmlIndicator.create(false));
    		xml.setObserver(TestXmlObserver.create(false));
    		xml.getValue().add(TestXmlValue.create());xml.getValue().add(TestXmlValue.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlData.initJaxb();
		TestXmlData.initFiles();	
		TestXmlData test = new TestXmlData();
		test.save();
    }
}