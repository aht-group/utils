package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSurveys extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSurveys.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Surveys.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Surveys actual = create(true);
    	Surveys expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Surveys.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Surveys create(boolean withChilds)
    {
    	Surveys xml = new Surveys();
    	
    	if(withChilds)
    	{    		
    		xml.getSurvey().add(TestXmlSurvey.create(false));
    		xml.getSurvey().add(TestXmlSurvey.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlSurveys.initJaxb();
		TestXmlSurveys.initFiles();	
		TestXmlSurveys test = new TestXmlSurveys();
		test.save();
    }
}