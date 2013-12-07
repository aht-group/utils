package net.sf.ahtutils.xml.finance;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFigures extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFigures.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"figures");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Figures actual = create(true);
    	Figures expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Figures.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Figures create(boolean withChilds)
    {
    	Figures xml = new Figures();

    	if(withChilds)
    	{
    		xml.getFinance().add(TestXmlFinance.create(false));
    		xml.getFinance().add(TestXmlFinance.create(false));
    		xml.getTime().add(TestXmlTime.create(false));
    		xml.getTime().add(TestXmlTime.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		AbstractXmlTest.initJaxb();
		
		TestXmlFigures.initFiles();	
		TestXmlFigures test = new TestXmlFigures();
		test.save();
    }
}