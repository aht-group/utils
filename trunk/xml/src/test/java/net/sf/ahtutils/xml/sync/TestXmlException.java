package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlException extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlException.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Exception.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Exception actual = create(true);
    	Exception expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Exception.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Exception create(boolean withChilds)
    {
    	Exception xml = new Exception();
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setType("myType");
    	xml.setClassName("myClassName");
    	xml.setLine(123);
    	xml.setMessage("myDescription");

    	if(withChilds)
    	{
    		xml.setException(TestXmlException.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlException.initJaxb();
		TestXmlException.initFiles();	
		TestXmlException test = new TestXmlException();
		test.save();
    }
}