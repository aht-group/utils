package net.sf.ahtutils.xml.text;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHint extends AbstractXmlTextTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHint.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Hint.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Hint actual = create();
    	Hint expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Hint.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Hint create(){return create(true);}
    public static Hint create(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Hint create(boolean withChilds, String key, String description)
    {
    	Hint xml = new Hint();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlHint.initFiles();	
		TestXmlHint test = new TestXmlHint();
		test.save();
    }
}