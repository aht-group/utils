package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCc extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCc.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cc.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Cc actual = create();
    	Cc expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Cc.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Cc create() {return create(true);}
    public static Cc create(boolean withChilds)
    {
    	Cc xml = new Cc();

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
			
		TestXmlCc.initJaxb();
		TestXmlCc.initFiles();	
		TestXmlCc test = new TestXmlCc();
		test.save();
    }
}