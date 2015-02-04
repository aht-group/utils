package net.sf.ahtutils.xml.aht;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.access.TestXmlRole;
import net.sf.ahtutils.xml.qa.TestXmlTest;
import net.sf.ahtutils.xml.security.TestXmlStaff;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.ahtutils.xml.survey.TestXmlSurvey;
import net.sf.ahtutils.xml.survey.TestXmlTemplate;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlAhtTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"query.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Query actual = create(true);
    	Query expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Query.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Query create(boolean withChilds)
    {
    	Query xml = new Query();
    	xml.setLang("myLang");
    	
    	if(withChilds)
    	{
    		xml.setRole(TestXmlRole.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setType(TestXmlType.create(false));
    		
    		xml.setTest(TestXmlTest.create(false));
    		
    		xml.setStaff(TestXmlStaff.create(false));
    		
    		xml.setTemplate(TestXmlTemplate.create(false));
    		xml.setSurvey(TestXmlSurvey.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlQuery.initJaxb();
		TestXmlQuery.initFiles();	
		TestXmlQuery test = new TestXmlQuery();
		test.save();
    }
}