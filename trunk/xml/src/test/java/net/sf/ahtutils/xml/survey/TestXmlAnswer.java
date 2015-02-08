package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAnswer extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAnswer.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Answer.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Answer actual = create(true);
    	Answer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Answer.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Answer create(boolean withChilds)
    {
    	Answer xml = new Answer();
    	xml.setId(123);
    	xml.setValueBoolean(true);
    	xml.setValueNumber(123);
    	
    	if(withChilds)
    	{
    		xml.setData(TestXmlData.create(false));
    		xml.setQuestion(TestXmlQuestion.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlAnswer.initJaxb();
		TestXmlAnswer.initFiles();	
		TestXmlAnswer test = new TestXmlAnswer();
		test.save();
    }
}