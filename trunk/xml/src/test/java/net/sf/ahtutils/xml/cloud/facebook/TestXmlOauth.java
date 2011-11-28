package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOauth extends AbstractXmlFacebookTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlApp.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"oauth.xml");
	}

    @Test
    public void testApp() throws FileNotFoundException
    {
    	Oauth actual = create();
    	Oauth expected = (Oauth)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Oauth.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Oauth create(){return create(true);}
    public static Oauth create(boolean withChilds)
    {
    	Oauth xml = new Oauth();
    	xml.setToken("myToken");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
}