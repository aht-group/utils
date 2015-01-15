package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViews extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViews.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Views.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Views actual = create(true);
    	Views expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Views.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Views create(boolean withChilds)
    {
    	Views xml = new Views();

    	
    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));xml.getView().add(TestXmlView.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlViews.initFiles();	
		TestXmlViews test = new TestXmlViews();
		test.save();
    }
}