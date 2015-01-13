package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlActions extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlActions.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Actions.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Actions actual = create(true);
    	Actions expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Actions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Actions create(boolean withChilds)
    {
    	Actions xml = new Actions();
    	
    	if(withChilds)
    	{
    		xml.getAction().add(TestXmlAction.create(false));
    		xml.getAction().add(TestXmlAction.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlActions.initFiles();	
		TestXmlActions test = new TestXmlActions();
		test.save();
    }
}