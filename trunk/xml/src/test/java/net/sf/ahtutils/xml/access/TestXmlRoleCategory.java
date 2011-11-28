package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRoleCategory extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRoleCategory.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"roleCategory.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	RoleCategory actual = create();
    	RoleCategory expected = (RoleCategory)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), RoleCategory.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static RoleCategory create(){return create(true);}
    public static RoleCategory create(boolean withChilds)
    {
    	RoleCategory xml = new RoleCategory();
    	xml.setCode("myCode");
    	xml.setIndex(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
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
			
		TestXmlRoleCategory.initFiles();	
		TestXmlRoleCategory test = new TestXmlRoleCategory();
		test.save();
    }
}