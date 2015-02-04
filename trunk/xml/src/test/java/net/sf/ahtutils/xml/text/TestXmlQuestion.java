package net.sf.ahtutils.xml.text;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuestion extends AbstractXmlTextTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuestion.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Question.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Question actual = create();
    	Question expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Question.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Question create(){return create(true);}
    public static Question create(boolean withChilds){return create(withChilds,"myKey","myValue");}
    public static Question create(boolean withChilds, String key, String description)
    {
    	Question xml = new Question();
    	xml.setVersion(1);
    	xml.setKey(key);
    	xml.setValue(description);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlQuestion.initFiles();	
		TestXmlQuestion test = new TestXmlQuestion();
		test.save();
    }
}