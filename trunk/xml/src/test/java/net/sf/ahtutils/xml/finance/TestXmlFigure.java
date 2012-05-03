package net.sf.ahtutils.xml.finance;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFigure extends AbstractXmlFinanceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFigure.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"figure.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Figure actual = create(true);
    	Figure expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Figure.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Figure create(boolean withChilds)
    {
    	Figure xml = new Figure();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	xml.setValue(567.89);
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlFigure.initPrefixMapper();
		TestXmlFigure.initFiles();	
		TestXmlFigure test = new TestXmlFigure();
		test.save();
    }
}