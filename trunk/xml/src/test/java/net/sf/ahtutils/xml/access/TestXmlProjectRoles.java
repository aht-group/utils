package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlProjectRoles extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlProjectRoles.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"projectRoles.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	ProjectRoles actual = create();
    	ProjectRoles expected = (ProjectRoles)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ProjectRoles.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static ProjectRoles create(){return create(true);}
    public static ProjectRoles create(boolean withChilds)
    {
    	ProjectRoles xml = new ProjectRoles();
    	
    	if(withChilds)
    	{
    		xml.getProjectRole().add(TestXmlProjectRole.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlProjectRoles.initFiles();	
		TestXmlProjectRoles test = new TestXmlProjectRoles();
		test.save();
    }
}