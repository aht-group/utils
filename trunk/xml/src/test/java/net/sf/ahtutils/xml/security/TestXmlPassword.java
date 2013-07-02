package net.sf.ahtutils.xml.security;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPassword extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPassword.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Password.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Password test = create(true);
    	Password ref = (Password)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Password.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Password create(boolean withChilds)
    {
    	Password xml = new Password();
    	xml.setValid(true);
    	if(withChilds)
    	{
    		xml.getRule().add(TestXmlRule.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlPassword.initJaxb();
		TestXmlPassword.initFiles();	
		TestXmlPassword test = new TestXmlPassword();
		test.save();
    }
}