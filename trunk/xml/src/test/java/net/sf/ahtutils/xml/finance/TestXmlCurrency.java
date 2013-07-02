package net.sf.ahtutils.xml.finance;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCurrency extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCurrency.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Currency.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Currency actual = create(true);
    	Currency expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Currency.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Currency create(boolean withChilds)
    {
    	Currency xml = new Currency();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlCurrency.initJaxb();
		TestXmlCurrency.initFiles();	
		TestXmlCurrency test = new TestXmlCurrency();
		test.save();
    }
}