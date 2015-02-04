package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTemplates extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplates.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Templates.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Templates actual = create(true);
    	Templates expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Templates.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Templates create(boolean withChilds)
    {
    	Templates xml = new Templates();
    	
    	if(withChilds)
    	{    		
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlTemplates.initJaxb();
		TestXmlTemplates.initFiles();	
		TestXmlTemplates test = new TestXmlTemplates();
		test.save();
    }
}