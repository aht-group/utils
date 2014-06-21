package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlInfo extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(Info.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Info.class);}
    
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
    	xml.setId(123);
    	xml.setRecord(TestXmlInfo.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setComment(TestXmlComment.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlInfo.initJaxb();
		TestXmlInfo.initFiles();	
		TestXmlInfo test = new TestXmlInfo();
		test.save();
    }
}