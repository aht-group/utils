package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAclContainer extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestAclContainer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"aclContainer.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	AclContainer actual = createAclContainer();
    	AclContainer expected = (AclContainer)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), AclContainer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static AclContainer createAclContainer()
    {
    	AclContainer aclContainer = new AclContainer();
    	return aclContainer;
    }
    
    public void save() {save(createAclContainer(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestAclContainer.initFiles();	
		TestAclContainer test = new TestAclContainer();
		test.save();
    }
}