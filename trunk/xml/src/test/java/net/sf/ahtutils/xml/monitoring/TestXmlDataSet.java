package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataSet extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataSet.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,DataSet.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	DataSet actual = create(true);
    	DataSet expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DataSet.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static DataSet create(boolean withChilds)
    {
    	DataSet xml = new DataSet();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setIndicator(TestXmlIndicator.create(false));
    		xml.getData().add(TestXmlData.create(false));xml.getData().add(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		TestXmlDataSet.initFiles();	
		TestXmlDataSet test = new TestXmlDataSet();
		test.save();
    }
}