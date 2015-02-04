package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlCategory;
import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.ahtutils.xml.text.TestXmlRemark;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTemplate extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Template.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Template actual = create(true);
    	Template expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Template.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Template create(boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setId(123);
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setCategory(TestXmlCategory.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    		
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		
    		xml.getSection().add(TestXmlSection.create(false));xml.getSection().add(TestXmlSection.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlTemplate.initJaxb();
		TestXmlTemplate.initFiles();	
		TestXmlTemplate test = new TestXmlTemplate();
		test.save();
    }
}