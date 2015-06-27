package net.sf.ahtutils.xml.finance;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFinance extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFinance.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Finance.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Finance actual = create(true);
    	Finance expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Finance.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Finance create(boolean withChilds)
    {
    	Finance xml = new Finance();
    	xml.setId(123);
    	xml.setNr(1);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	xml.setValue(567.89);
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlFinance.initJaxb();
		TestXmlFinance.initFiles();	
		TestXmlFinance test = new TestXmlFinance();
		test.save();
    }
}