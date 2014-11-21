package net.sf.ahtutils.xml.text;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRemark extends AbstractXmlTextTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRemark.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Remark.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Remark actual = create();
    	Remark expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Remark.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Remark create(){return create(true);}
    public static Remark create(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Remark create(boolean withChilds, String key, String description)
    {
    	Remark xml = new Remark();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlRemark.initFiles();	
		TestXmlRemark test = new TestXmlRemark();
		test.save();
    }
}