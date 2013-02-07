package net.sf.ahtutils.xml.utils;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.access.TestXmlCategory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils extends AbstractXmlUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestUtils.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Utils.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Utils actual = create(true);
    	Utils expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Utils.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Utils create(boolean withChilds)
    {
    	Utils xml = new Utils();
        	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestUtils.initPrefixMapper();
		TestUtils.initFiles();	
		TestUtils test = new TestUtils();
		test.save();
    }
}