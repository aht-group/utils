package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.cloud.facebook.User;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlUser extends AbstractXmlFacebookTest
{
	static Log logger = LogFactory.getLog(TestXmlUser.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"user.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	User actual = create();
    	User expected = (User)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static User create(){return create(false);}
    public static User create(boolean withChilds)
    {
    	User xml = new User();
    	xml.setId("1");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestXmlUser.initFiles();
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}