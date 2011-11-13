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

public class TestXmlToken extends AbstractXmlFacebookTest
{
	static Log logger = LogFactory.getLog(TestXmlToken.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"token.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Token actual = create();
    	Token expected = (Token)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Token.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Token create(){return create(false);}
    public static Token create(boolean withChilds)
    {
    	Token xml = new Token();
    	xml.setCode("myCode");
    	xml.setValue("myValue");
    	xml.setExpires(getXmlDefaultDate());
    	xml.setExpiresIn(10);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestXmlToken.initFiles();
		TestXmlToken test = new TestXmlToken();
		test.save();
    }
}