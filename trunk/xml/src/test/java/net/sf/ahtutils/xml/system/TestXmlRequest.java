package net.sf.ahtutils.xml.system;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRequest extends AbstractXmlSystemTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRequest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Request.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Request actual = create(true);
    	Request expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Request.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Request create(boolean withChilds)
    {
    	Request xml = new Request();
    	xml.setCounter(123);
    	
    	if(withChilds)
    	{
    		xml.setType(TestXmlType.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlRequest.initJaxb();
		TestXmlRequest.initFiles();	
		TestXmlRequest test = new TestXmlRequest();
		test.save();
    }
}