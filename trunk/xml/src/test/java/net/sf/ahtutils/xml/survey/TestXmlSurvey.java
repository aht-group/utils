package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSurvey extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSurvey.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Survey.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Survey actual = create(true);
    	Survey expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Survey.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Survey create(boolean withChilds)
    {
    	Survey xml = new Survey();
    	xml.setId(123);
    	xml.setName("myName");
    	xml.setValidFrom(AbstractXmlSurveyTest.getDefaultXmlDate());
    	xml.setValidTo(AbstractXmlSurveyTest.getDefaultXmlDate());
    	
    	if(withChilds)
    	{    		
    		xml.setTemplate(TestXmlTemplate.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    		
    		xml.getData().add(TestXmlData.create(false));xml.getData().add(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlSurvey.initJaxb();
		TestXmlSurvey.initFiles();	
		TestXmlSurvey test = new TestXmlSurvey();
		test.save();
    }
}