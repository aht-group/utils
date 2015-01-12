package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSecurity extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSecurity.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Security.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Security test = create(true);
    	Security ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Security.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Security create(boolean withChilds)
    {
    	Security xml = new Security();
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlSecurity.initJaxb();
		TestXmlSecurity.initFiles();	
		TestXmlSecurity test = new TestXmlSecurity();
		test.save();
    }
}