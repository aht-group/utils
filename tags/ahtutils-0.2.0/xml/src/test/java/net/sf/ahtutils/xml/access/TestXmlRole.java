package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlRole extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlRole.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"role.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Role actual = create();
    	Role expected = (Role)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Role.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Role create(){return create(true);}
    public static Role create(boolean withChilds)
    {
    	Role xml = new Role();
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setUsecases(TestXmlUsecases.create(false));
    		xml.setRoles(TestXmlRoles.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlRole.initFiles();	
		TestXmlRole test = new TestXmlRole();
		test.save();
    }
}