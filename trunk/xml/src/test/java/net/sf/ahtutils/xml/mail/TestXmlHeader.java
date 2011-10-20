package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlHeader extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlHeader.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"db.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Header actual = createHeader();
    	Header expected = (Header)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Header.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Header createHeader() {return createHeader(true);}
    public static Header createHeader(boolean withChilds)
    {
    	Header xml = new Header();
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(createHeader(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlHeader.initPrefixMapper();
		TestXmlHeader.initFiles();	
		TestXmlHeader test = new TestXmlHeader();
		test.save();
    }
}