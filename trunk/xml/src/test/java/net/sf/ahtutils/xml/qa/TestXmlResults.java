package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlResults extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResults.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Results.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Results actual = create(true);
    	Results expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Results.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Results create(boolean withChilds)
    {
    	Results xml = new Results();
    	
    	if(withChilds)
    	{
    		xml.getResult().add(TestXmlResult.create(false));xml.getResult().add(TestXmlResult.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlResults.initJaxb();
		TestXmlResults.initFiles();	
		TestXmlResults test = new TestXmlResults();
		test.save();
    }
}