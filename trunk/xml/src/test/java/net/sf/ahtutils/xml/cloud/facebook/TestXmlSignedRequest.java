package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSignedRequest extends AbstractXmlFacebookTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSignedRequest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"signedRequest.xml");
	}
    
    @Test
    public void testApp() throws FileNotFoundException
    {
    	SignedRequest actual = create();
    	SignedRequest expected = (SignedRequest)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), SignedRequest.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private SignedRequest create(){return create(false);}
    public  SignedRequest create(boolean withChilds)
    {
    	SignedRequest xml = new SignedRequest();
    	xml.setExpires(getXmlDefaultDate());
    	xml.setIssuedAt(getXmlDefaultDate());
    	
    	if(withChilds)
    	{
    		xml.setOauth(TestXmlOauth.create(false));
    		xml.setUser(TestXmlUser.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestXmlSignedRequest.initFiles();
		TestXmlSignedRequest test = new TestXmlSignedRequest();
		test.save();
    }
}