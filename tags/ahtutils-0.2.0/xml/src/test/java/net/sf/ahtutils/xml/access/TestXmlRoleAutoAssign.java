package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlRoleAutoAssign extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlRoleAutoAssign.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"roleAutoAssign.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	RoleAutoAssign actual = create();
    	RoleAutoAssign expected = (RoleAutoAssign)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), RoleAutoAssign.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static RoleAutoAssign create(){return create(true);}
    public static RoleAutoAssign create(boolean withChilds)
    {
    	RoleAutoAssign xml = new RoleAutoAssign();
    	xml.setCode("myCode");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlRoleAutoAssign.initFiles();	
		TestXmlRoleAutoAssign test = new TestXmlRoleAutoAssign();
		test.save();
    }
}