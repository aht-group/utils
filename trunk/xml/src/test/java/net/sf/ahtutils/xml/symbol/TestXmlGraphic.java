package net.sf.ahtutils.xml.symbol;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.File;

public class TestXmlGraphic extends AbstractXmlSymbolTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGraphic.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Graphic.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Graphic actual = create(true);
    	Graphic expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Graphic.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Graphic create(boolean withChilds)
    {
    	Graphic xml = new Graphic();
    	xml.setId(123);
    	
    	if(withChilds)
    	{
    		xml.setFile(new File());
    		xml.setType(TestXmlType.create(false));
    		xml.setSymbol(TestXmlSymbol.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlGraphic.initFiles();
		TestXmlGraphic test = new TestXmlGraphic();
		test.save();
    }
}