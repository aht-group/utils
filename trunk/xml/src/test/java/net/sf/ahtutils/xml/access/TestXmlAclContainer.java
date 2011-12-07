package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAclContainer extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAclContainer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"aclContainer.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	AclContainer actual = create();
    	AclContainer expected = (AclContainer)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), AclContainer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static AclContainer create(){return create(true);}
    public static AclContainer create(boolean withChilds)
    {
    	AclContainer xml = new AclContainer();
    	
    	if(withChilds)
    	{
    		xml.getUsecaseCategory().add(TestXmlUsecaseCategory.create(false));
    		xml.getRoleAutoAssign().add(TestXmlRoleAutoAssign.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlAclContainer.initFiles();	
		TestXmlAclContainer test = new TestXmlAclContainer();
		test.save();
    }
}