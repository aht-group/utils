package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlProjectRoleCategory extends AbstractXmlAccessTest
{
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"projectRoleCategory.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	ProjectRoleCategory actual = create();
    	ProjectRoleCategory expected = (ProjectRoleCategory)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ProjectRoleCategory.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static ProjectRoleCategory create(){return create(true);}
    public static ProjectRoleCategory create(boolean withChilds)
    {
    	ProjectRoleCategory xml = new ProjectRoleCategory();
    	xml.setCode("myCode");
    	xml.setIndex(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setProjectRoles(TestXmlProjectRoles.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlProjectRoleCategory.initFiles();	
		TestXmlProjectRoleCategory test = new TestXmlProjectRoleCategory();
		test.save();
    }
}