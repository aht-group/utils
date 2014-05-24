package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.security.TestXmlStaff;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQa extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQa.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Qa.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Qa actual = create(true);
    	Qa expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Qa.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Qa create(boolean withChilds)
    {
    	Qa xml = new Qa();
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getStaff().add(TestXmlStaff.create(false));xml.getStaff().add(TestXmlStaff.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlQa.initJaxb();
		TestXmlQa.initFiles();	
		TestXmlQa test = new TestXmlQa();
		test.save();
    }
}