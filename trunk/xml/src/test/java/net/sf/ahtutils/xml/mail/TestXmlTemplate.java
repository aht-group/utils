package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTemplate extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"template.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Template actual = create();
    	Template expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Template.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Template create() {return create(true);}
    public static Template create(boolean withChilds){return create("myLang", "myType",withChilds);}
    public static Template create(String lang, String type, boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setFile("myFile");
    	xml.setLang(lang);
    	xml.setType(type);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTemplate.initJaxb();
		TestXmlTemplate.initFiles();	
		TestXmlTemplate test = new TestXmlTemplate();
		test.save();
    }
}