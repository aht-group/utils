package net.sf.ahtutils.xml.finance;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTime extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTime.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Time.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Time actual = create(true);
    	Time expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Time.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Time create(boolean withChilds)
    {
    	Time xml = new Time();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		TestXmlTime.initFiles();	
		TestXmlTime test = new TestXmlTime();
		test.save();
    }
}