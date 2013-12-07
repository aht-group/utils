package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTo extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"to.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	To actual = create();
    	To expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), To.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static To create() {return create(true);}
    public static To create(boolean withChilds)
    {
    	To xml = new To();

    	if(withChilds)
    	{
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTo.initJaxb();
		TestXmlTo.initFiles();	
		TestXmlTo test = new TestXmlTo();
		test.save();
    }
}