package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCategory extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategory.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Category.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Category test = create(true);
    	Category ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Category.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Category create(boolean withChilds)
    {
    	Category xml = new Category();
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setRoles(TestXmlRoles.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlCategory.initJaxb();
		TestXmlCategory.initFiles();	
		TestXmlCategory test = new TestXmlCategory();
		test.save();
    }
}