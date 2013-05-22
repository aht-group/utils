package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMails extends AbstractXmlMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMails.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"mails.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Mails actual = create();
    	Mails expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Mails.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Mails create() {return create(true);}
    public static Mails create(boolean withChilds)
    {
    	Mails xml = new Mails();
    	xml.setDir("myDir");
    	
    	if(withChilds)
    	{
    		xml.getMail().add(TestXmlMail.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlMails.initPrefixMapper();
		TestXmlMails.initFiles();	
		TestXmlMails test = new TestXmlMails();
		test.save();
    }
}