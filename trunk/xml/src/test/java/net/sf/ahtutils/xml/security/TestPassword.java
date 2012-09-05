package net.sf.ahtutils.xml.security;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.report.Media;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPassword extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPassword.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Password.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testMedia() throws FileNotFoundException
    {
    	Password test = create();
    	Media ref = (Media)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Media.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Password create()
    {
    	Password xml = new Password();

    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestPassword.initPrefixMapper();
		TestPassword.initFiles();	
		TestPassword test = new TestPassword();
		test.save();
    }
}