package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlResult extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Result.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Result actual = create(true);
    	Result expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Result.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Result create(boolean withChilds)
    {
    	Result xml = new Result();
    	xml.setId(123);
    	xml.setRecord(TestXmlResult.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setActual(TestXmlActual.create());
    		xml.setComment(TestXmlComment.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlResult.initJaxb();
		TestXmlResult.initFiles();	
		TestXmlResult test = new TestXmlResult();
		test.save();
    }
}