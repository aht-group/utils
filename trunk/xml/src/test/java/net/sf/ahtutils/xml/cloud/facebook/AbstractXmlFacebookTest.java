package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractXmlFacebookTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlFacebookTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/cloud/facebook";
	protected static File fXml;
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestXmlApp.initFiles();
		TestXmlApp tA = new TestXmlApp();
		tA.save();	
			
		TestXmlSignedRequest.initFiles();
		TestXmlSignedRequest tSr = new TestXmlSignedRequest();
		tSr.save();
		
		TestXmlUser.initFiles();
		TestXmlUser tU = new TestXmlUser();
		tU.save();
		
		TestXmlToken.initFiles();
		TestXmlToken tT = new TestXmlToken();
		tT.save();
		
		TestXmlOauth.initFiles();
		TestXmlOauth tO = new TestXmlOauth();
		tO.save();
    }
}