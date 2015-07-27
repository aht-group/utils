package net.sf.ahtutils.xml.symbol;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStyle;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlSymbol extends AbstractXmlSymbolTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSymbol.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Symbol.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Symbol actual = create(true);
    	Symbol expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Symbol.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Symbol create(boolean withChilds)
    {
    	Symbol xml = new Symbol();
    	xml.setId(123);
    	xml.setSize(10);
    	xml.setSizeBorder(1);
    	xml.setColor("AABBCC");
    	xml.setColorBorder("DDEEFF");
    	
    	if(withChilds)
    	{
    		xml.setStyle(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlSymbol.initFiles();
		TestXmlSymbol test = new TestXmlSymbol();
		test.save();
    }
}