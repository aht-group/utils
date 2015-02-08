package net.sf.ahtutils.xml.survey;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCorrelation extends AbstractXmlSurveyTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCorrelation.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Correlation.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Correlation actual = create(true);
    	Correlation expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Correlation.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Correlation create(boolean withChilds)
    {
    	Correlation xml = new Correlation();
    	xml.setId(123);
    	xml.setType("myType");
    	if(withChilds)
    	{    		
    		xml.getCorrelation().add(TestXmlCorrelation.create(false));
    		xml.getCorrelation().add(TestXmlCorrelation.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlCorrelation.initJaxb();
		TestXmlCorrelation.initFiles();	
		TestXmlCorrelation test = new TestXmlCorrelation();
		test.save();
    }
}