package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlRoles extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlRoles.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"roles.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Roles actual = create();
    	Roles expected = (Roles)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Roles.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Roles create(){return create(true);}
    public static Roles create(boolean withChilds)
    {
    	Roles xml = new Roles();
    	
    	if(withChilds)
    	{
    		xml.getRole().add(TestXmlRole.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlRoles.initFiles();	
		TestXmlRoles test = new TestXmlRoles();
		test.save();
    }
}