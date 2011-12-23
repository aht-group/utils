package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.navigation.TestXmlNavigation;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlView extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"view.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	View actual = create();
    	View expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), View.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static View create(){return create(true);}
    public static View create(boolean withChilds)
    {
    	View xml = new View();
    	xml.setCode("myCode");
    	xml.setIndex(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setActions(TestXmlActions.create(false));
    		xml.setNavigation(TestXmlNavigation.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlView.initFiles();	
		TestXmlView test = new TestXmlView();
		test.save();
    }
}