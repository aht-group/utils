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

public class TestXmlInfo extends AbstractXmlSystemTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlInfo.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Info.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Info actual = create(true);
    	Info expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Info.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Info create(boolean withChilds)
    {
    	Info xml = new Info();

    	if(withChilds)
    	{
    		xml.getRequest().add(TestXmlRequest.create(false));xml.getRequest().add(TestXmlRequest.create(false));
    		xml.getUptime().add(TestXmlUptime.create(false));xml.getUptime().add(TestXmlUptime.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlInfo.initJaxb();
		TestXmlInfo.initFiles();	
		TestXmlInfo test = new TestXmlInfo();
		test.save();
    }
}