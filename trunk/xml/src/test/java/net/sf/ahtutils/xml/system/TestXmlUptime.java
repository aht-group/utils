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

public class TestXmlUptime extends AbstractXmlSystemTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUptime.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Uptime.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Uptime actual = create(true);
    	Uptime expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Uptime.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Uptime create(boolean withChilds)
    {
    	Uptime xml = new Uptime();
    	xml.setSince(TestXmlUptime.getDefaultXmlDate());
    	
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
		
		TestXmlUptime.initJaxb();
		TestXmlUptime.initFiles();	
		TestXmlUptime test = new TestXmlUptime();
		test.save();
    }
}