package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlData extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Data.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Data actual = create(true);
    	Data expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Data.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Data create(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setId(123);
    	
    	if(withChilds)
    	{    		
    		xml.setSurvey(TestXmlSurvey.create(false));
    		xml.setCorrelation(TestXmlCorrelation.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlData.initJaxb();
		TestXmlData.initFiles();	
		TestXmlData test = new TestXmlData();
		test.save();
    }
}