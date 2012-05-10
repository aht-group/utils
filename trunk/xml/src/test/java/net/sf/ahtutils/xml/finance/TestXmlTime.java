package net.sf.ahtutils.xml.finance;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
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
		fXml = new File(rootDir,Time.class.getSimpleName()+".xml");
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
    	xml.setRecord(getXmlDefaultDate());
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlTime.initPrefixMapper();
		TestXmlTime.initFiles();	
		TestXmlTime test = new TestXmlTime();
		test.save();
    }
}