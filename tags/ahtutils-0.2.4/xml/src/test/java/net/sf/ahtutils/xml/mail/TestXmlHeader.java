package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHeader extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHeader.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"header.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Header actual = createHeader();
    	Header expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Header.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Header createHeader() {return createHeader(true);}
    public static Header createHeader(boolean withChilds)
    {
    	Header xml = new Header();
    	xml.setSubject("mySubject");
    	
    	if(withChilds)
    	{
    		xml.setFrom(TestXmlFrom.createFrom(false));
    		xml.setTo(TestXmlTo.create(false));
    		xml.setCc(TestXmlCc.create(false));
    		xml.setBcc(TestXmlBcc.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(createHeader(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlHeader.initPrefixMapper();
		TestXmlHeader.initFiles();	
		TestXmlHeader test = new TestXmlHeader();
		test.save();
    }
}