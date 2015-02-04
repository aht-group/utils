package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.text.TestXmlRemark;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSection.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Section.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Section actual = create(true);
    	Section expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Section.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Section create(boolean withChilds)
    {
    	Section xml = new Section();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	
    	if(withChilds)
    	{
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		
    		xml.getQuestion().add(TestXmlQuestion.create(false));xml.getQuestion().add(TestXmlQuestion.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlSection.initJaxb();
		TestXmlSection.initFiles();	
		TestXmlSection test = new TestXmlSection();
		test.save();
    }
}