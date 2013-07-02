package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLink extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLink.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"link.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Link actual = createLink(true);
    	Link expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Link.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Link createLink(boolean withChilds)
    {
    	Link xml = new Link();

    	xml.setId(123);
    	xml.setRefId(345);
    	xml.setType("myType");
    	xml.setCode("myCode");
    	xml.setUrl("myUrl");
    	    	
    	return xml;
    }
    
    public void save() {save(createLink(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlLink.initJaxb();
		TestXmlLink.initFiles();	
		TestXmlLink test = new TestXmlLink();
		test.save();
    }
}