package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlUnit;
import net.sf.ahtutils.xml.text.TestXmlRemark;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuestion extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuestion.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Question.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Question actual = create(true);
    	Question expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Question.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Question create(boolean withChilds)
    {
    	Question xml = new Question();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	
    	if(withChilds)
    	{
    		xml.setUnit(TestXmlUnit.create(false));
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlQuestion.initJaxb();
		TestXmlQuestion.initFiles();	
		TestXmlQuestion test = new TestXmlQuestion();
		test.save();
    }
}