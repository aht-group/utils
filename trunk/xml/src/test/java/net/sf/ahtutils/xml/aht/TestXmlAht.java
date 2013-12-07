package net.sf.ahtutils.xml.aht;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.finance.TestXmlCurrency;
import net.sf.ahtutils.xml.project.TestXmlUser;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAht extends AbstractXmlAhtTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAht.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Aht.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Aht actual = create(true);
    	Aht expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Aht.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Aht create(boolean withChilds)
    {
    	Aht xml = new Aht();
        	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getCurrency().add(TestXmlCurrency.create(false));xml.getCurrency().add(TestXmlCurrency.create(false));
    		xml.getUser().add(TestXmlUser.create(false));xml.getUser().add(TestXmlUser.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlAht.initJaxb();
		TestXmlAht.initFiles();	
		TestXmlAht test = new TestXmlAht();
		test.save();
    }
}